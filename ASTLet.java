import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.lang.reflect.Field;

public class ASTLet implements ASTNode {
  List<Bind> decls;
  ASTNode body;

  public IValue eval(Environment<IValue> env) throws InterpreterError {
    int mutCount = 0;
    if (body != null) {
      // let ... in expr;
      Environment<IValue> newEnv = env.beginScope();

      try {
        for (Bind b : decls) {
          IValue v = b.getExp().eval(newEnv);
          if (b.isMut()) {
            VAddress addr = MemoryManager.getInstance().push(v);
            newEnv.assoc(b.getId(), addr);
            mutCount++;
          } else {
            newEnv.assoc(b.getId(), v);
          }
        }
        return body.eval(newEnv);
      } finally {
        // Ensure strict LIFO deallocation for stack variables at the end of scope
        for (int i = 0; i < mutCount; i++) {
          MemoryManager.getInstance().pop();
        }
      }
    } else {
      // let ...;
      for (Bind b : decls) {
        IValue v = b.getExp().eval(env);
        if (b.isMut()) {
          VAddress addr = MemoryManager.getInstance().push(v);
          env.assoc(b.getId(), addr);
        } else {
          env.assoc(b.getId(), v);
        }
      }

      return new VUnit();
    }
  }

  public ASTType typecheck(Environment<ASTType> env) throws TypeError {
    if (body != null) {
      Environment<ASTType> newEnv = env.beginScope();
      boolean hasMut = false;

      // Pre-bind to support recursive functions statically
      for (Bind b : decls) {
        ASTType declaredType = b.getType();
        ASTType prebind = declaredType != null ? declaredType : new TVar(b.getId());
        if (b.isMut()) {
          newEnv.assoc(b.getId(), new TMut(prebind));
        } else {
          newEnv.assoc(b.getId(), prebind);
        }
      }

      for (Bind b : decls) {
        ASTType expType = b.getExp().typecheck(newEnv);
        ASTType declaredType = b.getType(); // Assuming Bind exposes the parsed type
        if (declaredType != null && !expType.isSubtypeOf(declaredType)) {
          throw new TypeError("Type mismatch in let binding for " + b.getId());
        }
        ASTType finalType = declaredType != null ? declaredType : expType;
        if (b.isMut()) {
          newEnv.assoc(b.getId(), new TMut(finalType));
          hasMut = true;
        } else {
          newEnv.assoc(b.getId(), finalType);
        }
      }

      ASTType retType = body.typecheck(newEnv);

      if (hasMut && (retType instanceof TRef || retType instanceof TMut)) {
        throw new TypeError("Memory Leak Violation: block returns a stack reference out of its scope.");
      }
      return retType;
    } else {
      // let ...; (Adds bindings directly to the current sequential scope)
      // Pre-bind to support recursive functions statically
      for (Bind b : decls) {
        ASTType declaredType = b.getType();
        ASTType prebind = declaredType != null ? declaredType : new TVar(b.getId());
        if (b.isMut()) {
          env.assoc(b.getId(), new TMut(prebind));
        } else {
          env.assoc(b.getId(), prebind);
        }
      }

      for (Bind b : decls) {
        ASTType expType = b.getExp().typecheck(env);
        ASTType declaredType = b.getType();
        if (declaredType != null && !expType.isSubtypeOf(declaredType)) {
          throw new TypeError("Type mismatch in let binding for " + b.getId());
        }
        ASTType finalType = declaredType != null ? declaredType : expType;
        if (b.isMut()) {
          env.assoc(b.getId(), new TMut(finalType));
        } else {
          env.assoc(b.getId(), finalType);
        }
      }
      return new TUnit();
    }
  }

  public ASTLet(List<Bind> decls, ASTNode b) {
    this.decls = decls;
    body = b;

    // --- SAFE RECURSION STATIC CHECK ---
    Set<String> declaredVars = new HashSet<>();
    for (Bind bind : decls) {
      declaredVars.add(bind.getId());
    }

    // 1. Build a dependency graph mapping each binding to the variables it directly accesses
    Map<String, Set<String>> graph = new HashMap<>();
    for (Bind bind : decls) {
      Set<String> deps = new HashSet<>();
      collectUnshieldedDeps(bind.getExp(), declaredVars, deps);
      graph.put(bind.getId(), deps);
    }

    // 2. Perform cycle detection using Depth-First Search
    Set<String> visited = new HashSet<>();
    Set<String> stack = new HashSet<>();
    for (String node : graph.keySet()) {
      if (hasCycle(node, graph, visited, stack)) {
        throw new RuntimeException("Static Error: Recursion unsafe. Identifier '" + node +
            "' is directly accessible from itself via the expressions (not shielded by a function abstraction).");
      }
    }
  }

  private boolean hasCycle(String node, Map<String, Set<String>> graph, Set<String> visited, Set<String> stack) {
    if (stack.contains(node)) return true;
    if (visited.contains(node)) return false;
    
    visited.add(node);
    stack.add(node);
    
    Set<String> neighbors = graph.get(node);
    if (neighbors != null) {
      for (String neighbor : neighbors) {
        if (hasCycle(neighbor, graph, visited, stack)) return true;
      }
    }
    
    stack.remove(node);
    return false;
  }

  private void collectUnshieldedDeps(ASTNode node, Set<String> targets, Set<String> deps) {
    if (node == null || targets.isEmpty()) return;
    String name = node.getClass().getSimpleName();

    // Rule: Function abstractions safely shield their bodies
    if (name.equals("ASTFun")) return;

    // Rule: If it's an Identifier, check if it matches one of our block targets
    if (name.equals("ASTId")) {
      try {
        for (Field f : node.getClass().getDeclaredFields()) {
          if (f.getType() == String.class) {
            f.setAccessible(true);
            String id = (String) f.get(node);
            if (targets.contains(id)) deps.add(id);
          }
        }
      } catch (Exception e) {}
      return;
    }

    // Rule: Recursively search all structural AST children (e.g. inside ASTPlus, ASTWhile, ASTMatch, etc.)
    try {
      for (Field f : node.getClass().getDeclaredFields()) {
        f.setAccessible(true);
        Object val = f.get(node);
        if (val instanceof ASTNode) {
          collectUnshieldedDeps((ASTNode) val, targets, deps);
        } else if (val instanceof Iterable) {
          for (Object elem : (Iterable<?>) val) {
            if (elem instanceof ASTNode) collectUnshieldedDeps((ASTNode) elem, targets, deps);
          }
        }
      }
    } catch (Exception e) {}
  }
}

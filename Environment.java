import java.util.*;

public class Environment<E> {
  Environment<E> parent;
  Map<String, E> bindings;

  Environment() {
    parent = null;
    bindings = new HashMap<String, E>();
  }

  Environment(Environment<E> parentEnv) {
    parent = parentEnv;
    bindings = new HashMap<String, E>();
  }

  Environment<E> beginScope() {
    return new Environment<E>(this);
  }

  Environment<E> endScope() {
    return parent;
  }

  void assoc(String id, E bind) {
    bindings.put(id, bind);
  }

  E find(String id) throws InterpreterError {
    if (bindings.containsKey(id)) {
      return bindings.get(id);
    }
    if (parent != null) {
      return parent.find(id);
    }
    throw new InterpreterError("No value associated with binding '" + id + "'");
  }
}

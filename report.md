# Programming Languages - Project Phase 2 (Rusty 2)
**Report on Static Type Checking and Recursive Types**

## 1. Static Type Checker Architecture

The static type checker is implemented as a pre-execution analysis pass over the Abstract Syntax Tree (AST). The core of this system revolves around the `ASTType` interface and the `Environment<ASTType>` environment mapper.

### 1.1 The `ASTType` Interface
Instead of reusing runtime `IValue` objects, the type system introduces a dedicated `ASTType` interface. Every type in the Rusty 2 language (e.g., `TInt`, `TBool`, `TUnit`, `TMut`, `TCell`, `TEnum`, `TRef`) implements this interface. The interface mandates three critical methods:
*   `isSubtypeOf(ASTType other)`: Determines if the current type can be safely used in place of the target type.
*   `unfold()`: Used extensively for recursive types to expose the underlying structure.
*   `substitute(String varName, ASTType replacement)`: Used to replace generic type variables (`TVar`) with concrete types during unfolding.

### 1.2 The `typecheck` AST Pass
Every AST node was extended with a `public ASTType typecheck(Environment<ASTType> env) throws TypeError` method. Before `eval` is called, the `R0int` main class triggers `typecheck` on the root node. 
*   **Expressions and Primitives:** Nodes like `ASTPlus` evaluate their children and assert they are integers (`TInt`), propagating the type upward.
*   **Scoping:** Nodes like `ASTLet` and `ASTFun` clone the `Environment` (via `beginScope()`), bind their local variables to specific `ASTType`s, and evaluate their bodies in this isolated context.
*   **Permissive Closures:** Because Rusty 2 closures currently lack explicit type annotations on parameters (`|n| { ... }`), the type checker binds these parameters to a generic `TVar`. Arithmetic and relational nodes are designed to safely permit operations against `TVar`s during the static pass, allowing flexible dynamic evaluation while strictly enforcing known types.

### 1.3 L-Values, R-Values, and Implicit Dereferencing (Challenge 2)
To support stack-allocated mutable variables (`mut`), the type checker enforces L-Value and R-Value semantics:
*   **R-Values (`ASTId`)**: When a variable is evaluated statically and resolves to a `TMut`, the `ASTId` node *implicitly dereferences* it by returning the underlying `refType`.
*   **L-Values (`ASTAssign`)**: Assignment operations intercept `ASTId` nodes to retrieve the raw `TMut` (or `TCell`/`TRef`) type, verifying that the right-hand side is a valid subtype of the underlying reference type.

### 1.4 Memory Leak Prevention (Challenge 3)
The type checker ensures that no references to stack-allocated variables outlive the scope they were created in. 
*   Inside `ASTLet` and `ASTFun`, the type checker analyzes the final returned `ASTType` of the body block. 
*   If the block declares any local `mut` variables, and the return type is evaluated to be a `TRef` or `TMut`, the static analyzer intercepts this as an escaping reference and throws a `TypeError: Memory Leak Violation`, safely rejecting the program before execution.

---

## 2. Recursive Types Extension (Challenge 1)

The language supports recursive data structures (like binary trees) through an **equi-recursive** type system. In an equi-recursive system, a recursive type and its unfolded form are considered entirely equivalent and interchangeable.

### 2.1 Implementation of `TRec` and `TVar`
Recursive types are handled using a binding node (`TRec`) and a variable node (`TVar`).
*   `TRec` holds the name of the type variable (e.g., `Btree`) and the body of the type definition (e.g., the `TEnum` containing `#nil` and `#node`).
*   When `isSubtypeOf` is evaluated on a recursive type, `TRec` calls `this.unfold()`.

### 2.2 Unfolding Mechanism
The `unfold()` method calls `substitute()` on its body. When the `substitute` method encounters a `TVar` that matches the bound name, it replaces that `TVar` with the entire parent `TRec` object. This one-step, lazy unfolding effectively resolves recursive definitions on the fly, allowing the type checker to inspect the inner structure of a recursive Enum without infinite loops.

### 2.3 Depth and Width Subtyping (`TEnum`)
Variant unions (Enums) enforce strict type-safety over structural tuples using both Depth and Width subtyping rules:
*   **Width Subtyping**: `TEnum` verifies that the proposed subtype possesses a subset of (or equal to) the variant tags of the target type. (A type with fewer options can safely masquerade as a broader union).
*   **Depth Subtyping**: For each matching tag, the arity (number of fields) must match exactly. The `TEnum` then recursively maps `isSubtypeOf` across every individual field. 
*   Because `ASTType` subtyping implicitly calls `.unfold()` at every step, a `TEnum` wrapped in a `TRec` can be seamlessly evaluated for variant matching against standard structural tuples.

---

## Conclusion

The resulting interpreter achieves full static type verification prior to runtime execution. It successfully differentiates between heap and stack allocations, implements implicit dereferencing, evaluates equi-recursive subtyping gracefully, and most notably, performs static lifetime analysis to prevent dangling pointers and memory leaks.
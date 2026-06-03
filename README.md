# LProg Language Interpreter

This project is an interpreter for a small, custom-designed programming language. It is implemented in Java, using the JavaCC parser generator to handle lexical analysis and parsing.

The language supports a blend of functional and imperative programming paradigms and includes features for static analysis to catch certain errors before runtime.

## Features

The programming language demonstrates several modern programming language concepts:

*   **Functional Programming (Level 0):**
    *   First-class, higher-order functions.
    *   Closures that capture their environment.
    *   Currying (partial application).
    *   Recursion.
*   **Imperative Programming (Level 1):**
    *   Heap-allocated mutable state via `cell`s.
    *   Stack-allocated mutable variables via `mut`, featuring implicit dereferencing.
    *   Dereferencing (`!`) and assignment (`:=`).
    *   `while` loops.
*   **Data Structures (Level 2):**
    *   Custom struct-like tuples (e.g., `#Add(left, right)`).
    *   Pattern matching with `match`.
    *   Equi-recursive types (e.g., recursive lists or trees) evaluated via structural subtyping.
*   **Safety Features:**
    *   **Static Cycle Detection:** The interpreter performs a static analysis check on `let` bindings to detect and prevent unsafe mutual recursion that is not shielded by a function abstraction.
    *   **Static Type Checking:** A pre-execution analysis pass (`typecheck`) rigorously verifies type safety before any code is run.
    *   **Memory Leak Prevention:** The static analyzer performs escape analysis to prevent dangling pointers, halting execution if local stack references attempt to escape their scope.

## How to Build and Run

### Prerequisites

*   Java Development Kit (JDK)
*   `make` (or manual compilation)

### Building

The project uses JavaCC to generate the parser from the `Parser.jj` grammar file. You can build the project by running the compile shell script:

```sh
sh makeit.sh
```

This should invoke JavaCC to generate the parser files and then `javac` to compile all the Java source files.

### Running the Demos

Once built, you can run the interpreter on any of the provided demo files. The main class is `R0int`.

```sh
java R0int < demos/<demo_file_name>.rs
```

## Demos Explained

The `demos/` directory contains several files that showcase the language's features.

### `demo1_functional.rs`
*   **Concept:** Demonstrates functional programming capabilities like currying, higher-order functions, and recursion.
*   **Run:** `java R0int < demos/demo1_functional.rs`
*   **See:** The results of the functional computations.

### `demo2_imperative.rs`
*   **Concept:** Demonstrates imperative features like mutable state (`cell`) and `while` loops.
*   **Run:** `java R0int < demos/demo2_imperative.rs`
*   **See:** How stateful counters and loops work.

### `demo3_structs.rs`
*   **Concept:** Shows how to use struct-like tuples and pattern matching to evaluate an expression tree.
*   **Run:** `java R0int < demos/demo3_structs.rs`
*   **See:** The result of the tree evaluation.

### `demo4_unsafe_ko.rs`
*   **Concept:** This demo will **not** run. It showcases the static cycle detection that prevents unshielded mutual recursion.
*   **Run:** `java R0int < demos/demo4_unsafe_ko.rs`
*   **See:** The interpreter will throw a `RuntimeException` before any code is executed, identifying the unsafe recursion.

### `demo5_type_ko.rs`
*   **Concept:** This demo will start but then crash. It demonstrates strict runtime type checking.
*   **Run:** `java R0int < demos/demo5_type_ko.rs`
*   **See:** The program will execute until it hits the type violation (assigning to a non-cell), at which point it will crash with an `InterpreterError`.

### `demo6_recursive.rs`
*   **Concept:** Demonstrates recursive types and evaluating trees (Challenge 1).
*   **Run:** `java R0int < demos/demo6_recursive.rs`
*   **See:** The correct evaluation of a recursively defined binary tree.

### `demo7_mut.rs`
*   **Concept:** Demonstrates stack allocated mutable cells and implicit dereferencing (Challenge 2).
*   **Run:** `java R0int < demos/demo7_mut.rs`
*   **See:** Clean imperative loops without garbage collection overhead.

### `demo8_mut_vs_cell.rs`
*   **Concept:** Contrasts stack (`mut`) vs heap (`cell`) allocation (Challenge 2).
*   **Run:** `java R0int < demos/demo8_mut_vs_cell.rs`
*   **See:** The seamless integration of both memory models.

### `demo9_explicit_aliasing.rs`
*   **Concept:** Demonstrates taking explicit references to stack variables (Challenge 3).
*   **Run:** `java R0int < demos/demo9_explicit_aliasing.rs`
*   **See:** Aliasing in action to safely mutate variables across functions.

### `demo10_alias_leak_ko.rs`
*   **Concept:** This demo will **not** type-check. It demonstrates leak prevention (Challenge 3).
*   **Run:** `java R0int < demos/demo10_alias_leak_ko.rs`
*   **See:** The static analyzer rejecting escaping stack-allocated references.

### `rustytestsv2` (or `sample.rs`)
*   **Concept:** A comprehensive test suite provided by the professor. It tests all aspects of the interpreter together, including Church encoding, recursive structures (Lists and Nats), stack vs. heap allocations, and memory leak prevention.
*   **Run:** `java R0int rustytestsv2` (if located in your root directory)
*   **See:** The correct sequential evaluation of nested loops, list pattern matching, and full language feature compliance.
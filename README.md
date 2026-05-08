# LProg Language Interpreter

This project is an interpreter for a small, custom-designed programming language, referred to as "LProg". It is implemented in Java, using the JavaCC parser generator to handle lexical analysis and parsing.

The language supports a blend of functional and imperative programming paradigms and includes features for static analysis to catch certain errors before runtime.

## Features

The LProg language demonstrates several modern programming language concepts:

*   **Functional Programming (Level 0):**
    *   First-class, higher-order functions.
    *   Closures that capture their environment.
    *   Currying (partial application).
    *   Recursion.
*   **Imperative Programming (Level 1):**
    *   Mutable state via `cell`s.
    *   Dereferencing (`!`) and assignment (`:=`).
    *   `while` loops.
*   **Data Structures (Level 2):**
    *   Custom struct-like tuples (e.g., `#Add(left, right)`).
    *   Pattern matching with `match`.
*   **Safety Features:**
    *   **Static Cycle Detection:** The interpreter performs a static analysis check on `let` bindings to detect and prevent unsafe mutual recursion that is not shielded by a function abstraction.
    *   **Runtime Type Safety:** The language is strictly typed at runtime, and operations that violate type rules will cause the interpreter to crash with an error.

## How to Build and Run

### Prerequisites

*   Java Development Kit (JDK)
*   `make` (or manual compilation)

### Building

The project uses JavaCC to generate the parser from the `Parser.jj` grammar file. If you have a `Makefile`, you can likely build the project by running:

```sh
make
```

This should invoke JavaCC to generate the parser files and then `javac` to compile all the Java source files.

### Running the Demos

Once built, you can run the interpreter on any of the provided demo files. The main class is `Parser`.

```sh
java Parser demos/<demo_file_name>.rs
```

## Demos Explained

The `demos/` directory contains several files that showcase the language's features.

### `demo1_functional.rs`
*   **Concept:** Demonstrates functional programming capabilities like currying, higher-order functions, and recursion.
*   **Run:** `java Parser demos/demo1_functional.rs`
*   **See:** The results of the functional computations.

### `demo2_imperative.rs`
*   **Concept:** Demonstrates imperative features like mutable state (`cell`) and `while` loops.
*   **Run:** `java Parser demos/demo2_imperative.rs`
*   **See:** How stateful counters and loops work.

### `demo3_structs.rs`
*   **Concept:** Shows how to use struct-like tuples and pattern matching to evaluate an expression tree.
*   **Run:** `java Parser demos/demo3_structs.rs`
*   **See:** The result of the tree evaluation.

### `demo4_unsafe_ko.rs`
*   **Concept:** This demo will **not** run. It showcases the static cycle detection that prevents unshielded mutual recursion.
*   **Run:** `java Parser demos/demo4_unsafe_ko.rs`
*   **See:** The interpreter will throw a `RuntimeException` before any code is executed, identifying the unsafe recursion.

### `demo5_type_ko.rs`
*   **Concept:** This demo will start but then crash. It demonstrates strict runtime type checking.
*   **Run:** `java Parser demos/demo5_type_ko.rs`
*   **See:** The program will execute until it hits the type violation (assigning to a non-cell), at which point it will crash with an `InterpreterError`.
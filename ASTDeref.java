class ASTDeref implements ASTNode {
  ASTNode expr;

  ASTDeref(ASTNode e) {
    expr = e;
  }

  public IValue eval(Environment<IValue> env) throws InterpreterError {
    IValue v = expr.eval(env);

    if (v instanceof VCell) {
      return ((VCell) v).get();
    } else if (v instanceof VRef) {
      return MemoryManager.getInstance().memrd(((VRef) v).address);
    }

    throw new InterpreterError("Expression is not a cell or explicit reference");
  }

  public ASTType typecheck(Environment<ASTType> env) throws TypeError {
    ASTType exprType = expr.typecheck(env).unfold();
    if (exprType instanceof TCell) {
      return ((TCell) exprType).refType;
    } else if (exprType instanceof TRef) {
      return ((TRef) exprType).refType;
    } else if (exprType instanceof TVar) {
      return exprType; // Permissive fallback for unannotated function parameters
    }
    throw new TypeError("Cannot dereference a non-reference type");
  }
}

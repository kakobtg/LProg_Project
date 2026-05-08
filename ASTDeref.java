class ASTDeref implements ASTNode {
  ASTNode expr;

  ASTDeref(ASTNode e) {
    expr = e;
  }

  public IValue eval(Environment<IValue> env) throws InterpreterError {
    IValue v = expr.eval(env);

    if (!(v instanceof VCell)) {
      throw new InterpreterError("Expression is not a cell");
    }

    return ((VCell) v).get();
  }
}

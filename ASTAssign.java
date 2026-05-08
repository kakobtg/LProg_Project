class ASTAssign implements ASTNode {
  ASTNode left, right;

  ASTAssign(ASTNode l, ASTNode r) {
    left = l;
    right = r;
  }

  public IValue eval(Environment<IValue> env) throws InterpreterError {
    IValue v1 = left.eval(env);

    if (!(v1 instanceof VCell)) {
      throw new InterpreterError("Left side of expression is not a cell");
    }

    IValue val = right.eval(env);

    ((VCell) v1).set(val);

    return new VUnit();
  }
}

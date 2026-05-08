class ASTSeq implements ASTNode {
  ASTNode left, right;

  ASTSeq(ASTNode l, ASTNode r) {
    left = l;
    right = r;
  }

  public IValue eval(Environment<IValue> env) throws InterpreterError {
    left.eval(env);
    return right.eval(env);
  }
}

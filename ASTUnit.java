class ASTUnit implements ASTNode {
  public IValue eval(Environment<IValue> env) throws InterpreterError {
    return new VUnit();
  }
}

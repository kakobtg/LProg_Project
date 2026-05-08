class ASTString implements ASTNode {
  String s;

  ASTString(String s0) {
    s = s0;
  }

  public IValue eval(Environment<IValue> env) throws InterpreterError {
    return new VString(s);
  }
}

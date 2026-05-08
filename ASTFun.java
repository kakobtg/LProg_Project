class ASTFun implements ASTNode {
  String param;
  ASTNode body;

  public ASTFun(String param, ASTNode body) {
    this.param = param;
    this.body = body;
  }

  public void setBody(ASTNode b) {
    body = b;
  }

  public IValue eval(Environment<IValue> env) throws InterpreterError {
    return new VClosure(param, body, env);
  }

}

class VClosure implements IValue {
  String param;
  ASTNode body;
  Environment<IValue> env;

  VClosure(String p, ASTNode b, Environment<IValue> e) {
    this.param = p;
    this.body = b;
    this.env = e;
  }

  public String getParam() {
    return param;
  }

  public ASTNode getBody() {
    return body;
  }

  public Environment<IValue> getEnv() {
    return env;
  }

  public String toStr() {
    return "<closure>";
  }
}

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

  public ASTType typecheck(Environment<ASTType> env) throws TypeError {
    Environment<ASTType> newEnv = env.beginScope();
    newEnv.assoc(param, new TVar(param)); // Assign a generic type to the parameter
    ASTType retType = body.typecheck(newEnv);
    if (retType instanceof TRef || retType instanceof TMut) {
      throw new TypeError("Memory Leak Violation: function returns a stack reference out of its scope.");
    }
    return new TUnit(); // Return TUnit representing the closure abstraction itself
  }
}

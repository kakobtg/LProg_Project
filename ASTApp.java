public class ASTApp implements ASTNode {
  ASTNode func, arg;

  public ASTApp(ASTNode f, ASTNode a) {
    func = f;
    arg = a;
  }

  public IValue eval(Environment<IValue> env) throws InterpreterError {
    IValue v1 = func.eval(env);
    if (!(v1 instanceof VClosure)) {
      throw new InterpreterError("Not a function");
    }

    VClosure clos = (VClosure) v1;

    IValue v2 = arg.eval(env);
    Environment<IValue> functionEnv = clos.getEnv().beginScope();
    functionEnv.assoc(clos.getParam(), v2);

    return clos.getBody().eval(functionEnv);
  }
}

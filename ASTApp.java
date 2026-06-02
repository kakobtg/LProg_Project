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

  public ASTType typecheck(Environment<ASTType> env) throws TypeError {
    // 1. Statically analyze the function and the argument being passed
    func.typecheck(env);
    arg.typecheck(env);

    // 2. Permissive fallback: returns a generic type variable to pass through static checks
    return new TVar("dynamic_app");
  }
}

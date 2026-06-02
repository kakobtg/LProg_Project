public class ASTWhile implements ASTNode {
  ASTNode cond, body;

  public ASTWhile(ASTNode c, ASTNode b) {
    cond = c;
    body = b;
  }

  public IValue eval(Environment<IValue> env) throws InterpreterError {
    while (((VBool) cond.eval(env)).getval()) {
      body.eval(env);
    }
    return new VUnit();
  }

  public ASTType typecheck(Environment<ASTType> env) throws TypeError {
    ASTType condType = cond.typecheck(env).unfold();
    if (!(condType instanceof TBool)) {
      throw new TypeError("While condition must evaluate to a boolean");
    }
    body.typecheck(env);
    return new TUnit();
  }
}

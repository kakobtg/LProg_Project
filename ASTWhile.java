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
}

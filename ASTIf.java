public class ASTIf implements ASTNode {
  ASTNode cond, thenBranch, elseBranch;

  public ASTIf(ASTNode c, ASTNode t, ASTNode e) {
    cond = c;
    thenBranch = t;
    elseBranch = e;
  }

  public IValue eval(Environment<IValue> env) throws InterpreterError {
    IValue v = cond.eval(env);

    if (!(v instanceof VBool))
      throw new InterpreterError("if condition not boolean");

    return ((VBool) v).getval() ? thenBranch.eval(env) : elseBranch.eval(env);
  }
}

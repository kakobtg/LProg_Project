public class ASTAnd implements ASTNode {
  ASTNode t1;
  ASTNode t2;

  public IValue eval(Environment<IValue> e) throws InterpreterError {
    IValue v1 = t1.eval(e);
    IValue v2 = t2.eval(e);
    if (!(v1 instanceof VBool) || !(v2 instanceof VBool)) {
      throw new InterpreterError("illegal types to && operator");
    }
    if (((VBool) v1).getval()) {
      return new VBool(((VBool) v2).getval());
    } else {
      return new VBool(false);
    }
  }

  public ASTAnd(ASTNode t1, ASTNode t2) {
    this.t1 = t1;
    this.t2 = t2;
  }
}

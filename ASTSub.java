public class ASTSub implements ASTNode {
  ASTNode lhs, rhs;

  public IValue eval(Environment<IValue> e) throws InterpreterError {
    IValue v1 = lhs.eval(e);
    IValue v2 = rhs.eval(e);
    if (v1 instanceof VInt && v2 instanceof VInt) {
      return new VInt(((VInt) v1).getval() - ((VInt) v2).getval());
    } else {
      throw new InterpreterError("illegal types to + operator");
    }
  }

  public ASTSub(ASTNode l, ASTNode r) {
    lhs = l;
    rhs = r;
  }

}

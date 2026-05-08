public class ASTDiv implements ASTNode {
  ASTNode lhs, rhs;

  public IValue eval(Environment<IValue> e) throws InterpreterError {
    IValue v1 = lhs.eval(e);
    IValue v2 = rhs.eval(e);
    if (v1 instanceof VInt && v2 instanceof VInt) {
      int i1 = ((VInt) v1).getval();
      int i2 = ((VInt) v2).getval();
      return new VInt(i1 / i2);
    } else {
      throw new InterpreterError("illegal types to / operator");
    }
  }

  public ASTDiv(ASTNode l, ASTNode r) {
    lhs = l;
    rhs = r;
  }
}

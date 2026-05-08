public class ASTPlus implements ASTNode {
  ASTNode lhs, rhs;

  public IValue eval(Environment<IValue> e) throws InterpreterError {
    IValue v1 = lhs.eval(e);
    IValue v2 = rhs.eval(e);
    if (v1 instanceof VInt) {
      int num = ((VInt) v1).getval();

      if (v2 instanceof VInt) {
        return new VInt(num + ((VInt) v2).getval());
      } else if (v2 instanceof VString) {
        return new VString(Integer.toString(num) + ((VString) v2).getval());
      }
    } else if (v1 instanceof VString) {
      String str = ((VString) v1).getval();

      if (v2 instanceof VInt) {
        return new VString(str + Integer.toString(((VInt) v2).getval()));
      } else if (v2 instanceof VString) {
        return new VString(str + ((VString) v2).getval());
      }
    }

    throw new InterpreterError("illegal types to + operator");
  }

  public ASTPlus(ASTNode l, ASTNode r) {
    lhs = l;
    rhs = r;
  }

}

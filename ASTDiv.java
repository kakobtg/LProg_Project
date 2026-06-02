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

  public ASTType typecheck(Environment<ASTType> env) throws TypeError {
    ASTType leftType = lhs.typecheck(env).unfold();
    ASTType rightType = rhs.typecheck(env).unfold();
    if ((!(leftType instanceof TInt) && !(leftType instanceof TVar)) || 
        (!(rightType instanceof TInt) && !(rightType instanceof TVar))) {
      throw new TypeError("Division requires integer operands");
    }
    return new TInt();
  }
}

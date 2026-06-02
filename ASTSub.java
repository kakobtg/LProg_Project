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

  public ASTType typecheck(Environment<ASTType> env) throws TypeError {
    ASTType leftType = lhs.typecheck(env).unfold();
    ASTType rightType = rhs.typecheck(env).unfold();
    if ((!(leftType instanceof TInt) && !(leftType instanceof TVar)) || 
        (!(rightType instanceof TInt) && !(rightType instanceof TVar))) {
      throw new TypeError("Subtraction requires integer operands");
    }
    return new TInt();
  }
}

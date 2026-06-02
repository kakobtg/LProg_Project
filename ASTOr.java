public class ASTOr implements ASTNode {
  ASTNode t1;
  ASTNode t2;

  public IValue eval(Environment<IValue> e) throws InterpreterError {
    IValue v1 = t1.eval(e);
    if (v1 instanceof VBool && ((VBool) v1).getval()) {
      return new VBool(true);
    }

    IValue v2 = t2.eval(e);
    if (v2 instanceof VBool) {
      return new VBool(((VBool) v2).getval());
    }
    throw new InterpreterError("illegal types to || operator");
  }

  public ASTOr(ASTNode t1, ASTNode t2) {
    this.t1 = t1;
    this.t2 = t2;
  }

  public ASTType typecheck(Environment<ASTType> env) throws TypeError {
    ASTType leftType = t1.typecheck(env).unfold();
    ASTType rightType = t2.typecheck(env).unfold();
    if ((!(leftType instanceof TBool) && !(leftType instanceof TVar)) || 
        (!(rightType instanceof TBool) && !(rightType instanceof TVar))) {
      throw new TypeError("|| operator requires boolean operands");
    }
    return new TBool();
  }
}

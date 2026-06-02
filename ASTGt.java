public class ASTGt implements ASTNode {
  ASTNode t1;
  ASTNode t2;

  public IValue eval(Environment<IValue> e) throws InterpreterError {
    IValue v1 = t1.eval(e);
    IValue v2 = t2.eval(e);
    if (!(v1 instanceof VInt) || !(v2 instanceof VInt)) {
      throw new InterpreterError("illegal types to > operator");
    }
    return new VBool(((VInt) v1).getval() > ((VInt) v2).getval());
  }

  public ASTGt(ASTNode t1, ASTNode t2) {
    this.t1 = t1;
    this.t2 = t2;
  }

  public ASTType typecheck(Environment<ASTType> env) throws TypeError {
    ASTType leftType = t1.typecheck(env).unfold();
    ASTType rightType = t2.typecheck(env).unfold();
    if ((!(leftType instanceof TInt) && !(leftType instanceof TVar)) || 
        (!(rightType instanceof TInt) && !(rightType instanceof TVar))) {
      throw new TypeError("Relational operator > requires integer operands");
    }
    return new TBool();
  }
}

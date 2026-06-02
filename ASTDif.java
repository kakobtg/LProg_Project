public class ASTDif implements ASTNode {
  ASTNode t1;
  ASTNode t2;

  public IValue eval(Environment<IValue> e) throws InterpreterError {
    IValue v1 = t1.eval(e);
    IValue v2 = t2.eval(e);

    if (v1.getClass() != v2.getClass()) {
      return new VBool(true);
    }

    if (v1 instanceof VInt) {
      int i1 = ((VInt) v1).getval();
      int i2 = ((VInt) v2).getval();
      return new VBool(i1 != i2);
    }

    if (v1 instanceof VBool) {
      boolean b1 = ((VBool) v1).getval();
      boolean b2 = ((VBool) v2).getval();
      return new VBool(b1 != b2);
    }

    throw new InterpreterError("illegal types to == operator");
  }

  public ASTDif(ASTNode t1, ASTNode t2) {
    this.t1 = t1;
    this.t2 = t2;
  }

  public ASTType typecheck(Environment<ASTType> env) throws TypeError {
    t1.typecheck(env);
    t2.typecheck(env);
    return new TBool();
  }
}

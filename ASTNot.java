public class ASTNot implements ASTNode {
  ASTNode exp;

  public IValue eval(Environment<IValue> e) throws InterpreterError {
    IValue v0 = exp.eval(e);
    if (v0 instanceof VBool) {
      return new VBool(!((VBool) v0).getval());
    } else {
      throw new InterpreterError("illegal types to not operator");
    }
  }

  public ASTNot(ASTNode e) {
    exp = e;
  }

  public ASTType typecheck(Environment<ASTType> env) throws TypeError {
    ASTType expType = exp.typecheck(env).unfold();
    if (!(expType instanceof TBool) && !(expType instanceof TVar)) {
      throw new TypeError("NOT operator ~ requires a boolean operand");
    }
    return new TBool();
  }
}

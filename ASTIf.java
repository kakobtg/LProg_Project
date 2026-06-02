public class ASTIf implements ASTNode {
  ASTNode cond, thenBranch, elseBranch;

  public ASTIf(ASTNode c, ASTNode t, ASTNode e) {
    cond = c;
    thenBranch = t;
    elseBranch = e;
  }

  public IValue eval(Environment<IValue> env) throws InterpreterError {
    IValue v = cond.eval(env);

    if (!(v instanceof VBool))
      throw new InterpreterError("if condition not boolean");

    return ((VBool) v).getval() ? thenBranch.eval(env) : elseBranch.eval(env);
  }

  public ASTType typecheck(Environment<ASTType> env) throws TypeError {
    ASTType condType = cond.typecheck(env).unfold();
    if (!(condType instanceof TBool)) {
      throw new TypeError("If condition must evaluate to a boolean");
    }
    ASTType thenType = thenBranch.typecheck(env);
    ASTType elseType = elseBranch.typecheck(env);
    if (!thenType.isSubtypeOf(elseType) && !elseType.isSubtypeOf(thenType)) {
      throw new TypeError("If branches must have compatible return types");
    }
    return thenType.isSubtypeOf(elseType) ? elseType : thenType;
  }
}

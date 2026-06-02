public class ASTAlias implements ASTNode {
  ASTNode exp;

  public ASTAlias(ASTNode exp) {
    this.exp = exp;
  }

  public IValue eval(Environment<IValue> env) throws InterpreterError {
    if (exp instanceof ASTId) {
      IValue val = env.find(((ASTId) exp).id);
      if (val instanceof VAddress) {
        return new VRef((VAddress) val);
      }
      throw new InterpreterError("Can only take a reference to a stack-allocated mut variable");
    }
    throw new InterpreterError("Alias operator '&' requires an identifier");
  }

  public ASTType typecheck(Environment<ASTType> env) throws TypeError {
    if (exp instanceof ASTId) {
      try {
        ASTType t = env.find(((ASTId) exp).id);
        if (t instanceof TMut) {
          return new TRef(((TMut) t).refType);
        }
        throw new TypeError("Can only take a reference to a stack-allocated mut variable");
      } catch (InterpreterError e) {
        throw new TypeError(e.getMessage());
      }
    }
    throw new TypeError("Alias operator '&' requires an identifier");
  }
}
class ASTCell implements ASTNode {
  ASTNode expr;

  ASTCell(ASTNode e) {
    expr = e;
  }

  public IValue eval(Environment<IValue> env) throws InterpreterError {
    IValue v = expr.eval(env);
    return new VCell(v);
  }

  public ASTType typecheck(Environment<ASTType> env) throws TypeError {
    return new TCell(expr.typecheck(env));
  }
}

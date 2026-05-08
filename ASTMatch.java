public class ASTMatch implements ASTNode {
  ASTNode expr, matchExpr, elseExpr;
  ASTPattern pattern;

  public ASTMatch(ASTNode e, ASTPattern p, ASTNode pb, ASTNode eb) {
    expr = e;
    pattern = p;
    matchExpr = pb;
    elseExpr = eb;
  }

  public IValue eval(Environment<IValue> env) throws InterpreterError {
    IValue v = expr.eval(env);

    if (!(v instanceof VStructTuple)) {
      throw new InterpreterError("Match on non-tag value");
    }

    VStructTuple vt = (VStructTuple) v;

    if (pattern.tag.equals(vt.tag)) {
      if (pattern.pvs.size() != vt.vals.size()) {
        throw new InterpreterError("Arity mismatch");
      }

      Environment<IValue> newEnv = env.beginScope();
      for (int i = 0; i < pattern.pvs.size(); i++) {
        newEnv.assoc(pattern.pvs.get(i), vt.vals.get(i).eval(newEnv));
      }

      return matchExpr.eval(newEnv);
    }

    return elseExpr.eval(env);
  }
}

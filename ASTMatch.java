import java.util.List;

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

  public ASTType typecheck(Environment<ASTType> env) throws TypeError {
    ASTType exprType = expr.typecheck(env);
    ASTType unfoldedExpr = exprType.unfold();

    if (unfoldedExpr instanceof TVar) {
      // Permissive fallback: allow pattern matching on unannotated closure parameters
      Environment<ASTType> matchEnv = env.beginScope();
      for (String pv : pattern.getPvs()) {
        matchEnv.assoc(pv, new TVar(pv));
      }
      ASTType matchBranchType = matchExpr.typecheck(matchEnv);
      ASTType elseBranchType = elseExpr.typecheck(env);
      return matchBranchType.isSubtypeOf(elseBranchType) ? elseBranchType : matchBranchType;
    }

    if (!(unfoldedExpr instanceof TEnum)) {
      throw new TypeError("Match expression must evaluate to an Enum type.");
    }

    TEnum enumType = (TEnum) unfoldedExpr;
    List<ASTType> variantFields = enumType.variants.get(pattern.getTag());

    if (variantFields == null) {
      throw new TypeError("Tag '" + pattern.getTag() + "' is not a valid variant for this Enum.");
    }

    if (variantFields.size() != pattern.getPvs().size()) {
      throw new TypeError("Arity mismatch in pattern match for tag '" + pattern.getTag() + "'.");
    }

    Environment<ASTType> matchEnv = env.beginScope();
    for (int i = 0; i < pattern.getPvs().size(); i++) {
      matchEnv.assoc(pattern.getPvs().get(i), variantFields.get(i));
    }

    ASTType matchBranchType = matchExpr.typecheck(matchEnv);
    ASTType elseBranchType = elseExpr.typecheck(env);

    // For safety, ensure both branches yield compatible types
    if (!matchBranchType.isSubtypeOf(elseBranchType) && !elseBranchType.isSubtypeOf(matchBranchType)) {
      throw new TypeError("Match and Else branches must have compatible return types.");
    }

    // Return the more general type (the supertype of the two)
    return matchBranchType.isSubtypeOf(elseBranchType) ? elseBranchType : matchBranchType;
  }
}

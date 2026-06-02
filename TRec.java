public class TRec implements ASTType {
  private String typeVar;
  private ASTType body;

  public TRec(String typeVar, ASTType body) {
    this.typeVar = typeVar;
    this.body = body;
  }

  @Override
  public boolean isSubtypeOf(ASTType other) {
    // For equi-recursive types, we safely compare them by unfolding one level.
    return this.unfold().isSubtypeOf(other.unfold());
  }

  @Override
  public ASTType unfold() {
    // Unfolding replaces the bound variable inside the body with the entire recursive type itself
    return body.substitute(typeVar, this);
  }

  @Override
  public ASTType substitute(String varName, ASTType replacement) {
    // If the variable is shadowed by this recursive binder, we stop substituting
    if (this.typeVar.equals(varName)) {
      return this;
    }
    return new TRec(typeVar, body.substitute(varName, replacement));
  }
}
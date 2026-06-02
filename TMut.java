public class TMut implements ASTType {
  public ASTType refType;

  public TMut(ASTType refType) {
    this.refType = refType;
  }

  @Override
  public boolean isSubtypeOf(ASTType other) {
    ASTType otherUnfolded = other.unfold();
    if (!(otherUnfolded instanceof TMut)) return false;
    // Mutable references require invariant subtyping
    return this.refType.isSubtypeOf(((TMut) otherUnfolded).refType) &&
           ((TMut) otherUnfolded).refType.isSubtypeOf(this.refType);
  }

  @Override
  public ASTType unfold() { return this; }

  @Override
  public ASTType substitute(String varName, ASTType replacement) {
    return new TMut(refType.substitute(varName, replacement));
  }
}
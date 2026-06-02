public class TRef implements ASTType {
  public ASTType refType;

  public TRef(ASTType refType) {
    this.refType = refType;
  }

  @Override
  public boolean isSubtypeOf(ASTType other) {
    ASTType otherUnfolded = other.unfold();
    if (!(otherUnfolded instanceof TRef)) return false;
    
    return this.refType.isSubtypeOf(((TRef) otherUnfolded).refType) &&
           ((TRef) otherUnfolded).refType.isSubtypeOf(this.refType);
  }

  @Override
  public ASTType unfold() { return this; }

  @Override
  public ASTType substitute(String varName, ASTType replacement) {
    return new TRef(refType.substitute(varName, replacement));
  }
}
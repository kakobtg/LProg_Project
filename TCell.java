public class TCell implements ASTType {
  public ASTType refType;

  public TCell(ASTType refType) {
    this.refType = refType;
  }

  @Override
  public boolean isSubtypeOf(ASTType other) {
    ASTType otherUnfolded = other.unfold();
    if (!(otherUnfolded instanceof TCell)) return false;
    
    // Heap cells, like mutable references, require invariant subtyping
    return this.refType.isSubtypeOf(((TCell) otherUnfolded).refType) &&
           ((TCell) otherUnfolded).refType.isSubtypeOf(this.refType);
  }

  @Override
  public ASTType unfold() { return this; }

  @Override
  public ASTType substitute(String varName, ASTType replacement) {
    return new TCell(refType.substitute(varName, replacement));
  }
}
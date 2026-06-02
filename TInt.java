public class TInt implements ASTType {
  @Override
  public boolean isSubtypeOf(ASTType other) {
    return other.unfold() instanceof TInt;
  }

  @Override
  public ASTType unfold() {
    return this;
  }

  @Override
  public ASTType substitute(String varName, ASTType replacement) {
    return this;
  }
}
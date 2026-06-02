public class TUnit implements ASTType {
  @Override
  public boolean isSubtypeOf(ASTType other) {
    return other.unfold() instanceof TUnit;
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
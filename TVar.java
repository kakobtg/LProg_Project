public class TVar implements ASTType {
  private String name;

  public TVar(String name) {
    this.name = name;
  }

  @Override
  public boolean isSubtypeOf(ASTType other) {
    ASTType otherUnfolded = other.unfold();
    return otherUnfolded instanceof TVar && ((TVar) otherUnfolded).name.equals(this.name);
  }

  @Override
  public ASTType unfold() {
    return this;
  }

  @Override
  public ASTType substitute(String varName, ASTType replacement) {
    if (this.name.equals(varName)) {
      return replacement;
    }
    return this;
  }
}
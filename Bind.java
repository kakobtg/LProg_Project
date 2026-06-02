// x = expr (id = "x", exp = expr)
class Bind {
  private final String id;
  private final ASTNode exp;
  private boolean isMut = false;
  private ASTType type = null;

  public Bind(String _id, ASTNode _exp) {
    this.id = _id;
    this.exp = _exp;
  }

  public Bind(String _id, ASTNode _exp, boolean _isMut, ASTType _type) {
    this.id = _id;
    this.exp = _exp;
    this.isMut = _isMut;
    this.type = _type;
  }

  public String getId() {
    return id;
  }

  public ASTNode getExp() {
    return exp;
  }

  public boolean isMut() {
    return isMut;
  }

  public ASTType getType() {
    return type;
  }
}

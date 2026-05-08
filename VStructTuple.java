import java.util.List;

class VStructTuple implements IValue {
  String tag;
  List<ASTNode> vals;

  VStructTuple(String t, List<ASTNode> v) {
    tag = t;
    vals = v;
  }

  List<ASTNode> getval() {
    return vals;
  }

  public String toStr() {
    return tag + vals.toString();
  }
}

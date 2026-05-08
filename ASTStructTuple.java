import java.util.List;

class ASTStructTuple implements ASTNode {
  String tag;
  List<ASTNode> vals;

  public ASTStructTuple(String t, List<ASTNode> v) {
    tag = t;
    vals = v;
  }

  public IValue eval(Environment<IValue> env) throws InterpreterError {
    return new VStructTuple(tag, vals);
  }
}

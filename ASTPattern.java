import java.util.List;

public class ASTPattern implements ASTNode {
  String tag;
  List<String> pvs;

  public IValue eval(Environment<IValue> e) throws InterpreterError {
    throw new InterpreterError("not to be evaluated");
  }

  String getTag() {
    return tag;
  }

  List<String> getPvs() {
    return pvs;
  }

  public ASTPattern(String t, List<String> ids) {
    tag = t;
    pvs = ids;
  }
}

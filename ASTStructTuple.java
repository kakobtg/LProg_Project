import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

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

  public ASTType typecheck(Environment<ASTType> env) throws TypeError {
    List<ASTType> fieldTypes = new ArrayList<>();
    for (ASTNode val : vals) {
      fieldTypes.add(val.typecheck(env));
    }
    Map<String, List<ASTType>> variants = new HashMap<>();
    variants.put(tag, fieldTypes);
    return new TEnum(variants);
  }
}

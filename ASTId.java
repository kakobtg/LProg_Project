public class ASTId implements ASTNode {
  String id;

  public ASTId(String id) {
    this.id = id;
  }

  public IValue eval(Environment<IValue> env) throws InterpreterError {
    IValue val = env.find(id);
    if (val instanceof VAddress) {
      return MemoryManager.getInstance().memrd((VAddress) val);
    }
    return val;
  }

  public ASTType typecheck(Environment<ASTType> env) throws TypeError {
    try {
      ASTType type = env.find(id);
      // Implicit dereference for stack allocated variables (R-Value)
      if (type instanceof TMut) {
        return ((TMut) type).refType;
      }
      return type;
    } catch (InterpreterError e) {
      throw new TypeError(e.getMessage());
    }
  }
}

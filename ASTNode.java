public interface ASTNode {
  IValue eval(Environment<IValue> e) throws InterpreterError;
}

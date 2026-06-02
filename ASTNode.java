public interface ASTNode {
  IValue eval(Environment<IValue> e) throws InterpreterError;

  // Default method allows gradual implementation of typechecking across AST nodes
  default ASTType typecheck(Environment<ASTType> e) throws TypeError { return new TUnit(); }
}

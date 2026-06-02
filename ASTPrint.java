import java.util.List;

public class ASTPrint implements ASTNode {
  ASTNode expr;

  public IValue eval(Environment<IValue> e) throws InterpreterError {
    IValue v = expr.eval(e);
    System.out.print(v.toStr());
    return new VUnit();
  }

  public ASTPrint(ASTNode e) {
    this.expr = e;
  }

  public ASTType typecheck(Environment<ASTType> env) throws TypeError {
    expr.typecheck(env);
    return new TUnit();
  }
}

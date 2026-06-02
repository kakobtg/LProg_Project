import java.util.List;

public class ASTPrintln implements ASTNode {
  ASTNode expr;

  public IValue eval(Environment<IValue> e) throws InterpreterError {
    IValue v = expr.eval(e);
    System.out.println(v.toStr());
    return new VUnit();
  }

  public ASTPrintln(ASTNode e) {
    this.expr = e;
  }

  public ASTType typecheck(Environment<ASTType> env) throws TypeError {
    expr.typecheck(env);
    return new TUnit();
  }
}

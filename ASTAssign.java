class ASTAssign implements ASTNode {
  ASTNode left, right;

  ASTAssign(ASTNode l, ASTNode r) {
    left = l;
    right = r;
  }

  public IValue eval(Environment<IValue> env) throws InterpreterError {
    IValue targetRef;

    // Intercept L-values to prevent implicit dereferencing of stack variables
    if (left instanceof ASTId) {
      targetRef = env.find(((ASTId) left).id);
    } else {
      targetRef = left.eval(env);
    }

    IValue val = right.eval(env);

    if (targetRef instanceof VAddress) {
      MemoryManager.getInstance().memwrt((VAddress) targetRef, val);
    } else if (targetRef instanceof VCell) {
      ((VCell) targetRef).set(val);
    } else if (targetRef instanceof VRef) {
      MemoryManager.getInstance().memwrt(((VRef) targetRef).address, val);
    } else {
      throw new InterpreterError("Left side of assignment is not a valid reference");
    }

    return new VUnit();
  }

  public ASTType typecheck(Environment<ASTType> env) throws TypeError {
    ASTType targetType;

    // Intercept L-values to prevent implicit dereferencing of stack variables
    if (left instanceof ASTId) {
      try {
        targetType = env.find(((ASTId) left).id);
      } catch (InterpreterError e) {
        throw new TypeError(e.getMessage());
      }
    } else {
      targetType = left.typecheck(env);
    }

    ASTType rightType = right.typecheck(env);

    if (targetType instanceof TMut) {
      ASTType refType = ((TMut) targetType).refType.unfold();
      if (!rightType.isSubtypeOf(refType) && !(refType instanceof TVar) && !(rightType.unfold() instanceof TVar)) {
        throw new TypeError("Type mismatch in assignment to stack variable (mut).");
      }
    } else if (targetType instanceof TCell) {
      ASTType refType = ((TCell) targetType).refType.unfold();
      if (!rightType.isSubtypeOf(refType) && !(refType instanceof TVar) && !(rightType.unfold() instanceof TVar)) {
        throw new TypeError("Type mismatch in assignment to heap cell.");
      }
    } else if (targetType instanceof TRef) {
      ASTType refType = ((TRef) targetType).refType.unfold();
      if (!rightType.isSubtypeOf(refType) && !(refType instanceof TVar) && !(rightType.unfold() instanceof TVar)) {
        throw new TypeError("Type mismatch in assignment to explicit reference.");
      }
    } else if (targetType instanceof TVar) {
      // Permissive fallback to allow dynamic evaluation of unannotated closure parameters
    } else {
      throw new TypeError("Left side of assignment is not a valid reference (expected mut or cell).");
    }

    return new TUnit();
  }
}

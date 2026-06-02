public interface ASTType {
  // Checks if this type is a subtype of another type
  boolean isSubtypeOf(ASTType other);

  // Unfolds the type by one level if it is a recursive type (useful for equi-recursive matching)
  ASTType unfold();

  // Substitutes any occurrence of the type variable 'varName' with the 'replacement' type
  ASTType substitute(String varName, ASTType replacement);
}
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class TEnum implements ASTType {
  public Map<String, List<ASTType>> variants;

  public TEnum(Map<String, List<ASTType>> variants) {
    this.variants = variants;
  }

  @Override
  public boolean isSubtypeOf(ASTType other) {
    ASTType otherUnfolded = other.unfold();
    if (!(otherUnfolded instanceof TEnum)) return false;
    
    TEnum otherEnum = (TEnum) otherUnfolded;

    // Width Subtyping: The subtype can have "less" or "equal" options than the supertype
    for (String tag : this.variants.keySet()) {
      if (!otherEnum.variants.containsKey(tag)) return false; 

      List<ASTType> myFields = this.variants.get(tag);
      List<ASTType> otherFields = otherEnum.variants.get(tag);
      
      // Arity strictly needs to match for a specific variant tag
      if (myFields.size() != otherFields.size()) return false;

      // Depth Subtyping: The internal types of the fields must be valid subtypes
      for (int i = 0; i < myFields.size(); i++) {
        if (!myFields.get(i).isSubtypeOf(otherFields.get(i))) return false;
      }
    }
    return true;
  }

  @Override
  public ASTType unfold() { return this; }

  @Override
  public ASTType substitute(String varName, ASTType replacement) {
    Map<String, List<ASTType>> newVariants = new HashMap<>();
    for (Map.Entry<String, List<ASTType>> entry : variants.entrySet()) {
      List<ASTType> newFields = new ArrayList<>();
      for (ASTType t : entry.getValue()) {
        newFields.add(t.substitute(varName, replacement));
      }
      newVariants.put(entry.getKey(), newFields);
    }
    return new TEnum(newVariants);
  }
}
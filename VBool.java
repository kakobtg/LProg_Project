class VBool implements IValue {
  boolean b;

  VBool(boolean b0) {
    b = b0;
  }

  boolean getval() {
    return b;
  }

  public String toStr() {
    return Boolean.toString(b);
  }
}

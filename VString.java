class VString implements IValue {
  String s;

  VString(String s0) {
    if (s0 != null && s0.length() >= 2 && s0.startsWith("\"") && s0.endsWith("\"")) {
      s = s0.substring(1, s0.length() - 1);
    } else {
      s = s0;
    }
  }

  String getval() {
    return s;
  }

  public String toStr() {
    return s;
  }
}

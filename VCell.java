class VCell implements IValue {
  IValue value;

  public VCell(IValue v) {
    this.value = v;
  }

  public IValue get() {
    return value;
  }

  public void set(IValue v) {
    value = v;
  }

  public String toStr() {
    return value.toStr();
  }
}

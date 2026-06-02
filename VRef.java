public class VRef implements IValue {
  public VAddress address;

  public VRef(VAddress address) {
    this.address = address;
  }

  public String toStr() {
    return "ref " + address.toStr();
  }
}
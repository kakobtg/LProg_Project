public class VAddress implements IValue {
  public int address;

  public VAddress(int address) {
    this.address = address;
  }

  public String toStr() {
    return "0x" + Integer.toHexString(address);
  }
}
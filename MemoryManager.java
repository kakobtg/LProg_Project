import java.util.Stack;

public class MemoryManager {
  private static MemoryManager instance;
  private Stack<IValue> stack;

  private MemoryManager() {
    stack = new Stack<>();
  }

  public static MemoryManager getInstance() {
    if (instance == null) instance = new MemoryManager();
    return instance;
  }

  public VAddress push(IValue val) {
    stack.push(val);
    return new VAddress(stack.size() - 1);
  }

  public void pop() {
    if (!stack.isEmpty()) stack.pop();
  }

  public IValue memrd(VAddress addr) {
    return stack.get(addr.address);
  }

  public void memwrt(VAddress addr, IValue val) {
    stack.set(addr.address, val);
  }
}
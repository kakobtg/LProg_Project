public class R0int {

    public static void main(String args[]) {
	Parser parser = new Parser(System.in);
	ASTNode exp;
    
	System.out.println("R0 interpreter PL MEIC 2025/26 (v0.0)\n");

	while (true) {
	    try {
		System.out.print("# ");
		exp = parser.Start();
		if (exp==null) System.exit(0);
			IValue v = exp.eval(new Environment<IValue>());
			System.out.println(v.toStr());
	    } catch (ParseException e) {
			System.out.println("Syntax Error.");
			parser.ReInit(System.in);
	    } catch (Exception e) {
			e.printStackTrace();
			parser.ReInit(System.in);
	    }
	}
    }
    
}

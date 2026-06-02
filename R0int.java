import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class R0int {

    public static void main(String args[]) {
	InputStream input = System.in;
	boolean runTypechecker = true;

	for (String arg : args) {
	    if (arg.equals("-types")) {
		runTypechecker = false; // The flag explicitly DEACTIVATES the type-checker
	    } else {
		try {
		    input = new FileInputStream(arg);
		} catch (FileNotFoundException e) {
		    System.err.println("File not found: " + arg);
		    System.exit(1);
		}
	    }
	}

	Parser parser = new Parser(input);
	boolean isInteractive = (input == System.in);
	ASTNode exp;
    
	if (isInteractive) {
	    System.out.println("R0 interpreter PL MEIC 2025/26 (v0.0)\n");
	}

	while (true) {
	    try {
		if (isInteractive) System.out.print("# ");
		exp = parser.Start();
		if (exp==null) System.exit(0);

			try {
				if (runTypechecker) {
				    ASTType type = exp.typecheck(new Environment<ASTType>());
				}
				IValue v = exp.eval(new Environment<IValue>());
				System.out.println(v.toStr());
			} catch (TypeError e) {
				System.out.println("Static Type Error: " + e.getMessage());
			}
	    } catch (ParseException e) {
			System.out.println("Syntax Error.");
			parser.ReInit(input);
	    } catch (Exception e) {
			e.printStackTrace();
			parser.ReInit(input);
	    }
	}
    }
    
}

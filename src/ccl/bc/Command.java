package ccl.bc;

public class Command {

	private String cmd;
	private String arg;
	public Bytecode bytecode;

	public Command(Bytecode bc, String line) {
		bytecode = bc;
		
		String[] split = line.split(" ");
		cmd = split[0];
		arg = link(split, 1);
	}
	
	public boolean isRuntime(){
		return !cmd.equals("mark");
	}

	private String link(String[] arr, int skip) {
		StringBuilder b = new StringBuilder();
		for(int i = skip; i < arr.length; i++){
			b.append(arr[i]);
			if(i < arr.length - 1){
				b.append(" ");
			}
		}
		return b.toString();
	}

	public String getArg() {
		return arg;
	}
	public String getCmd() {
		return cmd;
	}

	public ArgType getArgType() {
		switch(cmd){
		case "putS":
		case "load":
		case "get":
		case "reserve":
			return ArgType.STR;
		case "putI":
		case "putA":
		case "invoke":
			return ArgType.NUM;
		case "pop":
		case "newscope":
		case "oldscope":
		case "store":
		case "ret":
			return ArgType.NONE;
		case "putM":
			return ArgType.METHOD;
		case "if":
		case "goto":
			return ArgType.MARK;
		default: throw new RuntimeException("No type for " + cmd);
		}
	}

}

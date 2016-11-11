package ccl.bc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Bytecode {
	
	private List<Command> commands;
	private char[] shorts;
	HashMap<String, Integer> marks;
	private String[] args;

	public Bytecode(List<String> instructions){
		commands = new ArrayList<Command>();
		marks = new HashMap<String, Integer>();
		
		for(int i = 0; i < instructions.size(); i++){
			Command c = new Command(this, instructions.get(i));
			if(!c.isRuntime()){
				marks.put(c.getArg(), commands.size());
			}else{
				commands.add(c);
			}
		}
		
		shorts = new char[commands.size()];
		args = new String[commands.size()];
		for(int i = 0; i < shorts.length; i++){
			shorts[i] = getShortcut(commands.get(i).getCmd());
			args[i] = commands.get(i).getArgType().make(commands.get(i));
		}
	}

	private char getShortcut(String cmd) {
		switch(cmd){
		case "reserve": return 'r';
		case "putS": return 'S';
		case "putI": return 'I';
		case "putF": return 'F';
		case "putA": return 'A';
		case "putM": return 'M';
		case "if": return '?';
		case "goto": return '>';
		case "pop": return 'p';
		case "invoke": return '(';
		case "newscope": return '{';
		case "oldscope": return '}';
		case "load": return '#';
		case "store": return '$';
		case "get": return '.';
		case "ret": return 'r';
		default: throw new RuntimeException("Unknown bc command " + cmd);
		}
	}
	
	public String toString(){
		StringBuilder b = new StringBuilder();
		for(int i = 0; i < shorts.length; i++){
			b.append(shorts[i]);
			if(args[i].length() != 0){
				try{
					int val = Integer.parseInt(args[i]);
					if(val >= 0 && val <= 255){
						b.append((char) 0);
						b.append((char) val);
						continue;
					}
				}catch(NumberFormatException e){};
				int length = args[i].length();
				b.append((char) length);
				b.append(args[i]);
			}
		}
		return b.toString();
	}

	public String complete() {
		StringBuilder sb = new StringBuilder();
		
		//Number index
		String numCount = ArgType.numList.size() + "";
		sb.append((char) numCount.length());
		sb.append(numCount);
		
		for(int i = 0; i < ArgType.numList.size(); i++){
			String s = ArgType.numList.get(i);
			int length = s.length();
			sb.append((char) (length + "").length());
			sb.append(length);
			sb.append(s);
		}
		
		//String index
		String strCount = ArgType.strList.size() + "";
		sb.append((char) strCount.length());
		sb.append(strCount);
		
		for(int i = 0; i < ArgType.strList.size(); i++){
			String s = ArgType.strList.get(i);
			int length = s.length();
			sb.append((char) (length + "").length());
			sb.append(length);
			sb.append(s);
		}
		
		//ByteCode instructions
		sb.append(toString());
		
		return sb.toString();
	}
	
}

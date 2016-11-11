package ccl.bc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public enum ArgType {
	
	NUM, STR, MARK, NONE, METHOD;
	
	public static HashMap<String, Integer> numMap = new HashMap<String, Integer>();
	public static ArrayList<String> numList = new ArrayList<String>();
	
	public static HashMap<String, Integer> strMap = new HashMap<String, Integer>();
	public static ArrayList<String> strList = new ArrayList<String>();
	
	public String make(Command cmd){
		switch(this){
		case NUM: return num(cmd);
		case STR: return str(cmd);
		case MARK: return mrk(cmd);
		case METHOD: return mtd(cmd);
		case NONE: return "";
		default: throw new RuntimeException("Unknown type " + this);
		}
	}

	private String mtd(Command cmd) {
		try {
			return str0(BC.compileBC(new File(cmd.getArg())));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private String mrk(Command cmd) {
		return cmd.bytecode.marks.get(cmd.getArg()) + "";
	}

	private static String str(Command cmd) {
		String arg = cmd.getArg();
		return str0(arg);
	}
	
	private static String str0(String arg){
		if(strMap.get(arg) != null){
			return strMap.get(arg) + "";
		}else{
			strMap.put(arg, strList.size());
			strList.add(arg);
			return str0(arg);
		}
	}

	private static String num(Command cmd) {
		String arg = cmd.getArg();
		return num0(arg);
	}
	
	public static String num0(String arg){
		if(numMap.get(arg) != null){
			return numMap.get(arg) + "";
		}else{
			numMap.put(arg, numList.size());
			numList.add(arg);
			return num0(arg);
		}
	}
	
}

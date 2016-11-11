package ccl.bc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BC {
	
	public static String compileBC(File in) throws FileNotFoundException{
		List<String> list = new ArrayList<String>();
		Scanner sc = new Scanner(in);
		
		while(sc.hasNextLine()){
			list.add(sc.nextLine());
		}
		
		sc.close();
		
		Bytecode bc = new Bytecode(list);
		return bc.toString();
	}
	
}

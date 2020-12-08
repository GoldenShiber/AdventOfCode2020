package solutions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utils.DataHandling;

public class Day8 {

	private static int index = 0;
	private static int accel = 0;
	private static int part1Accel = 0;
	private static HashMap<Integer, Integer> commandMap = new HashMap<>();
	private static boolean isRepeated = false;
	
	public static void inputRules(String line) {
		String[] actions = line.split(" ");
		int value = Integer.parseInt(actions[1].substring(1));
		value = (actions[1].charAt(0) == '-') ? -value : value;
		if(!commandMap.containsKey(index)) {
			commandMap.put(index, 1);
		} else {
			commandMap.put(index, commandMap.get(index)+1);
			isRepeated = true;
			part1Accel = accel;
		}
		switch(actions[0]) {
		case "acc": 
			accel += value;
			index++;
			break;
		case "jmp":
			index += value;
			break;
		default: 
			index++;
		}
	}
	
	public static void reset() {
		index = 0;
		accel = 0;
		commandMap = new HashMap<>();
		isRepeated = false;
	}
	
	public static void main(String[] args) throws IOException {
		 List<String> lines = DataHandling.readStringsBySeparator("inputFiles/inputDay8.txt");
		 while(!isRepeated) {
			 inputRules(lines.get(index));
		 }
		 for(int i = 0; i<lines.size(); i++) {
			 List<String> testLines = new ArrayList<String>(lines);
			 if(testLines.get(i).contains("nop")) {
				 testLines.set(i, testLines.get(i).replace("nop", "jmp"));
			 } else if(testLines.get(i).contains("jmp")) {
				 testLines.set(i,testLines.get(i).replace("jmp", "nop"));
			 }
			 reset();
			 while(!isRepeated && index < testLines.size()) {
				 inputRules(testLines.get(index));
			 }
			 if(index == testLines.size()) {
				 break;
			 }
		 }
		 System.out.println("Part 1: " + part1Accel); 
		 System.out.println("==============================");
		 System.out.println("Part 2: " + accel);
}
	
}

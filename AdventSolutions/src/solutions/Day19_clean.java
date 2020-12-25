package solutions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import utils.DataHandling;

public class Day19_clean {

	public static HashMap<Integer, String> ruleMap = new HashMap<>();
	public static HashMap<Integer, ArrayList<String>> ruleConfigurations = new HashMap<>();
	
	public static ArrayList<String> recursiveFill(String rule){
		
		ArrayList<String> modList = new ArrayList<>();
		ArrayList<ArrayList<String>> arrayOfArrayList = new ArrayList<>();
		if(rule.contains("|")) {
			String[] rules = rule.replace("|", ":").split(":");
			for(String testRule : rules) {
				arrayOfArrayList.add(recursiveFill(testRule.trim()));
			}
			for(ArrayList<String> arrList : arrayOfArrayList) {
				for(String line : arrList) {
					modList.add(line);
				}
			}
			
			
		} else if(rule.length() > 1){
			for(String ruleNumbers : rule.split(" ")){
				arrayOfArrayList.add(recursiveFill(ruleMap.get(Integer.parseInt(ruleNumbers))));
			}
			modList = parallelList(arrayOfArrayList);
			
		}
		else {
			if(Character.isDigit(rule.charAt(0))) {
				modList = recursiveFill(ruleMap.get(Integer.parseInt(rule)));
			} else {
			modList.add(rule);	
			}
		}
		return modList;
	}
	
	
	public static ArrayList<String> parallelList(ArrayList<ArrayList<String>> bigArrayList){
		ArrayList<String> testList = new ArrayList<>();
		String testString = "";
		String testXString = "";
		String testYString = "";
		if(bigArrayList.size() > 1) {
			for(int i = 0; i < bigArrayList.get(0).size(); i++) {
				testXString = bigArrayList.get(0).get(i) + testString;
				for(int j = 0; j < bigArrayList.get(1).size(); j++) {
					testYString =testXString + bigArrayList.get(1).get(j);
					if(bigArrayList.size() == 3) {
						for(int k = 0; k < bigArrayList.get(2).size(); k++) {
							testList.add(testYString + bigArrayList.get(2).get(k));
						} 
					} else {
						testList.add(testYString);
					}
				}
			}
			
		} else {
			if(bigArrayList.size() == 1) {
			testList = bigArrayList.get(0);
			}
		}
		return testList;
		
	}

	public static boolean stringContainsItemFromList(String inputStr, String[] items) {
	    return Arrays.stream(items).anyMatch(inputStr::contains);
	}
	
	public static int partOne(ArrayList<String> testInput, ArrayList<String> candidates) {
		int count = 0;
		for(String input: testInput) {
			if(candidates.contains(input)) {
				count++;
			}
		}
		return count;
	}
	
	
	public static void main(String[] args) throws IOException {
		 ArrayList<String> lines = DataHandling.readStringsBySeparatorRaw("inputFiles/inputDay19.txt");

		 ArrayList<String> kekLines = new ArrayList<>();
		 kekLines.add("keke");
		 kekLines.add("loco");
		 
		 ArrayList<String> testLines = new ArrayList<>();
		 for(String line : lines) {
			 String[] ruleLines = line.split(":");
			 if(ruleLines.length > 1) {
				 int index = Integer.parseInt(ruleLines[0]);
				 String quoteSign = String.valueOf('"');
				 String ruleDefinition = ruleLines[1].trim().replace(quoteSign, "");
				 ruleMap.put(index, ruleDefinition);
			 }
			 else {
				 testLines.add(line);
			 }
		 }
		 
		 kekLines = recursiveFill(ruleMap.get(0));
		 System.out.println(partOne(testLines, kekLines));
		 System.out.println("hej");
	}
	
}
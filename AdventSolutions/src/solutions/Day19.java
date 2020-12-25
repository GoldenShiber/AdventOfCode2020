package solutions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import utils.DataHandling;

public class Day19 {

	public static HashMap<Integer, String> ruleMap = new HashMap<>();
	public static HashMap<Integer, ArrayList<String>> ruleConfigurations = new HashMap<>();
	public static int[] combinationImageSizes = {0, 0};
	
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
			modList = DataHandling.parallelList(arrayOfArrayList);
			
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
	
	
	public static int validLines(ArrayList<String> testLines) {
		int count = 0;
		ArrayList<String> shortRules = ruleConfigurations.get(42);
		ArrayList<String> longRules = ruleConfigurations.get(31);

		
		for(String line : testLines) {
			// Start with 42x examples
			
			// First check base rules, if it has the chance of being legit. 
			// First two must be of type 42, and last must be of type 31
			int forTwoCounter = 0;
			int forThreeCounter = 0;
			int mode = 0;
			String testString = "";
			String firstCheck = line.substring(0, combinationImageSizes[0]);
			String secondCheck = line.substring(combinationImageSizes[0], combinationImageSizes[0]*2);
			String thirdCheck = line.substring(line.length()-combinationImageSizes[0]);
			int startIndex = 0;
			int endIndex = startIndex + combinationImageSizes[0];
			if(shortRules.contains(firstCheck) && shortRules.contains(secondCheck) && longRules.contains(thirdCheck)) {
				forTwoCounter = 0;
				forThreeCounter = 0;
				if(endIndex == line.length()){
					count++;
					continue;
				}
				while(endIndex < line.length()) {
					testString = line.substring(startIndex, endIndex);
					if(shortRules.contains(testString) && mode == 0) {
						startIndex += combinationImageSizes[0];
						endIndex += combinationImageSizes[0];
						forTwoCounter++;
					} 
					else if(longRules.contains(testString) && forThreeCounter < forTwoCounter -2) {
						mode = 1;
						startIndex += combinationImageSizes[0];
						endIndex += combinationImageSizes[0];
						forThreeCounter++;
					}
					else {
						break;
					}
				}
				if(endIndex == line.length()) {
					count++;
					}
			}
		}
		return count;
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
				 if(line.length()>0) {
				 testLines.add(line);
				 }
			 }
		 }
		 
		 kekLines = recursiveFill(ruleMap.get(0));
		 System.out.println("The part 1 gives answer: "+ partOne(testLines, kekLines));
		 
		 ArrayList<String> totLines = recursiveFill(ruleMap.get(42));
		 ArrayList<String> mehLines = recursiveFill(ruleMap.get(31));
		 ruleConfigurations.put(42, totLines);
		 ruleConfigurations.put(31, mehLines);
		 combinationImageSizes[0] = DataHandling.maxWordLengthInList(totLines);
		 combinationImageSizes[1] = combinationImageSizes[0]*2;
		 
		 System.out.println("The solution for part 2 is: "+validLines(testLines));
	}
	
}

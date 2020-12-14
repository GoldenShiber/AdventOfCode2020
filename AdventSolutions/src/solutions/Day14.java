package solutions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utils.DataHandling;

public class Day14 {

	public static final String BINARY_ZERO_LEN_32 = "000000000000000000000000000000000000";
	public static HashMap<String, String> memorySpace = new HashMap<String, String>();
	public static HashMap<String, String> memorySpaceTwo = new HashMap<String, String>();
	
	// Part one masking operations.
	public static String maskValue(String inputString, String mask) {
		StringBuilder endString = new StringBuilder();
		for(int i = 0; i < inputString.length(); i++) {
			if(mask.charAt(i) == 'X') {
				endString.append(inputString.charAt(i));
			}else {
				endString.append(mask.charAt(i));
			}
		}
		
		return endString.toString();
	}
	
	// Part two masking operations.
	public static String maskMultiValues(String inputString, String mask) {
		StringBuilder endString = new StringBuilder();
		for(int i = 0; i < inputString.length(); i++) {
			if(mask.charAt(i) == '0') {
				endString.append(inputString.charAt(i));
			} else {
				endString.append(mask.charAt(i));
			}
		}
		return endString.toString();
	}
	
	//Updates the memory for part two with all the multiple adresses!
	public static void updateMemoryKey(String filterValue, String memoryIndex, String insertValue) {
		List<Integer> indexList = new ArrayList<Integer>();
		
		for(int i = 0; i<filterValue.length(); i++) {
			if(filterValue.charAt(i) == 'X') {
				indexList.add(i);
			}
		}
		testCombinations(filterValue, indexList, memoryIndex, insertValue);
	}
	
	// Test all the possible memory cases through recursion
	public static void testCombinations(String testString, List<Integer> indexList, String memoryIndex, String insertValue) {
		StringBuilder newString = new StringBuilder(testString);
		if(indexList.size() != 0) {
		for (int i = 0; i<2; i++) {
			char c=Character.forDigit(i,10);   
			newString.setCharAt(indexList.get(0), c);
			if(indexList.size() ==1) {
				memorySpaceTwo.put(newString.toString(), insertValue);
			}
			else {
				testCombinations(newString.toString(), indexList.subList(1, indexList.size()), memoryIndex, insertValue);
				}
			} 
		 }
	}
	
	// Part one calculate the sum
	public static long memorySum(ArrayList<String> memoryLines) {
		String filterMask = "";
		 for(String line : memoryLines) {
			 String[] order = line.replace("mem","").replace("[", "").replace("]", "").trim().split("=");
			 if(order[0].trim().equals("mask")){
				 filterMask = order[1].trim();
			 } else {
				 String binaryNumber = Integer.toBinaryString(Integer.parseInt(order[1].trim()));
				 String memoryInputString = BINARY_ZERO_LEN_32.substring(binaryNumber.length()) + binaryNumber;
				 String filteredValue = maskValue(memoryInputString, filterMask);
				 memorySpace.put(order[0].trim(), filteredValue);
			 }
		 }
		 return memorySpace.keySet().stream().mapToLong(key -> Long.parseLong(memorySpace.get(key), 2)).sum();
	}
	
	// Part two calculate the sum
	public static long memoryMultiSum(ArrayList<String> memoryLines) {
		String filterMask = "";
		 for(String line : memoryLines) {
			 String[] order = line.replace("mem","").replace("[", "").replace("]", "").trim().split("=");
			 if(order[0].trim().equals("mask")){
				 filterMask = order[1].trim();
			 } else {
				 String binaryNumber = Integer.toBinaryString(Integer.parseInt(order[0].trim()));
				 String memoryInputString = BINARY_ZERO_LEN_32.substring(binaryNumber.length()) + binaryNumber;
				 String binaryValue = Integer.toBinaryString(Integer.parseInt(order[1].trim()));
				 String memoryBinaryInputString = BINARY_ZERO_LEN_32.substring(binaryValue.length()) + binaryValue;
				 String filterString = maskMultiValues(memoryInputString, filterMask);
				 updateMemoryKey(filterString, order[0].trim(), memoryBinaryInputString);
			 }
		 }
		 
		 return memorySpaceTwo.keySet().stream().mapToLong(key -> Long.parseLong(memorySpaceTwo.get(key), 2)).sum();
	}
	
	public static void main(String[] args) throws IOException {
		 ArrayList<String> lines = DataHandling.readStringsBySeparatorRaw("inputFiles/inputDay14.txt");
		 ArrayList<String> testLines = DataHandling.readStringsBySeparatorRaw("inputFiles/inputDay14.txt");
		 System.out.println("Part One solution is:"+memorySum(lines));
		  
		 System.out.println("Part Two solution is: "+memoryMultiSum(testLines));
		 }
	
}

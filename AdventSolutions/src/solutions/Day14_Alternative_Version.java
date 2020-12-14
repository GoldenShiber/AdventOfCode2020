package solutions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import utils.DataHandling;

public class Day14_Alternative_Version {

	/* 
	 * This was the first version, which was some numbers wrong and didn't utilize the smart
	 * Bit-calculator version and used an own method.
	 * Was fun though!
	 */
	
	public static HashMap<Integer, String> memoryMap = new HashMap<>();
	public static HashMap<Integer, Long> memorySumMap = new HashMap<>();
	
	public static void memoryOperation(String bitmask, String memoryData, int memoryIndex) {
		if(!memoryMap.containsKey(memoryIndex)) {
			memoryMap.put(memoryIndex, new String(new char[36]).replace('\0', '0'));
		}
		int oneIndex = -1;
		int testIndex = 0;
		
		StringBuilder memoryString = new StringBuilder(memoryMap.get(memoryIndex));
		
		if (memoryData.length() == 0) {
			memoryString = new StringBuilder(new String(new char[36]).replace('\0', '0'));
		}
		for(int i = 0; i < memoryData.length(); i++) {
			memoryString.setCharAt(memoryString.length()-1-i, memoryData.charAt(memoryData.length()-1-i));
		}
		
		for(int i = 0; i < bitmask.length(); i++) {
			if(bitmask.charAt(i) != 'X') {
				memoryString.setCharAt(i, bitmask.charAt(i));
			}
		}
		
		
		memoryMap.put(memoryIndex, memoryString.toString());
		memorySumMap.put(memoryIndex, bitCalculator(memoryString.toString()));
	}
	
	public static String memoryConverter(long value) {
		int index = 0;
		long bitMultiplier = 1;
		long remainder = value%2;
		long currentValue = value;
		ArrayList<Integer> indexList = new ArrayList<>();
		String memoryString = new String(new char[36]).replace('\0', '0');
		StringBuilder memoryBuilder = new StringBuilder(memoryString);
		int maxIndex = 0;
		if(value == 0) {
			
		}
		while(bitMultiplier <= currentValue) {
			if(remainder != 0) {
			index++;
			
			if(bitMultiplier*2 >= currentValue) {
				if (maxIndex == 0) {
					maxIndex = index;
				}
				currentValue -= bitMultiplier;
				remainder %=2;
				indexList.add(index);
				index = 0;
				bitMultiplier = 1;
			} else {
				bitMultiplier*=2;
			}
			} else {
				break;
			}
		}
		for(int memoryIndex : indexList) {
			memoryBuilder.setCharAt(memoryBuilder.length()-memoryIndex, '1');
		}
		return memoryBuilder.toString().substring(memoryBuilder.length()-maxIndex);
	}
	
	public static long bitCalculator(String bitvalues) {
		long value = 0;
		long bitMultiplier = 1;
		int index = 0;
		for(int i = bitvalues.length()-1; i != -1; i--) {
			if(bitvalues.charAt(i) == '1') {
				value += bitMultiplier;
			}
			index++;
			bitMultiplier *=2;
		}
		
		return value;
	}
	
	
	public static void main(String[] args) throws IOException {
		String filterMask = "";
//		ArrayList<String> lines = DataHandling.readStringsBySeparatorRaw("inputFiles/testData14.txt");
		 ArrayList<String> lines = DataHandling.readStringsBySeparatorRaw("inputFiles/inputDay14.txt");
		 for(String line : lines) {
			 String[] order = line.replace("mem","").replace("[", "").replace("]", "").trim().split("=");
			 if(order[0].trim().equals("mask")){
				 filterMask = order[1].trim();
			 } else {
//				 String latestMemory = memoryOperation(filterMask, memoryConverter(Long.parseLong(order[1].trim())), Integer.parseInt(order[0].trim()));
//				 memoryMap.put(Integer.parseInt(order[0]), memoryOperation(filterMask, memoryData, memoryIndex))
//				 memorySumMap.put(Integer.parseInt(order[0].trim()),
				memoryOperation(filterMask, memoryConverter(Long.parseLong(order[1].trim())), Integer.parseInt(order[0].trim()));
			 }
		 }
		  
		 long testSum = memorySumMap.values().stream().reduce((long)0, Long::sum);
		 System.out.println("The test sum is: "+ testSum);
		 
		 }
	
}

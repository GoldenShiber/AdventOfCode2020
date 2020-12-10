package solutions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import utils.DataHandling;

public class Day10 {

	
	 private static HashMap<Integer, List<Integer>> potentialMap = new HashMap<>();
	
	public static class IntegerCompareitor implements Comparator<Integer> {

		@Override
		public int compare(Integer arg0, Integer arg1) {
			return Integer.compare(arg0, arg1);
		}

	}
	
	public static HashMap<Integer, Integer> fillDifferenceList(List<Integer> numberList){
		HashMap<Integer, Integer> differenceList = new HashMap<>();
		int startJoltage = 0;
		for(int i = 0; i < numberList.size(); i++) {
			int difference = numberList.get(i)- startJoltage;
			int count = differenceList.containsKey(difference) ? differenceList.get(difference) : 0;
			differenceList.put(difference, count +1);
			startJoltage = numberList.get(i);
		}
		int count = differenceList.containsKey(3) ? differenceList.get(3) : 0;
		differenceList.put(3, count +1);
		
		return differenceList;
	}
	
	
	public static long calculateVariantsThree(int value) {
		List<Integer> potentialList = potentialMap.get(value);
		long count = 0;
		if(potentialList.size() != 0) {
			for(Integer number : potentialList) {
				count += calculateVariantsThree(number);
			}
		}
		else {
			return 1; }
		return count;
	}
	
	public static void main(String[] args) throws IOException {
		 List<String> lines = DataHandling.readStringsBySeparator("inputFiles/inputDay10.txt");
		 List<Integer> numberList = new ArrayList<>();
		 lines.forEach(line -> numberList.add(Integer.parseInt(line)));
		 IntegerCompareitor intCompare = new IntegerCompareitor();
		 Collections.sort(numberList, intCompare);
		 HashMap<Integer, Integer> differenceList = new HashMap<>();
		 int[] tribonacci = {0, 1, 1, 2, 4, 7, 13};
		 
		 // The first solution used a recursive method to find all permutations of chargers, which does not work 
		 // with long lists!
		 numberList.add(0, 0);
		 for(int number: numberList) {
			 int start = numberList.indexOf(number);
			 int limit = (number +4 > numberList.size() ) ? numberList.size() : number + 4;
			 List<Integer> valueList = new ArrayList<>();
			 for(int i = start+1; i < limit; i++) {
				 int value = numberList.get(i);
				 if(value > number && value < (number+4)) {
						valueList.add(value);
					}
			 }
			 potentialMap.put(number, valueList);
		 }
		 
		 
		 differenceList = fillDifferenceList(numberList);
		 System.out.println("The total joltage score is: "+ (differenceList.get(1)*differenceList.get(3)));
		 numberList.add(numberList.get(numberList.size()-1)+3);
		 int prev = 0;
	        long result = 1;
	        int consecutiveCount = 1;
	        
	        for(int number : numberList){
	            if (number == prev+1){
	                consecutiveCount++;
	            }
	            else{
	                result *= tribonacci[consecutiveCount];
	                consecutiveCount = 1;
	            }
	            prev = number;
	        }
	        System.out.println("The smart method gives part 2 solution of: "+(long)result);

		 
		 System.out.println("The reursive method gives the answer: "+ calculateVariantsThree(0));
		 }
		
}

package solutions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import utils.DataHandling;



public class Day9 {

	
	/*
	 * This is a fun pair class, made when misunderstanding part 2, however, could be used later.
	 */
	
	static class Pair{
		double value1;
		double value2;
		
		Pair(double minValue, double maxValue){
			this.value1 = minValue;
			this.value2 = maxValue;
		}
		
		public double getMinValue() {
			return Math.min(value1, value2);
		}
		public double getMaxValue() {
			return Math.max(value1, value2);
		}
		
		public void print() {
			System.out.println("The pair of biggest weakness is: [" +getMinValue() + ", " + getMaxValue()+"]");
		}
	}
	
	public static boolean pairRule(int index, int preAmble, List<Double> numberList) {
		for(int i = index-preAmble; i < index; i++) {
			if(numberList.get(i) < numberList.get(index)) {
				for (int j = index-preAmble; j < index; j++) {
					if (j != i && (numberList.get(i) + numberList.get(j) == numberList.get(index))){
						return false;
					}
				}
			}
		}
		return true;
	}
	
	// This is not the weakness, however in the situation where you find smallest pairs it is nice!
	public static Pair findWeakness(int index, List<Double> numberList) {
		Pair lowestPair = new Pair(Double.MAX_VALUE, Double.MAX_VALUE);
		ArrayList<Integer> usedIndexList = new ArrayList<>();
		double targetValue = numberList.get(index);
		for(int i = 0; i < numberList.size(); i++) {
			if(i != index && !usedIndexList.contains(i) && numberList.get(i)<targetValue) {
				for(int j = i+1; j < numberList.size(); j++) {
					if(j != index && (numberList.get(i)+ numberList.get(j))== targetValue) {
						Pair testPair = new Pair(numberList.get(i), numberList.get(j));
						lowestPair  = (lowestPair.getMinValue() > testPair.getMinValue()) ? testPair : lowestPair;
						usedIndexList.add(i);
						usedIndexList.add(j);
					}
				}
			}
		}
		lowestPair.print();
		return lowestPair;
	}
	
	public static Pair realPart2Weakness(int index, List<Double> numberList) {
		double targetValue = numberList.get(index);
		int startIndex = 0;
		double sum = 0;
		ArrayList<Double> valueSet = new ArrayList<Double>();
		for(int i = 0; i< numberList.size(); i++) {
			startIndex = i;
			while (sum < targetValue) {
				valueSet.add(numberList.get(startIndex));
				sum += numberList.get(startIndex);
				startIndex++;
				
			}
			if(sum == targetValue) {
				Pair weaknessPair = new Pair(Collections.min(valueSet), Collections.max(valueSet));
				return weaknessPair;
			} 
			sum = 0;
			valueSet = new ArrayList<Double>();
		}
		return new Pair(0, 1);
	}
	
	public static void main(String[] args) throws IOException {
		 List<String> lines = DataHandling.readStringsBySeparator("inputFiles/inputDay9.txt");
		 List<Double> numberList = new ArrayList<>();
		 lines.forEach(line -> numberList.add(Double.parseDouble(line)));
		 
		 int index = 25;
		 int preambleValue = 25;
		 boolean firstNumberBad = false;
		 
		 while(!firstNumberBad) {
			 index++;
			 firstNumberBad = pairRule(index, preambleValue, numberList);
		 }
		 System.out.println("Invalid number which is!: "+ lines.get(index));
		 Pair weaknessPair = realPart2Weakness(index, numberList);
		 System.out.println("==============================");
		 System.out.println("The weakness value is:" + (weaknessPair.value1 + weaknessPair.value2));
		 }
		
}

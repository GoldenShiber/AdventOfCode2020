package solutions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import utils.DataHandling;

public class Day13 {
	
	public static HashMap<Double, Double> busMap = new HashMap<>();
	public static HashMap<Double, Double> busRuleMap = new HashMap<>();
	public static HashMap<Double, Double> chineseMap = new HashMap<>();
	
	public static boolean busRule(long timeStamp, double value, double offset) {
		return (timeStamp%value)-(offset) == 0;
	}
	
	public static long problemTwo(ArrayList<Double> numberList) {
		boolean status = false;
		double maxValue = Collections.max(numberList);
		double compareValue = -(maxValue - busRuleMap.get(maxValue));
		long longCompare = Double.valueOf(compareValue).longValue();
		while(!status) {
			for(Double number : numberList) {
				if(!busRule(longCompare, number, busRuleMap.get(number)) && (number != maxValue)) {
					status = false;
					break;
				}
				status = true;
			}
			if (status) {
				return longCompare;
			}
			longCompare += maxValue;
		}
		return longCompare;
	}
	
	public static int problemOne(double earlyValue, ArrayList<Double> numbers) {
		double difference = Double.MAX_VALUE;
		double bus = 0;
		for(Double number : numbers) {
			if(number -earlyValue < difference) {
				difference = number -earlyValue;
				bus = busMap.get(number);
			}
		}
		return (int) (difference * bus);
	}
	
	public static void main(String[] args) throws IOException {
		 ArrayList<String> lines = DataHandling.readStringsBySeparatorRaw("inputFiles/inputDay13.txt");
		 ArrayList<String> busList = new ArrayList<>();
		 double earliestNumber = Double.parseDouble(lines.get(0));
		 ArrayList<Double> busList2 = new ArrayList<>();
		 String[] busses = lines.get(1).split(",");
		 
		 for(int i = 0; i < busses.length; i++) {
			 if(!busses[i].equals("x")) {
				 busList.add(busses[i]);
				 busRuleMap.put(Double.parseDouble(busses[i]), Double.parseDouble(busses[i]) - (double)i);
				 chineseMap.put(Double.parseDouble(busses[i]), (double)-i);
			 }
		 }
		 
		 busRuleMap.put(Double.parseDouble(busses[0]), 0.);
		 
		 for(String bus: busList) {
			 double busValue = Double.parseDouble(bus);
			 double value = Double.parseDouble(bus);
			 while(value <= earliestNumber) {
				 value += busValue;
			 }
			 busList2.add(value);
			 busMap.put(value, busValue);
		 }
		 
		 System.out.println("The first available time is: "+problemOne(earliestNumber, busList2));
		 
		 System.out.println("Starting part 2: If it takes too much time, use the Chinese Remainder Theorem");
		 System.out.println("Follow the chinese remainder theorem rules, with inputs from the chinese map!");
		 System.out.println("https://www.dcode.fr/chinese-remainder is a tool here which is easy to use.");
		 System.out.println("For example, 16, 17, from input means -> [Remainder = 0, mod 16] and [Remainder = -1, mod 17] ");
		 ArrayList<Double> numberList = new ArrayList<>();
		 for(String line : busList) {
			 numberList.add(Double.parseDouble(line));
		 }
		 System.out.println(problemTwo(numberList));
		 System.out.println("");
		 }
}


package solutions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import utils.DataHandling;

public class Day16 {

	
	private static ArrayList<ArrayList<String>> ruleList = new ArrayList<>();
	private static ArrayList<String> ticketList = new ArrayList<>();
	private static HashMap<Integer, String> initialMap = new HashMap<>();
	private static int errorValue = 0;
	private static HashMap<String, ArrayList<Integer>> uniqueList = new HashMap<String, ArrayList<Integer>>();
	public static HashMap<String, Integer> finalFieldList = new HashMap<>();
	
	public static void ticketHandling(String line) {
		boolean validTicket = true;
		ArrayList<String> ticketInformation = new ArrayList<>();
		 String[] information = line.split(",");
		 for(int i = 0; i < information.length; i++) {
			 ticketInformation.add(information[i].trim());
			 if(!ticketValidation(i, information[i])) {
				 errorValue += Integer.parseInt(information[i]);
				 validTicket = false;
			 }
		 }
		 if(validTicket) {
			 ticketList.add(line);
		 }
	}
	
	public static boolean ruleValidation(int index, ArrayList<String> ruleSet) {
		boolean validated = false;
//		ArrayList<String> rule = ruleList.get(index);
		ArrayList<String> rule = ruleSet;
		int startValue = 0; int endValue = 0; int value = 0;
		try {
			for(String ticket : ticketList) {
				String[] ticketInfo = ticket.split(",");
				value = Integer.valueOf(ticketInfo[index]);
				validated = false;
				for(String ruleInterval : rule) {
					String interval[] = ruleInterval.split("-");
					startValue = Integer.parseInt(interval[0]);
					endValue = Integer.parseInt(interval[1]);
					if(value <= endValue && value >= startValue) {
						validated = true;
						break;
					}
				}
				if (!validated) {
					return false;
				}
			}
		} catch (Exception e) {
			System.out.println("HellO!");
		}
		
		return validated;
	}
	
	public static void classifyFields() {
		int columnIndex = 0;
		for(ArrayList<String> testArray : ruleList) {
			ArrayList<Integer> integerList = new ArrayList<Integer>();
			for(int i = 0; i < ruleList.size(); i++) {
				if(ruleValidation(i, testArray)) {
					integerList.add(i);
				}
			}
			uniqueList.put(initialMap.get(columnIndex), integerList);
			columnIndex++;
		}
		
		while(uniqueList.size() > 0) { 
		int min = Integer.MAX_VALUE;
		String currentString = "";
		for(String mapString : uniqueList.keySet()) {
			if(uniqueList.get(mapString).size() < min) {
				currentString = mapString;
				min = uniqueList.get(mapString).size();
			}
		}
		
		int fieldAttribute = uniqueList.get(currentString).get(0);
		finalFieldList.put(currentString, fieldAttribute);
		uniqueList.remove(currentString);
		
		for(String uniqueID : uniqueList.keySet()) {
			ArrayList<Integer> testList = new ArrayList<>(uniqueList.get(uniqueID));
			for(int i = 0; i<testList.size(); i++) {
				if(testList.get(i)== fieldAttribute) {
					testList.remove(i);
					break;
				}
			}
			uniqueList.put(uniqueID, testList);
		}
		}
	}
	
	public static boolean ticketValidation(int index, String location) {
//		ArrayList<String> rules = ruleList.get(index);
		int startValue = 0; int endValue = 0; int value = Integer.parseInt(location);
		for(ArrayList<String> ticketRules : ruleList) {
			for(String ruleInterval : ticketRules) {
				String interval[] = ruleInterval.split("-");
				startValue = Integer.parseInt(interval[0]);
				endValue = Integer.parseInt(interval[1]);
				if(value <= endValue && value >= startValue) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static void main(String[] args) throws IOException {
		 ArrayList<String> lines = DataHandling.readStringsBySeparatorRaw("inputFiles/inputDay16.txt");
		 int mode = 0;
		 String myTicket = "";
		 int ruleIndex = 0;
		 for(String line : lines) {
			 
			 if(line.contains("your ticket")) {
				 mode = 1;
				 continue;
			 }
			 
			 if(line.contains("nearby tickets")) {
				 mode = 2;
				 continue;
			 }
			 if(line.length() != 0) {
				 if (mode == 0) {
					 String[] stringRules = line.split(":");
					 String ruleName = stringRules[0];
					 initialMap.put(ruleIndex, ruleName);
					 ruleIndex++;
					 String[] intervals = stringRules[1].trim().replace(" ", "").split("or");
					 ArrayList<String> intervalList = new ArrayList<>();
					 for(String interval : intervals) {
						 intervalList.add(interval);
					 }
					 ruleList.add(intervalList);
				 }
				 
				 if(mode == 1) {
					 myTicket = line;
					 ticketList.add(myTicket);
				 }
				 
				 if(mode == 2) {
					 ticketHandling(line);
				 }
			 }
		 }
		 // Part 2 starts
		 classifyFields();
		 long sum = 1;
		 String[] ticketInformation = myTicket.split(",");
		 for(String column : finalFieldList.keySet()) {
			 if(column.contains("departure")) {
				 sum *= Long.parseLong(ticketInformation[finalFieldList.get(column)]);
			 }
		 }
		 System.out.println("The total error for part 1 is: "+errorValue);
		 System.out.println("The sum for my ticket is: "+ sum);
	}
}

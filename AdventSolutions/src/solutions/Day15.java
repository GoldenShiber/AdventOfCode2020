package solutions;

import java.io.IOException;
import java.util.HashMap;

public class Day15 {

	public static HashMap<Integer, Integer> lastMentionMap = new HashMap<>();
	public static HashMap<Integer, Boolean> firstTimeMentioned = new HashMap<>();
	private static int[] data = {1,0,15,2,10,13};

	public static int memoryPartOne(int[] startingNumbers, int turnCondition) {
		firstTimeMentioned = new HashMap<>();
		lastMentionMap = new HashMap<Integer, Integer>();
		int turn = 0;
		
		for(int number : startingNumbers) {
			turn++;
			numberInstrictions(turn, number);
		}
		int numberSpoken = 0;
		while(turn < turnCondition-1) {
			turn++;
			numberSpoken = numberInstrictions(turn, numberSpoken);
		}
		return numberSpoken;
	}

	public static int numberInstrictions(int turn, int number) {
		if (firstTimeMentioned.containsKey(number)){
			int newValue = turn - lastMentionMap.get(number);
			lastMentionMap.put(number, turn);
			return newValue;
			
		} else {
			firstTimeMentioned.put(number, true);
			lastMentionMap.put(number, turn);
			return 0;
		}
	}
	
	
	public static void main(String[] args) throws IOException { 
		System.out.println("The Solution for part one is: "+ memoryPartOne(data, 2020));
		System.out.println("The Solution for part one is: "+ memoryPartOne(data, 30000000));
	}
	
}

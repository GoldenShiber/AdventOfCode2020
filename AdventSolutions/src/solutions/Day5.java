package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;


public class Day5 {

	private static String inputLine;
	
	private static ArrayList<Integer> boardingPasses = new ArrayList<>();
	
	public static double boardingPass(String instructions) {
		double rowMin = 0;
		double rowMax = 127;
		for(int i = 0; i < instructions.length()-3; i++) {
			double interval = rowMax - rowMin;
			if(instructions.charAt(i) == 'B') {
				rowMin = (int) rowMin + Math.ceil(interval/2);
			} else {
				rowMax =  (int) rowMax - Math.ceil(interval/2);
			}
		}
		double colMin = 0;
		double colMax = 7;
		for(int i = instructions.length()-3; i < instructions.length(); i++) {
			double interval = colMax - colMin;
			if(instructions.charAt(i) == 'R') {
				colMin = (int) colMin + Math.ceil(interval/2);
			} else {
				colMax =  (int) colMax - Math.ceil(interval/2);
			}
		}
		return rowMax*8+colMax;
	}
	
	public static int minIndex (ArrayList<Integer> list) {
		  return list.indexOf(Collections.min(list)); }
	
	public static void main(String[] args) throws IOException {
		URL inputURL = Day1.class.getClassLoader().getResource("inputFiles/inputDay5.txt");
		BufferedReader inFile = new BufferedReader(
				new InputStreamReader(inputURL.openStream()));
		double value = 0;
        while ((inputLine = inFile.readLine()) != null) {
        	double testValue = boardingPass(inputLine);
        	if(testValue > 7 && testValue < 1016) {
        		boardingPasses.add((int)testValue);
        	}
        	value = (testValue > value) ? testValue:value;
        	
        }

        for(int i = boardingPasses.get(minIndex(boardingPasses)); i <1016; i++) {
        	if(!boardingPasses.contains(i)) {
        		System.out.println("My boarding pass is: " + i);
        		break;
        	}
        }
        System.out.println("The biggest boarding pass value is: "+ value);
        inFile.close();
}
}

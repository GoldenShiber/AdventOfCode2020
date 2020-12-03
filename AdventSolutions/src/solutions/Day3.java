package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class Day3 {

	private static String inputLine;
	
	
	public static int rowSize(String mapMpathUrl) throws IOException {
		URL inputURL = Day1.class.getClassLoader().getResource("inputFiles/inputDay3.txt");
		BufferedReader inFile = new BufferedReader(
				new InputStreamReader(inputURL.openStream()));
		int rows = 0;
        while ((inputLine = inFile.readLine()) != null) {
        	rows ++;
        	}
        inFile.close();
        return rows;
	}
	
	public static int slopeCourse(ArrayList<String> course , int rowSteps, int colSteps) {
		int treeCounter = 0;
		int colIndex = 0;
		for(int i = 0; i < course.size(); i+=rowSteps) {
        	if(course.get(i).charAt(colIndex) == '#') {
        		treeCounter++;
        	}
        	colIndex+=colSteps;
        	if(colIndex >= course.get(i).length()) {
        		colIndex = colIndex-course.get(i).length();
        	}
        	
        }
		return treeCounter;
	}
	
	
	public static void main(String[] args) throws IOException {
		URL inputURL = Day1.class.getClassLoader().getResource("inputFiles/inputDay3.txt");
		BufferedReader inFile = new BufferedReader(
				new InputStreamReader(inputURL.openStream()));

		System.out.println("Number of Iterations are: "+ rowSize("inputFiles/inputDay3.txt"));
		ArrayList<String> patternMap = new ArrayList<String>();
        while ((inputLine = inFile.readLine()) != null) {
        	patternMap.add(inputLine);
        	}
        
        System.out.println("The number of tree encounters for the first part is: "+slopeCourse(patternMap, 1, 3));
        
        int multipleTreeTheory = 1;
        multipleTreeTheory *= slopeCourse(patternMap, 1, 1);
        multipleTreeTheory *= slopeCourse(patternMap, 1, 3);
        multipleTreeTheory *= slopeCourse(patternMap, 1, 5);
        multipleTreeTheory *= slopeCourse(patternMap, 1, 7);
        multipleTreeTheory *= slopeCourse(patternMap, 2, 1);
        
        System.out.println("The number of tree encounters possible for the multiple paths is: "+multipleTreeTheory);
        
        inFile.close();
	}
}

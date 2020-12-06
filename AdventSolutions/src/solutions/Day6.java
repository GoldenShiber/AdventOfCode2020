package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;


public class Day6 {

	private static String inputLine;
	private static ArrayList<Character> answerBox = new ArrayList<>(); 
	
	public static String similiarAnswer(String answers1, String answers2) {
		String newAnswer = "";
		for(int i = 0; i < answers2.length(); i++) {
			if(answers1.indexOf(answers2.charAt(i)) != -1) {
				newAnswer += answers2.charAt(i);
			}
		}
		return newAnswer;
	}
	
	public static void main(String[] args) throws IOException {
		URL inputURL = Day1.class.getClassLoader().getResource("inputFiles/inputDay6.txt");
		BufferedReader inFile = new BufferedReader(
				new InputStreamReader(inputURL.openStream()));
		int realScore = 0;
		boolean newGroup = true;
		int currentScore = 0;
		String realAnswer = "";
        while ((inputLine = inFile.readLine()) != null) {
        	if(!inputLine.equals("")) {
        		for(int i = 0; i < inputLine.length(); i++) {
        			if(!answerBox.contains(inputLine.charAt(i))) {
        				answerBox.add(inputLine.charAt(i));
        			}
        		}
        		if(realAnswer.length() == 0 && newGroup) {
    				realAnswer = inputLine;
    				newGroup = false;
    			} else {
    				realAnswer = similiarAnswer(realAnswer, inputLine);
    			}
        	}else {
        		currentScore += answerBox.size();
        		realScore += realAnswer.length();
        		answerBox = new ArrayList<>();
        		realAnswer = "";
        		newGroup = true;
        		
        	}
        }
        currentScore += answerBox.size();
        realScore += realAnswer.length();
        
        System.out.println("Part A score is: "+currentScore);
        System.out.println("Part B score is: "+realScore);
        inFile.close();
	}

}
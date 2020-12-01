package solutions;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

import utils.DataHandling;

public class Day1 {

	private static String inputLine;
	private static ArrayList<Integer> moneyList = new ArrayList<>();
	private static long startTime;
	private static long endTime;
	
	public static int expenseReportTwice(ArrayList<Integer> expenseList, int targetValue) {
		for (int i = 0; i < expenseList.size(); i++) {
			for (int j = i+1; j < expenseList.size(); j++) {
				if (expenseList.get(i) + expenseList.get(j) == targetValue) {
					return expenseList.get(i) * expenseList.get(j);
				}
			}
			
		}
		return 0;
	}
	
	public static int expenseReportTrice(ArrayList<Integer> expenseList, int targetValue) {
		for (int i = 0; i < expenseList.size(); i++) {
			for (int j = i+1; j < expenseList.size(); j++) {
				for (int k = j+1; k < expenseList.size(); k++) {
				if (expenseList.get(i) + expenseList.get(j) +  expenseList.get(k) == targetValue) {
					return expenseList.get(i) * expenseList.get(j) * expenseList.get(k);
				}
				}
			}
			
		}
		return 0;
	}
	
	public static void main(String[] args) throws IOException {
		URL inputURL = Day1.class.getClassLoader().getResource("inputFiles/inputDay1.txt");
		BufferedReader inFile = new BufferedReader(
				new InputStreamReader(inputURL.openStream()));

        while ((inputLine = inFile.readLine()) != null) {
        	if (DataHandling.isNumeric(inputLine)){
        		moneyList.add(Integer.parseInt(inputLine));
        		}
        	 {
        	}
        	}
        startTime = System.nanoTime();
        System.out.println("The two entries to together sums up to 2020 multiplies to:" + expenseReportTwice(moneyList, 2020));
        endTime = System.nanoTime();
        System.out.println("Part one of the problem took "+ ((endTime-startTime)/100000) + " milliseconds");
        
        startTime = System.nanoTime();
        System.out.println("The three entries to together sums up to 2020 multiplies to:" + expenseReportTrice(moneyList, 2020));
        endTime = System.nanoTime();
        System.out.println("Part two of the problem took "+ ((endTime-startTime)/100000) + " milliseconds");
        
        inFile.close();
	}
	
	
}

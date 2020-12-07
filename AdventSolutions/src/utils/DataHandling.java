package utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import solutions.Day1;
	
	
public class DataHandling {

	private static String inputLine;
	
	public static boolean isNumeric(String str) { 
	  try {  
	    Double.parseDouble(str);  
	    return true;
	  } catch(NumberFormatException e){  
	    return false;  
	  }  
	}

// Generic function to convert List of 
	// String to List of Integer 
	public static <T, U> List<U> 
	convertStringListToIntList(List<T> listOfString, 
	                           Function<T, U> function) 
	{ 
	    return listOfString.stream() 
	        .map(function) 
	        .collect(Collectors.toList()); 
	} 

	public static void printArrayList(ArrayList<String> list, int startindex, int endindex) {
		for (int i = startindex; i < endindex; i++) {
			System.out.println(list.get(i));
		}
	}
	
	public static int minIndex (ArrayList<Integer> list) {
		  return list.indexOf(Collections.min(list)); }
	
	
	public static ArrayList<String> readStringsBySeparator(String fileName) throws IOException{
		ArrayList<String> newList = new ArrayList<>();
		URL inputURL = DataHandling.class.getClassLoader().getResource(fileName);
		BufferedReader inFile = new BufferedReader(
				new InputStreamReader(inputURL.openStream()));
		while ((inputLine = inFile.readLine()) != null) {
        	inputLine = inputLine.replace(".", "");
        	newList.add(inputLine);
        }
		
		return newList;
	}
}

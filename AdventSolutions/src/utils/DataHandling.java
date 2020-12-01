package utils;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
	
	
public class DataHandling {


	
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
	
}

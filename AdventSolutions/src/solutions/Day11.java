package solutions;

import java.io.IOException;
import java.util.ArrayList;


import utils.DataHandling;

public class Day11 {

	public static ArrayList<String> rule(ArrayList<String> planeMap, ArrayList<String> fillMap, int row, int col) {
		int adjacent = 0;
		int rowMin = (row -1 >= 0) ? row -1 : 0;
		int rowMax = (row +1 < planeMap.size()) ? row + 2 : planeMap.size();
		int colMin = (col -1 >= 0) ? col -1 : 0;
		int colMax = (col + 1 < planeMap.get(0).length()) ? col  + 2 : planeMap.get(0).length();
			char charLabel = planeMap.get(row).charAt(col);
			StringBuilder rebuiltString = new StringBuilder(fillMap.get(row));
			
			for(int i = rowMin; i < rowMax; i++) {
				for(int j = colMin; j < colMax; j++) {
					if(planeMap.get(i).charAt(j) == '#') {
						adjacent++;
					}
				}
			}
			if(charLabel == 'L' && adjacent == 0) {
				rebuiltString.setCharAt(col, '#');
				
			} else if(charLabel == '#' && adjacent > 4) {
				rebuiltString.setCharAt(col, 'L');
			}
			fillMap.set(row, rebuiltString.toString());
		return fillMap;
	}
	
	public static ArrayList<String> updateMap(ArrayList<String>oldMap){
		ArrayList<String> newMap = new ArrayList<>(oldMap);
		for(int i = 0; i < oldMap.size(); i++) {
			for( int j = 0; j < oldMap.get(0).length(); j++) {
				newMap = rule(oldMap, newMap, i, j);
			}
		}
		return newMap;
	}
	
	public static ArrayList<String> updateMap2(ArrayList<String>oldMap){
		ArrayList<String> newMap = new ArrayList<>(oldMap);
		for(int i = 0; i < oldMap.size(); i++) {
			for( int j = 0; j < oldMap.get(0).length(); j++) {
				newMap = rule2(oldMap, newMap, i, j);
			}
		}
		return newMap;
	}
	
	public static int countOccupied(ArrayList<String> finalMap) {
		int count = 0;
		
		for(String line : finalMap) {
			for(int i= 0; i < line.length(); i++) {
				if(line.charAt(i)== '#') {
					count ++;
				}
			}
		}
		
		return count;
	}
	
	public static ArrayList<String> rule2(ArrayList<String> planeMap, ArrayList<String> fillMap, int row, int col) {
		int adjacent = 0;
		char charLabel = planeMap.get(row).charAt(col);
		StringBuilder rebuiltString = new StringBuilder(fillMap.get(row));

		if(charLabel == '#' || charLabel == 'L') {
			ArrayList<ArrayList<Integer>> directionsIndexes = new ArrayList<>();
			for(int i = 0; i < 8; i++) {
				ArrayList<Integer> path = new ArrayList<Integer>();
				path.add(row);
				path.add(col);
				directionsIndexes.add(directionRules(i, path));
				boolean nonStop = true;
				while(nonStop) {
					try {
						path = (directionRules(i, path));
						char dirChar = planeMap.get(path.get(0)).charAt(path.get(1));
						if(dirChar != '.') {
							if(dirChar == '#') {
								adjacent++;
							}
							nonStop = false;
						}
					} catch (Exception e) {
						nonStop = false;					}
				}
			}
			if(adjacent >= 5 && charLabel == '#') {
				rebuiltString.setCharAt(col, 'L');
			} else if(adjacent == 0 && charLabel == 'L') {
				rebuiltString.setCharAt(col, '#');
			}
		}
		fillMap.set(row, rebuiltString.toString());
		return fillMap;
	}
	
	public static ArrayList<Integer> directionRules(int direction, ArrayList<Integer> coordinate){
		ArrayList<Integer> newCoordinate = new ArrayList<Integer>();
		switch(direction) {
			case 0:
				newCoordinate.add(coordinate.get(0) -1);
				newCoordinate.add(coordinate.get(1) -1);
				break;
			case 1: 
				newCoordinate.add(coordinate.get(0)-1);
				newCoordinate.add(coordinate.get(1));
				break;
			case 2: 
				newCoordinate.add(coordinate.get(0) -1);
				newCoordinate.add(coordinate.get(1) +1);
				break;
			case 3: 
				newCoordinate.add(coordinate.get(0));
				newCoordinate.add(coordinate.get(1) +1);
				break;
			case 4: 
				newCoordinate.add(coordinate.get(0) +1);
				newCoordinate.add(coordinate.get(1) +1);
				break;
			case 5: 
				newCoordinate.add(coordinate.get(0) +1);
				newCoordinate.add(coordinate.get(1));
				break;
			case 6: 
				newCoordinate.add(coordinate.get(0) +1);
				newCoordinate.add(coordinate.get(1) -1);
				break;
			case 7: 
				newCoordinate.add(coordinate.get(0));
				newCoordinate.add(coordinate.get(1) -1);
				break;
		}
		
		return newCoordinate;
	}
	
	public static void main(String[] args) throws IOException {
		 ArrayList<String> lines = DataHandling.readStringsBySeparatorRaw("inputFiles/inputDay11.txt");
		 ArrayList<String> oldMap = new ArrayList<>(lines);
		 ArrayList<String> oldMap2 = new ArrayList<>(lines);
		 ArrayList<String> newMap = updateMap(oldMap);
		 ArrayList<String> newMap2 = updateMap(oldMap2);
		 while(!oldMap.equals(newMap)) {
			 oldMap = newMap;
			 newMap = updateMap(newMap);
		 }
		 System.out.println("The answer for part 1 is: "+countOccupied(newMap));
		 while(!oldMap2.equals(newMap2)) {
			 oldMap2 = newMap2;
			 newMap2 = updateMap2(newMap2);
		 }
		 System.out.println("The answer for part 2 is: "+countOccupied(newMap2));
		 }
}

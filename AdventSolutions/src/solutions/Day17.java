package solutions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


import utils.DataHandling;

public class Day17 {

	/*
	 * Setup:
	 * 0. Cube consists of an ArrayList, of an ArrayList of an ArrayList
	 * 0.1 For example: ArrayList<ArrayList<ArrayList<String>>> Cube since it is easy to copy.
	 * 
	 * 1. Create a cube updater
	 * 1.1 Increase the dimension of the new cube by 1 in each direction, in each dimension.
	 * The new places of the cube starts as inactive
	 * 1.2 X-dim -> i to [i-1, i+1]
	 * 1.3 y-dim -> j to [j-1, j+1]
	 * 1.3 z-dim -> k to [k-1, k+1]
	 * 
	 * 2. Create a rule function, which looks at the new cube.
	 * If active (#) and (2 or 3 other active # neighbors) keep being #, othervise inactive (.)
	 * If Inactive (.) and has 3 active (#) neighbours, become acive, overvise stay inactive(.)
	 */
	public static int wSize = 0;
	public static int xSize = 0;
	public static int ySize = 0;
	public static int zSize = 0;
	
	public static ArrayList<ArrayList<ArrayList<String>>> cloneHyperMap(ArrayList<ArrayList<ArrayList<String>>> hyperMap){
		ArrayList<ArrayList<ArrayList<String>>> newHyperwMap = new ArrayList<>();
		ArrayList<ArrayList<String>> normalTestList = new ArrayList<>();
		for(ArrayList<ArrayList<String>> normalMap : hyperMap) {
			normalTestList = new ArrayList<>();
			for(ArrayList<String> stringList : normalMap) {
				ArrayList<String> stringTestList = new ArrayList<String>();
				for(String line : stringList) {
					stringTestList.add(line);
				}
				normalTestList.add(stringTestList);
			}
			newHyperwMap.add(normalTestList);
		}
		
		return newHyperwMap;
	}
	
	public static ArrayList<ArrayList<String>> cloneMap(ArrayList<ArrayList<String>> map){
		ArrayList<ArrayList<String>> newMap = new ArrayList<>();
		for(ArrayList<String> stringList : map) {
			ArrayList<String> stringTestList = new ArrayList<String>();
			for(String line : stringList) {
				stringTestList.add(line);
			}
			newMap.add(stringTestList);
		}
		return newMap;
	}
	
	public static ArrayList<ArrayList<String>> updateCubeSize(ArrayList<ArrayList<String>> oldCube){
		ArrayList<ArrayList<String>> newCube = new ArrayList<>();
		xSize += 2;
		ySize += 2;
		zSize += 2;
		String emptyString = "";
		
		// Top layer and bottom layer set as . layers
		for(int k = 0; k < zSize; k++) {
			ArrayList<String> yList = new ArrayList<>();
			if(k == 0 || k == zSize-1) {
				char[] chars = new char[xSize];
				Arrays.fill(chars, '.');
				emptyString = new String(chars);
				
				for(int j = 0; j < ySize; j++) {
					yList.add(emptyString);
				}
				newCube.add(yList);
			} else {
				
				yList.add(emptyString);
				for(String yWord : oldCube.get(k-1)) {
					String infoString = "." + yWord + ".";
					yList.add(infoString);
				}
				newCube.add(yList);
				yList.add(emptyString);
			}
		}
		
		return newCube;
	}
	
	public static ArrayList<ArrayList<ArrayList<String>>> updateHyperCubeSize(ArrayList<ArrayList<ArrayList<String>>> oldHyperCube){
		ArrayList<ArrayList<ArrayList<String>>> newHyperCube = new ArrayList<>();
		wSize += 2;
		xSize += 2;
		ySize += 2;
		zSize += 2;
		String emptyString = "";
		
		// Top layer and bottom layer set as . layers
		for (int q = 0; q < wSize; q++) {
			ArrayList<ArrayList<String>> zList = new ArrayList<>();
			if (q == 0 || q == wSize -1) {
				ArrayList<String> yList = new ArrayList<>();
				for(int k = 0; k < zSize; k++) {
					char[] chars = new char[xSize];
					Arrays.fill(chars, '.');
					emptyString = new String(chars);
					
					for(int j = 0; j < ySize; j++) {
						yList.add(emptyString);
					}
					zList.add(yList);
				}
			} else { 
			for(int k = 0; k < zSize; k++) {
				ArrayList<String> yList = new ArrayList<>();
				if(k == 0 || k == zSize-1) {
					char[] chars = new char[xSize];
					Arrays.fill(chars, '.');
					emptyString = new String(chars);
					
					for(int j = 0; j < ySize; j++) {
						yList.add(emptyString);
					}
					zList.add(yList);
				} else {
					
					yList.add(emptyString);
					for(String yWord : oldHyperCube.get(q-1).get(k-1)) {
						String infoString = "." + yWord + ".";
						yList.add(infoString);
					}
					zList.add(yList);
					yList.add(emptyString);
				}
				
			}
			}
			newHyperCube.add(zList);
		}
		
		
		return newHyperCube;
	}
	
	public static ArrayList<ArrayList<String>> updateCubeWithRules(ArrayList<ArrayList<String>> cube){
		ArrayList<ArrayList<String>> newCube = cloneMap(cube);
		for(int k = 0; k < zSize; k++) {
			for(int j = 0; j< ySize; j++) {
				String yString = "";
				for(int i = 0; i < xSize; i++) {
					yString += rule(cube, i, j, k);
				}
				newCube.get(k).set(j, yString);
			}
		}
		
		return newCube;
	}
	
	public static ArrayList<ArrayList<ArrayList<String>>> updateHyperCubeWithRules(ArrayList<ArrayList<ArrayList<String>>> hyperCube){
		ArrayList<ArrayList<ArrayList<String>>> newHyperCube = cloneHyperMap(hyperCube);
		for(int q = 0; q < wSize; q++) {
			for(int k = 0; k < zSize; k++) {
				for(int j = 0; j< ySize; j++) {
					String yString = "";
					for(int i = 0; i < xSize; i++) {
						yString += hyperRule(hyperCube, i, j, k, q);
					}
					newHyperCube.get(q).get(k).set(j, yString);
				}
			}
		}
		
		
		return newHyperCube;
	}
	
	public static String rule(ArrayList<ArrayList<String>> cube, int x, int y, int z){
		int count = 0;
		String replacementString = "";
		ArrayList<String> yList = new ArrayList<String>();
		String xList = "";
		char myChar = 'a';
		
		for(int k = z-1; k<=z+1; k ++) {
			try {
				yList = cube.get(k);
				for(int j = y-1; j <= y+1; j++) {
					try {
						xList = yList.get(j);
						for(int i = x-1; i <= x+1; i++) {
							try {
								if(i == x && j == y && k == z) {
									myChar = xList.charAt(i);
								} else {
									if(xList.charAt(i) == '#') {
										count++;
									}
								}
							} catch (Exception e) {
								continue;
							}
						}
					} catch (Exception e) {
						continue;
					}
				}
			} catch (Exception e) {
				continue;
			}
		}
		
		if((myChar == '.' && count == 3) || (myChar == '#' && count > 1 && count <=3)) {
			replacementString = "#";
		}  else {
			replacementString = ".";
		}
		
		return replacementString;
	}
	
	public static String hyperRule(ArrayList<ArrayList<ArrayList<String>>> cube, int x, int y, int z, int w){
		int count = 0;
		String replacementString = "";
		ArrayList<String> yList = new ArrayList<String>();
		ArrayList<ArrayList<String>> zList = new ArrayList<>();
		String xList = "";
		char myChar = 'a';
		
		for(int q = w-1; q<= w+1; q++) {
			try {
				zList = cube.get(q);
				for(int k = z-1; k<=z+1; k ++) {
					try {
						yList = zList.get(k);
						for(int j = y-1; j <= y+1; j++) {
							try {
								xList = yList.get(j);
								for(int i = x-1; i <= x+1; i++) {
									try {
										if(i == x && j == y && k == z && q == w) {
											myChar = xList.charAt(i);
										} else {
											if(xList.charAt(i) == '#') {
												count++;
											}
										}
									} catch (Exception e) {
										continue;
									}
								}
							} catch (Exception e) {
								continue;
							}
						}
					} catch (Exception e) {
						continue;
					}
				}
			} catch (Exception e) {
				continue;
			}
		}
		
		
		
		if((myChar == '.' && count == 3) || (myChar == '#' && count > 1 && count <=3)) {
			replacementString = "#";
		}  else {
			replacementString = ".";
		}
		
		return replacementString;
	}
	
	public static int calculateActivity(ArrayList<ArrayList<String>> cube) {
		int count = 0;
		
		for(ArrayList<String> stringList : cube) {
			for(String line : stringList) {
				for(int i = 0; i < line.length(); i++) {
					if (line.charAt(i) == '#') {
						count++;
					}
				}
			}
		}
		
		return count;
	}
	
	
	public static int calculateHyperActivity(ArrayList<ArrayList<ArrayList<String>>> hyperCube) {
		int count = 0;
		for(ArrayList<ArrayList<String>> zList : hyperCube) {
			for(ArrayList<String> stringList : zList) {
				for(String line : stringList) {
					for(int i = 0; i < line.length(); i++) {
						if (line.charAt(i) == '#') {
							count++;
						}
					}
				}
			}
		}
		
		
		return count;
	}
	
	public static void main(String[] args) throws IOException {
		 ArrayList<String> lines = DataHandling.readStringsBySeparatorRaw("inputFiles/inputDay17.txt");
//		 ArrayList<String> lines = DataHandling.readStringsBySeparatorRaw("inputFiles/testInput17.txt");
		 ArrayList<String> yData = new ArrayList<String>();
		 for(String line : lines) {
			 if(line.length()>0) {
				 yData.add(line);
			 }
		 }
		 // First cube sizes
		 xSize = yData.size();
		 ySize = yData.get(0).length();
		 zSize = 1;
		 
		 ArrayList<ArrayList<String>> cube = new ArrayList<>();
		 ;
		 ArrayList<ArrayList<String>> newCube = new ArrayList<>();
		 cube.add(yData);
		 
		 // Part one start: 
		 newCube = updateCubeSize(cube);
		 newCube = updateCubeWithRules(newCube);
		 for(int i = 0; i<5; i++) {
			 newCube = updateCubeSize(newCube);
			 newCube = updateCubeWithRules(newCube);
		 }
		 
		 System.out.println(calculateActivity(newCube));
		 
		 // Part 2 HYPERCUBE 
		 // Add another dimension to it
		 
		 // Create a hypercube
		 
		 xSize = yData.size();
		 ySize = yData.get(0).length();
		 zSize = 1;
		 wSize = 1;
		 
		 ArrayList<ArrayList<ArrayList<String>>> hyperCube = new ArrayList<>();
		 hyperCube.add(cube);
		 ArrayList<ArrayList<ArrayList<String>>> newHyperCube = new ArrayList<>(hyperCube);
//		 newHyperCube = cloneHyperMap(hyperCube);
		 newHyperCube = updateHyperCubeSize(newHyperCube);
		 newHyperCube = updateHyperCubeWithRules(newHyperCube);
		 
		 for(int i = 0; i<5; i++) {
			 newHyperCube = updateHyperCubeSize(newHyperCube);
			 newHyperCube = updateHyperCubeWithRules(newHyperCube);
		 }
		 
		 System.out.println("hej");
		 System.out.println(calculateHyperActivity(newHyperCube));
	}
	
}

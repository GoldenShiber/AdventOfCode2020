package solutions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import utils.DataHandling;


public class Day7 {

	static class BagRule {
		String color;
		HashMap<String, Integer> containedBags = new HashMap<>();
		
		BagRule(String line) {
            String cleanLine = line.replace(" bags contain ", ":").
                    replace("bags, ", "").
                    replace("bag, ", "");
            String[] split = cleanLine.split(":");
            System.out.println(split[1].replace("bags", ""));
            color = split[0];
            String rest = split[1].replace("bags", "").replace("bag", "").strip();
            if (rest.contains("no other")) {
                return;
            }
            String[] singles = rest.split(" ");
            for (int i = 0; i < singles.length; i += 3) {
                int amount = Integer.parseInt(singles[i]);
                String color = singles[i + 1] + " " + singles[i + 2];
                containedBags.put(color, amount);
            }
        }
		
		// Since we use a custom created Bag Rule class, we create a equal class.
		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
            if (!(o instanceof BagRule)) return false;
            BagRule bagRule = (BagRule) o;
            return Objects.equals(color, bagRule.color);
		}
		
		// We also makes it return the Color since it is the key of the hashmap.
		@Override
        public int hashCode() {
            return Objects.hash(color);
        }
		
	}
	
	public static void makeMatchingRules(List<BagRule> totalRules, List<BagRule> matchedRules, String color) {
		totalRules.forEach(bagRule -> {
			if(bagRule.containedBags.containsKey(color) && !matchedRules.contains(bagRule)) {
				matchedRules.add(bagRule);
				makeMatchingRules(totalRules, matchedRules, bagRule.color);
			}
		});
	}
	
	public static long solutionPart1(List<BagRule> bagRules) {
		List<BagRule> matchedRules = new ArrayList<>();
		makeMatchingRules(bagRules, matchedRules, "shiny gold");
		return matchedRules.size();
	}
	
	
	// For the second part each node is converted into an Integer
	// The amount of bags are: Inner bag count + (Inner Bag Count * childrenBags)
	// Sum up these nodes!
	
	public static int countChildrenBags(List<BagRule> totalRules, String color) {
		return totalRules.stream().filter(r -> r.color.equals(color)).
				findFirst().orElse(null).containedBags.entrySet().stream().
				// Convert from Entry<String:color,Integer:count> -> 
				//count + (count* children-count(current_color)
				map(e -> e.getValue() + (e.getValue() * countChildrenBags(totalRules, e.getKey()))).
                reduce(Integer::sum).orElse(0);
				
	}
	
	public static long solutionPart2(List<BagRule> bagRules) {
		return countChildrenBags(bagRules, "shiny gold");
	}
	
	
	public static void main(String[] args) throws IOException {
		 List<String> lines = DataHandling.readStringsBySeparator("inputFiles/inputDay7.txt");

	        System.out.println("DAY 07 - Handy Haversacks");

	        List<BagRule> bagRules = lines.stream().map(BagRule::new).collect(Collectors.toList());
	        System.out.println("Part 1: " + solutionPart1(bagRules)); // 233
	        System.out.println("==============================");
	        System.out.println("Part 2: " + solutionPart2(bagRules)); // 8030
}
}

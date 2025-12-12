//1. ARRAYS - Fuel-Efficient Route Detection (Google Maps) 
//Problem: 
//You are given an array representing fuel cost per kilo-meter for a long trip. 
//Find the most fuel-efficient contiguous route segment of length ≥ k. 
//Requirements: 
// Must run in O(n) using sliding window or prefix sums. 
// Return: start index, end index, and minimum total cost. 
// If two segments have same cost → choose shorter one. 
// If still tie → choose the one that starts earlier. 
package assessment;
public class arrays {
    public static int[] findEfficientRoute(int[] fuel, int k) {
        if (fuel == null || fuel.length < k) return new int[]{-1, -1, -1};

        int minCost = 0;
        int currentSum = 0;
        int bestStart = 0;
        int bestEnd = 0;  
        for (int i = 0; i < k; i++) {
        	currentSum += fuel[i];
        } 
        minCost = currentSum;
        bestEnd = k - 1;   
        for (int i = k; i < fuel.length; i++) {
            currentSum += fuel[i] - fuel[i - k];
            if (currentSum < minCost) {
                minCost = currentSum;
                bestStart = i - k + 1;
                bestEnd = i;
            }
        }
        return new int[]{bestStart, bestEnd, minCost};
    }
    public static void main(String[] args) {
    	int[] fuel = {7, 5, 1, 4,3,5};
    	int k = 4;
    	int[] result = findEfficientRoute(fuel, k);
		System.out.println("Start Route: " + result[0] + ", End Route: " + result[1] + ", Minimum Fuel Cost: " + result[2]);
    	
    }
}


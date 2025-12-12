//2.  STRINGS - Gmail Autocorrect Suggestion Engine 
//Problem: 
//Given a typed incorrect word and a dictionary of valid words, 
//find the closest valid suggestions using minimum edit distance. 
//Requirements: 
// Dictionary size can be up to 100,000 words → must optimize. 
// Return the top 3 closest words, not just one. 
// Ties: return lexicographically smallest. 
// Total computation must be < 100 ms per query. 

package assessment;

import java.util.*;

class Node {
    String word;
    Map<Integer, Node> children = new HashMap<>();
    Node(String w) { word = w; }
}

class Pair {
    int dist;
    String word;
    Pair(int d, String w){ dist=d; word=w; }
}

class BKTree {
    Node root;

    int dist(String a, String b) {
        int m = a.length(), n = b.length();
        int[][] dp = new int[m+1][n+1];
        for (int i=0;i<=m;i++) dp[i][0]=i;
        for (int j=0;j<=n;j++) dp[0][j]=j;
        for (int i=1;i<=m;i++)
            for (int j=1;j<=n;j++)
                dp[i][j]=Math.min(Math.min(
                        dp[i-1][j]+1,
                        dp[i][j-1]+1),
                        dp[i-1][j-1] + (a.charAt(i-1)==b.charAt(j-1)?0:1));
        return dp[m][n];
    }

    void add(String w) {
        if (root == null) { root = new Node(w); return; }
        Node cur = root;
        while (true) {
            int d = dist(w, cur.word);
            if (!cur.children.containsKey(d)) {
                cur.children.put(d, new Node(w));
                return;
            }
            cur = cur.children.get(d);
        }
    }

    List<String> search(String w) {
        PriorityQueue<Pair> best = new PriorityQueue<>((a,b)->a.dist-b.dist);
        searchRec(root, w, best);
        List<String> out = new ArrayList<>();
        while (out.size()<3 && !best.isEmpty())
            out.add(best.poll().word);
        return out;
    }

    void searchRec(Node n, String w, PriorityQueue<Pair> best) {
        if (n==null) return;
        int d = dist(w, n.word);
        best.add(new Pair(d, n.word));
        for (int k : n.children.keySet())
            if (k >= d-2 && k <= d+2)
                searchRec(n.children.get(k), w, best);
    }
}

public class string {

    public static void main(String[] args) {

        List<String> dict = Arrays.asList(
                "apple","apply","ape","appeal","apart",
                "banana","band","bank","banner","bald",
                "car","cat","cart","care","carry",
                "dog","dot","dove","doom","dock",
                "fast","fat","fact","fate","facet",
                "ghost","ghast","goat","goal","gala", "rabiit"
        );

        BKTree t = new BKTree();
        for (String s : dict) t.add(s);

        System.out.println(t.search("rohith"));
    }
}

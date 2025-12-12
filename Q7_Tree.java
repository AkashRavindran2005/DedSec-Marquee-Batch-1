//7. TREE - Lowest Common Directory in a File System 
//Problem: 
//Given a Unix-like directory tree and two file paths, 
//find the lowest common directory. 
//Requirements: 
// Tree may have 100,000+ nodes → LCA must be O(log n) using binary 
//lifting. 
// Convert file path /a/b/c.txt → node ID before solving. 
// Return the full directory path of the LCA.
package assessment;
import java.util.*;


public class Tree{
    int MAX_LOG = 20;
    int MAX_NODES = 100000;

    int[][] up;
    int[] depth;
    List<List<Integer>> adj;
    
    Map<String, Integer> pathIdMap;
    Map<Integer, String> idPathMap;
    
    int nodeCounter;

    public Tree() {
        up = new int[MAX_NODES][MAX_LOG];
        depth = new int[MAX_NODES];
        adj = new ArrayList<>();
        for (int i = 0; i < MAX_NODES; i++) {
            adj.add(new ArrayList<>());
        }

        pathIdMap = new HashMap<>();
        idPathMap = new HashMap<>();

        nodeCounter = 0;
        pathIdMap.put("/", 0);
        idPathMap.put(0, "/");
        nodeCounter++;
    }

    public void addPath(String path) {
        if (path.equals("/")) return;

        String[] parts = path.split("/");
        int currentU = 0; 
        StringBuilder currentPath = new StringBuilder();

        for (String part : parts) {
            if (part.isEmpty()) continue;

            currentPath.append("/").append(part);
            String pathStr = currentPath.toString();

            if (!pathIdMap.containsKey(pathStr)) {
                int newNodeId = nodeCounter++;
                pathIdMap.put(pathStr, newNodeId);
                idPathMap.put(newNodeId, pathStr);
                
                adj.get(currentU).add(newNodeId);
                adj.get(newNodeId).add(currentU);
            }
            currentU = pathIdMap.get(pathStr);
        }
    }

    public void build() {
        dfs(0, 0, 0);
        
        for (int i = 1; i < MAX_LOG; i++) {
            for (int u = 0; u < nodeCounter; u++) {
                up[u][i] = up[up[u][i - 1]][i - 1];
            }
        }
    }

    private void dfs(int u, int p, int d) {
        depth[u] = d;
        up[u][0] = p;
        
        for (int v : adj.get(u)) {
            if (v != p) {
                dfs(v, u, d + 1);
            }
        }
    }

    public String getLCA(String pathA, String pathB) {
        if (!pathIdMap.containsKey(pathA) || !pathIdMap.containsKey(pathB)) {
            return null;
        }

        int u = pathIdMap.get(pathA);
        int v = pathIdMap.get(pathB);

        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        for (int i = MAX_LOG - 1; i >= 0; i--) {
            if (depth[u] - (1 << i) >= depth[v]) {
                u = up[u][i];
            }
        }

        if (u == v) return idPathMap.get(u);

        for (int i = MAX_LOG - 1; i >= 0; i--) {
            if (up[u][i] != up[v][i]) {
                u = up[u][i];
                v = up[v][i];
            }
        }

        return idPathMap.get(up[u][0]);
    }

    public static void main(String[] args) {
        Tree fs = new Tree();

        fs.addPath("/home/user/documents/work");
        fs.addPath("/home/user/documents/personal");
        fs.addPath("/home/user/pictures");
        fs.addPath("/etc/nginx");

        fs.build();

        System.out.println(fs.getLCA("/home/user/documents/work", "/home/user/pictures")); 
        System.out.println(fs.getLCA("/home/user/documents/work", "/home/user/documents/personal")); 
        System.out.println(fs.getLCA("/home/user/documents/work", "/etc/nginx")); 
    }
}

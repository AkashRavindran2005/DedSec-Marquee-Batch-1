//3. RECURSION - File Explorer Folder Size Computation 
//Problem: 
//Compute the total size of a folder that contains nested folders and files. 
//Requirements: 
// Folder structure is a tree but may contain symbolic links → detect cycles. 
// Use recursion + memoization to avoid recomputing sizes. 
// Also return the largest 3 files inside the folder. 
// No global static variables allowed.

package assessment;

import java.util.*;

class FileNode {
    String name;
    long size;
    boolean isFile;
    List<FileNode> children = new ArrayList<>();
    FileNode link;
    FileNode(String n,long s,boolean f){name=n;size=s;isFile=f;}
}

class Result {
    long total;
    PriorityQueue<FileNode> top = new PriorityQueue<>((a,b)->Long.compare(a.size,b.size));
}

public class recursion {

    Result compute(FileNode root, Map<FileNode, Result> memo, Set<FileNode> path) {
        if (memo.containsKey(root)) return memo.get(root);
        if (path.contains(root)) return new Result();
        path.add(root);

        Result r = new Result();
        if (root.isFile) {
            r.total = root.size;
            r.top.add(root);
        } else {
            for (FileNode c : root.children) {
                FileNode next = c.link != null ? c.link : c;
                Result sub = compute(next, memo, path);
                r.total += sub.total;
                for (FileNode f : sub.top) {
                    r.top.add(f);
                    if (r.top.size() > 3) r.top.poll();
                }
            }
        }

        path.remove(root);
        memo.put(root, r);
        return r;
    }

    public static void main(String[] args) {
        recursion x = new recursion();

        FileNode f1 = new FileNode("a", 10, true);
        FileNode f2 = new FileNode("b", 50, true);
        FileNode f3 = new FileNode("c", 30, true);

        FileNode folder = new FileNode("root", 0, false);
        folder.children.add(f1);
        folder.children.add(f2);
        folder.children.add(f3);

        Result r = x.compute(folder, new HashMap<>(), new HashSet<>());
        System.out.println(r.total);

        List<FileNode> list = new ArrayList<>(r.top);
        list.sort((a,b)->Long.compare(b.size,a.size));
        for (FileNode f : list) System.out.println(f.name + " " + f.size);
    }
}
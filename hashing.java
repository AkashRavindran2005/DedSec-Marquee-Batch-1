//9.  HASHING - Real-Time Fraud Detection System 
//Problem: 
//Given a continuous stream of transactions, detect duplicates within the last k 
//seconds. 
//Requirements: 
// A duplicate = same transaction ID + amount. 
// Must process 10 million transactions/day. 
// Implement using HashMap + Doubly Linked List sliding window. 
// Return: 
//o Whether fraud occurred 
//o All suspicious transaction IDs 
package assessment;

import java.util.*;

class NNode {
    String id;
    double amt;
    long ts;
    NNode prev, next;
    NNode(String i,double a,long t){id=i;amt=a;ts=t;}
}

public class hashing {

    Map<String, NNode> map = new HashMap<>();
    NNode head = new NNode("",0,0);
    NNode tail = new NNode("",0,0);
    long window;

    public hashing(long k) {
        head.next = tail;
        tail.prev = head;
        window = k;
    }

    void add(NNode n) {
        n.next = head.next;
        n.prev = head;
        head.next.prev = n;
        head.next = n;
    }

    void remove(NNode n) {
        n.prev.next = n.next;
        n.next.prev = n.prev;
    }

    List<String> ingest(String id, double amt, long ts) {
        List<String> out = new ArrayList<>();

        while (tail.prev != head && ts - tail.prev.ts > window) {
            NNode old = tail.prev;
            remove(old);
            map.remove(old.id + "#" + old.amt);
        }

        String key = id + "#" + amt;
        if (map.containsKey(key)) out.add(id);

        NNode n = new NNode(id, amt, ts);
        add(n);
        map.put(key, n);

        return out;
    }

    static void run(hashing h, String id, double amt, long ts) {
        List<String> fraud = h.ingest(id, amt, ts);

        if (fraud.isEmpty()) {
            System.out.println("TS=" + ts + "  ID=" + id + "  AMT=" + amt + "  - No Fraud");
        } else {
            System.out.println("TS=" + ts + "  ID=" + id + "  AMT=" + amt + " - 🇮🇱🚨🇮🇱🚨FRAUD DETECTED🇮🇱🚨🇮🇱🚨 ");
            for (String f : fraud)
                System.out.println("   Duplicate of " + f + " (amt=" + amt + ") inside last window");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        hashing h = new hashing(30000);

        run(h, "A", 100, 1000);
        run(h, "B", 200, 2000);
        run(h, "A", 100, 2500);
        run(h, "C", 300, 40000);
    }
}
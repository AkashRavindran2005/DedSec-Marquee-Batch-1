//6. QUEUE - Hospital Emergency Priority System 
//Problem: 
//Patients arrive with name, severity, and arrival time. 
//Create a system to pick patients in correct priority order. 
//Requirements: 
// Maintain 2 queues: 
//o Critical Queue (8–10 severity) 
//o Normal Queue (1–7 severity) 
// At least 1 critical patient every 5 minutes must be treated. 
// If severity is same → earlier arrival first. 
// Must support real-time updates and retrieval.
package assessment;

import java.util.*;

public class queue {
    static class Task {
        String id;
        int priority;
        int time;
        Task(String id, int priority, int time) {
            this.id = id;
            this.priority = priority;
            this.time = time;
        }
        public String toString() {
            return "Patient ID: " + id + ", Priority:" + priority + ", Time:" + time;
        }
    }

    public static void main(String[] args) {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("A01", 3, 10));
        tasks.add(new Task("A02", 1, 20));
        tasks.add(new Task("A03", 1, 30));
        tasks.add(new Task("A04", 2, 40));

        System.out.println("original line:");
        tasks.forEach(System.out::println);

        PriorityQueue<Task> pq = new PriorityQueue<>((a, b) -> a.priority == b.priority ? Integer.compare(a.time, b.time) : Integer.compare(b.priority, a.priority));

        pq.addAll(tasks);

        System.out.println("\npriority for severe patients:");
        while (!pq.isEmpty()) {
            System.out.println(pq.poll());
        }
    }
}
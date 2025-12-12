//
//8. HEAP - YouTube Real-Time Trending Videos 
//Problem: 
//Each video has a dynamic "trending score" updated in real-time. 
//Return top K trending videos. 
//Requirements: 
// updateScore(videoID, score) must run in O(log n). 
// Fetch top-K in O(k log n). 
// Videos not watched for 24 hours → lose 10% score automatically. 
// If two videos have same score → newest timestamp is higher priority. 
package assessment;

import java.util.*;

public class youtube {

    static class Video {
        int id;
        double score;
        long lastTime;
        
        Video(int id, double score, long time) { 
            this.id = id; 
            this.score = score; 
            this.lastTime = time; 
        }
    }

    Map<Integer, Video> map = new HashMap<>();
    
    PriorityQueue<Video> pq = new PriorityQueue<>((a, b) -> 
        a.score == b.score ? Long.compare(b.lastTime, a.lastTime) : Double.compare(b.score, a.score)
    );

    void updateScore(int id, double add, long currentTime) {
        Video v = map.get(id);

        if (v != null) {
            pq.remove(v);
            
            if (currentTime - v.lastTime >= 86400) {
                v.score *= 0.9;
            }
            
            v.score += add;
            v.lastTime = currentTime;
        } else {
            v = new Video(id, add, currentTime);
            map.put(id, v);
        }
        pq.add(v);
    }

    List<Integer> topK(int k) {
        List<Integer> res = new ArrayList<>();
        List<Video> temp = new ArrayList<>();

        while (k --> 0 && !pq.isEmpty()) {
            Video v = pq.poll();
            res.add(v.id);
            temp.add(v);
        }
        pq.addAll(temp);
        return res;
    }

    public static void main(String[] args) {
        youtube h = new youtube();
        
        h.updateScore(1, 10, 0);
        h.updateScore(2, 15, 0);
        h.updateScore(3, 20, 0);
        h.updateScore(1, 5, 100000); 
        
        System.out.println(h.topK(2));
    }
}

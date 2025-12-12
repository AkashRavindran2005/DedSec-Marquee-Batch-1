//4. LINKED LIST - Music Playlist Engine (Spotify-like) 
//Problem: 
//Build a playlist system with: 
// playNext() 
// playPrev() 
// addSong() 
// removeSong() 
// prevent duplicate songs 
//Requirements: 
// All operations must run in O(1) using doubly linked list + HashSet. 
// Maintain a current pointer that moves dynamically. 
// Support exporting playlist both forward and backward.
package assessment;

import java.util.*;

class Song {
    String title;
    Song next;
    Song prev;
    Song(String title) {
        this.title = title;
    }
}

public class Linkedlist { 
    Song head;
    Song tail;
    Song current;
    Set<String> titles = new HashSet<>(); 

    public boolean addSong(String title) {
        if (title == null || title.isEmpty()) return false;
        if (!titles.add(title)) return false; 
        Song node = new Song(title);
        if (head == null) {
            head = tail = current = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        return true;
    }

    public String playNext() {
        if (current == null) return "No songs in playlist.";
        if (current.next == null) return "Already at end of playlist.";
        current = current.next;
        return "Playing: " + current.title;
    }

    public String playPrev() {
        if (current == null) return "No songs in playlist.";
        if (current.prev == null) return "Already at start of playlist.";
        current = current.prev;
        return "Playing: " + current.title;
    }

    public String removeSong() {
        if (current == null) return "Playlist empty.";
        String removed = current.title;
        titles.remove(removed);

        Song prev = current.prev;
        Song next = current.next;

        if (prev != null) prev.next = next;
        else head = next;

        if (next != null) next.prev = prev;
        else tail = prev;

        current = (next != null) ? next : prev;

        return "Removed: " + removed;
    }

    public String nowPlaying() {
        return (current == null) ? "No song playing." : "Now playing: " + current.title;
    }

    public static void main(String[] args) {
        Linkedlist playlist = new Linkedlist();
        playlist.addSong("New Light");
        playlist.addSong("All I Need");
        playlist.addSong("Let Down");
        playlist.addSong("Your Man");
        playlist.addSong("Die For You"); 

        System.out.println(playlist.nowPlaying());        
        System.out.println(playlist.playNext());            
        System.out.println(playlist.playNext());
        System.out.println(playlist.playPrev());
        System.out.println(playlist.removeSong());        
    }
}
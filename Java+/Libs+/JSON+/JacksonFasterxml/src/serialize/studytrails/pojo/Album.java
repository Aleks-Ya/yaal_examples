package serialize.studytrails.pojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Album {
	 private String title;
	    private String[] links;
	    private List<String> songs = new ArrayList<String>();
	    private Artist artist;
	    private Map<String,String> musicians = new HashMap<String, String>();
	 
	    public Album(String title) {
	        this.title = title;
	    }
	 
	    public String getTitle() {
	        return title;
	    }
	 
	    public void setLinks(String[] links) {
	        this.links = links;
	    }
	 
	    public String[] getLinks() {
	        return links;
	    }
	 
	    public void setSongs(List<String> songs) {
	        this.songs = songs;
	    }
	 
	    public List<String> getSongs() {
	        return songs;
	    }
	 
	    public void setArtist(Artist artist) {
	        this.artist = artist;
	    }
	 
	    public Artist getArtist() {
	        return artist;
	    }
	 
	    public Map<String, String> getMusicians() {
	        return Collections.unmodifiableMap(musicians);
	    }
	 
	    public void addMusician(String key, String value) {
	        musicians.put(key, value);
	    }
}

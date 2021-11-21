package app;

/*
 * Yash Patel
 * Neil Patel
 */

public class Song {
	
	private String title;  
	private String artist; 
	private String album;  
	private String year;  

	public Song(String name, String artist) { 
		
		this(name, artist, "", "");
	}
	
	public Song(String name, String artist, String album, String year) { 
		
		this.title = name;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}
	
	public String getTitle() {  
		
		return title;
	}
	
	public String getArtist() { 
		
		return artist;
	}
	
	public String getAlbum() {  
		
		return album;
	}
	
	public String getYear() {  
		
		return year;
	}
	
	public void setTitle(String songName) {  
		
		title = songName;
	}
	
	public void setArtist(String artistName) {  
		
		artist = artistName;
	}
	
	public void setAlbum(String albumName) {  
		
		album = albumName;
	}
	
	public void setYear(String songYear) {  
		
		this.year = songYear;
	}
	
	
	public String toString() {
		
		return title + " by " + artist;
	}
	
	public int compareTo(Song o) {
		// TODO Auto-generated method stub
		if(this.title.toUpperCase().compareTo(o.getTitle().toUpperCase()) == 0){
			if(this.artist.toUpperCase().compareTo(o.getArtist().toUpperCase()) == 0){ //song is same
				return 0;
			}
			else if(this.artist.toUpperCase().compareTo(o.getArtist().toUpperCase()) > 0){
				return 1;
			}
			else{
				return -1;
			}
		}
		else if(this.title.toUpperCase().compareTo(o.getTitle().toUpperCase()) > 0){
			return 1;
		}
		else{
			return -1;
		}
	}
	

}




package app;
/*
 * Yash Patel
 * Neil Patel
 */
import java.util.Comparator;

public class SongComparator implements Comparator<Song>{
	public int compare(Song a, Song b) {
		String aTitle = a.getTitle().toUpperCase();
		String bTitle = b.getTitle().toUpperCase();
		String aArtist = a.getArtist().toUpperCase();
		String bArtist = b.getArtist().toUpperCase();
		if(aTitle.compareTo(bTitle) == 0){
			if(aArtist.compareTo(bArtist) == 0){ //song is same
				return 0;
			}
			else if(aArtist.compareTo(bArtist) > 0){
				return 1;
			}
			else{
				return -1;
			}
		}
		else if(aTitle.compareTo(bTitle) > 0){
			return 1;
		}
		else{
			return -1;
		}
	}
}
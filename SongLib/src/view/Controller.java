package view;

/*
 * Yash Patel
 * Neil Patel
 */
import java.io.*;
import java.util.Optional;

import app.Song;
import app.SongComparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {

	  @FXML
	    private Label songLabel;

	    @FXML
	    private ListView<Song> songList;

	    @FXML
	    private Button addButton;

	    @FXML
	    private TextField songName;

	    @FXML
	    private TextField artistName;

	    @FXML
	    private TextField albumName;

	    @FXML
	    private TextField releaseYear;
	    
	    private ObservableList<Song> obsSongList = FXCollections.observableArrayList();
	    
	    String path = "src/SongLibFile.txt";

	    public void start(Stage primaryStage) {
	    	obsSongList = FXCollections.observableArrayList();
	    	try {
	    		FileReader fr = new FileReader(path);
	    		BufferedReader br = new BufferedReader(fr);
	    		String line;
	    		while((line = br.readLine()) != null) {
	    			String[] splitLine = line.split("\t");
	    			
	    			switch(splitLine.length) {
	    			case 2:
	    				obsSongList.add(new Song(splitLine[0].trim(),splitLine[1].trim()));
	    				break;
	    			case 3:
	    				obsSongList.add(new Song(splitLine[0].trim(),splitLine[1].trim(),splitLine[2].trim(),""));
	    				break;
	    			case 4:
	    				obsSongList.add(new Song(splitLine[0].trim(),splitLine[1].trim(),splitLine[2].trim(),splitLine[3].trim()));
	    				break;
	    			}
	    		}
	    		br.close();
	    	}
	    	catch(FileNotFoundException e) { //creating a file to store the details because it does not exist
	    		File f = new File(path);
	    		try {
					f.createNewFile();
				} catch (IOException e1) {
				}
	    	}
	    	catch(IOException e) {
	    		//IOExceptions
	    	}
	    	
	    	songList.setItems(obsSongList);
	    	if(!obsSongList.isEmpty()) {
	    		songList.getSelectionModel().select(0);
	    		updateText();
	    	}
	    	
	    	songList
			.getSelectionModel()
			.selectedIndexProperty()
			.addListener(
					(obs, oldVal, newVal) -> 
					updateText());
	    	
	    	primaryStage.setOnCloseRequest(event -> {
	    		writeToFile();
	    	});
	    }
	    @FXML
	    void addSong(ActionEvent event) {
	    	Song tempSong = new Song(songName.getText().trim(), artistName.getText().trim(),albumName.getText().trim(),releaseYear.getText().trim());
	    	add(tempSong);

	    }
	    private int add(Song tempSong) {
	    	boolean addedSong = false;
	    	int songIndex = findIndex(obsSongList, tempSong);
	    	String artist;
	    	String title;
	    	artist = tempSong.getArtist();
	    	title = tempSong.getTitle();    	
	    	
	    	
	    	if(artist.compareTo("") == 0 || title.compareTo("") == 0) {   //error check
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("WARNING");
				alert.setHeaderText("Missing input");
				alert.setContentText("Title and Artist  cannot be empty");
				alert.showAndWait();
				return -1;
	    	}else if(tempSong.getTitle().contains("|") || tempSong.getArtist().contains("|") || tempSong.getAlbum().contains("|") || tempSong.getYear().contains("|")) {
	    		Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("WARNING");
				alert.setHeaderText("Wrong input");
				alert.setContentText("The TextField contains a |");
				alert.showAndWait();
				return -1;
	    	} else if(!tempSong.getYear().equals("")&&!checkYear(tempSong.getYear())) {
	    		Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("WARNING");
				alert.setHeaderText("The Year can only contain a positive number");
				alert.setContentText("The year either contains a letter or is a negative number");
				alert.showAndWait();
				return -1;
	    	}else if (songIndex != -1) {	// duplicate
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("WARNING");
				alert.setHeaderText("Same Title and Artist already exists");
				alert.showAndWait();
				return -1;
	    	} else { 					//add the new song
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("WARNING");
				alert.setHeaderText("Adding a new song");
				alert.setContentText("You are about to add a new song: \n" + title + " by: " + artist + "\nAre you sure?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					obsSongList.add(tempSong);
					addedSong = true;
				} else {
					//cancel
				}
			}
	    	FXCollections.sort(obsSongList, new SongComparator());
	    	songIndex = findIndex(obsSongList, tempSong);
	    	if(addedSong) {
	    		songList.getSelectionModel().select(songIndex);
	    		updateText();
	    	}
	    	
	    	return 0;
	    	
	    }

	    @FXML
	    void deleteSong(ActionEvent event) {
	    	Song tempSong = new Song(songName.getText().trim(), artistName.getText().trim(),albumName.getText().trim(),releaseYear.getText().trim());
	       	delete(tempSong);

	    }
	    private void delete(Song tempSong) {
	    	if(obsSongList.isEmpty() == true) {
	    		Alert alert = new Alert(AlertType.WARNING);
	    		alert.setTitle("Warning");
	    		alert.setHeaderText("Nothing to delete");
	    		alert.setContentText("Song list is empty, no songs to delete");
	    		alert.showAndWait();
	    	} else {
	    		Alert alert = new Alert(AlertType.CONFIRMATION);
	    		alert.setTitle("Warning");
	    		alert.setHeaderText("You are about to delete the selected song");
	    		alert.setContentText("Are you sure?");
				Optional<ButtonType> result = alert.showAndWait();

	    		int currentIndex = 0;
	    		if(result.get() == ButtonType.OK) {
	    			currentIndex = songList.getSelectionModel().getSelectedIndex();
	    			obsSongList.remove(currentIndex);
	    			
	    			if(obsSongList.size() == 0) {
	    				clearText();
	    			}
	    			else {
	    				if(currentIndex < obsSongList.size()) {
			    			songList.getSelectionModel().select(currentIndex);
				    		updateText();
			    		}
			    		else {
			    			if(currentIndex - 1 > 0) {
			    				currentIndex--;
			    				songList.getSelectionModel().select(currentIndex);
			    	    		updateText();
			    			}
			    		}
	    			}
	    			
	    		}
	    	}
	    	
	    }

	    @FXML
	    void editList(ActionEvent event) {
	    	Song tempSong = new Song(songName.getText().trim(), artistName.getText().trim(),albumName.getText().trim(),releaseYear.getText().trim());
	    	edit(tempSong);
	    }
	    private void edit(Song tempSong) {
	    	
	    	Song selectedSong = null;
	    	
	    	if(obsSongList.size() == 0) {
	    		Alert alert = new Alert(AlertType.ERROR);
	    		alert.setTitle("Warning");
	    		alert.setHeaderText("Song cannot be edited");
	    		alert.setContentText("Selected song does not exist, please enter a song to edit");
	    		alert.showAndWait();
	    	}
	    	else { 
	    	
		    	selectedSong = songList.getSelectionModel().getSelectedItem();
		    	Song editedSong = new Song(songName.getText().trim(), artistName.getText().trim(),albumName.getText().trim(),releaseYear.getText().trim());
		    	
		    	if(editedSong.getTitle().compareTo("") == 0 || editedSong.getArtist().compareTo("") == 0) {   //error check
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("WARNING");
					alert.setHeaderText("Missing input");
					alert.setContentText("Title and Artist  cannot be empty");
					alert.showAndWait();
					return;
		    	}
		    	
		    	if(editedSong.getTitle().contains("|") || editedSong.getArtist().contains("|") || editedSong.getAlbum().contains("|") || editedSong.getYear().contains("|")) {
		    		Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("WARNING");
					alert.setHeaderText("Wrong input");
					alert.setContentText("The TextField contains a |");
					alert.showAndWait();
					return;
		    	}
		    	
		    	if(!tempSong.getYear().equals("")&&!checkYear(tempSong.getYear())) {
		    		Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("WARNING");
					alert.setHeaderText("The Year can only contain a positive number");
					alert.setContentText("The year either contains a letter or is a negative number");
					alert.showAndWait();
					return;
		    	}
		    	int index = findIndex(obsSongList,editedSong);
		    	if(index != -1 && index != songList.getSelectionModel().getSelectedIndex()) {
		    		Alert alert = new Alert(AlertType.WARNING);
		    		alert.setTitle("Warning");
		    		alert.setHeaderText("Nothing to edit");
		    		alert.setContentText("Song details are the same to another song with same title and artist, no changes made");
		    		alert.showAndWait();
		    		return;
		    	}
		    	if(selectedSong.getTitle().compareTo(editedSong.getTitle()) == 0 && selectedSong.getArtist().compareTo(editedSong.getArtist()) == 0 && selectedSong.getAlbum().compareTo(editedSong.getAlbum()) == 0 && selectedSong.getYear().compareTo(editedSong.getYear()) == 0) {
		    		Alert alert = new Alert(AlertType.WARNING);
		    		alert.setTitle("Warning");
		    		alert.setHeaderText("Nothing to edit");
		    		alert.setContentText("Song details are the same, no changes made");
		    		alert.showAndWait();
		    	}	
		    	else {
		    	Alert alert = new Alert(AlertType.CONFIRMATION);
	    		alert.setTitle("Warning");
	    		alert.setHeaderText("About to edit a song");
	    		alert.setContentText("You are about to edit this song, would you like to continue");
				Optional<ButtonType> result = alert.showAndWait();
	    		if(result.get() == ButtonType.OK ){

		    			selectedSong.setAlbum(editedSong.getAlbum());														
		    			selectedSong.setYear(editedSong.getYear());
		    			selectedSong.setArtist(editedSong.getArtist());	
		    			selectedSong.setTitle(editedSong.getTitle());		
	    		}

		    	}
		    		
		    }
	    	FXCollections.sort(obsSongList, new SongComparator());
	    	if(selectedSong != null) {
	    		int songIndex = findIndex(obsSongList, selectedSong);
	    		songList.getSelectionModel().select(songIndex);
	    		updateText();
	    	}
		}
	    
	    @FXML
	    void clearTextFields(ActionEvent event) {
	    	clearText();
	    }
	    
	    private void writeToFile() {
	    	try {
	    		File file = new File(path);
	    		
	    		if(!file.exists()) { // check if they delete file after loading the program
	    			file.createNewFile();
	    		}
	    		
				FileWriter fw = new FileWriter(file);
				
				BufferedWriter bw  = new BufferedWriter(fw);
				
				for(int i = 0; i < obsSongList.size(); i++) {
					Song temp = obsSongList.get(i);
					bw.write(temp.getTitle() + "\t" + temp.getArtist() + "\t" + temp.getAlbum() + "\t" + temp.getYear() + "\n");
				}
				bw.close();
				
			} catch (IOException e) {
				
			}
	    	
	    	
	    }
	    
	    private void updateText() {
	    	Song temp = songList.getSelectionModel().getSelectedItem();
	    	if(temp != null) {
		    	songName.setText(temp.getTitle());
		    	artistName.setText(temp.getArtist());
		    	albumName.setText(temp.getAlbum());
		    	releaseYear.setText(temp.getYear());
	    	}
	    }
	    
	    private void clearText() {
	    	songName.clear();
	    	artistName.clear();
	    	albumName.clear();
	    	releaseYear.clear();
	    }
	    
	    private boolean checkYear(String year) {
	    	try {
	    		int temp = Integer.parseInt(year);
	    		if(temp < 0) {
	    			return false;
	    		}
	    		else {
	    			return true;
	    		}
	    	}
	    	catch(NumberFormatException e) {
	    		return false;
	    	}
	    }

	    public static int findIndex(ObservableList<Song> list, Song s){
			int i;
			for( i = 0; i < list.size(); i++){
				if(list.get(i).compareTo(s)==0){
					return i; //song that are similar index
				}
			}
			
			return -1;//if there are no song similar
	    }
	}






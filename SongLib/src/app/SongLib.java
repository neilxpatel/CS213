package app;
/*
 * Yash Patel
 * Neil Patel
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
import view.Controller;

public class SongLib extends Application{

	@Override
	public void start(Stage primaryStage) 
	throws Exception {
		// TODO Auto-generated method stub
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/GUI.fxml"));
				SplitPane root = (SplitPane)loader.load();
				
				Controller listController = loader.getController();
				listController.start(primaryStage);
				
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("SongLib");
				primaryStage.setResizable(false);
				primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}

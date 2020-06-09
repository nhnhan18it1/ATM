package GDC;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class mainfrDangKi extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent p1=FXMLLoader.load(getClass().getResource("frDangKi.fxml"));
			Scene s1=new Scene(p1,916,752);
			s1.getStylesheets().add(getClass().getResource("frMain.css").toExternalForm());
			primaryStage.setTitle("abc");
			primaryStage.setScene( s1);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}

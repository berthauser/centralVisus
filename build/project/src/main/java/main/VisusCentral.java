package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class VisusCentral extends Application {
	/**
     * Main JavaFX initialisation method which is called indirectly by the main() method above on startup. This method
     * loads the user interface and adds it to the provided Stage.
     *
     * @param stage the main Stage (i.e. Window) that the application is to run within.
     * 
     * StyleManager no tendrá más soporte...fijarse en la siguiente línea de argumentos VM
     * --add-exports=javafx.graphics/com.sun.javafx.css=ALL-UNNAMED 
     * 
     * 
     */	
	
	@Override
	public void start(Stage primaryStage) {
		try {	
			Parent root = FXMLLoader.load(getClass().getResource("/view/splash.fxml"));
			
			Scene scene = new Scene(root,630,270);
			
			scene.getStylesheets().clear();
//			scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
			scene.getStylesheets().add("style.css");

			primaryStage.setAlwaysOnTop(true);
			final Rectangle2D bounds = Screen.getPrimary().getBounds();
			primaryStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - 630 / 2);
			primaryStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - 270 / 2);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
        launch(args);
    }
	
}

package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Visus extends Application {
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
	public void start(Stage stage) {
		try {
//			String fxmlFile = "/fxml/splash.fxml"; // para el IDE
			String fxmlFile = "/resources/fxml/splash.fxml"; // para el JAR
			FXMLLoader loader = new FXMLLoader();
			Parent root = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));

			Scene scene = new Scene(root,630,270);
//			scene.getStylesheets().add("/styles/style.css");
			scene.getStylesheets().add("/resources/styles/style.css");
			
			stage.setAlwaysOnTop(true);
			final Rectangle2D bounds = Screen.getPrimary().getBounds();
			stage.setX(bounds.getMinX() + bounds.getWidth() / 2 - 630 / 2);
			stage.setY(bounds.getMinY() + bounds.getHeight() / 2 - 270 / 2);
			stage.setScene(scene);
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
        launch(args);
    }
	
}

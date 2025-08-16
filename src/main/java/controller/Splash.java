package controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Splash {
	@FXML
	private AnchorPane apBase;
	@FXML
	private ImageView imgSplash;
	
	private double dragOffsetX = 0;
	private double dragOffsetY = 0;
	
	public void initialize() throws FileNotFoundException {
		
	// Cargo las imágenes
//		imgSplash.setImage(new Image("/images/datawarehouse.png"));
		imgSplash.setImage(new Image("/resources/images/datawarehouse.png"));
		imgSplash.setSmooth(true);
		imgSplash.setPreserveRatio(true);

		final FadeTransition transition = new FadeTransition(Duration.seconds(3.0), apBase);
		transition.setFromValue(1.0);
		transition.setToValue(1.0);
		transition.play();
		transition.setOnFinished(event -> {
			final Stage stSplash = (Stage) apBase.getScene().getWindow();
			final Stage stPrincipal = new Stage();
			stPrincipal.initStyle(StageStyle.UNDECORATED);
			stPrincipal.initModality(Modality.APPLICATION_MODAL);
//			stPrincipal.setMaximized(true);
			
			try {
//				String fxmlFile = "/fxml/centralVisus.fxml";
				String fxmlFile = "/resources/fxml/centralVisus.fxml";
				FXMLLoader loader = new FXMLLoader();
				Parent root = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
				
				final Scene scene = new Scene(root);
				
//				scene.getStylesheets().add("/styles/style.css");
				scene.getStylesheets().add("/resources/styles/style.css");
				
				scene.setOnMousePressed(e1 -> {
					dragOffsetX = e1.getSceneX();
					dragOffsetY = e1.getSceneY();
				});
				
				scene.setOnMouseDragged(e1 -> {
					stPrincipal.setX(e1.getScreenX() - dragOffsetX);
					stPrincipal.setY(e1.getScreenY() - dragOffsetY);
				});
				
				stPrincipal.setScene(scene);
				stPrincipal.show();
				stSplash.hide();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		});
}};

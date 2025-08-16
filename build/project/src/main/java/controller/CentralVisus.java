package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

import com.jfoenix.controls.JFXButton;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class CentralVisus {
	@FXML
	private JFXButton btnRubros;
	@FXML
	private JFXButton btnMarcas;
	@FXML
	private JFXButton btnLineas;
	@FXML
	private JFXButton btnArticulos;
	@FXML
	private JFXButton btnPresentaciones;
	@FXML
	private JFXButton btnUnidades;
	@FXML
	private JFXButton btnUbicaciones;
	@FXML
	private JFXButton btnDepositos;
	@FXML
	private JFXButton btnStaff;
	@FXML
	private JFXButton btnLocalidades;
	@FXML
	private ImageView imgLogo;
	@FXML
	private Label lblFecha;
	@FXML
	private Label lblHora;
	@FXML
	private Menu mnu1;
	@FXML
	private MenuItem mnuItem111;
	@FXML
	private Menu mnu2;
	@FXML
	private Menu mnu3;
	@FXML
	private Menu mnu4;
	@FXML
	private Menu mnu5;
	@FXML
	private Menu mnu6;
	@FXML
	private Menu mnuParametros;
	@FXML
	private MenuItem mnuItmParametros;
	@FXML
	private Menu mnuAyuda;
	@FXML
	private MenuItem mnuAbout;
	@FXML
	private JFXButton btnLogin;
	@FXML
	private JFXButton btnSalir;

    
    private double dragOffsetX = 0;
	private double dragOffsetY = 0;
	private static final DateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 * @return 
	 * @return 
	 */
	
    @FXML
	public void initialize() throws FileNotFoundException {
    	
    	Date date = new Date();
        lblFecha.setText((sdf.format(date)));
        
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {        
        	LocalTime currentTime = LocalTime.now();
        	lblHora.setText(currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
        }),
             new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    	
//    	Image portrait = new Image(getClass().getResourceAsStream("/images/datawarehouse.png"));
//	    imgLogo = new ImageView(portrait);
//	    imgLogo.setSmooth(true);
//    	imgLogo.setPreserveRatio(true);
        
//      InputStream is = getClass().getResourceAsStream("/images/datawarehouse.png");
//		Image image = new Image(is);
//		imgLogo = new ImageView(image);

    	btnArticulos.setOnAction(e -> {
    		try {
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/articulos.fxml"));
    			Stage stage = new Stage();
    			stage.initStyle(StageStyle.UNDECORATED);
    			stage.initModality(Modality.APPLICATION_MODAL);
    			Scene scene = new Scene(loader.load());

//    			scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
				scene.getStylesheets().add("style.css");

    			scene.setOnMousePressed(e1 -> {
    				dragOffsetX = e1.getSceneX();
    				dragOffsetY = e1.getSceneY();
    			});

    			scene.setOnMouseDragged(e1 -> {
    				stage.setX(e1.getScreenX() - dragOffsetX);
    				stage.setY(e1.getScreenY() - dragOffsetY);
    			});

    			stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
    				if (KeyCode.ESCAPE == event.getCode()) {
    					stage.close();
    				}
    			});

    			stage.setScene(scene);
    			stage.centerOnScreen();
    			stage.show();

    		} catch (Exception e2) {
    			e2.printStackTrace();
    		}
    	});
    	
    	btnRubros.setOnAction(e -> {
    		try {
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/rubros.fxml"));
    			Stage stage = new Stage();
    			stage.initStyle(StageStyle.UNDECORATED);
    			stage.initModality(Modality.APPLICATION_MODAL);
    			Scene scene = new Scene(loader.load());
    			
//    			scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
				scene.getStylesheets().add("style.css");
    			
    			scene.setOnMousePressed(e1 -> {
    				dragOffsetX = e1.getSceneX();
    				dragOffsetY = e1.getSceneY();
    			});
    			
    			scene.setOnMouseDragged(e1 -> {
    				stage.setX(e1.getScreenX() - dragOffsetX);
    				stage.setY(e1.getScreenY() - dragOffsetY);
    			});
    			
    			stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
    				if (KeyCode.ESCAPE == event.getCode()) {
    					stage.close();
    				}
    			});
    			
    			stage.setScene(scene);
    			stage.centerOnScreen();
    			stage.show();
    			
    		} catch (Exception e2) {
    			e2.printStackTrace();
    		}
    	});
    	
    	btnMarcas.setOnAction(e -> {
    		try {
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/marcas.fxml"));
    			Stage stage = new Stage();
    			stage.initStyle(StageStyle.UNDECORATED);
    			stage.initModality(Modality.APPLICATION_MODAL);
    			Scene scene = new Scene(loader.load());
    			
//    			scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
				scene.getStylesheets().add("style.css");
    			
    			scene.setOnMousePressed(e1 -> {
    				dragOffsetX = e1.getSceneX();
    				dragOffsetY = e1.getSceneY();
    			});
    			
    			scene.setOnMouseDragged(e1 -> {
    				stage.setX(e1.getScreenX() - dragOffsetX);
    				stage.setY(e1.getScreenY() - dragOffsetY);
    			});
    			
    			stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
    				if (KeyCode.ESCAPE == event.getCode()) {
    					stage.close();
    				}
    			});
    			
    			stage.setScene(scene);
    			stage.centerOnScreen();
    			stage.show();
    			
    		} catch (Exception e2) {
    			e2.printStackTrace();
    		}
    	});
    	
    	btnUnidades.setOnAction(e -> {
    		try {
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/unidades.fxml"));
    			Stage stage = new Stage();
    			stage.initStyle(StageStyle.UNDECORATED);
    			stage.initModality(Modality.APPLICATION_MODAL);
    			Scene scene = new Scene(loader.load());
    			
//    			scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
				scene.getStylesheets().add("style.css");
    			
    			scene.setOnMousePressed(e1 -> {
    				dragOffsetX = e1.getSceneX();
    				dragOffsetY = e1.getSceneY();
    			});
    			
    			scene.setOnMouseDragged(e1 -> {
    				stage.setX(e1.getScreenX() - dragOffsetX);
    				stage.setY(e1.getScreenY() - dragOffsetY);
    			});
    			
    			stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
    				if (KeyCode.ESCAPE == event.getCode()) {
    					stage.close();
    				}
    			});
    			
    			stage.setScene(scene);
    			stage.centerOnScreen();
    			stage.show();
    			
    		} catch (Exception e2) {
    			e2.printStackTrace();
    		}
    	});
    	
    	btnDepositos.setOnAction(e -> {
    		try {
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/depositos.fxml"));
    			Stage stage = new Stage();
    			stage.initStyle(StageStyle.UNDECORATED);
    			stage.initModality(Modality.APPLICATION_MODAL);
    			Scene scene = new Scene(loader.load());
    			
//    			scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
				scene.getStylesheets().add("style.css");
    			
    			scene.setOnMousePressed(e1 -> {
    				dragOffsetX = e1.getSceneX();
    				dragOffsetY = e1.getSceneY();
    			});
    			
    			scene.setOnMouseDragged(e1 -> {
    				stage.setX(e1.getScreenX() - dragOffsetX);
    				stage.setY(e1.getScreenY() - dragOffsetY);
    			});
    			
    			stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
    				if (KeyCode.ESCAPE == event.getCode()) {
    					stage.close();
    				}
    			});
    			
    			stage.setScene(scene);
    			stage.centerOnScreen();
    			stage.show();
    			
    		} catch (Exception e2) {
    			e2.printStackTrace();
    		}
    	});
    	
    	btnUbicaciones.setOnAction(e -> {
    		try {
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ubicaciones.fxml"));
    			Stage stage = new Stage();
    			stage.initStyle(StageStyle.UNDECORATED);
    			stage.initModality(Modality.APPLICATION_MODAL);
    			Scene scene = new Scene(loader.load());
    			
//    			scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
				scene.getStylesheets().add("style.css");
    			
    			scene.setOnMousePressed(e1 -> {
    				dragOffsetX = e1.getSceneX();
    				dragOffsetY = e1.getSceneY();
    			});
    			
    			scene.setOnMouseDragged(e1 -> {
    				stage.setX(e1.getScreenX() - dragOffsetX);
    				stage.setY(e1.getScreenY() - dragOffsetY);
    			});
    			
    			stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
    				if (KeyCode.ESCAPE == event.getCode()) {
    					stage.close();
    				}
    			});
    			
    			stage.setScene(scene);
    			stage.centerOnScreen();
    			stage.show();
    			
    		} catch (Exception e2) {
    			e2.printStackTrace();
    		}
    	});
    	
    	btnLineas.setOnAction(e -> {
    		try {
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/lineas.fxml"));
    			Stage stage = new Stage();
    			stage.initStyle(StageStyle.UNDECORATED);
    			stage.initModality(Modality.APPLICATION_MODAL);
    			Scene scene = new Scene(loader.load());
    			
//    			scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
				scene.getStylesheets().add("style.css");
    			
    			scene.setOnMousePressed(e1 -> {
    				dragOffsetX = e1.getSceneX();
    				dragOffsetY = e1.getSceneY();
    			});
    			
    			scene.setOnMouseDragged(e1 -> {
    				stage.setX(e1.getScreenX() - dragOffsetX);
    				stage.setY(e1.getScreenY() - dragOffsetY);
    			});
    			
    			stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
    				if (KeyCode.ESCAPE == event.getCode()) {
    					stage.close();
    				}
    			});
    			
    			stage.setScene(scene);
    			stage.centerOnScreen();
    			stage.show();
    			
    		} catch (Exception e2) {
    			e2.printStackTrace();
    		}
    	});
    	
    	btnPresentaciones.setOnAction(e -> {
    		try {
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/presentaciones.fxml"));
    			Stage stage = new Stage();
    			stage.initStyle(StageStyle.UNDECORATED);
    			stage.initModality(Modality.APPLICATION_MODAL);
    			Scene scene = new Scene(loader.load());
    			
//    			scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
				scene.getStylesheets().add("style.css");
    			
    			scene.setOnMousePressed(e1 -> {
    				dragOffsetX = e1.getSceneX();
    				dragOffsetY = e1.getSceneY();
    			});
    			
    			scene.setOnMouseDragged(e1 -> {
    				stage.setX(e1.getScreenX() - dragOffsetX);
    				stage.setY(e1.getScreenY() - dragOffsetY);
    			});
    			
    			stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
    				if (KeyCode.ESCAPE == event.getCode()) {
    					stage.close();
    				}
    			});
    			
    			stage.setScene(scene);
    			stage.centerOnScreen();
    			stage.show();
    			
    		} catch (Exception e2) {
    			e2.printStackTrace();
    		}
    	});
    	
    	
    	btnSalir.setOnAction(e -> {
    		Platform.exit();
    		System.exit(0);
    	});



    }
}

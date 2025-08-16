package controller;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CrudTelefonos {
	
	@FXML
	private ImageView imgPersonas;
	@FXML
	private Label lblFecha;
	@FXML
	private Label lblHora;
	@FXML
	private TableView<String> tblTelefonos;
	@FXML
	private JFXTextField txtBuscar;
	@FXML
	private JFXButton btnLimpiar;
	@FXML
	private JFXTextField txtNroDocumento;
	@FXML
	private JFXTextField txtTipoPersona;
	@FXML
	private JFXTextField txtTelefono;
	@FXML
	private JFXButton btnAgregar;
	@FXML
	private JFXButton btnBorrar;
	@FXML
	private JFXButton btnModificar;
	@FXML
	private JFXButton btnImprimir;
	@FXML
	private JFXButton btnSalir;

	private static final String MSG = "Requerido";
	private RequiredFieldValidator validator = new RequiredFieldValidator(); 
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy");
	
    @FXML
  	public void initialize() throws FileNotFoundException {
    	
    	LocalDate fecha = LocalDate.now();
        lblFecha.setText((dtf.format(fecha)));
        
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {        
        	LocalTime currentTime = LocalTime.now();
        	lblHora.setText(currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
        }),	new KeyFrame(Duration.seconds(1))
        		);
        
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    
        validator.setMessage(MSG);
    	
//		imgPersonas.setImage(new Image("/images/ic_contacts_black_36dp.png"));
		imgPersonas.setImage(new Image("/resources/images/telefonos.png"));
		imgPersonas.setSmooth(true);
		imgPersonas.setPreserveRatio(true);
    	
		
		
		
		
		
		Platform.runLater(() -> txtBuscar.requestFocus());
		
		btnSalir.setOnAction(e -> {
			((Stage)(((Button)e.getSource()).getScene().getWindow())).close();
		});
    }
    
}

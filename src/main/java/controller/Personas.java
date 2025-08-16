package controller;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Personas {
	
	@FXML
    private ImageView imgPersonas;
    @FXML
    private ImageView imgSecurity;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblHora;
    @FXML
    private JFXButton btnDomicilio;
    @FXML
    private ImageView imgDomicilios;
    @FXML
    private JFXButton btnBancos;
    @FXML
    private ImageView imgBancos;
    @FXML
    private JFXButton btnZonas;
    @FXML
    private ImageView imgZonas;
    @FXML
    private JFXButton btnCorreos;
    @FXML
    private ImageView imgCorreos;
    @FXML
    private JFXButton btnTelefono;
    @FXML
    private ImageView imgTelefonos;
    @FXML
    private JFXButton btnComisiones;
    @FXML
    private ImageView imgComisiones;
    @FXML
    private JFXButton btnListas;
    @FXML
    private ImageView imgListas;
    @FXML
    private ImageView imgVales1;
    @FXML
    private JFXButton btnSalir;
    @FXML
    private ImageView imgSalir;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnBorrar;
    @FXML
    private Button btnModificar;
    @FXML
    private JFXRadioButton rbCliente;
    @FXML
    private ToggleGroup tgTipoPersona;
    @FXML
    private JFXRadioButton rbProveedor;
    @FXML
    private JFXRadioButton rbTransportista;
    @FXML
    private JFXRadioButton rbEmpresa;
    @FXML
    private JFXRadioButton rbStaff;
    @FXML
    private JFXRadioButton rbVendedor;
    @FXML
    private JFXRadioButton rbMasculino;
    @FXML
    private ToggleGroup tgSexo;
    @FXML
    private JFXRadioButton rbFemenino;
    @FXML
    private JFXRadioButton rbNoAplica;
    @FXML
    private JFXTextField txtDocumento;
    @FXML
    private JFXComboBox<String> cmbSituacionFiscal;
    @FXML
    private JFXTextField txtCuit;
    @FXML
    private JFXDatePicker dpFechaIngreso;
    @FXML
    private JFXDatePicker dpFechaUltimaCompra;
    @FXML
    private JFXTextField txtLimiteCredito;
    @FXML
    private JFXTextField txtLimiteFacturasVencidas;
    @FXML
    private JFXTextField txtPagoMinimo;
    @FXML
    private JFXTextField txtSaldoCuentaCorriente;
    @FXML
    private JFXTextArea txaObservaciones;
    @FXML
    private JFXTextField txtApellido;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtDocumentoTitular;
    @FXML
    private JFXRadioButton rbHijo;
    @FXML
    private ToggleGroup tgParentesco;
    @FXML
    private JFXRadioButton rbHermano;
    @FXML
    private JFXRadioButton rbPadre;
    @FXML
    private JFXRadioButton rbMadre;
    @FXML
    private JFXRadioButton rbAbuelo;
    @FXML
    private JFXRadioButton rbSobrino;
    @FXML
    private JFXRadioButton rbPrimo;
    @FXML
    private JFXRadioButton rbTio;
    @FXML
    private JFXRadioButton rbNoCorresponde;
    @FXML
    private JFXCheckBox cbJubilado;
    @FXML
    private JFXCheckBox cbAdherente;
    @FXML
    private JFXDatePicker dpFechaBaja;
    @FXML
    private JFXRadioButton rbTerrestre;
    @FXML
    private ToggleGroup tgTipoTransporte;
    @FXML
    private JFXRadioButton rbFluvial;
    @FXML
    private JFXRadioButton rbAereo;
    @FXML
    private JFXRadioButton rbHabilitado;
    @FXML
    private ToggleGroup tgEstado;
    @FXML
    private JFXRadioButton rbNoHabilitado;
    @FXML
    private JFXRadioButton rbBaja;
	
    private double dragOffsetX = 0;
	private double dragOffsetY = 0;
    private static final String MSG = "Requerido";
	private RequiredFieldValidator validator = new RequiredFieldValidator(); 
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy");
    
    private ObservableList<String> listaCondicionIVA = FXCollections.observableArrayList("IVA Responsable Inscripto",
			"IVA Responsable No Inscripto", "IVA No Responsable", "IVA Sujeto Exento", "Consumidor Final",
			"Responsable Monotributo", "Sujeto No Categorizado", "Proveedor del Exterior", "Cliente del Exterior",
			"IVA Liberado - Ley 19.640", "IVA Responsable Inscripto – Agente de Percepción",
			"Pequeño Contribuyente Eventual", "Monotributista Social", "Pequeño Contribuyente Eventual Social");
    
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
		imgPersonas.setImage(new Image("/resources/images/ic_contacts_black_36dp.png"));
		imgPersonas.setSmooth(true);
		imgPersonas.setPreserveRatio(true);
		
		imgDomicilios.setImage(new Image("/resources/images/domicilios.png"));
		imgDomicilios.setSmooth(true);
		imgDomicilios.setPreserveRatio(true);
		
		imgBancos.setImage(new Image("/resources/images/bancos.png"));
		imgBancos.setSmooth(true);
		imgBancos.setPreserveRatio(true);
		
		imgZonas.setImage(new Image("/resources/images/zonas.png"));
		imgZonas.setSmooth(true);
		imgZonas.setPreserveRatio(true);
		
		imgCorreos.setImage(new Image("/resources/images/correos.png"));
		imgCorreos.setSmooth(true);
		imgCorreos.setPreserveRatio(true);
		
		imgTelefonos.setImage(new Image("/resources/images/telefonos.png"));
		imgTelefonos.setSmooth(true);
		imgTelefonos.setPreserveRatio(true);
		
		imgComisiones.setImage(new Image("/resources/images/comisiones.png"));
		imgComisiones.setSmooth(true);
		imgComisiones.setPreserveRatio(true);
		
		imgListas.setImage(new Image("/resources/images/listas.png"));
		imgListas.setSmooth(true);
		imgListas.setPreserveRatio(true);
		
		imgSalir.setImage(new Image("/resources/images/salir.png"));
		imgSalir.setSmooth(true);
		imgSalir.setPreserveRatio(true);
		
		
		
		
		
		
		
		
		
		
    
    
		
		cmbSituacionFiscal.setItems(listaCondicionIVA);
		
		cmbSituacionFiscal.getValidators().add(validator);

		cmbSituacionFiscal.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				cmbSituacionFiscal.validate();
			}
		});
    
    
    
		
		btnDomicilio.setOnAction(e -> {
			try {
//				FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/localidades.fxml"));
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/crudDomicilios.fxml"));
				Stage stage = new Stage();
				stage.initStyle(StageStyle.UNDECORATED);
				stage.initModality(Modality.APPLICATION_MODAL);
				Scene scene = new Scene(loader.load());
				
//				scene.getStylesheets().add("/styles/style.css");
				scene.getStylesheets().add("/resources/styles/style.css");
				
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
		
		btnBancos.setOnAction(e -> {
			try {
//				FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/localidades.fxml"));
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/crudBancos.fxml"));
				Stage stage = new Stage();
				stage.initStyle(StageStyle.UNDECORATED);
				stage.initModality(Modality.APPLICATION_MODAL);
				Scene scene = new Scene(loader.load());
				
//				scene.getStylesheets().add("/styles/style.css");
				scene.getStylesheets().add("/resources/styles/style.css");
				
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
		
		btnZonas.setOnAction(e -> {
			try {
//				FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/localidades.fxml"));
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/crudZonas.fxml"));
				Stage stage = new Stage();
				stage.initStyle(StageStyle.UNDECORATED);
				stage.initModality(Modality.APPLICATION_MODAL);
				Scene scene = new Scene(loader.load());
				
//				scene.getStylesheets().add("/styles/style.css");
				scene.getStylesheets().add("/resources/styles/style.css");
				
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
		
		btnCorreos.setOnAction(e -> {
			try {
//				FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/localidades.fxml"));
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/crudCorreos.fxml"));
				Stage stage = new Stage();
				stage.initStyle(StageStyle.UNDECORATED);
				stage.initModality(Modality.APPLICATION_MODAL);
				Scene scene = new Scene(loader.load());
				
//				scene.getStylesheets().add("/styles/style.css");
				scene.getStylesheets().add("/resources/styles/style.css");
				
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
		
		btnTelefono.setOnAction(e -> {
			try {
//				FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/localidades.fxml"));
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/crudTelefonos.fxml"));
				Stage stage = new Stage();
				stage.initStyle(StageStyle.UNDECORATED);
				stage.initModality(Modality.APPLICATION_MODAL);
				Scene scene = new Scene(loader.load());
				
//				scene.getStylesheets().add("/styles/style.css");
				scene.getStylesheets().add("/resources/styles/style.css");
				
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
		
		btnComisiones.setOnAction(e -> {
			try {
//				FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/localidades.fxml"));
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/crudComisiones.fxml"));
				Stage stage = new Stage();
				stage.initStyle(StageStyle.UNDECORATED);
				stage.initModality(Modality.APPLICATION_MODAL);
				Scene scene = new Scene(loader.load());
				
//				scene.getStylesheets().add("/styles/style.css");
				scene.getStylesheets().add("/resources/styles/style.css");
				
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
		
		btnListas.setOnAction(e -> {
			try {
//				FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/localidades.fxml"));
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/crudListas.fxml"));
				Stage stage = new Stage();
				stage.initStyle(StageStyle.UNDECORATED);
				stage.initModality(Modality.APPLICATION_MODAL);
				Scene scene = new Scene(loader.load());
				
//				scene.getStylesheets().add("/styles/style.css");
				scene.getStylesheets().add("/resources/styles/style.css");
				
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
		
		Platform.runLater(() -> txtDocumento.requestFocus());
		
		
		
		
		
		
		
		
		
		btnSalir.setOnAction(e -> {
//			((Node)(e.getSource())).getScene().getWindow().hide(); // Parecido, pero no cierra la ventana, sólo la oculta.
			((Stage)(((Button)e.getSource()).getScene().getWindow())).close();
		});
		
    }
    
    
    
}

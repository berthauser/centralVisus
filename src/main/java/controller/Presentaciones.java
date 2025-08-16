package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;

import consumer.PresentacionAccess;
import io.vertx.core.Future;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Presentacion;

public class Presentaciones {
	@FXML
	private TableView<Presentacion> tblPresentaciones;
	@FXML
	private TableColumn<Presentacion, String> colContenedor;
	@FXML
	private TableColumn<Presentacion, Number> colUnidad;
	@FXML
	private TableColumn<Presentacion, Number> colValorUnidad;
	@FXML
	private TableColumn<Presentacion, String> colTipoDecremento;
	@FXML
	private Button btnAgregar;
	@FXML
	private Button btnBorrar;
	@FXML
	private Button btnModificar;
	@FXML
	private ImageView imgHeader;
	@FXML
	private Label lblFecha;
	@FXML
	private Label lblHora;

	private ObservableList<Presentacion> presentacionData = FXCollections.observableArrayList();
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy");
    private Stage primaryStage;
    
	private static double dragOffsetX = 0;
	private static double dragOffsetY = 0;
	
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
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
		
		// Cargo las imagenes
//    	imgHeader.setImage(new Image("/images/ic_book_black_36dp.png"));
    	imgHeader.setImage(new Image("/resources/images/ic_book_black_36dp.png"));
		imgHeader.setSmooth(true);
		imgHeader.setPreserveRatio(true);

	    clearTabla();

	    inicializarTabla();

	    getPresentacionesWS();

	    tblPresentaciones.setItems(presentacionData);
	    
	    /**
         * Cuando el usuario hace click en el botan Agregar se abre la ventana 
         * de dialogo para agregar o editar los detalles.
         */
        btnAgregar.setOnAction(ins -> {
        	  Presentacion presentacion = new Presentacion();
              boolean okClicked = showVentana(presentacion);
              if (okClicked) {
                  presentacionData.add(presentacion);
              }
        });
	    
        btnBorrar.setOnAction(del -> {
        	if (tblPresentaciones.getSelectionModel().getSelectedIndex() >= 0) {
        		Presentacion presentacion = tblPresentaciones.getItems().get(tblPresentaciones.getSelectionModel().getSelectedIndex());

                JFXAlert<String> alert = new JFXAlert<>((Stage) btnBorrar.getScene().getWindow());
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setOverlayClose(false);
                JFXDialogLayout layout = new JFXDialogLayout();
                layout.setHeading(new Label("Confirme por favor!"));
                layout.setBody(new Label("aDesea borrar este item? -> " + presentacion.getContenedor()));
                
                JFXButton closeButton = new JFXButton("Aceptar");
                closeButton.getStyleClass().add("dialog-accept");
                closeButton.setOnAction(event -> alert.hideWithAnimation());
                layout.setActions(closeButton);
                alert.setContent(layout);

                Optional<String> result = alert.showAndWait();

                if (result.isPresent()) {
                	Future<String> deleteResult = PresentacionAccess.delete(presentacion);
                	deleteResult.onComplete (ar -> {
                		if (ar.succeeded()) {
                			Platform.runLater(new Runnable() {
                				@Override
                				public void run() {
                					presentacionData.remove(tblPresentaciones.getSelectionModel().getSelectedIndex());
                					tblPresentaciones.getSelectionModel().clearSelection();
                				}
                			});

                		} else {
                			System.out.println(ar.cause());
                		}
                	});
                } 
            }
        });
        
        btnModificar.setOnAction(upd -> {
        	Presentacion selectedPresentacion = tblPresentaciones.getSelectionModel().getSelectedItem();
        	if (selectedPresentacion!= null) {
        		showVentana(selectedPresentacion);
            } else {
                Alert exceptionDialog = new Alert(Alert.AlertType.WARNING);
                exceptionDialog.setTitle("Sin seleccian");
                exceptionDialog.setHeaderText("No hay seleccian");
                exceptionDialog.setContentText("Seleccione una Presentacion de la tabla");
                exceptionDialog.show();
            }
        });
	    
	}

	/**
     * Abre una ventana de dialogo para editar detalles del Presentacion.
     * Si el usuario hace click en el botan Registrar, se graban los 
     * cambios en el objeto "Presentacion" y devuelve "true"
     *
     * @param Presentacion el objeto "Presentacion" listo para editar o insertar
     * @return true si el usuario clickea Registrar, de otra manera false.
     */
	
	private boolean showVentana(Presentacion presentacion) {
		try {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/iuPresentaciones.fxml"));
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/iuPresentaciones.fxml"));

			Stage stage = new Stage();
			stage.initStyle(StageStyle.UNDECORATED);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(this.primaryStage);
			Scene scene = new Scene(loader.load());
//			scene.getStylesheets().add("/styles/style.css");
			scene.getStylesheets().add("/resources/styles/style.css");
			stage.setScene(scene);

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

			// Set the person into the controller.
			IUPresentaciones controller = loader.getController();
			controller.setDialogStage(stage);
			controller.setPresentacion(presentacion);

			// Show the dialog and wait until the user closes it
			stage.showAndWait();

			return controller.isbtnRegistrarClicked();

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private void getPresentacionesWS() {
		PresentacionAccess.getPresentaciones(presentacionData);
	}

	private void inicializarTabla() {
		colContenedor.setCellValueFactory(cellData -> cellData.getValue().contenedorProperty());
		colUnidad.setCellValueFactory(cellData -> cellData.getValue().unidadesProperty());
		colValorUnidad.setCellValueFactory(cellData -> cellData.getValue().valor_umedidaProperty());
		colTipoDecremento.setCellValueFactory(cellData -> cellData.getValue().tipo_decrementoProperty());
	}

	private void clearTabla() {
		tblPresentaciones.getItems().clear();
	}

}
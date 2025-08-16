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

import consumer.MarcaAccess;
import io.vertx.core.Future;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Marca;

public class Marcas {
	@FXML
	private TextField txtBuscar;
	@FXML
	private TableView<Marca> tblMarcas;
	@FXML
	private TableColumn<Marca, String> colDescripcion;
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
    
    private ObservableList<Marca> marcasData = FXCollections.observableArrayList();
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
//        imgHeader.setImage(new Image("/images/ic_collections_bookmark_black_36dp.png"));
        imgHeader.setImage(new Image("/resources/images/ic_collections_bookmark_black_36dp.png"));
  	    imgHeader.setSmooth(true);
  	    imgHeader.setPreserveRatio(true);

	    clearTablaMarcas();

	    inicializarTabla();

	    getMarcasWS();

	    tblMarcas.setItems(marcasData);
	    
	    // 1. Wrapeo la lista ObservableList en una lista FilteredList (inicialmente muestra todos los datos).
	    // Los filtros en la lista FilteredList dependen de un Predicate especificado. El valor inicial del Predicate es siempre verdadero: b -> true
	    FilteredList<Marca> filteredData = new FilteredList<>(marcasData, b -> true);
	    // 2. Seteo el filtro Predicate cuando el filtro cambia.
	    // Agrego un Listener al filtro del texto de basqueda. Cuando el usuario cambia el texto, el Predicate de la FilteredList se actualiza.
	   
	    txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
	    	filteredData.setPredicate(rubros -> {
	    		// If filter text is empty, display all data.
	    		if (newValue == null || newValue.isEmpty()) {
	    			return true;
	    		}
	    		// Compare first name and last name of every person with filter text.
	    		String lowerCaseFilter = newValue.toLowerCase();

	    		if (rubros.getDescripcion().toString().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
	    			return true; // Filter matches first name.
	    		} else if (rubros.getDescripcion().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
	    			return true; // Filter matches last name.
	    		}
	    		else  
	    			return false; // Does not match.
	    	});
	    });
	    // 3. Wrapeo la FilteredList en una SortedList.
	    // FilteredList es inmodificable, por lo tanto no puedo ordenarla (sorted); preciso "wrapearla" para eso.
	    SortedList<Marca> sortedData = new SortedList<>(filteredData);
	    // 4. Empareja (Bind) usando "comparator" de la SortedListcon con el "comparator" de la TableView.
	    // Si no se hace, el "sorting" de la TableView no tiene efecto. 
	    // A click on the column header changes the sorting of the TableView. 
	    // But now that we have a separate SortedList we must bind the sorting of that list to the TableView. 
	    // Notice that the TableView will return back to the original, unsorted state after three clicks on the column header 
	    sortedData.comparatorProperty().bind(tblMarcas.comparatorProperty());
	    // 5. Add sorted (and filtered) data to the table.
	    tblMarcas.setItems(sortedData);
	    
	    /**
         * Cuando el usuario hace click en el botan Agregar se abre la ventana 
         * de dialogo para agregar o editar los detalles.
         */
        btnAgregar.setOnAction(ins -> {
        	  Marca Marca = new Marca();
              boolean okClicked = showVentana(Marca);
              if (okClicked) {
                  marcasData.add(Marca);
              }
        });
	    
        btnBorrar.setOnAction(del -> {
        	if (tblMarcas.getSelectionModel().getSelectedIndex() >= 0) {
        		Marca Marca = tblMarcas.getItems().get(tblMarcas.getSelectionModel().getSelectedIndex());

                JFXAlert<String> alert = new JFXAlert<>((Stage) btnBorrar.getScene().getWindow());
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setOverlayClose(false);
                JFXDialogLayout layout = new JFXDialogLayout();
                layout.setHeading(new Label("Confirme por favor!"));
                layout.setBody(new Label("aDesea borrar este item? -> " + Marca.getDescripcion()));
                
                JFXButton closeButton = new JFXButton("Aceptar");
                closeButton.getStyleClass().add("dialog-accept");
                closeButton.setOnAction(event -> alert.hideWithAnimation());
                layout.setActions(closeButton);
                alert.setContent(layout);

                Optional<String> result = alert.showAndWait();

                if (result.isPresent()) {
                	Future<String> deleteResult = MarcaAccess.delete(Marca);
                	deleteResult.onComplete (ar -> {
                		if (ar.succeeded()) {
                			Platform.runLater(new Runnable() {
                				@Override
                				public void run() {
                					marcasData.remove(filteredData.getSourceIndex(
                							sortedData.getSourceIndex(tblMarcas.getSelectionModel().getSelectedIndex())
                							));
                					tblMarcas.getSelectionModel().clearSelection();
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
        	Marca selectedMarca = tblMarcas.getSelectionModel().getSelectedItem();
        	if (selectedMarca!= null) {
        		showVentana(selectedMarca);
            } else {
                Alert exceptionDialog = new Alert(Alert.AlertType.WARNING);
                exceptionDialog.setTitle("Sin seleccian");
                exceptionDialog.setHeaderText("No hay seleccian");
                exceptionDialog.setContentText("Seleccione un Marca de la tabla");
                exceptionDialog.show();
            }
        });
	    
	}

	/**
     * Abre una ventana de dialogo para editar detalles del Marca.
     * Si el usuario hace click en el botan Registrar, se graban los 
     * cambios en el objeto "Marca" y devuelve "true"
     *
     * @param Marca el objeto "Marca" listo para editar o insertar
     * @return true si el usuario clickea Registrar, de otra manera false.
     */
	
	private boolean showVentana(Marca Marca) {
		try {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/iuMarcas.fxml"));
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/iuMarcas.fxml"));

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
			IUMarcas controller = loader.getController();
			controller.setDialogStage(stage);
			controller.setMarca(Marca);

			// Show the dialog and wait until the user closes it
			stage.showAndWait();

			return controller.isbtnRegistrarClicked();

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private void getMarcasWS() {
		MarcaAccess.getMarcas(marcasData);
	}

	private void inicializarTabla() {
		colDescripcion.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
	}

	private void clearTablaMarcas() {
		tblMarcas.getItems().clear();
	}

}
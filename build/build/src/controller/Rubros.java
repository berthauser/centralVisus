package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;

import consumer.RubroAccess;
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
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Rubro;

public class Rubros {
	@FXML
	private TextField txtBuscar;
	@FXML
	private TableView<Rubro> tblRubros;
	
	/**
	 * One somewhat strange part of the property API is that IntegerProperty
	 * implements ObservableValue<Number>, not ObservableValue<Integer>.
	 * Por lo tanto, las columnas deben ser Number y no Integer
	 */
	
	@FXML
	private TableColumn<Rubro, String> colDescripcion;
	@FXML
	private TableColumn<Rubro, Number> colId;
	@FXML
	private Button btnAgregar;
	@FXML
	private Button btnBorrar;
	@FXML
	private Button btnModificar;
	@FXML
	private ImageView imgRubro;
	@FXML
    private Label lblFecha;
    @FXML
    private Label lblHora;
	
    private ObservableList<Rubro> rubrosData = FXCollections.observableArrayList();
    private static final DateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
    private Stage primaryStage;
    
	private static double dragOffsetX = 0;
	private static double dragOffsetY = 0;
	
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	public void initialize() throws FileNotFoundException {
		
		Date date = new Date();
        lblFecha.setText((sdf.format(date)));
        
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {        
        	LocalTime currentTime = LocalTime.now();
        	lblHora.setText(currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
        }),	new KeyFrame(Duration.seconds(1))
        		);
        
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
		
		// Cargo las imagenes
//	    Image image0 = new Image(new FileInputStream("src/main/resources/images/rubros.png"));
//	    imgRubro.setImage(image0);
//	    imgRubro.setSmooth(true);
//	    imgRubro.setPreserveRatio(true);

	    clearTablaRubros();

	    inicializarTabla();

	    getRubrosWS();

	    tblRubros.setItems(rubrosData);
	    
	    // 1. Wrapeo la lista ObservableList en una lista FilteredList (inicialmente muestra todos los datos).
	    // Los filtros en la lista FilteredList dependen de un Predicate especificado. El valor inicial del Predicate es siempre verdadero: b -> true
	    FilteredList<Rubro> filteredData = new FilteredList<>(rubrosData, b -> true);
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
	    SortedList<Rubro> sortedData = new SortedList<>(filteredData);
	    // 4. Empareja (Bind) usando "comparator" de la SortedListcon con el "comparator" de la TableView.
	    // Si no se hace, el "sorting" de la TableView no tiene efecto. 
	    // A click on the column header changes the sorting of the TableView. 
	    // But now that we have a separate SortedList we must bind the sorting of that list to the TableView. 
	    // Notice that the TableView will return back to the original, unsorted state after three clicks on the column header 
	    sortedData.comparatorProperty().bind(tblRubros.comparatorProperty());
	    // 5. Add sorted (and filtered) data to the table.
	    tblRubros.setItems(sortedData);
	    
	    /**
         * Cuando el usuario hace click en el botan Agregar se abre la ventana 
         * de dialogo para agregar o editar los detalles.
         */
        btnAgregar.setOnAction(ins -> {
        	  Rubro rubro = new Rubro();
              boolean okClicked = showVentanaRubro(rubro);
              if (okClicked) {
                  rubrosData.add(rubro);
              }
        });
	    
        btnBorrar.setOnAction(del -> {
        	if (tblRubros.getSelectionModel().getSelectedIndex() >= 0) {
        		Rubro rubro = tblRubros.getItems().get(tblRubros.getSelectionModel().getSelectedIndex());

                JFXAlert<String> alert = new JFXAlert<>((Stage) btnBorrar.getScene().getWindow());
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setOverlayClose(false);
                JFXDialogLayout layout = new JFXDialogLayout();
                layout.setHeading(new Label("Confirme por favor!"));
                layout.setBody(new Label("aDesea borrar este item? -> " + rubro.getDescripcion()));
                
                JFXButton closeButton = new JFXButton("Aceptar");
                closeButton.getStyleClass().add("dialog-accept");
                closeButton.setOnAction(event -> alert.hideWithAnimation());
                layout.setActions(closeButton);
                alert.setContent(layout);

                Optional<String> result = alert.showAndWait();

                if (result.isPresent()) {
                	Future<String> deleteResult = RubroAccess.deleteRubro(rubro);
                	deleteResult.onComplete (ar -> {
                		if (ar.succeeded()) {
                			Platform.runLater(new Runnable() {
                				@Override
                				public void run() {
                					rubrosData.remove(filteredData.getSourceIndex(
                							sortedData.getSourceIndex(tblRubros.getSelectionModel().getSelectedIndex())
                							));
                					tblRubros.getSelectionModel().clearSelection();
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
        	Rubro selectedRubro = tblRubros.getSelectionModel().getSelectedItem();
        	if (selectedRubro!= null) {
        		showVentanaRubro(selectedRubro);
            } else {
                Alert exceptionDialog = new Alert(Alert.AlertType.WARNING);
                exceptionDialog.setTitle("Sin seleccian");
                exceptionDialog.setHeaderText("No hay seleccian");
                exceptionDialog.setContentText("Seleccione un RUBRO de la tabla");
                exceptionDialog.show();
            }
        });
	    
	}

	/**
     * Abre una ventana de dialogo para editar detalles del Rubro.
     * Si el usuario hace click en el botan Registrar, se graban los 
     * cambios en el objeto "rubro" y devuelve "true"
     *
     * @param rubro el objeto "rubro" listo para editar o insertar
     * @return true si el usuario clickea Registrar, de otra manera false.
     */
	
	private boolean showVentanaRubro(Rubro rubro) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/iuRubros.fxml"));

			Stage stage = new Stage();
			stage.initStyle(StageStyle.UNDECORATED);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(this.primaryStage);
			Scene scene = new Scene(loader.load());
//			scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
			scene.getStylesheets().add("style.css");
			stage.setScene(scene);

			scene.setOnMousePressed(e1 -> {
				dragOffsetX = e1.getSceneX();
				dragOffsetY = e1.getSceneY();
			});

			scene.setOnMouseDragged(e1 -> {
				stage.setX(e1.getScreenX() - dragOffsetX);
				stage.setY(e1.getScreenY() - dragOffsetY);
			});

			// Set the person into the controller.
			IURubros controller = loader.getController();
			controller.setDialogStage(stage);
			controller.setRubro(rubro);

			// Show the dialog and wait until the user closes it
			stage.showAndWait();

			return controller.isbtnRegistrarClicked();

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private void getRubrosWS() {
		RubroAccess.getRubros(rubrosData);
	}

	private void inicializarTabla() {
		colId.setCellValueFactory(cellData -> cellData.getValue().idrubroProperty());
		colDescripcion.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
	}

	private void clearTablaRubros() {
		tblRubros.getItems().clear();
	}
}

package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import consumer.LocalidadAccess;
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
import javafx.scene.control.ButtonType;
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
import jfxtras.styles.jmetro.FlatAlert;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import model.Localidad;

public class Localidades {
	
	@FXML
    private TextField txtBuscar;
    @FXML
    private TableView<Localidad> tblLocalidades;
    @FXML
    private TableColumn<Localidad, String> colNombre;
    @FXML
    private TableColumn<Localidad, Number> colCodigoPostal;
    @FXML
    private TableColumn<Localidad, String> colProvincia;
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

    private Stage primaryStage;
    private static double dragOffsetX = 0;
    private static double dragOffsetY = 0;
    private ObservableList<Localidad> listLocalidad = FXCollections.observableArrayList();
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy");
	
	
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
		
//        imgHeader.setImage(new Image("/images/ic_business_black_36dp.png"));
        imgHeader.setImage(new Image("/resources/images/ic_business_black_36dp.png"));
   	    imgHeader.setSmooth(true);
   	    imgHeader.setPreserveRatio(true);

	    clearTabla();

	    inicializarTabla();

	    getLineasWS();

	    tblLocalidades.setItems(listLocalidad);
	    
	    // 1. Wrapeo la lista ObservableList en una lista FilteredList (inicialmente muestra todos los datos).
	    // Los filtros en la lista FilteredList dependen de un Predicate especificado. El valor inicial del Predicate es siempre verdadero: b -> true
	    FilteredList<Localidad> filteredData = new FilteredList<>(listLocalidad, b -> true);
	    // 2. Seteo el filtro Predicate cuando el filtro cambia.
	    // Agrego un Listener al filtro del texto de basqueda. Cuando el usuario cambia el texto, el Predicate de la FilteredList se actualiza.
	   
	    txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
	    	filteredData.setPredicate(localidades -> {
	    		// If filter text is empty, display all data.
	    		if (newValue == null || newValue.isEmpty()) {
	    			return true;
	    		}
	    		// Compare first name and last name of every person with filter text.
	    		String lowerCaseFilter = newValue.toLowerCase();

	    		if (localidades.getNombre().toString().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
	    			return true; // Filter matches first name.
	    		} else if (localidades.getNombre().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
	    			return true; // Filter matches last name.
	    		}
	    		else  
	    			return false; // Does not match.
	    	});
	    });
	    // 3. Wrapeo la FilteredList en una SortedList.
	    // FilteredList es inmodificable, por lo tanto no puedo ordenarla (sorted); preciso "wrapearla" para eso.
	    SortedList<Localidad> sortedData = new SortedList<>(filteredData);
	    // 4. Empareja (Bind) usando "comparator" de la SortedListcon con el "comparator" de la TableView.
	    // Si no se hace, el "sorting" de la TableView no tiene efecto. 
	    // A click on the column header changes the sorting of the TableView. 
	    // But now that we have a separate SortedList we must bind the sorting of that list to the TableView. 
	    // Notice that the TableView will return back to the original, unsorted state after three clicks on the column header 
	    sortedData.comparatorProperty().bind(tblLocalidades.comparatorProperty());
	    // 5. Add sorted (and filtered) data to the table.
	    tblLocalidades.setItems(sortedData);
	    
        btnAgregar.setOnAction(ins -> {
        	  Localidad localidad = new Localidad();
              boolean okClicked = showVentana(localidad);
              if (okClicked) {
                  listLocalidad.add(localidad);
              }
        });
	    
        btnBorrar.setOnAction(del -> {
        	if (tblLocalidades.getSelectionModel().getSelectedIndex() >= 0) {
        		Localidad localidad = tblLocalidades.getItems()
        				.get(tblLocalidades.getSelectionModel().getSelectedIndex());

        		JMetro jm = new JMetro(Style.DARK);
        		FlatAlert alert = new FlatAlert(Alert.AlertType.WARNING);
        		alert.setHeaderText("Confirme por favor!");
        		alert.setContentText("¿Desea borrar la Localidad seleccionada?");
        		jm.setScene(alert.getDialogPane().getScene());

        		Optional<ButtonType> result = alert.showAndWait();

        		if ((result.isPresent()) && (result.get() == ButtonType.OK)) {

        			Future<String> deleteResult = LocalidadAccess.delete(localidad);
        			deleteResult.onComplete(ar -> {
        				if (ar.succeeded()) {
        					Platform.runLater(new Runnable() {
        						@Override
        						public void run() {
        							listLocalidad.remove(filteredData.getSourceIndex(sortedData
        									.getSourceIndex(tblLocalidades.getSelectionModel().getSelectedIndex())));
        							tblLocalidades.getSelectionModel().clearSelection();
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
        	Localidad selectedLocalidad = tblLocalidades.getSelectionModel().getSelectedItem();
        	if (selectedLocalidad!= null) {
        		showVentana(selectedLocalidad);
            } else {
                Alert exceptionDialog = new Alert(Alert.AlertType.WARNING);
                exceptionDialog.setTitle("Sin seleccian");
                exceptionDialog.setHeaderText("No hay seleccian");
                exceptionDialog.setContentText("Seleccione una Linea de la tabla");
                exceptionDialog.show();
            }
        });
	    
	}

	/**
     * Abre una ventana de dialogo para editar detalles del Linea.
     * Si el usuario hace click en el botan Registrar, se graban los 
     * cambios en el objeto "Linea" y devuelve "true"
     *
     * @param Linea el objeto "Linea" listo para editar o insertar
     * @return true si el usuario clickea Registrar, de otra manera false.
     */
	
	private boolean showVentana(Localidad localidad) {
		try {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/iuLocalidades.fxml"));
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/iuLocalidades.fxml"));

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
			IULocalidades controller = loader.getController();
			controller.setDialogStage(stage);
			controller.setLocalidad(localidad);;

			// Show the dialog and wait until the user closes it
			stage.showAndWait();

			return controller.isbtnRegistrarClicked();

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private void getLineasWS() {
		LocalidadAccess.getLocalidades(listLocalidad);
	}

	private void inicializarTabla() {
		colNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
		colCodigoPostal.setCellValueFactory(cellData -> cellData.getValue().codigoPostalProperty());
		colProvincia.setCellValueFactory(cellData -> cellData.getValue().provinciaProperty());
	}

	private void clearTabla() {
		tblLocalidades.getItems().clear();
	}
    
}
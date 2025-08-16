package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;

import consumer.ConceptoAccess;
import io.vertx.core.Future;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.FlatAlert;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import model.Concepto;

public class Conceptos {
	
	@FXML
	private TextField txtBuscar;
	@FXML
	private TableView<Concepto> tblConceptos;
	@FXML
	private TableColumn<Concepto, String> colDescripcion;
	@FXML
	private Button btnAgregar;
	@FXML
	private Button btnModificar;
	@FXML
	private Button btnBorrar;
	@FXML
	private Button btnEnviar;
	@FXML
	private ImageView imgHeader;
	@FXML
	private JFXButton btnSalir;
	@FXML
	private ImageView imgSalir;

	private ObservableList<Concepto> conceptoData = FXCollections.observableArrayList();
	
	private static double dragOffsetX = 0;
	private static double dragOffsetY = 0;
	private int selectedIndex;
	
	private Stage primaryStage;
	
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	public void initialize() throws FileNotFoundException {
	
		JMetro mainStageJMetro = new JMetro(Style.DARK);
		
//		// Cargo las imágenes
//	    Image image0 = new Image(new FileInputStream("src/main/resources/images/backBis.png"));
//	    imgSalir.setImage(image0);
//	    imgSalir.setSmooth(true);
//	    imgSalir.setPreserveRatio(true);
		
	    clearTablaConceptos();
	    
	    inicializarTabla();
	    
	    getConceptosWS();
	    
	    /*
	     * In JavaFX, you cannot change the contents of an ObservableList while a change is already in progress. 
	     * What is happening here is that your listeners (any of the ones you try) are being fired as part of the
	     * box.getSelctionModel().getSelectedItems() ObservableList changing. So basically, you cannot change 
	     * the selection while a selection change is being processed.	     * 
	     */
	    
//	    Platform.runLater(() -> 
//	    tblConceptos.setItems(conceptoData));
//	    
	    tblConceptos.setItems(conceptoData);
	    
	    // 1. Wrapeo la lista ObservableList en una lista FilteredList (inicialmente muestra todos los datos).
	    // Los filtros en la lista FilteredList dependen de un Predicate especificado. El valor inicial del Predicate es siempre verdadero: b -> true
	    FilteredList<Concepto> filteredData = new FilteredList<>(conceptoData, b -> true);
	    // 2. Seteo el filtro Predicate cuando el filtro cambia.
	    // Agrego un Listener al filtro del texto de búsqueda. Cuando el usuario cambia el texto, el Predicate de la FilteredList se actualiza.
	   
	    txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
	    	filteredData.setPredicate(conceptos -> {
	    		// If filter text is empty, display all data.
	    		if (newValue == null || newValue.isEmpty()) {
	    			return true;
	    		}
	    		// Compare first name and last name of every person with filter text.
	    		String lowerCaseFilter = newValue.toLowerCase();

	    		if (conceptos.getDescripcion().toString().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
	    			return true; // Filter matches first name.
	    		} else if (conceptos.getDescripcion().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
	    			return true; // Filter matches last name.
	    		}
	    		else  
	    			return false; // Does not match.
	    	});
	    });
	    // 3. Wrapeo la FilteredList en una SortedList.
	    // FilteredList es inmodificable, por lo tanto no puedo ordenarla (sorted); preciso "wrapearla" para eso.
	    SortedList<Concepto> sortedData = new SortedList<>(filteredData);
	    // 4. Empareja (Bind) usando "comparator" de la SortedListcon con el "comparator" de la TableView.
	    // Si no se hace, el "sorting" de la TableView no tiene efecto. 
	    // A click on the column header changes the sorting of the TableView. 
	    // But now that we have a separate SortedList we must bind the sorting of that list to the TableView. 
	    // Notice that the TableView will return back to the original, unsorted state after three clicks on the column header 
	    sortedData.comparatorProperty().bind(tblConceptos.comparatorProperty());
	    // 5. Add sorted (and filtered) data to the table.
	    tblConceptos.setItems(sortedData);
	    
	    /*
	     * TransformationList (of which both SortedList and FilteredList are implementations) has a getSourceIndex(int index) method 
	     * that "translates" the index in the transformed list to the index in its source (underlying) list. 
	     * So currentListSorted(index) gives the index in the filtered list of an item that has the provided index in the sorted list, 
	     * and currentListFiltered(index) gives the index in the original items list of an item that has the provided index in the filtered list.
	     * 
	     */
	    
// Preparada la tabla para detectar los cambios y mostrar los detalles.
//	    tblConceptos.getSelectionModel().selectedItemProperty().addListener(
//	    		(observable, oldValue, newValue) -> {  
//	    			mostrarDetallesConceptos(newValue);
//	    });
        
        /**
         * Cuando el usuario hace click en el botón Agregar se abre la ventana 
         * de diálogo para agregar o editar los detalles del Concepto.
         */
        
        btnAgregar.setOnAction(ins -> {
        	  Concepto tempConcepto = new Concepto();
              boolean okClicked = showConceptoEditDialog(tempConcepto);
              if (okClicked) {
                  conceptoData.add(tempConcepto);
              }
        });
        
        btnBorrar.setOnAction(del -> {
        	// Para saber si existe algún ítem seleccionado
        	selectedIndex = tblConceptos.getSelectionModel().getSelectedIndex();        	
        	
        	if (selectedIndex >= 0) {
        		Concepto concepto = tblConceptos.getItems().get(selectedIndex);

                FlatAlert alert = new FlatAlert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("¿Desea borrar este item? -> " + concepto.getDescripcion().getValue());

                mainStageJMetro.setScene(alert.getDialogPane().getScene());

                Optional<ButtonType> result = alert.showAndWait();

                if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
                	Future<Void> deleteResult = ConceptoAccess.deleteConcepto(concepto);
                	deleteResult.onComplete (ar -> {
                		if (ar.succeeded()) {
                			Platform.runLater(new Runnable() {
                				@Override
                				public void run() {
                					conceptoData.remove(filteredData.getSourceIndex(
                							sortedData.getSourceIndex(selectedIndex)
                							));
                					tblConceptos.getSelectionModel().clearSelection();
                				}
                			});

                		} else {
                			System.out.println(ar.cause());
                		}
                	});
                } 
            }
        });
        
        btnModificar.setOnAction(del -> {
        	Concepto selectedConcepto = tblConceptos.getSelectionModel().getSelectedItem();
        	if (selectedConcepto != null) {
        		showConceptoEditDialog(selectedConcepto);
        	}        	
        });
        
        btnEnviar.setOnAction(del -> {
        	
        	
        });
        
        btnSalir.setOnAction(event -> {
//			Platform.exit();
//			System.exit(0);
		});	
	
	} // END initialize

	public void getConceptosWS() {
        ConceptoAccess.getConceptos(conceptoData);
    }
	
//	private void mostrarDetallesConceptos(Concepto concepto) {
//		if (concepto != null) {
//			txtBuscar.setText(concepto.getDescripcion().get());
//			System.out.println("Selected Value -> " + concepto.getDescripcion().get());					
//		} else {
//			txtBuscar.clear();
//		}
//	}

	private void clearTablaConceptos() {
		tblConceptos.getItems().clear();
	}

	private void inicializarTabla() {
		colDescripcion.setCellValueFactory(cellData -> cellData.getValue().getDescripcion());
	}
	
	/**
     * Abre una ventana de diálogo para editar detalles del Concepto.
     * Si el usuario hace click en el botón Registrar, se graban los 
     * cambios en el objeto "concepto" y devuelve "true"
     *
     * @param concepto el objeto concepto listo para editar o insertar
     * @return true si el usuario clickea Registrar, de otra manera false.
     */
	
	public boolean showConceptoEditDialog(Concepto concepto) {
		try {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/iuConceptos.fxml"));
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/iuConceptos.fxml"));
			
			Stage stage = new Stage();
			stage.initStyle(StageStyle.UNDECORATED);
			stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(this.primaryStage);
            Scene scene = new Scene(loader.load());
//            scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
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
			
			// Setea el concepto dentro del controlador
            IUConceptos controller = loader.getController();
            controller.setDialogStage(stage);
            controller.setConcepto(concepto);
           
            // Show the dialog and wait until the user closes it
            stage.showAndWait();
			
            return controller.isbtnRegistrarClicked();
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
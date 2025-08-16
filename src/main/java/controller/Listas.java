package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import consumer.ListaAccess;
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
import javafx.scene.Parent;
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
import model.Lista;

public class Listas {
	@FXML
    private TextField txtBuscar;
    @FXML
    private TableView<Lista> tblListas;
    @FXML
    private TableColumn<Lista, String> colLista;
    @FXML
    private TableColumn<Lista, Number> colMargen;
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
	
    private ObservableList<Lista> listasData = FXCollections.observableArrayList();
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy");
    private Stage primaryStage;
    
	private static double dragOffsetX = 0;
	private static double dragOffsetY = 0;

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
		
//        imgHeader.setImage(new Image("/images/ic_local_offer_black_36dp.png"));
        imgHeader.setImage(new Image("/resources/images/ic_local_offer_black_36dp.png"));
	    imgHeader.setSmooth(true);
	    imgHeader.setPreserveRatio(true);

	    clearTabla();

	    inicializarTabla();

	    getListasWS();

	    tblListas.setItems(listasData);
	    
	    // 1. Wrapeo la lista ObservableList en una lista FilteredList (inicialmente muestra todos los datos).
	    // Los filtros en la lista FilteredList dependen de un Predicate especificado. El valor inicial del Predicate es siempre verdadero: b -> true
	    FilteredList<Lista> filteredData = new FilteredList<>(listasData, b -> true);
	    // 2. Seteo el filtro Predicate cuando el filtro cambia.
	    // Agrego un Listener al filtro del texto de basqueda. Cuando el usuario cambia el texto, el Predicate de la FilteredList se actualiza.
	   
	    txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
	    	filteredData.setPredicate(depositos -> {
	    		// If filter text is empty, display all data.
	    		if (newValue == null || newValue.isEmpty()) {
	    			return true;
	    		}
	    		// Compare first name and last name of every person with filter text.
	    		String lowerCaseFilter = newValue.toLowerCase();

	    		if (depositos.getDescripcion().toString().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
	    			return true; // Filter matches first name.
	    		} else if (depositos.getDescripcion().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
	    			return true; // Filter matches last name.
	    		}
	    		else  
	    			return false; // Does not match.
	    	});
	    });
	    // 3. Wrapeo la FilteredList en una SortedList.
	    // FilteredList es inmodificable, por lo tanto no puedo ordenarla (sorted); preciso "wrapearla" para eso.
	    SortedList<Lista> sortedData = new SortedList<>(filteredData);
	    // 4. Empareja (Bind) usando "comparator" de la SortedListcon con el "comparator" de la TableView.
	    // Si no se hace, el "sorting" de la TableView no tiene efecto. 
	    // A click on the column header changes the sorting of the TableView. 
	    // But now that we have a separate SortedList we must bind the sorting of that list to the TableView. 
	    // Notice that the TableView will return back to the original, unsorted state after three clicks on the column header 
	    sortedData.comparatorProperty().bind(tblListas.comparatorProperty());
	    // 5. Add sorted (and filtered) data to the table.
	    tblListas.setItems(sortedData);
	    
        btnAgregar.setOnAction(ins -> {
        	  Lista lista = new Lista();
              boolean okClicked = showVentana(lista);
              if (okClicked) {
                  listasData.add(lista);
              }
        });
	    
		btnBorrar.setOnAction(del -> {
			if (tblListas.getSelectionModel().getSelectedIndex() >= 0) {
				Lista lista = tblListas.getItems().get(tblListas.getSelectionModel().getSelectedIndex());

				JMetro jm = new JMetro(Style.DARK);
				FlatAlert alert = new FlatAlert(Alert.AlertType.INFORMATION);
				alert.setHeaderText("Confirme por favor!");
				alert.setContentText("¿Desea borrar este item? -> " + lista.getDescripcion());
				jm.setScene(alert.getDialogPane().getScene());

				Optional<ButtonType> result = alert.showAndWait();

				if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
					Future<String> deleteResult = ListaAccess.deleteLista(lista);
					deleteResult.onComplete(ar -> {
						if (ar.succeeded()) {
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									listasData.remove(filteredData.getSourceIndex(sortedData
											.getSourceIndex(tblListas.getSelectionModel().getSelectedIndex())));
									tblListas.getSelectionModel().clearSelection();
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
        	Lista selectedLista = tblListas.getSelectionModel().getSelectedItem();
        	if (selectedLista != null) {
        		showVentana(selectedLista);
            } else {
                Alert exceptionDialog = new Alert(Alert.AlertType.WARNING);
                exceptionDialog.setTitle("Sin seleccian");
                exceptionDialog.setHeaderText("No hay seleccian");
                exceptionDialog.setContentText("Seleccione un Deposito de la tabla");
                exceptionDialog.show();
            }
        });
	}

	/**
     * Abre una ventana de dialogo para editar detalles del Deposito.
     * Si el usuario hace click en el botan Registrar, se graban los 
     * cambios en el objeto "Deposito" y devuelve "true"
     *
     * @param Deposito el objeto "Deposito" listo para editar o insertar
     * @return true si el usuario clickea Registrar, de otra manera false.
     */
	
	private boolean showVentana(Lista lista) {
		try {
//			String fxmlFile = "/fxml/iuListas.fxml";
			String fxmlFile = "/resources/fxml/iuListas.fxml";
			FXMLLoader loader = new FXMLLoader();
			Parent root = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
			
			final Scene scene = new Scene(root);
//			scene.getStylesheets().add("/styles/style.css");
			scene.getStylesheets().add("/resources/styles/style.css");
			
			Stage stage = new Stage();
			stage.initStyle(StageStyle.UNDECORATED);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(this.primaryStage);
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
			IUListas controller = loader.getController();
			controller.setDialogStage(stage);
			controller.setLista(lista);

			// Show the dialog and wait until the user closes it
			stage.showAndWait();

			return controller.isbtnRegistrarClicked();

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private void getListasWS() {
		ListaAccess.getListas(listasData);
	}

	private void inicializarTabla() {
		colLista.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
		colMargen.setCellValueFactory(cellData -> cellData.getValue().margenProperty());
	}

	private void clearTabla() {
		tblListas.getItems().clear();
	}
	
}
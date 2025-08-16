package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import model.Banco;
import consumer.BancoAccess;
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

public class Bancos {

	@FXML
	private TextField txtBuscar;
	@FXML
	private TableView<Banco> tblBancos;
	@FXML
	private TableColumn<Banco, Number> colNumero;
	@FXML
	private TableColumn<Banco, String> colBanco;
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
	
	private ObservableList<Banco> listBancos = FXCollections.observableArrayList();
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
		
//    	imgHeader.setImage(new Image("/images/ic_account_balance_black_48dp.png"));
    	imgHeader.setImage(new Image("/resources/images/ic_account_balance_black_48dp.png"));
   	    imgHeader.setSmooth(true);
   	    imgHeader.setPreserveRatio(true);

	    clearTabla();

	    inicializarTabla();

	    getBancosWS();

	    tblBancos.setItems(listBancos);
	    
	    // 1. Wrapeo la lista ObservableList en una lista FilteredList (inicialmente muestra todos los datos).
	    // Los filtros en la lista FilteredList dependen de un Predicate especificado. El valor inicial del Predicate es siempre verdadero: b -> true
	    FilteredList<Banco> filteredData = new FilteredList<>(listBancos, b -> true);
	    // 2. Seteo el filtro Predicate cuando el filtro cambia.
	    // Agrego un Listener al filtro del texto de búsqueda. Cuando el usuario cambia el texto, el Predicate de la FilteredList se actualiza.
	   
	    txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
	    	filteredData.setPredicate(bancos -> {
	    		// If filter text is empty, display all data.
	    		if (newValue == null || newValue.isEmpty()) {
	    			return true;
	    		}
	    		// Compare first name and last name of every person with filter text.
	    		String lowerCaseFilter = newValue.toLowerCase();

	    		if (bancos.getNombre().toString().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
	    			return true; // Filter matches first name.
	    		} else if (bancos.getNombre().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
	    			return true; // Filter matches last name.
	    		}
	    		else  
	    			return false; // Does not match.
	    	});
	    });
	    // 3. Wrapeo la FilteredList en una SortedList.
	    // FilteredList es inmodificable, por lo tanto no puedo ordenarla (sorted); preciso "wrapearla" para eso.
	    SortedList<Banco> sortedData = new SortedList<>(filteredData);
	    // 4. Empareja (Bind) usando "comparator" de la SortedListcon con el "comparator" de la TableView.
	    // Si no se hace, el "sorting" de la TableView no tiene efecto. 
	    // A click on the column header changes the sorting of the TableView. 
	    // But now that we have a separate SortedList we must bind the sorting of that list to the TableView. 
	    // Notice that the TableView will return back to the original, unsorted state after three clicks on the column header 
	    sortedData.comparatorProperty().bind(tblBancos.comparatorProperty());
	    // 5. Add sorted (and filtered) data to the table.
	    tblBancos.setItems(sortedData);
	    
        btnAgregar.setOnAction(ins -> {
        	  Banco banco = new Banco();
              boolean okClicked = showVentana(banco);
              if (okClicked) {
                  listBancos.add(banco);
              }
        });
	    
		btnBorrar.setOnAction(del -> {
			if (tblBancos.getSelectionModel().getSelectedIndex() >= 0) {
				Banco banco = tblBancos.getItems().get(tblBancos.getSelectionModel().getSelectedIndex());

				JMetro jm = new JMetro(Style.DARK);
				FlatAlert alert = new FlatAlert(Alert.AlertType.INFORMATION);
				alert.setHeaderText("Confirme por favor!");
				alert.setContentText("¿Desea borrar este ítem? -> " + banco.getNombre());
				jm.setScene(alert.getDialogPane().getScene());

				Optional<ButtonType> result = alert.showAndWait();

				if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
					Future<String> deleteResult = BancoAccess.delete(banco);
					deleteResult.onComplete(ar -> {
						if (ar.succeeded()) {
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									listBancos.remove(filteredData.getSourceIndex(sortedData
											.getSourceIndex(tblBancos.getSelectionModel().getSelectedIndex())));
									tblBancos.getSelectionModel().clearSelection();
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
        	Banco selectedBanco = tblBancos.getSelectionModel().getSelectedItem();
        	if (selectedBanco!= null) {
        		showVentana(selectedBanco);
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
     * Abre una ventana de dialogo para editar detalles del Banco.
     * Si el usuario hace click en el botan Registrar, se graban los 
     * cambios en el objeto "Banco" y devuelve "true"
     *
     * @param Banco el objeto "Banco" listo para editar o insertar
     * @return true si el usuario clickea Registrar, de otra manera false.
     */
	
	private boolean showVentana(Banco banco) {
		try {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/iuBancos.fxml"));
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/iuBancos.fxml"));

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
			IUBancos controller = loader.getController();
			controller.setDialogStage(stage);
			controller.setBanco(banco);;

			// Show the dialog and wait until the user closes it
			stage.showAndWait();

			return controller.isbtnRegistrarClicked();

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private void getBancosWS() {
		BancoAccess.getBancos(listBancos);
	}

	private void inicializarTabla() {
		colNumero.setCellValueFactory(cellData -> cellData.getValue().codigoProperty());
		colBanco.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
	}

	private void clearTabla() {
		tblBancos.getItems().clear();
	}

}
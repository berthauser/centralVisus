package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.json.simple.parser.ParseException;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialogLayout;

import consumer.ArticuloAccess;
import io.vertx.core.Future;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import model.Articulo;

public class Articulos {
	@FXML
	private Label lblFecha;
	@FXML
	private Label lblHora;
	@FXML
	private ImageView imgLogo;
	@FXML
	private Label lblCodigoBarra;
	@FXML
	private Label lblTipoPosicion;
	@FXML
	private Label lblUbicacion;
	@FXML
	private Label lblFila;
	@FXML
	private Label lblColumna;
	@FXML
	private Label lblStkMinimo;
	@FXML
	private Label lblStkOptimo;
	@FXML
	private Label lblStkMaximo;
	@FXML
	private JFXCheckBox cbNegativo;
	@FXML
	private JFXCheckBox cbArticulosDeBaja;
	@FXML
	private Label lblMerma;
	@FXML
	private JFXCheckBox cbFraccionable;
	@FXML
	private Label lblPlu;
	@FXML
	private Label lblUtilidadFraccionado;
	@FXML
	private Label lblLeyendaFraccionado;
	@FXML
	private Label lblRubro;
	@FXML
	private Label lblMarca;
	@FXML
	private Label lblUnidad;
	@FXML
	private Label lblPresentacion;
	@FXML
	private Label lblFechaCompra;
	@FXML
	private Label lblFechaBaja;
	@FXML
	private Label lblFechaActPrecios;
	@FXML
	private Label lblProveedor;
	@FXML
	private Label lblCuit;
	@FXML
	private TextField txtArticulo;
	@FXML
	private JFXButton btnConsultar;
	@FXML
	private TableView<Articulo> tblArticulos;
	@FXML
	private TableColumn<Articulo, String> colLeyenda;
	@FXML
	private TableColumn<Articulo, Number> colCodigoInterno;
	@FXML
	private TableColumn<Articulo, String> colTipoProducto;
	@FXML
	private TableColumn<Articulo, Number> colExistencia;
	@FXML
	private TableColumn<Articulo, Number> colCantidad;
	@FXML
	private TableColumn<Articulo, Number> colPrecioCosto;
	@FXML
	private TableColumn<Articulo, Number> colUtilidadMin;
	@FXML
	private TableColumn<Articulo, Number> colUtilidadMay;
	@FXML
	private TableColumn<Articulo, Number> colGravamen;
	@FXML
	private TableColumn<Articulo, String> colEstado;
	@FXML
	private JFXButton btnNuevo;
	@FXML
    private JFXButton btnModificar;
	@FXML
	private JFXButton btnEliminar;
	@FXML
	private JFXButton btnListado;
	@FXML
	private JFXButton btnSalir;
	
	private double dragOffsetX = 0;
	private double dragOffsetY = 0;
	private ObservableList<Articulo> articulosData = FXCollections.observableArrayList();
	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy");
	
	@FXML
	public void initialize() throws FileNotFoundException {

		LocalDate fecha = LocalDate.now();
        lblFecha.setText((dtf.format(fecha)));
        
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {        
        	LocalTime currentTime = LocalTime.now();
        	lblHora.setText(currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
        }),
             new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    	
    	// Cargo las imagenes
//    	imgLogo.setImage(new Image("/images/ic_local_convenience_store_black_36dp.png"));
    	imgLogo.setImage(new Image("/resources/images/ic_local_convenience_store_black_36dp.png"));
		imgLogo.setSmooth(true);
		imgLogo.setPreserveRatio(true);
    	
    	clearTabla();

    	inicializarTabla();

    	 // 1. Wrapeo la lista ObservableList en una lista FilteredList (inicialmente muestra todos los datos).
	    // Los filtros en la lista FilteredList dependen de un Predicate especificado. El valor inicial del Predicate es siempre verdadero: b -> true
	    FilteredList<Articulo> filteredData = new FilteredList<>(articulosData, b -> true);
	    // 2. Seteo el filtro Predicate cuando el filtro cambia.
	    // Agrego un Listener al filtro del texto de busqueda. Cuando el usuario cambia el texto, el Predicate de la FilteredList se actualiza.
	   
	    txtArticulo.textProperty().addListener((observable, oldValue, newValue) -> {
	    	filteredData.setPredicate(articulos -> {
	    		// If filter text is empty, display all data.
	    		if (newValue == null || newValue.isEmpty()) {
	    			return true;
	    		}
	    		// Compare first name and last name of every person with filter text.
	    		String lowerCaseFilter = newValue.toLowerCase();

	    		if (articulos.getLeyenda().toString().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
	    			return true; // Filter matches first name.
	    		} else if (articulos.getLeyenda().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
	    			return true; // Filter matches last name.
	    		}
	    		else  
	    			return false; // Does not match.
	    	});
	    });
	    // 3. Wrapeo la FilteredList en una SortedList.
	    // FilteredList es inmodificable, por lo tanto no puedo ordenarla (sorted); preciso "wrapearla" para eso.
	    SortedList<Articulo> sortedData = new SortedList<>(filteredData);
	    // 4. Empareja (Bind) usando "comparator" de la SortedListcon con el "comparator" de la TableView.
	    // Si no se hace, el "sorting" de la TableView no tiene efecto. 
	    // A click on the column header changes the sorting of the TableView. 
	    // But now that we have a separate SortedList we must bind the sorting of that list to the TableView. 
	    // Notice that the TableView will return back to the original, unsorted state after three clicks on the column header 
	    sortedData.comparatorProperty().bind(tblArticulos.comparatorProperty());
	    // 5. Add sorted (and filtered) data to the table.
	    tblArticulos.setItems(sortedData);
    	
	    tblArticulos.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
	    	mostrarEtiquetasArticulos(newValue);
	    });
	    
	    btnNuevo.setOnAction(e -> {
			 Articulo articulo = new Articulo();
             boolean okClicked = showVentana(articulo, "Alta");
             if (okClicked) {
                 articulosData.add(articulo);
             }
    	});
		
	    btnEliminar.setOnAction(del -> {
	    	if (tblArticulos.getSelectionModel().getSelectedIndex() >= 0) {
	    		Articulo articulo = tblArticulos.getItems().get(tblArticulos.getSelectionModel().getSelectedIndex());

	    		JFXAlert<String> alert = new JFXAlert<>((Stage) btnEliminar.getScene().getWindow());
	    		alert.initModality(Modality.APPLICATION_MODAL);
	    		alert.setOverlayClose(false);
	    		JFXDialogLayout layout = new JFXDialogLayout();
	    		layout.setHeading(new Label("Confirme por favor!"));
	    		layout.setBody(new Label("uDesea cambar el ESTADO de este item? -> " + articulo.getLeyenda()));

	    		JFXButton closeButton = new JFXButton("Aceptar");
	    		closeButton.getStyleClass().add("dialog-accept");
	    		closeButton.setOnAction(event -> alert.hideWithAnimation());
	    		layout.setActions(closeButton);
	    		alert.setContent(layout);

	    		Optional<String> result = alert.showAndWait();

	    		if (result.isPresent()) {
	    			Future<String> updateResult = null;
					try {
						updateResult = ArticuloAccess.updStateBaja(articulo.getCodigobarra());
					} catch (IOException | ParseException e1) {
						e1.printStackTrace();
					}			
	    			updateResult.onComplete(ar -> {
	    				if (ar.succeeded()) {
	    					Platform.runLater(new Runnable() {
	    						@Override
	    						public void run() {
	    							articulosData.remove(filteredData.getSourceIndex(
	    									sortedData.getSourceIndex(tblArticulos.getSelectionModel().getSelectedIndex())
	    									));
	    							tblArticulos.getSelectionModel().clearSelection();
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
	    	Articulo selectedArticulo = tblArticulos.getSelectionModel().getSelectedItem();
	    	if (selectedArticulo!= null) {
	    		showVentana(selectedArticulo, "Modificacion");
	    	} else {
	    		Alert exceptionDialog = new Alert(Alert.AlertType.WARNING);
	    		exceptionDialog.setTitle("Sin selección");
	    		exceptionDialog.setHeaderText("No hay selección");
	    		exceptionDialog.setContentText("Seleccione un Articulo de la tabla");
	    		exceptionDialog.show();
	    	}
	    });

	    btnConsultar.setOnAction(e -> {
	    	if (cbArticulosDeBaja.isSelected()) {
	    		try {
	    			ArticuloAccess.getDistinctByStateBaja(articulosData);
	    		} catch (IOException | ParseException e1) {
	    			e1.printStackTrace();
	    		}
	    	} else {
	    		try {
	    			ArticuloAccess.getArticulos(articulosData);
	    		} catch (IOException | ParseException e1) {
	    			e1.printStackTrace();
	    		}
	    	}
	    	tblArticulos.setItems(articulosData);
	    });

	    
	    btnSalir.setOnAction(e -> {
	    	((Node) (e.getSource())).getScene().getWindow().hide();
		});
	    
	    Platform.runLater(() -> 
	    txtArticulo.requestFocus());

	}

	private void mostrarEtiquetasArticulos(Articulo articulo) {
		if(articulo != null) {
			lblCodigoBarra.textProperty().bind(articulo.codigobarraProperty());
			lblTipoPosicion.textProperty().bind(articulo.tipo_posicionProperty());
            lblUbicacion.textProperty().bind(articulo.ubicacionProperty());
            lblFila.textProperty().bind(articulo.filaProperty().asString());
            lblColumna.textProperty().bind(articulo.colProperty().asString());
            lblStkMinimo.textProperty().bind(articulo.stock_minimoProperty().asString());
            lblStkOptimo.textProperty().bind(articulo.stock_optimoProperty().asString());
            lblStkMaximo.textProperty().bind(articulo.stock_maximoProperty().asString());
            cbNegativo.selectedProperty().bind(articulo.stock_negativoProperty());
            lblMerma.textProperty().bind(articulo.mermaProperty().asString());
            cbFraccionable.selectedProperty().bind(articulo.fraccionadoProperty());
            lblPlu.textProperty().bind(articulo.pluProperty().asString());
            lblUtilidadFraccionado.textProperty().bind(articulo.utilidad_fraccionadoProperty().asString());
            lblLeyendaFraccionado.textProperty().bind(articulo.leyenda_fraccionadoProperty());
            lblRubro.textProperty().bind(articulo.rubroProperty());
            lblMarca.textProperty().bind(articulo.marcaProperty());
            lblUnidad.textProperty().bind(articulo.unidadProperty());
            lblPresentacion.textProperty().bind(Bindings.concat(articulo.presentacionProperty(), " x ", articulo.cantUnidadesProperty().asString()));
            lblFechaCompra.textProperty().bind(articulo.fecha_compraProperty().asString());
            lblFechaActPrecios.textProperty().bind(articulo.fecha_actpreciosProperty().asString());
            lblFechaBaja.textProperty().bind(articulo.fecha_bajaProperty().asString());
            lblCuit.textProperty().bind(articulo.nrodoc_proveedorProperty().asString());
        } else {
        	lblCodigoBarra.textProperty().unbind();
        	lblCodigoBarra.setText(null);
        	lblTipoPosicion.textProperty().unbind();
        	lblTipoPosicion.setText(null);
        	lblUbicacion.textProperty().unbind();
        	lblUbicacion.setText(null);
        	lblFila.textProperty().unbind();
        	lblFila.setText(null);
        	lblColumna.textProperty().unbind();
        	lblColumna.setText(null);
        	lblStkMinimo.textProperty().unbind();
            lblStkMinimo.setText(null);
            lblStkOptimo.textProperty().unbind();
            lblStkOptimo.setText(null);
            lblStkMaximo.textProperty().unbind();
            lblStkMaximo.setText(null);
            cbNegativo.selectedProperty().unbind();
            cbNegativo.setSelected(false);
            lblMerma.textProperty().unbind();
            lblMerma.setText(null);
            cbFraccionable.selectedProperty().unbind();
            cbFraccionable.setSelected(false);
            lblPlu.textProperty().unbind();
            lblPlu.setText(null);
            lblUtilidadFraccionado.textProperty().unbind();
            lblUtilidadFraccionado.setText(null);
            lblLeyendaFraccionado.textProperty().unbind();
            lblLeyendaFraccionado.setText(null);
            lblRubro.textProperty().unbind();
            lblRubro.setText(null);
            lblMarca.textProperty().unbind();
            lblMarca.setText(null);
            lblUnidad.textProperty().unbind();
            lblUnidad.setText(null);
            lblPresentacion.textProperty().unbind();
            lblPresentacion.setText(null);
            lblFechaCompra.textProperty().unbind();
            lblFechaCompra.setText(null);
            lblFechaActPrecios.textProperty().unbind();
            lblFechaActPrecios.setText(null);
            lblFechaBaja.textProperty().unbind();
            lblFechaBaja.setText(null);
            lblCuit.textProperty().unbind();
            lblCuit.setText(null);
        }
		
	}

	private boolean showVentana(Articulo articulo, String tipo) {
		try {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/iuArticulos.fxml"));
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/iuArticulos.fxml"));
			Stage stage = new Stage();
			stage.initStyle(StageStyle.UNDECORATED);
			stage.initModality(Modality.APPLICATION_MODAL);
			Scene scene = new Scene(loader.load());

//			scene.getStylesheets().add("/styles/style.css");
			scene.getStylesheets().add("/resources/styles/style.css");

			scene.setOnMousePressed(e1 -> {
				dragOffsetX = e1.getSceneX();
				dragOffsetY = e1.getSceneY();
			});

			scene.setOnMouseDragged(e1 -> {
				stage.setX(e1.getScreenX() - dragOffsetX);
				stage.setY(e1.getScreenY() - dragOffsetY);
			});
			
			// Setea el artículo dentro del controller
			
			IUArticulos controller = loader.getController();
			controller.setDialogStage(stage);
			controller.setArticulos(articulo, tipo);;

//			switch(tipo) { 
//			case "Alta": 
//				controller.setArticulos(articulo, tipo);;
//				break; 
//			case "Modificacion": 
//				controller.setArticulos(articulo, tipo);;
//				break; 
//			default: 
// 
//			}
			
			stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
				if (KeyCode.ESCAPE == event.getCode()) {
					stage.close();
				}
			});
			
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.showAndWait();
			
			return controller.isbtnRegistrarClicked();
			
		} catch (Exception e2) {
			e2.printStackTrace();
			return false;
		}
	}

	private void inicializarTabla() {
		colLeyenda.setCellValueFactory(cellData -> cellData.getValue().leyendaProperty());
		colCodigoInterno.setCellValueFactory(cellData -> cellData.getValue().codigointernoProperty());
		colTipoProducto.setCellValueFactory(cellData -> cellData.getValue().tipo_productoProperty());
		colExistencia.setCellValueFactory(cellData -> cellData.getValue().existenciaProperty());
		colCantidad.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty());
		colPrecioCosto.setCellValueFactory(cellData -> cellData.getValue().precio_costoProperty());
		colUtilidadMay.setCellValueFactory(cellData -> cellData.getValue().margen_utilidad_mayoristaProperty());
		colUtilidadMin.setCellValueFactory(cellData -> cellData.getValue().margen_utilidad_minoristaProperty());
		colGravamen.setCellValueFactory(cellData -> cellData.getValue().gravamenProperty());
		colEstado.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());
	}

	private void clearTabla() {
		tblArticulos.getItems().clear();
	}
	
}
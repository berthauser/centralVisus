package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import consumer.ArticuloAccess;
import consumer.LineaAccess;
import consumer.MarcaAccess;
import consumer.PresentacionAccess;
import consumer.RubroAccess;
import consumer.UbicacionAccess;
import consumer.UnidadAccess;
import io.vertx.core.Future;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Articulo;
import model.Linea;
import model.Marca;
import model.Presentacion;
import model.Rubro;
import model.Ubicacion;
import model.Unidad;

public class IUArticulos {
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblHora;
    @FXML
    private Label lblExistencia;
    @FXML
    private Label lblContenedor;
    @FXML
    private Label lblUnidades;
    @FXML
    private Label lblValorUMedida;
    @FXML
    private Label lblCantidad;
    @FXML
    private Label lblTotalen;
    @FXML
    private Label lblNetoNormal;
    @FXML
    private Label lblBrutoNormal;
    @FXML
    private Label lblIvaNormal;
    @FXML
    private Label lblNetoFraccionado;
    @FXML
    private Label lblBrutoFraccionado;
    @FXML
    private Label lblIvaFraccionado;
    @FXML
    private ImageView imgLogo;
    @FXML
    private JFXButton btnRubros;
    @FXML
    private ImageView imgRubros;
    @FXML
    private JFXButton btnLineas;
    @FXML
    private ImageView imgLineas;
    @FXML
    private JFXButton btnSalir;
    @FXML
    private ImageView imgSalir;
    @FXML
    private JFXButton btnDepositos;
    @FXML
    private ImageView imgDepositos;
    @FXML
    private JFXButton btnUbicaciones;
    @FXML
    private ImageView imgUbicaciones;
    @FXML
    private JFXButton btnMedidas;
    @FXML
    private ImageView imgMedidas;
    @FXML
    private JFXButton btnPresentaciones;
    @FXML
    private ImageView imgPresentaciones;
    @FXML
    private JFXButton btnMarcas;
    @FXML
    private ImageView imgMarcas;
    @FXML
    private JFXButton btnProveedores;
    @FXML
    private ImageView imgProveedores;
    @FXML
    private JFXButton btnRegistrar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXTextField txtCodigoBarra;
    @FXML
    private JFXTextField txtCodigoEmpresa;
    @FXML
    private JFXTextField txtCodProdEmpresa;
    @FXML
    private JFXTextField txtCodigoInterno;
    @FXML
    private JFXTextField txtPlu;
    @FXML
    private JFXTextField txtLeyenda;
    @FXML
    private JFXTextField txtExistencia;
    @FXML
    private JFXTextField txtMerma;
    @FXML
    private JFXTextField txtUtilidad;
    @FXML
    private JFXTextField txtPrecioCosto;
    @FXML
    private JFXTextField txtGravamen;
    @FXML
    private JFXTextField txtFila;
    @FXML
    private JFXTextField txtColumna;
    @FXML
    private JFXTextField txtMinimo;
    @FXML
    private JFXTextField txtMaximo;
    @FXML
    private JFXTextField txtOptimo;
    @FXML
    private JFXCheckBox cbStockNegativo;
    @FXML
    private JFXCheckBox cbFraccionable;
    @FXML
    private JFXTextField txtMargenUtilidad;
    @FXML
    private JFXTextField txtLeyendaFraccionado;
    @FXML
    private JFXComboBox<Rubro> cmbRubro;
    @FXML
    private JFXComboBox<Linea> cmbLinea;
    @FXML
    private JFXComboBox<Unidad> cmbUnidadMedida;
    @FXML
    private JFXComboBox<Marca> cmbMarca;
    @FXML
	private TableView<Presentacion> tblPresentacion;
	@FXML
	private TableColumn<Presentacion, String> colContenedor;
	@FXML
	private TableColumn<Presentacion, Number> colUnidades;
	@FXML
	private TableColumn<Presentacion, Number> colValorUMedida;
	@FXML
	private TableColumn<Presentacion, String> colTipoDecremento;
	@FXML
	private TableView<Ubicacion> tblUbicacion;
	@FXML
	private TableColumn<Ubicacion, String> colDescripcion;
	@FXML
	private TableColumn<Ubicacion, String> colNumero;
	@FXML
	private TableColumn<Ubicacion, String> colDeposito;
	@FXML
    private JFXComboBox<?> cmbProveedor;
    @FXML
    private JFXDatePicker dpFechaCompra;
    @FXML
    private JFXDatePicker dpFechaActPrecios;
    @FXML
    private JFXDatePicker dpFechaBaja;
    @FXML
    private JFXRadioButton rbActivo;
    @FXML
    private JFXRadioButton rbBaja;
    @FXML
    private JFXRadioButton rbOferta;
    @FXML
    private JFXRadioButton rbEstandard;
    @FXML
    private JFXRadioButton rbServicio;
    @FXML
    private JFXRadioButton rbKit;
    @FXML
    private JFXRadioButton rbMatriz;
    @FXML
    private JFXRadioButton rbNormal;
    @FXML
    private JFXRadioButton rbConsignacion;
    @FXML
    private ToggleGroup tgTipoProducto;
    @FXML
    private ToggleGroup tgTipoPosicion;
    @FXML
    private ToggleGroup tgEstado;
    
    private Articulo articulo;
    private Presentacion presentacion;
    
    private Stage stage;
    private Integer idRubro;
    private Integer idLinea;
    private Integer idMarca;
    private Integer idUnidad;
    private Integer idUbicacion;
    private String unidadMedida;
    private Integer idPresentacion;
    
    private boolean isStockNegativo = false;
    private boolean isFraccionable = false;
    private boolean btnRegistrarClicked = false;
    private static final String MSG = "Se requiere un valor";
	private final ValidationSupport vs = new ValidationSupport();
	private static final DateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
	
	ObservableList<Rubro> rubrosData = FXCollections.observableArrayList();
	ObservableList<Linea> lineasData = FXCollections.observableArrayList();
	ObservableList<Marca> marcasData = FXCollections.observableArrayList();
	ObservableList<Unidad> unidadesData = FXCollections.observableArrayList();
	ObservableList<Ubicacion> ubicacionesData = FXCollections.observableArrayList();
	ObservableList<Presentacion> presentacionesData = FXCollections.observableArrayList();
	
	@FXML
	public void initialize() throws FileNotFoundException {
		
		Date fecha = new Date();
        lblFecha.setText((sdf.format(fecha)));
        
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {        
        	LocalTime currentTime = LocalTime.now();
        	lblHora.setText(currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
        }),	new KeyFrame(Duration.seconds(1))
        		);
        
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    	
//    	Image image0 = new Image(new FileInputStream("src/main/resources/images/articulos.png"));
//    	imgLogo.setImage(image0);
//    	imgLogo.setSmooth(true);
//    	imgLogo.setPreserveRatio(true);
//    	
//    	Image image1 = new Image(new FileInputStream("src/main/resources/images/rubros.png"));
//	    imgRubros.setImage(image1);
//	    imgRubros.setSmooth(true);
//	    imgRubros.setPreserveRatio(true);
//	    
//	    Image image2 = new Image(new FileInputStream("src/main/resources/images/lineas.png"));
//	    imgLineas.setImage(image2);
//	    imgLineas.setSmooth(true);
//	    imgLineas.setPreserveRatio(true);
//  
//	    Image image3 = new Image(new FileInputStream("src/main/resources/images/icons8-esc-48.png"));
//	    imgSalir.setImage(image3);
//	    imgSalir.setSmooth(true);
//	    imgSalir.setPreserveRatio(true);
//	    
//	    Image image4 = new Image(new FileInputStream("src/main/resources/images/depositos.png"));
//	    imgDepositos.setImage(image4);
//	    imgDepositos.setSmooth(true);
//	    imgDepositos.setPreserveRatio(true);
//	    
//	    Image image5 = new Image(new FileInputStream("src/main/resources/images/estanterias.png"));
//	    imgUbicaciones.setImage(image5);
//	    imgUbicaciones.setSmooth(true);
//	    imgUbicaciones.setPreserveRatio(true);
//	    
//	    Image image6 = new Image(new FileInputStream("src/main/resources/images/unidades.png"));
//	    imgMedidas.setImage(image6);
//	    imgMedidas.setSmooth(true);
//	    imgMedidas.setPreserveRatio(true);
//    	
//	    Image image7 = new Image(new FileInputStream("src/main/resources/images/presentaciones.png"));
//	    imgPresentaciones.setImage(image7);
//	    imgPresentaciones.setSmooth(true);
//	    imgPresentaciones.setPreserveRatio(true);
//	    
//	    Image image8 = new Image(new FileInputStream("src/main/resources/images/marcas.png"));
//	    imgMarcas.setImage(image8);
//	    imgMarcas.setSmooth(true);
//	    imgMarcas.setPreserveRatio(true);
//	    
//	    Image image9 = new Image(new FileInputStream("src/main/resources/images/proveedores.png"));
//	    imgProveedores.setImage(image9);
//	    imgProveedores.setSmooth(true);
//	    imgProveedores.setPreserveRatio(true);
	    
		Platform.runLater(() -> 
		txtCodigoBarra.requestFocus());
		
//		validationSupport.setValidationDecorator(new StyleClassValidationDecoration());
//		validationSupport.setValidationDecorator(new CompoundValidationDecoration(
//				new GraphicValidationDecoration(), 
//				new StyleClassValidationDecoration()));

		
		RadioButton rbTipoProducto = (RadioButton) tgTipoProducto.getSelectedToggle();
		String tipoProducto = rbTipoProducto.getText();
		
		RadioButton rbTipoPosicion = (RadioButton) tgTipoPosicion.getSelectedToggle();
		String tipoPosicion = rbTipoPosicion.getText();
		
		RadioButton rbEstado= (RadioButton) tgEstado.getSelectedToggle();
		String estado = rbEstado.getText();
		
		//		tgTipoProducto.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
		//		if (tgTipoProducto.getSelectedToggle() != null) {
		//        System.out.println(tgTipoProducto.getSelectedToggle().getUserData().toString());
		//		    });
		//		
		
		cbStockNegativo.selectedProperty().addListener(this::stkNegativoChanged);
		
		cbFraccionable.selectedProperty().addListener(this::fraccionableChanged); 
		
//		vs.setErrorDecorationEnabled(false);
//		
//		vs.registerValidator(txtCodigoBarra, Validator.createEmptyValidator(MSG, Severity.ERROR));
//		vs.errorDecorationEnabledProperty().bind(txtCodigoBarra.focusedProperty());
//		
//		vs.registerValidator(txtCodigoEmpresa, Validator.createEmptyValidator(MSG, Severity.ERROR));
//		vs.errorDecorationEnabledProperty().bind(txtCodigoEmpresa.focusedProperty());
//		
//		vs.registerValidator(txtCodProdEmpresa, Validator.createEmptyValidator(MSG, Severity.ERROR));
//		vs.errorDecorationEnabledProperty().bind(txtCodProdEmpresa.focusedProperty());
//
//		vs.registerValidator(txtCodigoInterno, Validator.createEmptyValidator(MSG, Severity.ERROR));
//		vs.errorDecorationEnabledProperty().bind(txtCodigoInterno.focusedProperty());
//		
////		vs.registerValidator(txtPlu, Validator.createEmptyValidator(MSG, Severity.ERROR));
////		vs.errorDecorationEnabledProperty().bind(txtPlu.focusedProperty());
//		
//		vs.registerValidator(txtLeyenda, Validator.createEmptyValidator(MSG, Severity.ERROR));
//		vs.errorDecorationEnabledProperty().bind(txtLeyenda.focusedProperty());
//		
//		vs.registerValidator(tblUbicacion, Validator.createEmptyValidator(MSG, Severity.ERROR));
//		vs.errorDecorationEnabledProperty().bind(tblUbicacion.focusedProperty());
//		
//		vs.registerValidator(txtFila, Validator.createEmptyValidator(MSG, Severity.ERROR));
//		vs.errorDecorationEnabledProperty().bind(txtFila.focusedProperty());
//		
//		vs.registerValidator(txtColumna, Validator.createEmptyValidator(MSG, Severity.ERROR));
//		vs.errorDecorationEnabledProperty().bind(txtColumna.focusedProperty());
//		
//		vs.registerValidator(txtExistencia, Validator.createEmptyValidator(MSG, Severity.ERROR));
//		vs.errorDecorationEnabledProperty().bind(txtExistencia.focusedProperty());
//		
//		vs.registerValidator(txtMinimo, Validator.createEmptyValidator(MSG, Severity.ERROR));
//		vs.errorDecorationEnabledProperty().bind(txtMinimo.focusedProperty());
//		
//		vs.registerValidator(txtOptimo, Validator.createEmptyValidator(MSG, Severity.ERROR));
//		vs.errorDecorationEnabledProperty().bind(txtOptimo.focusedProperty());
//		
//		vs.registerValidator(txtMaximo, Validator.createEmptyValidator(MSG, Severity.ERROR));
//		vs.errorDecorationEnabledProperty().bind(txtMaximo.focusedProperty());
//		
//		vs.registerValidator(cmbMarca, Validator.createEmptyValidator(MSG, Severity.ERROR));
//		vs.errorDecorationEnabledProperty().bind(cmbMarca.focusedProperty());
//		
//		vs.registerValidator(cmbRubro, Validator.createEmptyValidator(MSG, Severity.ERROR));
//		vs.errorDecorationEnabledProperty().bind(cmbRubro.focusedProperty());
//		
//		vs.registerValidator(cmbLinea, Validator.createEmptyValidator(MSG, Severity.ERROR));
//		vs.errorDecorationEnabledProperty().bind(cmbLinea.focusedProperty());
//		
//		vs.registerValidator(cmbUnidadMedida, Validator.createEmptyValidator(MSG, Severity.ERROR));
//		vs.errorDecorationEnabledProperty().bind(cmbUnidadMedida.focusedProperty());
//		
//		vs.registerValidator(tblPresentacion, Validator.createEmptyValidator(MSG, Severity.ERROR));
//		vs.errorDecorationEnabledProperty().bind(tblPresentacion.focusedProperty());
//		
//		vs.registerValidator(txtPrecioCosto, Validator.createEmptyValidator(MSG, Severity.ERROR));
//		vs.errorDecorationEnabledProperty().bind(txtPrecioCosto.focusedProperty());
//		
//		vs.registerValidator(txtUtilidad, Validator.createEmptyValidator(MSG, Severity.ERROR));
//		vs.errorDecorationEnabledProperty().bind(txtUtilidad.focusedProperty());
//		
//		vs.registerValidator(txtGravamen, Validator.createEmptyValidator(MSG, Severity.ERROR));
//		vs.errorDecorationEnabledProperty().bind(txtGravamen.focusedProperty());
		
		btnRegistrar.disableProperty().bind(vs.invalidProperty());
		
		txtMargenUtilidad.disableProperty().bind(cbFraccionable.selectedProperty().not());
		txtLeyendaFraccionado.disableProperty().bind(cbFraccionable.selectedProperty().not());
		
		inicializarTablaPresentaciones();
		
		inicializarTablaUbicaciones();
		
		UbicacionAccess.getUbicaciones(ubicacionesData);
		tblUbicacion.setItems(ubicacionesData);
		
		RubroAccess.getRubros(rubrosData);
		cmbRubro.setItems(rubrosData);
		
		UnidadAccess.getUnidades(unidadesData);
		cmbUnidadMedida.setItems(unidadesData);
		
		MarcaAccess.getMarcas(marcasData);
		cmbMarca.setItems(marcasData);
		
		PresentacionAccess.getPresentaciones(presentacionesData);
		tblPresentacion.setItems(presentacionesData);

		cmbRubro.valueProperty().addListener((obs, oldItem, newItem) -> {
			if (!(newItem == null)) {
            	idRubro = newItem.getIdrubro();
            	lineasData =  LineaAccess.getLineasByRubro(idRubro);
        		cmbLinea.setItems(lineasData);
            }
        });
		
		cmbLinea.valueProperty().addListener((obs, oldItem, newItem) -> {
			if (!(newItem == null)) {
				idLinea = newItem.getIdlinea();
			}
		});
		
		cmbUnidadMedida.valueProperty().addListener((obs, oldItem, newItem) -> {
			if (!(newItem == null)) {
				idUnidad = newItem.getIdunidad();
				unidadMedida = newItem.getNombre();
			}
		});
		
		cmbMarca.valueProperty().addListener((obs, oldItem, newItem) -> {
			if (!(newItem == null)) {
				idMarca = newItem.getIdmarca();
			}
		});
		
		txtPrecioCosto.textProperty().addListener((obs, oldPrecioCosto, newPrecioCosto) -> {
//			calcularPrecios(newPrecioCosto);
		});
		
		txtPrecioCosto.setOnKeyPressed(e -> {

			Platform.runLater(() -> {
				if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.TAB) {
					calcularPrecios(txtPrecioCosto.getText());
				}
			});	
		});

		tblUbicacion.setOnMouseClicked(event -> {
			Ubicacion ubicacion = tblUbicacion.getSelectionModel().getSelectedItem();
			idUbicacion = ubicacion.getIdubicacion();
		});
		
		tblPresentacion.setOnMouseClicked(event -> {
			presentacion = tblPresentacion.getSelectionModel().getSelectedItem();
			idPresentacion = presentacion.getIdpresentacion();
			calcularCantidad(); 
		});

		txtCodigoBarra.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
            	txtCodigoEmpresa.setText(txtCodigoBarra.getText().substring(3, 7));
            	txtCodProdEmpresa.setText(txtCodigoBarra.getText().substring(7, 12));
            	txtCodigoInterno.requestFocus();
            }
        });
		
		btnRegistrar.setOnAction(reg -> {
			if (!vs.isInvalid()) {
				Future<Integer> handlerResult = (articulo.getIdarticulo() == 0)?
						
						ArticuloAccess.insert(txtCodigoBarra.getText(), Integer.parseInt(txtCodigoEmpresa.getText()), Integer.parseInt(txtCodProdEmpresa.getText()),
						 		 Integer.parseInt(txtCodigoInterno.getText()), txtLeyenda.getText(), tipoProducto, tipoPosicion, idUbicacion, 
						 		 Integer.parseInt(txtFila.getText()), Integer.parseInt(txtColumna.getText()), Double.parseDouble(txtExistencia.getText()), 
								 Double.parseDouble(lblCantidad.getText()), Integer.parseInt(txtMinimo.getText()), Integer.parseInt(txtOptimo.getText()),
								 Integer.parseInt(txtMaximo.getText()), isStockNegativo, Double.parseDouble(txtMerma.getText()), isFraccionable, 
								 Integer.parseInt(txtPlu.getText()), Double.parseDouble(txtMargenUtilidad.getText()), txtLeyendaFraccionado.getText(), idMarca, idRubro,
//								 idUnidad, idPresentacion, Long.parseLong("17412018"), "Proveedor", formatter.format(dpFechaCompra.getValue()), formatter.format(dpFechaBaja.getValue()),
								 idUnidad, idPresentacion, Long.parseLong("14808837"), "Proveedor", Double.parseDouble(txtPrecioCosto.getText()), Double.parseDouble(txtUtilidad.getText()),
								 Double.parseDouble(txtGravamen.getText()), estado) 
						:
							ArticuloAccess.update(articulo.getIdarticulo(), txtCodigoBarra.getText(), Integer.parseInt(txtCodigoEmpresa.getText()), Integer.parseInt(txtCodProdEmpresa.getText()), 
									Integer.parseInt(txtCodigoInterno.getText()), txtLeyenda.getText(), tipoProducto, tipoPosicion, 
									idUbicacion, Integer.parseInt(txtFila.getText()), Integer.parseInt(txtColumna.getText()), Double.parseDouble(txtExistencia.getText()), 
									Double.parseDouble(lblCantidad.getText()), Integer.parseInt(txtMinimo.getText()), Integer.parseInt(txtOptimo.getText()),
									Integer.parseInt(txtMaximo.getText()), isStockNegativo, Double.parseDouble(txtMerma.getText()), isFraccionable, 
									Integer.parseInt(txtPlu.getText()), Double.parseDouble(txtMargenUtilidad.getText()), txtLeyendaFraccionado.getText(), idMarca, idRubro,
									idUnidad, idPresentacion, Long.parseLong("14808837"), "Proveedor", Double.parseDouble(txtPrecioCosto.getText()), Double.parseDouble(txtUtilidad.getText()), 
									//											 Instant.from(dpFechaCompra.getValue()), Instant.from(dpFechaBaja.getValue()),	nstant.from(dpFechaActPrecios.getValue()), 
									Double.parseDouble(txtGravamen.getText()), estado); 

						handlerResult.onComplete(ar -> {
							if (ar.succeeded()) {
								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										// if you change the UI, do it here !
										articulo.setLeyenda(txtLeyenda.getText());
										articulo.setCodigointerno(Integer.parseInt(txtCodigoInterno.getText()));
										articulo.setTipo_producto(tipoProducto);
										articulo.setExistencia(Double.parseDouble(txtExistencia.getText()));
										articulo.setCantidad(Double.parseDouble(lblCantidad.getText()));
										articulo.setPrecio_costo(Double.parseDouble(txtPrecioCosto.getText()));
										articulo.setUtilidad(Double.parseDouble(txtUtilidad.getText()));
										articulo.setGravamen(Double.parseDouble(txtGravamen.getText()));
										articulo.setEstado(estado);
										btnRegistrarClicked = true;
										stage.close();
									}
								});
							} else {
								System.out.println(ar.cause());
								btnRegistrarClicked = false;
								stage.close();
							}
						});
			}
		});
		
		btnCancelar.setOnAction(can -> {
			stage.close();
		});
		
	}
	
	private void calcularPrecios(String newPrecioCosto) {

		if (cbFraccionable.isSelected()) {
			
			Platform.runLater(new Runnable() {
				public void run() {

					BigDecimal gravamen = new BigDecimal(txtGravamen.getText());
					BigDecimal margenUtilidad = new BigDecimal(txtMargenUtilidad.getText());			

					BigDecimal bruto = new BigDecimal(newPrecioCosto).multiply(margenUtilidad).divide(new BigDecimal(100).add(new BigDecimal(1)));
					lblBrutoNormal.setText(String.valueOf(bruto));

					BigDecimal montoIVA =  gravamen.divide(new BigDecimal(100).multiply(bruto));
					lblIvaNormal.setText(String.valueOf(montoIVA));

					BigDecimal neto = bruto.add(montoIVA);
					lblNetoNormal.setText(String.valueOf(neto));
				}
			});

		} else {

			Platform.runLater(new Runnable() {
				public void run() {

					BigDecimal gravamen = new BigDecimal(txtGravamen.getText());
					BigDecimal margenUtilidad = new BigDecimal(txtUtilidad.getText());			

					BigDecimal bruto = new BigDecimal(newPrecioCosto).multiply(margenUtilidad).divide(new BigDecimal(100).add(new BigDecimal(1)));
					lblBrutoNormal.setText(String.valueOf(bruto));

					BigDecimal montoIVA =  gravamen.divide(new BigDecimal(100).multiply(bruto));
					lblIvaNormal.setText(String.valueOf(montoIVA));

					BigDecimal neto = bruto.add(montoIVA);
					lblNetoNormal.setText(String.valueOf(neto));
				}
			});
		};
	}

	private void calcularCantidad() {
		lblExistencia.setText(txtExistencia.getText());
		lblContenedor.setText(presentacion.getContenedor());
		lblUnidades.setText(String.valueOf(presentacion.getUnidades()));
		lblValorUMedida.setText(String.valueOf(presentacion.getValor_umedida()));
		
		BigDecimal cantidad = new BigDecimal(txtExistencia.getText());
		
		switch(presentacion.getTipo_decremento()) { 
		case "Existencia": 

			Platform.runLater(new Runnable() {
				public void run() {

					lblTotalen.setText("Total en Unid.:");
					BigDecimal unidades = new BigDecimal(presentacion.getUnidades());
					BigDecimal totalUnidades = cantidad.multiply(unidades);

					lblCantidad.setText(String.valueOf(totalUnidades));

				}
			});

			break; 
		case "Cantidad": 

			Platform.runLater(new Runnable() {
				public void run() {

					lblTotalen.setText("Total en " + unidadMedida +  ":");
					BigDecimal valorUMedida = new BigDecimal(presentacion.getValor_umedida());
					BigDecimal totalCantidad = cantidad.multiply(valorUMedida);

					lblCantidad.setText(String.valueOf(totalCantidad));
				}
			});

			break; 
		default: 
			System.out.println("Sin coincidencias!"); 
		} 
	}

	private void inicializarTablaPresentaciones() {
		colContenedor.setCellValueFactory(cellData -> cellData.getValue().contenedorProperty());
		colUnidades.setCellValueFactory(cellData -> cellData.getValue().unidadesProperty());
		colValorUMedida.setCellValueFactory(cellData -> cellData.getValue().valor_umedidaProperty());
		colTipoDecremento.setCellValueFactory(cellData -> cellData.getValue().tipo_decrementoProperty());
	}
	
	private void inicializarTablaUbicaciones() {
		colDescripcion.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
		colNumero.setCellValueFactory(cellData -> cellData.getValue().numeroProperty());
		colDeposito.setCellValueFactory(cellData -> cellData.getValue().depositoProperty());
	}
	
	/**
	 * Sets the stage of this dialog.
	 *
	 * @param dialogStage
	 */
	public void setDialogStage(Stage stage) {
		this.stage = stage;
	}
	
	public void setArticulos(Articulo articulo) {
		this.articulo = articulo;
		this.txtCodigoBarra.setText(articulo.getCodigobarra());
		this.txtCodigoEmpresa.setText(String.valueOf(articulo.getCodigoempresa()));
		this.txtCodProdEmpresa.setText(String.valueOf(articulo.getCodigoprodempresa()));
		this.txtCodigoInterno.setText(String.valueOf(articulo.getCodigointerno())); 
		this.txtLeyenda.setText(articulo.getLeyenda());
		
//		switch(articulo.getTipo_producto()) { 
//		case "ESTANDAR": 
//			this.rbEstandard.setSelected(true);
//			break; 
//		case "SERVICIO": 
//			this.rbServicio.setSelected(true);
//			break; 
//		case "KIT": 
//			this.rbKit.setSelected(true);
//			break; 
//		case "MATRIZ": 
//			this.rbMatriz.setSelected(true);
//			break; 
//		default: 
//			System.out.println("Sin coincidencias!"); 
//		}
		
//		switch(articulo.getTipo_posicion()) { 
//		case "NORMAL": 
//			this.rbNormal.setSelected(true);
//			break; 
//		case "CONSIGNACION": 
//			this.rbConsignacion.setSelected(true);
//			break; 
//		default: 
//			System.out.println("Sin coincidencias!"); 
//		} 
		
//		idUbicacion, 
		this.txtFila.setText(String.valueOf(articulo.getFila()));
		this.txtColumna.setText(String.valueOf(articulo.getCol()));
		this.txtExistencia.setText(String.valueOf(articulo.getExistencia()));
//		lblCantidad.setText()
		this.txtMinimo.setText(String.valueOf(articulo.getStock_minimo()));
		this.txtOptimo.setText(String.valueOf(articulo.getStock_optimo()));
		this.txtMaximo.setText(String.valueOf(articulo.getStock_maximo()));
		
//		if (articulo.isStock_negativo()) {
//			cbStockNegativo.setSelected(true);
//		}
//		else {
//			cbStockNegativo.setSelected(false);
//		}
//		
//		this.txtMerma.setText(String.valueOf(articulo.getMerma()));
//
//		if (articulo.isFraccionado()) {
//			cbFraccionable.setSelected(true);
//		}
//		else {
//			cbFraccionable.setSelected(false);
//		}

		this.txtPlu.setText(String.valueOf(articulo.getPlu()));
		this.txtUtilidad.setText(String.valueOf(articulo.getUtilidad()));
		this.txtMargenUtilidad.setText(String.valueOf(articulo.getUtilidad_fraccionado()));
		this.txtLeyendaFraccionado.setText(articulo.getLeyenda_fraccionado());
//		idMarca
//		idRubro,
//		idUnidad
//		idPresentacion, 
//		Long.parseLong("17412018"),
//		"Proveedor", 
//		this.dpFechaCompra.setValue(articulo.get)
//		this.dpFechaBaja.setValue()
//		this.dpFechaActPrecios.setValue()
		this.txtPrecioCosto.setText(String.valueOf(articulo.getPrecio_costo()));
		this.txtGravamen.setText(String.valueOf(articulo.getGravamen()));
		
//		switch(articulo.getEstado()) { 
//		case "ACTIVO": 
//			this.rbActivo.setSelected(true);
//			break; 
//		case "BAJA": 
//			this.rbBaja.setSelected(true);
//			break; 
//		case "OFERTA": 
//			this.rbOferta.setSelected(true);
//			break; 
//		default: 
//			System.out.println("Sin coincidencias!"); 
//		}
	}
	
	/**
	 * Returns true if the user clicked OK, false otherwise.
	 *
	 * @return
	 */
	public boolean isbtnRegistrarClicked() {
		return btnRegistrarClicked;
	}

	private void stkNegativoChanged(ObservableValue<? extends Boolean> observable, 
			Boolean oldValue,
			Boolean newValue) {

		if (newValue) {
			isStockNegativo = true; 
		} else {
			isStockNegativo = false;
		}
	}
	
	private void fraccionableChanged(ObservableValue<? extends Boolean> observable, 
			Boolean oldValue, 
			Boolean newValue) {
		
		if (newValue) {
			isFraccionable = true;
			Future<Integer> maxPLU = ArticuloAccess.getMaxPLU();
			
			Integer result = maxPLU.result();
			
			if (result == null) {
				result = 0;
				AtomicInteger plu = new AtomicInteger(result);
				plu.getAndIncrement();
				String pluString = plu.toString();
				txtPlu.setText(pluString);
			} else {
				AtomicInteger plu = new AtomicInteger(result);
				plu.getAndIncrement();
				String pluString = plu.toString();
				txtPlu.setText(pluString);
			}
		} else {
			txtPlu.clear();
			isFraccionable = false;
		}
	};
	
}
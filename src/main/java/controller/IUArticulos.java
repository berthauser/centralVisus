package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.simple.parser.ParseException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
	private Label lblNetoMayorista;
	@FXML
	private Label lblBrutoMayorista;
	@FXML
	private Label lblIvaMayorista;
	@FXML
	private Label lblNetoMinorista;
	@FXML
	private Label lblBrutoMinorista;
	@FXML
	private Label lblIVAMinorista;
	@FXML
	private Label lblNetoFraccionado;
	@FXML
	private Label lblBrutoFraccionado;
	@FXML
	private Label lblIvaFraccionado;
	@FXML
	private Label lblNetoCantidad;
	@FXML
	private Label lblBrutoCantidad;
	@FXML
	private Label lblIvaCantidad;
	@FXML
	private Label lblDescripcion;
	@FXML
	private Label lblNumero;
	@FXML
	private Label lblDeposito;
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
	private JFXTextField txtUtilidadMay;
	@FXML
	private JFXTextField txtUtilidadMin;
	@FXML
	private JFXTextField txtPrecioCosto;
	@FXML
	private JFXTextField txtGravamen;
	@FXML
	private JFXCheckBox cbDescuentoMasivo;
	@FXML
	private TextField txtCantidadMinima;
	@FXML
	private TextField txtDtoPorCantidad;
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
	private JFXTextField txtUtilidadFraccionado;
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
	private boolean isDescuentoMasivo = false;
	private boolean btnRegistrarClicked = false;

	private String rbTipoProducto;
	private String rbTipoPosicion;
	private String rbEstado;
	private String tipoActividad;
	private String leyenda;

	private static final String MSG = "Requerido";
	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy");
	private DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private RequiredFieldValidator validator = new RequiredFieldValidator();

	ObservableList<Rubro> rubrosData = FXCollections.observableArrayList();
	ObservableList<Linea> lineasData = FXCollections.observableArrayList();
	ObservableList<Marca> marcasData = FXCollections.observableArrayList();
	ObservableList<Unidad> unidadesData = FXCollections.observableArrayList();
	ObservableList<Ubicacion> ubicacionesData = FXCollections.observableArrayList();
	ObservableList<Presentacion> presentacionesData = FXCollections.observableArrayList();

	@FXML
	public void initialize() throws FileNotFoundException {

		validator.setMessage(MSG);

		LocalDate fecha = LocalDate.now();
		lblFecha.setText((dtf.format(fecha)));

		Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
			LocalTime currentTime = LocalTime.now();
			lblHora.setText(currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
		}), new KeyFrame(Duration.seconds(1)));

		clock.setCycleCount(Animation.INDEFINITE);
		clock.play();

//        imgLogo.setImage(new Image("/images/articulos.png"));
//        imgLogo.setImage(new Image("/resources/images/articulos.png"));
//	    imgLogo.setSmooth(true);
//	    imgLogo.setPreserveRatio(true);

		Platform.runLater(() -> txtCodigoBarra.requestFocus());

		RadioButton rEstado = (RadioButton) tgEstado.getSelectedToggle();
		rbEstado = rEstado.getText();

		RadioButton rTipoProducto = (RadioButton) tgTipoProducto.getSelectedToggle();
		rbTipoProducto = rTipoProducto.getText();

		RadioButton rTipoPosicion = (RadioButton) tgTipoPosicion.getSelectedToggle();
		rbTipoPosicion = rTipoPosicion.getText();

		tgEstado.selectedToggleProperty().addListener(i -> {
			RadioButton estado = (RadioButton) tgEstado.getSelectedToggle();
			rbEstado = estado.getText();
		});

		tgTipoPosicion.selectedToggleProperty().addListener(i -> {
			RadioButton tipoPosicion = (RadioButton) tgTipoPosicion.getSelectedToggle();
			rbTipoPosicion = tipoPosicion.getText();
		});

		tgTipoProducto.selectedToggleProperty().addListener(i -> {
			RadioButton tipoProducto = (RadioButton) tgTipoProducto.getSelectedToggle();
			rbTipoProducto = tipoProducto.getText();
		});

		cbStockNegativo.selectedProperty().addListener(this::stkNegativoChanged);
		cbDescuentoMasivo.selectedProperty().addListener(this::dtoMasivoChanged);
		cbFraccionable.selectedProperty().addListener((observable, oldValue, newValue) -> {
			try {
				fraccionableChanged(observable, oldValue, newValue);
			} catch (IOException | ParseException e1) {
				e1.printStackTrace();
			}
		});

		txtCodigoBarra.getValidators().add(validator);
		txtCodigoBarra.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtCodigoBarra.validate();
			}
		});

		txtCodigoEmpresa.getValidators().add(validator);
		txtCodigoEmpresa.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtCodigoEmpresa.validate();
			}
		});

		txtCodProdEmpresa.getValidators().add(validator);
		txtCodProdEmpresa.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtCodProdEmpresa.validate();
			}
		});

		txtCodigoInterno.getValidators().add(validator);
		txtCodigoInterno.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtCodigoInterno.validate();
			}
		});

		txtPlu.getValidators().add(validator);
		txtPlu.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtPlu.validate();
			}
		});

		txtLeyenda.getValidators().add(validator);
		txtLeyenda.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtLeyenda.validate();
			}
		});

		txtFila.getValidators().add(validator);
		txtFila.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtFila.validate();
			}
		});

		txtColumna.getValidators().add(validator);
		txtColumna.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtColumna.validate();
			}
		});

		txtExistencia.getValidators().add(validator);
		txtExistencia.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtExistencia.validate();
			}
		});

		txtMinimo.getValidators().add(validator);
		txtMinimo.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtCodigoBarra.validate();
			}
		});

		txtOptimo.getValidators().add(validator);
		txtOptimo.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtOptimo.validate();
			}
		});

		txtMaximo.getValidators().add(validator);
		txtMaximo.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtMaximo.validate();
			}
		});

		cmbMarca.getValidators().add(validator);
		cmbMarca.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				cmbMarca.validate();
			}
		});

		cmbRubro.getValidators().add(validator);
		cmbRubro.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				cmbRubro.validate();
			}
		});

		cmbLinea.getValidators().add(validator);
		cmbLinea.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				cmbLinea.validate();
			}
		});

		cmbUnidadMedida.getValidators().add(validator);
		cmbUnidadMedida.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				cmbUnidadMedida.validate();
			}
		});

		txtPrecioCosto.getValidators().add(validator);
		txtPrecioCosto.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtPrecioCosto.validate();
			}
		});

		txtUtilidadMay.getValidators().add(validator);
		txtUtilidadMay.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtUtilidadMay.validate();
			}
		});

		txtUtilidadMin.getValidators().add(validator);
		txtUtilidadMin.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtUtilidadMin.validate();
			}
		});

		txtGravamen.getValidators().add(validator);
		txtGravamen.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtGravamen.validate();
			}
		});

		dpFechaCompra.setValue(LocalDate.now());

		dpFechaActPrecios.setValue(LocalDate.now());

		dpFechaBaja.setValue(LocalDate.of(1899, 01, 01));

		btnRegistrar.disableProperty().bind((txtCodigoBarra.textProperty().isNotEmpty()
				.and(txtCodigoEmpresa.textProperty().isNotEmpty().and(txtCodProdEmpresa.textProperty().isNotEmpty())
						.and(txtCodProdEmpresa.textProperty().isNotEmpty())
						.and(txtCodigoInterno.textProperty().isNotEmpty()).and(txtPlu.textProperty().isNotEmpty())
						.and(txtLeyenda.textProperty().isNotEmpty()).and(txtFila.textProperty().isNotEmpty())
						.and(txtColumna.textProperty().isNotEmpty()).and(txtExistencia.textProperty().isNotEmpty())
						.and(txtMinimo.textProperty().isNotEmpty()).and(txtOptimo.textProperty().isNotEmpty())
						.and(txtMaximo.textProperty().isNotEmpty()).and(cmbMarca.valueProperty().isNotNull())
						.and(cmbRubro.valueProperty().isNotNull()).and(cmbLinea.valueProperty().isNotNull())
						.and(tblPresentacion.getSelectionModel().selectedItemProperty().isNotNull())
						.and(tblUbicacion.getSelectionModel().selectedItemProperty().isNotNull())
						.and(cmbUnidadMedida.selectionModelProperty().isNotNull())
						.and(txtPrecioCosto.textProperty().isNotEmpty()).and(txtUtilidadMay.textProperty().isNotEmpty())
						.and(txtUtilidadMin.textProperty().isNotEmpty()).and(txtGravamen.textProperty().isNotEmpty()))
				.not()));

		txtUtilidadFraccionado.disableProperty().bind(cbFraccionable.selectedProperty().not());
		txtLeyendaFraccionado.disableProperty().bind(cbFraccionable.selectedProperty().not());
		txtPlu.disableProperty().bind(cbFraccionable.selectedProperty().not());

		txtCantidadMinima.disableProperty().bind(cbDescuentoMasivo.selectedProperty().not());
		txtDtoPorCantidad.disableProperty().bind(cbDescuentoMasivo.selectedProperty().not());

		inicializarTablaPresentaciones();

		inicializarTablaUbicaciones();

		UbicacionAccess.getUbicaciones(ubicacionesData);
		tblUbicacion.setItems(ubicacionesData);

		RubroAccess.getRubros(rubrosData);
		cmbRubro.setItems(rubrosData);

		LineaAccess.getLineas(lineasData);
//		cmbLinea.setItems(lineasData);

		UnidadAccess.getUnidades(unidadesData);
		cmbUnidadMedida.setItems(unidadesData);

		MarcaAccess.getMarcas(marcasData);
		cmbMarca.setItems(marcasData);

		PresentacionAccess.getPresentaciones(presentacionesData);
		tblPresentacion.setItems(presentacionesData);

		cmbRubro.valueProperty().addListener((obs, oldItem, newItem) -> {
			if (!(newItem == null)) {
				idRubro = newItem.getIdrubro();
				lineasData = LineaAccess.getLineasByRubro(idRubro);
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

//		txtPrecioCosto.textProperty().addListener((obs, oldPrecioCosto, newPrecioCosto) -> {
//			Platform.runLater(() -> {
//				calcularPrecios(newPrecioCosto);
//			});
//		});

		txtPrecioCosto.setOnKeyPressed(e -> {
			Platform.runLater(() -> {
				if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.TAB) {
					calcularPreciosMinMay(txtPrecioCosto.getText());
				}
			});
		});

		txtUtilidadFraccionado.setOnKeyPressed(e -> {
			Platform.runLater(() -> {
				if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.TAB) {
					calcularPreciosFraccionados(txtPrecioCosto.getText());
				}
			});
		});

		txtDtoPorCantidad.setOnKeyPressed(e -> {
			Platform.runLater(() -> {
				if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.TAB) {
					calcularPreciosMasivos(txtPrecioCosto.getText());
				}
			});
		});

		tblUbicacion.setOnMouseClicked(event -> {
			Ubicacion ubicacion = tblUbicacion.getSelectionModel().getSelectedItem();
			idUbicacion = ubicacion.getIdubicacion();
			lblDescripcion.setText(ubicacion.getDescripcion());
			lblNumero.setText(ubicacion.getNumero());
			lblDeposito.setText(ubicacion.getDeposito());
		});

		tblUbicacion.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.SPACE) {
				Ubicacion ubicacion = tblUbicacion.getSelectionModel().getSelectedItem();
				idUbicacion = ubicacion.getIdubicacion();
				lblDescripcion.setText(ubicacion.getDescripcion());
				lblNumero.setText(ubicacion.getNumero());
				lblDeposito.setText(ubicacion.getDeposito());
			}
		});

		tblPresentacion.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.SPACE) {
				presentacion = tblPresentacion.getSelectionModel().getSelectedItem();
				idPresentacion = presentacion.getIdpresentacion();
				calcularCantidad();
			}
		});

		tblPresentacion.setOnMouseClicked(event -> {
			presentacion = tblPresentacion.getSelectionModel().getSelectedItem();
			idPresentacion = presentacion.getIdpresentacion();
			calcularCantidad();
		});

		txtCodigoBarra.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
				startTask();
			}
		});
		
		txtCodigoInterno.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
				validarCodigoRepetido();
			}
		});

		txtLeyendaFraccionado.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.length() == 19) {
				txtLeyendaFraccionado.setText(oldValue);
			}
		});

		btnRegistrar.setOnAction(reg -> {
			Future<Integer> handlerResult = null;
			try {
				handlerResult = (articulo.getIdarticulo() == 0) ?

						ArticuloAccess.insert(txtCodigoBarra.getText(), Integer.valueOf(txtCodigoEmpresa.getText()),
								Integer.valueOf(txtCodProdEmpresa.getText()),
								Integer.valueOf(txtCodigoInterno.getText()), txtLeyenda.getText(), rbTipoProducto,
								rbTipoPosicion, idUbicacion, Integer.valueOf(txtFila.getText()),
								Integer.valueOf(txtColumna.getText()), Double.valueOf(txtExistencia.getText()),
								Double.valueOf(lblCantidad.getText()), Integer.valueOf(txtMinimo.getText()),
								Integer.valueOf(txtOptimo.getText()), Integer.valueOf(txtMaximo.getText()),
								isStockNegativo, Double.valueOf(txtMerma.getText()), isFraccionable,
								Integer.valueOf(txtPlu.getText()), Double.valueOf(txtUtilidadFraccionado.getText()),
								txtLeyendaFraccionado.getText(), isDescuentoMasivo,
								Double.valueOf(txtCantidadMinima.getText()),
								Double.valueOf(txtDtoPorCantidad.getText()), idMarca, idRubro, idLinea, idUnidad,
								idPresentacion, Long.valueOf("14808837"), "Proveedor",
								Double.valueOf(txtPrecioCosto.getText()),

								dpFechaCompra.getValue().format(dtf2), dpFechaBaja.getValue().format(dtf2),
								dpFechaActPrecios.getValue().format(dtf2),

								Double.valueOf(txtUtilidadMay.getText()), Double.valueOf(txtUtilidadMin.getText()),
								Double.valueOf(txtGravamen.getText()), rbEstado)
						: ArticuloAccess.updateAll(articulo.getIdarticulo(), txtCodigoBarra.getText(),
								Integer.valueOf(txtCodigoEmpresa.getText()),
								Integer.valueOf(txtCodProdEmpresa.getText()),
								Integer.valueOf(txtCodigoInterno.getText()), txtLeyenda.getText(), rbTipoProducto,
								rbTipoPosicion, idUbicacion, Integer.valueOf(txtFila.getText()),
								Integer.valueOf(txtColumna.getText()), Double.valueOf(txtExistencia.getText()),
								Double.valueOf(lblCantidad.getText()), Integer.valueOf(txtMinimo.getText()),
								Integer.valueOf(txtMaximo.getText()), Integer.valueOf(txtOptimo.getText()),
								isStockNegativo, Double.valueOf(txtMerma.getText()), isFraccionable,
								Integer.valueOf(txtPlu.getText()), Double.valueOf(txtUtilidadFraccionado.getText()),
								txtLeyendaFraccionado.getText(), isDescuentoMasivo,
								Double.valueOf(txtCantidadMinima.getText()),
								Double.valueOf(txtDtoPorCantidad.getText()), idMarca, idRubro, idLinea, idUnidad,
								idPresentacion, Long.valueOf("14808837"), "Proveedor",
								Double.valueOf(txtPrecioCosto.getText()),

								dpFechaCompra.getValue().format(dtf2), dpFechaBaja.getValue().format(dtf2),
								dpFechaActPrecios.getValue().format(dtf2),

								Double.valueOf(txtUtilidadMay.getText()), Double.valueOf(txtUtilidadMin.getText()),
								Double.valueOf(txtGravamen.getText()), rbEstado);
			} catch (NumberFormatException | IOException | ParseException e1) {
				e1.printStackTrace();
			}

			handlerResult.onComplete(ar -> {
				if (ar.succeeded()) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							// if you change the UI, do it here !
							articulo.setLeyenda(txtLeyenda.getText());
							articulo.setCodigointerno(Integer.valueOf(txtCodigoInterno.getText()));
							articulo.setTipo_producto(rbTipoProducto);
							articulo.setExistencia(Double.valueOf(txtExistencia.getText()));
							articulo.setCantidad(Double.valueOf(lblCantidad.getText()));
							articulo.setPrecio_costo(Double.valueOf(txtPrecioCosto.getText()));
							articulo.setMargen_utilidad_mayorista(Double.valueOf(txtUtilidadMay.getText()));
							articulo.setMargen_utilidad_minorista(Double.valueOf(txtUtilidadMin.getText()));
							articulo.setGravamen(Double.valueOf(txtGravamen.getText()));
							articulo.setEstado(rbEstado);
							btnRegistrarClicked = true;
							stage.close();
						}
					});
				} else {
					System.out.println(ar.cause());
					btnRegistrarClicked = false;
					stage.close();
				}
			}); // handler
		});

		btnCancelar.setOnAction(can -> {
			stage.close();
		});

	}

	private void validarCodigoRepetido() {
		try {
			Future<String> result = ArticuloAccess.getForValidateCodigoInterno(txtCodigoInterno.getText());

			result.onComplete(valor -> {
				leyenda = valor.result();
			});

			Thread.sleep(1000);
		} catch (InterruptedException | IOException | ParseException e) {
			e.printStackTrace();
		}

		if (leyenda == null) {
			txtLeyenda.requestFocus();
		} else {
			Alert exceptionDialog = new Alert(Alert.AlertType.ERROR);
			exceptionDialog.setTitle("Error");
			exceptionDialog.setHeaderText("Código Interno duplicado!");
			exceptionDialog.setContentText("ARTÍCULO -> " + leyenda);
			exceptionDialog.showAndWait();
			txtCodigoInterno.clear();
			leyenda = null;
			txtCodigoInterno.requestFocus();
		}
	}

	public void startTask() {
//		Creo un runnable
		Runnable task = () -> runTask();
//		Corro la tarea en un thread en background
		Thread backgroundThread = new Thread(task);
//		Finalizo el thread si la aplicación termina
		backgroundThread.setDaemon(true);
//		Arranco el thread
		backgroundThread.start();
	}

	public void runTask() {

		switch (tipoActividad) {
		case "Alta":
			Platform.runLater(() -> {
				try {

					Future<String> result = ArticuloAccess.getForValidateCodigoBarra(txtCodigoBarra.getText());

					result.onComplete(valor -> {
						leyenda = valor.result();
						System.out.println(leyenda);
					});

					Thread.sleep(1000);
				} catch (InterruptedException | IOException | ParseException e) {
					e.printStackTrace();
				}

				if (leyenda == null) {
					// if (isEmptyBarCode(txtCodigoBarra.getText())) {
					txtCodigoEmpresa.setText(txtCodigoBarra.getText().substring(3, 7));
					txtCodProdEmpresa.setText(txtCodigoBarra.getText().substring(7, 12));
					txtCodigoInterno.requestFocus();
				} else {
					Alert exceptionDialog = new Alert(Alert.AlertType.ERROR);
					exceptionDialog.setTitle("Error");
					exceptionDialog.setHeaderText("Código de Barra duplicado!");
					exceptionDialog.setContentText("ARTÍCULO -> " + leyenda);
					exceptionDialog.showAndWait();
					txtCodigoBarra.clear();
					leyenda = null;
					txtCodigoBarra.requestFocus();
				}
			});
			break;
		case "Modificacion":
			txtCodigoEmpresa.setText(txtCodigoBarra.getText().substring(3, 7));
			txtCodProdEmpresa.setText(txtCodigoBarra.getText().substring(7, 12));
			txtCodigoInterno.requestFocus();
			break;
		default:
		}

	}

	private void calcularPreciosMinMay(String newPrecioCosto) {
		BigDecimal gravamen = new BigDecimal(txtGravamen.getText());
		BigDecimal margenUtilidadMay = new BigDecimal(txtUtilidadMay.getText());
		BigDecimal margenUtilidadMin = new BigDecimal(txtUtilidadMin.getText());

		BigDecimal gananciaMay = new BigDecimal(newPrecioCosto).multiply(margenUtilidadMay).divide(new BigDecimal(100));
		BigDecimal brutoMay = new BigDecimal(newPrecioCosto).add(gananciaMay);

		BigDecimal gananciaMin = new BigDecimal(newPrecioCosto).multiply(margenUtilidadMin).divide(new BigDecimal(100));
		BigDecimal brutoMin = new BigDecimal(newPrecioCosto).add(gananciaMin);

		// Etiquetas para precios mayoristas
		lblBrutoMayorista.setText(brutoMay.setScale(2, RoundingMode.HALF_EVEN).toPlainString());
		BigDecimal montoIVAMay = brutoMay.multiply(gravamen.divide(new BigDecimal(100)));
		lblIvaMayorista.setText(montoIVAMay.setScale(2, RoundingMode.HALF_EVEN).toPlainString());
		BigDecimal netoMay = brutoMay.add(montoIVAMay);
		lblNetoMayorista.setText(netoMay.setScale(2, RoundingMode.HALF_EVEN).toPlainString());

		// Etiquetas para precios minoristas
		lblBrutoMinorista.setText(brutoMin.setScale(2, RoundingMode.HALF_EVEN).toPlainString());
		BigDecimal montoIVAMin = brutoMin.multiply(gravamen.divide(new BigDecimal(100)));
		lblIVAMinorista.setText(montoIVAMin.setScale(2, RoundingMode.HALF_EVEN).toPlainString());
		BigDecimal netoMin = brutoMin.add(montoIVAMin);
		lblNetoMinorista.setText(netoMin.setScale(2, RoundingMode.HALF_EVEN).toPlainString());
	}

	private void calcularPreciosFraccionados(String newPrecioCosto) {
		BigDecimal gravamen = new BigDecimal(txtGravamen.getText());
		BigDecimal margenUtilidadFraccionado = new BigDecimal(txtUtilidadFraccionado.getText());

		BigDecimal gananciaFraccionado = new BigDecimal(newPrecioCosto).multiply(margenUtilidadFraccionado)
				.divide(new BigDecimal(100));
		BigDecimal brutoFraccionado = new BigDecimal(newPrecioCosto).add(gananciaFraccionado);
		lblBrutoFraccionado.setText(brutoFraccionado.setScale(2, RoundingMode.HALF_EVEN).toPlainString());

		BigDecimal montoIVAFraccionado = brutoFraccionado.multiply(gravamen.divide(new BigDecimal(100)));
		lblIvaFraccionado.setText(montoIVAFraccionado.setScale(2, RoundingMode.HALF_EVEN).toPlainString());

		BigDecimal netoFraccionado = brutoFraccionado.add(montoIVAFraccionado);
		lblNetoFraccionado.setText(netoFraccionado.setScale(2, RoundingMode.HALF_EVEN).toPlainString());
	};

	private void calcularPreciosMasivos(String newPrecioCosto) {
		BigDecimal gravamen = new BigDecimal(txtGravamen.getText());
		BigDecimal gananciaBasica = new BigDecimal(newPrecioCosto).multiply(new BigDecimal(txtUtilidadMin.getText()))
				.divide(new BigDecimal(100));
		BigDecimal descuento = new BigDecimal(txtDtoPorCantidad.getText());

		BigDecimal brutoBasico = new BigDecimal(newPrecioCosto).add(gananciaBasica);
		BigDecimal descuentoMasivo = brutoBasico.multiply(descuento.divide(new BigDecimal(100)));
		BigDecimal brutoConDescuento = brutoBasico.subtract(descuentoMasivo);
		
		lblBrutoCantidad.setText(brutoConDescuento.setScale(2, RoundingMode.HALF_EVEN).toPlainString());

		BigDecimal montoIVA = brutoConDescuento.multiply(gravamen.divide(new BigDecimal(100)));
		lblIvaCantidad.setText(montoIVA.setScale(2, RoundingMode.HALF_EVEN).toPlainString());

		BigDecimal netoConIva = brutoConDescuento.add(montoIVA);
		lblNetoCantidad.setText(netoConIva.setScale(2, RoundingMode.HALF_EVEN).toPlainString());
	};

	private void calcularCantidad() {
		lblContenedor.setText(presentacion.getContenedor());
		lblUnidades.setText(String.valueOf(presentacion.getUnidades()));
		lblValorUMedida.setText(String.valueOf(presentacion.getValor_umedida()));

		BigDecimal cantidad = new BigDecimal(txtExistencia.getText());

		switch (presentacion.getTipo_decremento()) {
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

					lblTotalen.setText("Total en " + unidadMedida + ":");
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

	public void setArticulos(Articulo articulo, String tipo) {

		this.articulo = articulo;
		tipoActividad = tipo; // uso esta variable para saber si busco el BC o no

		switch (tipo) {
		case "Alta":
			this.txtMerma.setText(String.valueOf(articulo.getMerma()));
			this.txtUtilidadFraccionado.setText(String.valueOf(articulo.getUtilidad_fraccionado()));
			this.txtPlu.setText(String.valueOf(articulo.getPlu()));
			break;
		case "Modificacion":
			this.txtCodigoBarra.setText(articulo.getCodigobarra());
			this.txtCodigoEmpresa.setText(String.valueOf(articulo.getCodigoempresa()));
			this.txtCodProdEmpresa.setText(String.valueOf(articulo.getCodigoprodempresa()));
			this.txtCodigoInterno.setText(String.valueOf(articulo.getCodigointerno()));
			this.txtLeyenda.setText(articulo.getLeyenda());

			switch (articulo.getTipo_producto()) {
			case "ESTANDAR":
				this.rbEstandard.setSelected(true);
				break;
			case "SERVICIO":
				this.rbServicio.setSelected(true);
				break;
			case "KIT":
				this.rbKit.setSelected(true);
				break;
			case "MATRIZ":
				this.rbMatriz.setSelected(true);
				break;
			default:
				this.rbEstandard.setSelected(true);
			}

			switch (articulo.getTipo_posicion()) {
			case "NORMAL":
				this.rbNormal.setSelected(true);
				break;
			case "CONSIGNACION":
				this.rbConsignacion.setSelected(true);
				break;
			default:
				this.rbNormal.setSelected(true);
			}

			this.txtFila.setText(String.valueOf(articulo.getFila()));
			this.txtColumna.setText(String.valueOf(articulo.getCol()));
			this.txtExistencia.setText(String.valueOf(articulo.getExistencia()));
			this.txtMinimo.setText(String.valueOf(articulo.getStock_minimo()));
			this.txtOptimo.setText(String.valueOf(articulo.getStock_optimo()));
			this.txtMaximo.setText(String.valueOf(articulo.getStock_maximo()));

			cbStockNegativo.setSelected(articulo.isStock_negativo());

			this.txtMerma.setText(String.valueOf(articulo.getMerma()));

			cbFraccionable.setSelected(articulo.isFraccionado());
			;

			this.txtPlu.setText(String.valueOf(articulo.getPlu()));
			this.txtUtilidadMay.setText(String.valueOf(articulo.getMargen_utilidad_mayorista()));
			this.txtUtilidadMin.setText(String.valueOf(articulo.getMargen_utilidad_minorista()));
			this.txtUtilidadFraccionado.setText(String.valueOf(articulo.getUtilidad_fraccionado()));
			this.txtLeyendaFraccionado.setText(articulo.getLeyenda_fraccionado());

			Platform.runLater(() -> {
				lblContenedor.setText(articulo.getPresentacion());
				lblUnidades.setText(String.valueOf(articulo.getCantUnidades()));
				lblValorUMedida.setText(String.valueOf(articulo.getValor_umedida()));

				BigDecimal cantidad = new BigDecimal(txtExistencia.getText());

				switch (articulo.getTipo_decremento()) {
				case "Existencia":
					Platform.runLater(new Runnable() {
						public void run() {
							lblTotalen.setText("Total en Unid.:");
							BigDecimal unidades = new BigDecimal(articulo.getCantUnidades());
							BigDecimal totalUnidades = cantidad.multiply(unidades);
							lblCantidad.setText(String.valueOf(totalUnidades));
						}
					});
					break;
				case "Cantidad":
					Platform.runLater(new Runnable() {
						public void run() {
							lblTotalen.setText("Total en " + unidadMedida + ":");
							BigDecimal valorUMedida = new BigDecimal(articulo.getValor_umedida());
							BigDecimal totalCantidad = cantidad.multiply(valorUMedida);
							lblCantidad.setText(String.valueOf(totalCantidad));
						}
					});
					break;
				default:
					System.out.println("Sin coincidencias!");
				}
			});

			Platform.runLater(() -> {

				for (Rubro rubro : rubrosData) {
					if (rubro.getDescripcion().equals(articulo.getRubro())) {
						cmbRubro.setValue(rubro);
					}
				}

				for (Linea linea : lineasData) {
					if (linea.getDescripcion().equals(articulo.getLinea())) {
						cmbLinea.setValue(linea);
					}
				}

				for (Unidad unidad : unidadesData) {
					if (unidad.getNombre().equals(articulo.getUnidad())) {
						cmbUnidadMedida.setValue(unidad);
					}
				}

				for (Marca marca : marcasData) {
					if (marca.getDescripcion().equals(articulo.getMarca())) {
						cmbMarca.setValue(marca);
					}
				}

			});

			Platform.runLater(() -> {
				lblDescripcion.setText(articulo.getUbicacion());
				lblNumero.setText(articulo.getNumero());
				lblDeposito.setText(articulo.getDeposito());
			});

			// Long.parseLong("17412018"),
			// "Proveedor",

			this.dpFechaCompra.setValue(articulo.getFecha_compra());
			this.dpFechaBaja.setValue(articulo.getFecha_baja());
			this.dpFechaActPrecios.setValue(articulo.getFecha_actprecios());

			this.txtPrecioCosto.setText(String.valueOf(articulo.getPrecio_costo()));
			this.txtGravamen.setText(String.valueOf(articulo.getGravamen()));

			switch (articulo.getEstado()) {
			case "ACTIVO":
				this.rbActivo.setSelected(true);
				break;
			case "BAJA":
				this.rbBaja.setSelected(true);
				break;
			case "OFERTA":
				this.rbOferta.setSelected(true);
				break;
			default:
				this.rbActivo.setSelected(true);
			}
			break;
		default:

		}
	}

	/**
	 * Returns true if the user clicked OK, false otherwise.
	 *
	 * @return
	 */
	public boolean isbtnRegistrarClicked() {
		return btnRegistrarClicked;
	}

	private void stkNegativoChanged(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

		if (newValue) {
			isStockNegativo = true;
		} else {
			isStockNegativo = false;
		}
	}

	private void dtoMasivoChanged(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

		if (newValue) {
			isDescuentoMasivo = true;
		} else {
			isDescuentoMasivo = false;
		}
	}

	private void fraccionableChanged(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
			throws FileNotFoundException, IOException, ParseException {

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
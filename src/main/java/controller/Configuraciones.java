package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.json.simple.parser.ParseException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import consumer.ConfiguracionAccess;
import io.vertx.core.Future;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import jfxtras.styles.jmetro.FlatAlert;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import model.Configuracion;

public class Configuraciones {
	@FXML
	private ToggleGroup tgTipo;
	@FXML
	private ToggleGroup tgImprimir;
	@FXML
	private JFXRadioButton rbProduccion;
	@FXML
	private JFXRadioButton rbHomologacion;
	@FXML
	private JFXRadioButton rbFacturas;
	@FXML
	private JFXRadioButton rbTickets;
	@FXML
	private JFXTextField txtRazonSocial;
	@FXML
	private JFXTextField txtNombreFantasia;
	@FXML
	private JFXTextField txtEmail;
	@FXML
	private JFXTextField txtDomicilioComercial;
	@FXML
	private JFXTextField txtLocalidad;
	@FXML
	private JFXComboBox<String> cmbProvincia;
	@FXML
	private JFXTextField txtCodigoPostal;
	@FXML
	private JFXTextField txtTelefonoFijo;
	@FXML
	private JFXTextField txtTelefonoMovil;
	@FXML
	private JFXComboBox<String> cmbSituacionFiscal;
	@FXML
	private JFXTextField txtDomicilioFiscal;
	@FXML
	private JFXTextField txtPuntoVenta;
	@FXML
	private JFXTextField txtCUIT;
	@FXML
	private JFXTextField txtIIBB;
	@FXML
	private JFXTextField txtImporteAfip;
	@FXML
	private JFXDatePicker dpFechaInicio;
	@FXML
	private JFXButton btnCP;
	@FXML
	private JFXTextField txtDirectorioCP;
	@FXML
	private JFXButton btnCH;
	@FXML
	private JFXTextField txtDirectorioCH;
	@FXML
	private JFXButton btnClavePrivada;
	@FXML
	private JFXTextField txtDirectorioClavePrivada;
	@FXML
	private JFXButton btnFPDF;
	@FXML
	private JFXTextField txtFacturasPDF;
	@FXML
	private Button btnActualizar;
	@FXML
	private Button btnRecuperar;
	@FXML
	private ImageView imgConfiguracion;
	@FXML
	private Label lblFecha;
	@FXML
	private Label lblHora;

	private Stage myStage;
	private static Boolean isEmpty;
	private Integer idconfiguracion;
	private static Boolean modoProduccion;
	private static Boolean modoFactura;
	private static final String MSG = "Requerido";
	private RequiredFieldValidator validator = new RequiredFieldValidator();
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd MM yyyy");
	private ObservableList<Configuracion> configuracionList = FXCollections.observableArrayList();

	/**
	 * Propiedad boolean modifiedProperty verifica si el usuario cambio cualquiera
	 * de los atributos de texto del formulario. Reseteamos esa bandera cada vez que
	 * se selecciona algo en el tableview y la volvemos a usar en una expresión bind
	 * para controlar la propiedad disable del botón "Modificar".
	 */

	private final BooleanProperty modifiedProperty = new SimpleBooleanProperty(false);

	private ObservableList<String> listaCondicionIVA = FXCollections.observableArrayList("IVA Responsable Inscripto",
			"IVA Responsable No Inscripto", "IVA No Responsable", "IVA Sujeto Exento", "Consumidor Final",
			"Responsable Monotributo", "Sujeto No Categorizado", "Proveedor del Exterior", "Cliente del Exterior",
			"IVA Liberado - Ley 19.640", "IVA Responsable Inscripto – Agente de Percepción",
			"Pequeño Contribuyente Eventual", "Monotributista Social", "Pequeño Contribuyente Eventual Social");
	
	private ObservableList<String> listaProvincias = FXCollections.observableArrayList("Buenos Aires", "Catamarca",
			"Chaco", "Chubut", "Córdoba", "Corrientes", "Entre Ríos", "Formosa", "Jujuy", "La Pampa", "La Rioja",
			"Mendoza", "Misiones", "Neuquén", "Río Negro", "Salta", "San Juan", "San Luis", "Santa Cruz", "Santa Fe",
			"Santiago del Estero", "Tierra del Fuego", "Tucumán");

	@FXML
	public void initialize() throws ClassNotFoundException, SQLException, IOException, ParseException {

		JMetro jm = new JMetro(Style.DARK);
		
		LocalDate fecha = LocalDate.now();
        lblFecha.setText((dtf2.format(fecha)));
        
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {        
        	LocalTime currentTime = LocalTime.now();
        	lblHora.setText(currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
        }),	new KeyFrame(Duration.seconds(1))
        		);
        
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
		
//		imgConfiguracion.setImage(new Image("/images/ic_settings_black_36dp.png"));
		imgConfiguracion.setImage(new Image("/resources/images/ic_settings_black_36dp.png"));
		imgConfiguracion.setSmooth(true);
		imgConfiguracion.setPreserveRatio(true);

		validator.setMessage(MSG);

		tgTipo.selectedToggleProperty().addListener(e -> {
			if (rbProduccion.isSelected()) {
				modoProduccion = true;
			} else {
				modoProduccion = false;
			}
		});

		tgImprimir.selectedToggleProperty().addListener(e -> {
			if (rbTickets.isSelected()) {
				modoFactura = false;
			} else {
				modoFactura = true;
			}
		});
		
		txtRazonSocial.getValidators().add(validator);

		txtRazonSocial.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtRazonSocial.validate();
			}
		});

		txtRazonSocial.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});

		txtNombreFantasia.getValidators().add(validator);
		
		txtNombreFantasia.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtNombreFantasia.validate();
			}
		});
		
		txtNombreFantasia.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});
		
		txtDomicilioComercial.getValidators().add(validator);
		
		txtDomicilioComercial.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtDomicilioComercial.validate();
			}
		});
		
		txtDomicilioComercial.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});
		
		txtLocalidad.getValidators().add(validator);

		txtLocalidad.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtLocalidad.validate();
			}
		});

		txtLocalidad.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});
		
		cmbProvincia.setItems(listaProvincias);

		cmbProvincia.getValidators().add(validator);

		cmbProvincia.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				cmbProvincia.validate();
			}
		});
		
		txtCodigoPostal.getValidators().add(validator);

		txtCodigoPostal.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtCodigoPostal.validate();
			}
		});

		txtCodigoPostal.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});

		cmbSituacionFiscal.setItems(listaCondicionIVA);

		cmbSituacionFiscal.getValidators().add(validator);

		cmbSituacionFiscal.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				cmbSituacionFiscal.validate();
			}
		});

		cmbSituacionFiscal.valueProperty().addListener((obs, oldItem, newItem) -> {
			if (!(newItem == null)) {
				Integer condicionIva;
				
				switch (newItem) {
				case "IVA Responsable Inscripto":
					condicionIva = 1;
					break;
				case "IVA Responsable No Inscripto":
					condicionIva = 2;
					break;
				case "IVA No Responsable":
					condicionIva = 4;
					break;
				case "IVA Sujeto Exento":
					condicionIva = 5;
					break;
				case "Consumidor Final":
					condicionIva = 6;
					break;
				case "Responsable Monotributo":
					condicionIva = 7;
					break;
				case "Sujeto No Categorizado":
					condicionIva = 8;
					break;
				case "Proveedor del Exterior":
					condicionIva = 9;
					break;
				case "IVA Liberado - Ley 19.640":
					condicionIva = 10;
					break;
				case "IVA Responsable Inscripto – Agente de Percepción":
					condicionIva = 11;
					break;
				case "Pequeño Contribuyente Eventual":
					condicionIva = 12;
					break;
				case "Monotributista Social":
					condicionIva = 13;
					break;
				case "Pequeño Contribuyente Eventual Social":
					condicionIva = 14;
					break;
				default:
					condicionIva = 6;
				}
				modifiedProperty.set(true);
			}
		});

		txtDomicilioFiscal.getValidators().add(validator);

		txtDomicilioFiscal.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtDomicilioFiscal.validate();
			}
		});

		txtDomicilioFiscal.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});

		txtPuntoVenta.getValidators().add(validator);

		txtPuntoVenta.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtPuntoVenta.validate();
			}
		});

		txtPuntoVenta.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});

		txtIIBB.getValidators().add(validator);

		txtIIBB.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtIIBB.validate();
			}
		});

		txtIIBB.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});

//		txtIIBB.textProperty().bind(txtCUIT.textProperty());
		
		txtCUIT.getValidators().add(validator);

		txtCUIT.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtCUIT.validate();
			}
		});

		txtCUIT.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});


		dpFechaInicio.getValidators().add(validator);

		dpFechaInicio.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				dpFechaInicio.validate();
			}
		});

		dpFechaInicio.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});

		txtDirectorioCP.getValidators().add(validator);

		txtDirectorioCP.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtDirectorioCP.validate();
			}
		});

		txtDirectorioCP.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});

		txtDirectorioClavePrivada.getValidators().add(validator);

		txtDirectorioClavePrivada.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtDirectorioClavePrivada.validate();
			}
		});

		txtDirectorioClavePrivada.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});

		txtDirectorioCH.getValidators().add(validator);

		txtDirectorioCH.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtDirectorioCH.validate();
			}
		});

		txtDirectorioCH.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});

		txtFacturasPDF.getValidators().add(validator);

		txtFacturasPDF.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtFacturasPDF.validate();
			}
		});

		txtFacturasPDF.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});

		Platform.runLater(() -> txtRazonSocial.requestFocus());

		btnActualizar.disableProperty()
				.bind((txtRazonSocial.textProperty().isEmpty()).or(txtNombreFantasia.textProperty().isEmpty())
						.or(txtDomicilioComercial.textProperty().isEmpty()).or(txtLocalidad.textProperty().isEmpty())
						.or(cmbProvincia.valueProperty().isNull()).or(txtCodigoPostal.textProperty().isEmpty())
						.or(cmbSituacionFiscal.valueProperty().isNull()).or(txtDomicilioFiscal.textProperty().isEmpty())
						.or(txtPuntoVenta.textProperty().isEmpty()).or(txtIIBB.textProperty().isEmpty())
						.or(txtCUIT.textProperty().isEmpty()).or(dpFechaInicio.valueProperty().isNull())
						.or(txtDirectorioCP.textProperty().isEmpty())
						.or(txtDirectorioClavePrivada.textProperty().isEmpty())
						.or(txtDirectorioCH.textProperty().isEmpty()).or(txtFacturasPDF.textProperty().isEmpty()));

		btnActualizar.setOnAction(event -> {
			if (isEmpty == false) {
				try {
					modificarConfiguracion(idconfiguracion);
				} catch (NumberFormatException | IOException | ParseException e1) {
					e1.printStackTrace();
				}
			} else {
				try {
					insertarConfiguracion();
				} catch (NumberFormatException | IOException | ParseException e1) {
					e1.printStackTrace();
				}
			}
			((Node) (event.getSource())).getScene().getWindow().hide(); // salgo de aquí
		});
		
		btnRecuperar.setOnAction(event -> {
			try {
				Future<ObservableList<Configuracion>> lista = ConfiguracionAccess.getConfiguracion();
				lista.onComplete(ar -> {
					if (ar.succeeded()) {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								if (!(lista.result().isEmpty())) {
									configuracionList = lista.result();
									isEmpty = false;
									idconfiguracion = configuracionList.get(0).getIdconfiguracion();
									txtRazonSocial.setText(configuracionList.get(0).getRazon_social());
									txtNombreFantasia.setText(configuracionList.get(0).getNombre_fantasia());
									txtEmail.setText(configuracionList.get(0).getEmail());
									txtDomicilioComercial.setText(configuracionList.get(0).getDomicilio_comercial());
									txtTelefonoFijo.setText(configuracionList.get(0).getTelefono_fijo());
									txtTelefonoMovil.setText(configuracionList.get(0).getTelefono_movil());
									txtLocalidad.setText(configuracionList.get(0).getLocalidad());
									cmbProvincia.setValue(configuracionList.get(0).getProvincia());
									txtCodigoPostal.setText(configuracionList.get(0).getCodigo_postal());
									cmbSituacionFiscal.setValue(configuracionList.get(0).getSituacion_fiscal());
									txtDomicilioFiscal.setText(configuracionList.get(0).getDomicilio_fiscal());
									txtPuntoVenta.setText(String.valueOf(configuracionList.get(0).getPunto_venta()));
									txtIIBB.setText(configuracionList.get(0).getIibb_convmultilateral());
									txtCUIT.setText(configuracionList.get(0).getCuit());
									dpFechaInicio.setValue(configuracionList.get(0).getFecha_iactividades());

									if (configuracionList.get(0).isModo_produccion()) {
										rbProduccion.setSelected(true);
									} else {
										rbHomologacion.setSelected(true);
									}

									if (configuracionList.get(0).isModo_factura()) {
										rbFacturas.setSelected(true);
									} else {
										rbTickets.setSelected(true);
									}
									
									txtFacturasPDF.setText(configuracionList.get(0).getPath_facturaspdf());
									txtDirectorioCP.setText(configuracionList.get(0).getPath_certproduccion());
									txtDirectorioCH.setText(configuracionList.get(0).getPath_certhomologacion());
									txtDirectorioClavePrivada.setText(configuracionList.get(0).getPath_claveprivada());
									txtImporteAfip.setText(String.valueOf(configuracionList.get(0).getImporte_afip()));
									btnActualizar.disableProperty().unbind();
								} else {
									isEmpty = true;
									new Configuracion();
									FlatAlert alert = new FlatAlert(Alert.AlertType.INFORMATION);
									alert.setHeaderText("Configuración inexistente");
									alert.setContentText("Configure los datos iniciales");
									jm.setScene(alert.getDialogPane().getScene());

									Optional<ButtonType> result = alert.showAndWait();

									if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
										txtRazonSocial.requestFocus();
									}
								}
							} // run
						}); // runnable
					}
				});
			} catch (IOException | ParseException e1) {
				e1.printStackTrace();
			}
		});

		btnCP.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("Certificados de Seguridad", "*.crt"),
					new FileChooser.ExtensionFilter("Claves Privadas", "*.key"));
			/**
			 * Esta es otra opción para recuperar el stage
			 * 
			 * Node source = (Node) e.getSource(); Window theStage =
			 * source.getScene().getWindow();
			 */
			File selectedFile = fileChooser.showOpenDialog(myStage);
			txtDirectorioCP.setText(String.valueOf(selectedFile));
		});

		btnCH.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("Certificados de Seguridad", "*.crt"),
					new FileChooser.ExtensionFilter("Claves Privadas", "*.key"));
			File selectedFile = fileChooser.showOpenDialog(myStage);
			txtDirectorioCH.setText(String.valueOf(selectedFile));
		});

		btnClavePrivada.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("Claves Privadas", "*.key"));
			File selectedFile = fileChooser.showOpenDialog(myStage);
			txtDirectorioClavePrivada.setText(String.valueOf(selectedFile));
		});

		btnFPDF.setOnAction(e -> {
			DirectoryChooser directoryChooser = new DirectoryChooser();
//			directoryChooser.setInitialDirectory(new File("src"));
			File selectedDirectory = directoryChooser.showDialog(myStage);
			txtFacturasPDF.setText(selectedDirectory.getAbsolutePath());
		});
	}

	public void setStage(Stage stage) {
		myStage = stage;
	}

	private void insertarConfiguracion()
			throws NumberFormatException, FileNotFoundException, IOException, ParseException {
		Future<Integer> handlerResult = ConfiguracionAccess.insert(txtRazonSocial.getText().trim(),
				txtNombreFantasia.getText().trim(), txtEmail.getText().trim(), txtDomicilioComercial.getText().trim(),
				txtTelefonoFijo.getText().trim(), txtTelefonoMovil.getText().trim(), txtLocalidad.getText().trim(),
				cmbProvincia.getValue(), txtCodigoPostal.getText().trim(), cmbSituacionFiscal.getValue(),
				txtDomicilioFiscal.getText().trim(), Integer.valueOf(txtPuntoVenta.getText().trim()),
				txtIIBB.getText().trim(), txtCUIT.getText().trim(), dpFechaInicio.getValue().format(dtf),
				modoProduccion, modoFactura, txtFacturasPDF.getText().trim(), txtDirectorioCP.getText().trim(),
				txtDirectorioCH.getText().trim(), txtDirectorioClavePrivada.getText().trim(),
				Double.valueOf(txtImporteAfip.getText().trim()));

		handlerResult.onComplete(ar -> {
			if (ar.succeeded()) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						System.out.println("Todo bien con la inserción");
					}
				});
			} else {
				System.out.println(ar.cause());
			}
		});
	}

	private void modificarConfiguracion(Integer idconfiguracion)
			throws NumberFormatException, FileNotFoundException, IOException, ParseException {
		if (idconfiguracion != null) {
			Future<Integer> handlerResult = ConfiguracionAccess.updateAll(idconfiguracion,
					txtRazonSocial.getText().trim(), txtNombreFantasia.getText().trim(), txtEmail.getText().trim(),
					txtDomicilioComercial.getText().trim(), txtTelefonoFijo.getText().trim(),
					txtTelefonoMovil.getText().trim(), txtLocalidad.getText().trim(), cmbProvincia.getValue(),
					txtCodigoPostal.getText().trim(), cmbSituacionFiscal.getValue(),
					txtDomicilioFiscal.getText().trim(), Integer.valueOf(txtPuntoVenta.getText().trim()),
					txtIIBB.getText().trim(), txtCUIT.getText().trim(), dpFechaInicio.getValue().format(dtf),
					modoProduccion, modoFactura, txtFacturasPDF.getText().trim(), txtDirectorioCP.getText().trim(),
					txtDirectorioCH.getText().trim(), txtDirectorioClavePrivada.getText().trim(),
					Double.valueOf(txtImporteAfip.getText().trim()));

			handlerResult.onComplete(ar -> {
				if (ar.succeeded()) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							System.out.println("Todo bien con la actualización");
						}
					});
				} else {
					System.out.println(ar.cause());
				}
			}); // handler
		} else {
			Alert alert2 = new Alert(AlertType.INFORMATION);
			alert2.setTitle("Error de SQL");
			alert2.setContentText("Seleccione una Configuración para modificarla");
			alert2.showAndWait();
		}
	}

}
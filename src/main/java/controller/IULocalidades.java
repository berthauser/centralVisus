package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import consumer.LocalidadAccess;
import io.vertx.core.Future;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Localidad;

public class IULocalidades {
	@FXML
	private Button btnRegistrar;
	@FXML
	private Button btnCancelar;
	@FXML
	private JFXTextField txtLocalidad;
	@FXML
	private JFXComboBox<String> cmbProvincias;
	@FXML
	private JFXTextField txtCodigoPostal;
	
	private Stage stage;
	private boolean btnRegistrarClicked = false;
	private Integer idLocalidad;
	private Localidad localidad;
	private static final String MSG = "Requerido";
	private RequiredFieldValidator validator = new RequiredFieldValidator();

	/**
	 * Propiedad boolean modifiedProperty verifica si el usuario cambio cualquiera
	 * de los atributos de texto del formulario. Reseteamos esa bandera cada vez que
	 * se selecciona algo en el tableview y la volvemos a usar en una expresión bind
	 * para controlar la propiedad disable del botón "Modificar".
	 */

	private final BooleanProperty modifiedProperty = new SimpleBooleanProperty(false);
	
	private ObservableList<String> listProvincias = FXCollections.observableArrayList("Buenos Aires", "Catamarca",
			"Chaco", "Chubut", "Córdoba", "Corrientes", "Entre Ríos", "Formosa", "Jujuy", "La Pampa", "La Rioja",
			"Mendoza", "Misiones", "Neuquén", "Río Negro", "Salta", "San Juan", "San Luis", "Santa Cruz", "Santa Fe",
			"Santiago del Estero", "Tierra del Fuego", "Tucumán");
	
	@FXML
	private void initialize() {

		Platform.runLater(new Runnable() {
			public void run() {
				txtLocalidad.requestFocus();
			}
		});
		
		validator.setMessage(MSG);
		
		txtLocalidad.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});

		txtLocalidad.getValidators().add(validator);

		txtLocalidad.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtLocalidad.validate();
			}
		});
		
		txtCodigoPostal.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});
		
		txtCodigoPostal.getValidators().add(validator);
		
		txtCodigoPostal.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtCodigoPostal.validate();
			}
		});
		
		cmbProvincias.getValidators().add(validator);

		cmbProvincias.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				cmbProvincias.validate();
			}
		});
		
		btnRegistrar.disableProperty()
				.bind(txtLocalidad.textProperty().isEmpty().or(txtCodigoPostal.textProperty().isEmpty()).or(cmbProvincias.valueProperty().isNull()));

		cmbProvincias.setItems(listProvincias);
		
		btnRegistrar.setOnAction(reg -> {
			Future<Integer> handlerResult = (localidad.getIdlocalidad() == 0)
					? LocalidadAccess.insert(txtLocalidad.getText(), Integer.valueOf(txtCodigoPostal.getText()), cmbProvincias.getValue())
					: LocalidadAccess.update(localidad.getIdlocalidad(), txtLocalidad.getText(), Integer.valueOf(txtCodigoPostal.getText()), cmbProvincias.getValue());
			handlerResult.onComplete(ar -> {
				if (ar.succeeded()) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							// if you change the UI, do it here !
							localidad.setNombre(txtLocalidad.getText());
							localidad.setCodigoPostal(Integer.valueOf(txtCodigoPostal.getText()));
							localidad.setProvincia(cmbProvincias.getValue());
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
		});

		btnCancelar.setOnAction(can -> {
			stage.close();
		});
	} // initialize

	/**
	 * Sets the stage of this dialog.
	 *
	 * @param dialogStage
	 */
	public void setDialogStage(Stage stage) {
		this.stage = stage;
	}

	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
		this.txtLocalidad.setText(localidad.getNombre());
		this.txtCodigoPostal.setText(String.valueOf(localidad.getCodigoPostal()));
		this.cmbProvincias.setValue(localidad.getProvincia());
	}

	/**
	 * Returns true if the user clicked OK, false otherwise.
	 *
	 * @return
	 */
	public boolean isbtnRegistrarClicked() {
		return btnRegistrarClicked;
	}
	
}

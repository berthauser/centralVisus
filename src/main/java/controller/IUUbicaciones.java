package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import consumer.UbicacionAccess;
import io.vertx.core.Future;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Deposito;
import model.Ubicacion;

public class IUUbicaciones {
	@FXML
	private JFXTextField txtDescripcion;
	@FXML
	private JFXTextField txtNombre;
	@FXML
	private JFXComboBox<Deposito> cmbDeposito;
	@FXML
	private JFXTextField txtDeposito;
	@FXML
	private JFXTextField txtFilas;
	@FXML
	private JFXTextField txtColumnas;
	@FXML
	private Button btnRegistrar;
	@FXML
	private Button btnCancelar;
	
	private Stage stage;
	private boolean btnRegistrarClicked = false;
	private Integer idDeposito;
	private Ubicacion ubicacion;
	
	private static final String MSG = "Requerido";
	private RequiredFieldValidator validator = new RequiredFieldValidator();
	
	/**
	 * Propiedad boolean modifiedProperty verifica si el usuario cambio cualquiera
	 * de los atributos de texto del formulario. Reseteamos esa bandera cada vez que
	 * se selecciona algo en el tableview y la volvemos a usar en una expresión bind
	 * para controlar la propiedad disable del botón "Modificar".
	 */

	private final BooleanProperty modifiedProperty = new SimpleBooleanProperty(false);
	
	ObservableList<Deposito> depositosData = FXCollections.observableArrayList();
	
	@FXML
	private void initialize() {

		Platform.runLater(new Runnable() {
			public void run() {
				txtDescripcion.requestFocus();
			}
		});

		validator.setMessage(MSG);

		txtDescripcion.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});
		
		txtDescripcion.getValidators().add(validator);
		
		txtDescripcion.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtDescripcion.validate();
			}
		});
		
		txtDeposito.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});

		txtDeposito.getValidators().add(validator);

		txtDeposito.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtDeposito.validate();
			}
		});
		
		cmbDeposito.getValidators().add(validator);

		cmbDeposito.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				cmbDeposito.validate();
			}
		});
		
		txtNombre.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});
		
		txtNombre.getValidators().add(validator);
		
		txtNombre.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtNombre.validate();
			}
		});
		
		txtFilas.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});
		
		txtFilas.getValidators().add(validator);
		
		txtFilas.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtFilas.validate();
			}
		});
		
		txtColumnas.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});
		
		txtColumnas.getValidators().add(validator);
		
		txtColumnas.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtColumnas.validate();
			}
		});

		btnRegistrar.disableProperty()
				.bind(txtDescripcion.textProperty().isEmpty().or(txtNombre.textProperty().isEmpty())
						.or(cmbDeposito.itemsProperty().isNull().or(txtNombre.textProperty().isEmpty()
								.or(txtFilas.textProperty().isEmpty().or(txtColumnas.textProperty().isEmpty())))));

		UbicacionAccess.getDepositos(depositosData);
		cmbDeposito.setItems(depositosData);
		
		cmbDeposito.setOnAction(e -> {
			valueChanged(cmbDeposito);
		});
		
		btnRegistrar.setOnAction(reg -> {
			Future<Integer> handlerResult = (ubicacion.getIdubicacion() == 0)
					? UbicacionAccess.insert(txtDescripcion.getText(), txtNombre.getText(), idDeposito,
							Integer.parseInt(txtFilas.getText()), Integer.parseInt(txtColumnas.getText()))
					: UbicacionAccess.update(ubicacion.getIdubicacion(), txtDescripcion.getText(), txtNombre.getText(),
							idDeposito, Integer.parseInt(txtFilas.getText()), Integer.parseInt(txtColumnas.getText()));
			handlerResult.onComplete(ar -> {
				if (ar.succeeded()) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							// if you change the UI, do it here !
							ubicacion.setDescripcion(txtDescripcion.getText());
							ubicacion.setNumero(txtNombre.getText());
							ubicacion.setIddeposito(idDeposito);
							ubicacion.setTot_filas(Integer.parseInt(txtFilas.getText()));
							ubicacion.setTot_columnas(Integer.parseInt(txtColumnas.getText()));
							ubicacion.setDeposito(txtDeposito.getText());
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

	public void valueChanged(JFXComboBox<Deposito> list) {
		Deposito deposito = list.getValue();
		idDeposito = deposito.getIddeposito();
		txtDeposito.setText(deposito.getDescripcion());
	}
	
	/**
	 * Sets the stage of this dialog.
	 *
	 * @param dialogStage
	 */
	public void setDialogStage(Stage stage) {
		this.stage = stage;
	}

	public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
		this.txtDescripcion.setText(ubicacion.getDescripcion());
		this.txtNombre.setText(ubicacion.getNumero());
		this.txtDeposito.setText(ubicacion.getDeposito());
		this.txtFilas.setText(String.valueOf((ubicacion.getTot_filas())));
		this.txtColumnas.setText(String.valueOf((ubicacion.getTot_columnas())));
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
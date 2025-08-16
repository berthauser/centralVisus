package controller;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import consumer.UnidadAccess;
import io.vertx.core.Future;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Unidad;

public class IUUnidades {
	@FXML
	private Button btnRegistrar;
	@FXML
	private Button btnCancelar;
	@FXML
	private JFXTextField txtUnidades;
	@FXML
	private JFXTextField txtAbreviatura;

	private Stage stage;
	private boolean btnRegistrarClicked = false;
	
	private Unidad unidad;
	
	private static final String MSG = "Requerido";
	private RequiredFieldValidator validator = new RequiredFieldValidator();
	
	/**
	 * Propiedad boolean modifiedProperty verifica si el usuario cambio cualquiera
	 * de los atributos de texto del formulario. Reseteamos esa bandera cada vez que
	 * se selecciona algo en el tableview y la volvemos a usar en una expresión bind
	 * para controlar la propiedad disable del botón "Modificar".
	 */

	private final BooleanProperty modifiedProperty = new SimpleBooleanProperty(false);
	
	@FXML
	private void initialize() {
		
		Platform.runLater(new Runnable() {
			public void run() {
				txtUnidades.requestFocus();
			}
		});

		validator.setMessage(MSG);

		txtUnidades.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});

		txtUnidades.getValidators().add(validator);

		txtUnidades.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtUnidades.validate();
			}
		});
		
		txtAbreviatura.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});
		
		txtAbreviatura.getValidators().add(validator);
		
		txtAbreviatura.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtAbreviatura.validate();
			}
		});

		btnRegistrar.disableProperty().bind(txtUnidades.textProperty().isEmpty()
				.or(txtAbreviatura.textProperty().isEmpty()));

		btnRegistrar.setOnAction(reg -> {
			Future<Integer> handlerResult = (unidad.getIdunidad() == 0)
					? UnidadAccess.insert(txtUnidades.getText(), txtAbreviatura.getText())
					: UnidadAccess.update(unidad.getIdunidad(), txtUnidades.getText(), txtAbreviatura.getText());
			handlerResult.onComplete(ar -> {
				if (ar.succeeded()) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							// if you change the UI, do it here !
							unidad.setNombre(txtUnidades.getText());
							unidad.setAbreviatura(txtAbreviatura.getText());
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

	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
		this.txtUnidades.setText(unidad.getNombre());
		this.txtAbreviatura.setText(unidad.getAbreviatura());
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
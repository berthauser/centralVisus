package controller;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import consumer.RubroAccess;
import io.vertx.core.Future;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Rubro;

public class IURubros {
	
	@FXML
	private Button btnRegistrar;
	@FXML
	private Button btnCancelar;
	@FXML
	private JFXTextField txtRubro;
	
	private Stage stage;
	private boolean btnRegistrarClicked = false;
	
	private Rubro rubro;
	
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
				txtRubro.requestFocus();
			}
		});

		validator.setMessage(MSG);

		txtRubro.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});

		txtRubro.getValidators().add(validator);

		txtRubro.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtRubro.validate();
			}
		});
		
		btnRegistrar.disableProperty().bind(txtRubro.textProperty().isEmpty());

		btnRegistrar.setOnAction(reg -> {
			Future<Integer> handlerResult = (rubro.getIdrubro() == 0) ? RubroAccess.insertRubro(txtRubro.getText())
					: RubroAccess.updateRubro(rubro.getIdrubro(), txtRubro.getText());
			handlerResult.onComplete(ar -> {
				if (ar.succeeded()) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							// if you change the UI, do it here !
							rubro.setDescripcion(txtRubro.getText());
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

	public void setRubro(Rubro rubro) {
		this.rubro = rubro;
		this.txtRubro.setText(rubro.getDescripcion());
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
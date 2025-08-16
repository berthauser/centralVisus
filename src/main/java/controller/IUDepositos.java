package controller;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import consumer.DepositoAccess;
import io.vertx.core.Future;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Deposito;

public class IUDepositos {
	@FXML
    private Button btnRegistrar;
    @FXML
    private Button btnCancelar;
    @FXML
    private JFXTextField txtDepositos;
    
    private Stage stage;
    private Deposito deposito;
	private boolean btnRegistrarClicked = false;
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
				txtDepositos.requestFocus();
			}
		});
		
		validator.setMessage(MSG);

		txtDepositos.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});
		
		txtDepositos.getValidators().add(validator);

		txtDepositos.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtDepositos.validate();
			}
		});
		
		btnRegistrar.disableProperty().bind(txtDepositos.textProperty().isEmpty());

		btnRegistrar.setOnAction(reg -> {
			Future<Integer> handlerResult = (deposito.getIddeposito() == 0)
					? DepositoAccess.insertDeposito(txtDepositos.getText())
					: DepositoAccess.updateDeposito(deposito.getIddeposito(), txtDepositos.getText());
			handlerResult.onComplete(ar -> {
				if (ar.succeeded()) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							// if you change the UI, do it here !
							deposito.setDescripcion(txtDepositos.getText());
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

	public void setDeposito(Deposito deposito) {
		this.deposito = deposito;
		this.txtDepositos.setText(deposito.getDescripcion());
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

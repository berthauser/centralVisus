package controller;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import consumer.BancoAccess;
import io.vertx.core.Future;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Banco;

public class IUBancos {
	
    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnCancelar;
    @FXML
    private JFXTextField txtCodigo;
    @FXML
    private JFXTextField txtNombre;
    
    private Stage stage;
	
    private boolean btnRegistrarClicked = false;
	
	private Banco banco;
	
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
				txtNombre.requestFocus();
			}
		});

		validator.setMessage(MSG);

		txtNombre.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});

		txtNombre.getValidators().add(validator);

		txtNombre.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtNombre.validate();
			}
		});
		
		txtCodigo.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});
		
		txtCodigo.getValidators().add(validator);
		
		txtCodigo.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtNombre.validate();
			}
		});
		
		btnRegistrar.disableProperty().bind(txtNombre.textProperty().isEmpty().or(txtCodigo.textProperty().isEmpty()));

		btnRegistrar.setOnAction(reg -> {
			Future<Integer> handlerResult = (banco.getIdbanco() == 0) ? BancoAccess.insert(Integer.valueOf(txtCodigo.getText()), txtNombre.getText())
					: BancoAccess.update(banco.getIdbanco(), Integer.valueOf(txtCodigo.getText()), txtNombre.getText());
			handlerResult.onComplete(ar -> {
				if (ar.succeeded()) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							// if you change the UI, do it here !
							banco.setCodigo(Integer.valueOf(txtCodigo.getText()));
							banco.setNombre(txtNombre.getText());
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

	public void setBanco(Banco banco) {
		this.banco = banco;
		this.txtCodigo.setText(String.valueOf(banco.getCodigo()));
		this.txtNombre.setText(banco.getNombre());
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
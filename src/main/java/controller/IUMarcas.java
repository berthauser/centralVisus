package controller;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import consumer.MarcaAccess;
import io.vertx.core.Future;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Marca;

public class IUMarcas {
	@FXML
	private Label lblHeader;
	@FXML
	private Button btnRegistrar;
	@FXML
	private Button btnCancelar;
	@FXML
	private JFXTextField txtMarca;

	private Stage stage;
	private boolean btnRegistrarClicked = false;
	
	private Marca marca;
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
				txtMarca.requestFocus();
			}
		});
		
		validator.setMessage(MSG);
		
		txtMarca.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});

		txtMarca.getValidators().add(validator);

		txtMarca.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtMarca.validate();
			}
		});
		
		btnRegistrar.disableProperty().bind(txtMarca.textProperty().isEmpty());

		btnRegistrar.setOnAction(reg -> {
			Future<Integer> handlerResult = (marca.getIdmarca() == 0) ? MarcaAccess.insert(txtMarca.getText())
					: MarcaAccess.update(marca.getIdmarca(), txtMarca.getText());
			handlerResult.onComplete(ar -> {
				if (ar.succeeded()) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							// if you change the UI, do it here !
							marca.setDescripcion(txtMarca.getText());
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

	public void setMarca(Marca marca) {
		this.marca = marca;
		this.txtMarca.setText(marca.getDescripcion());
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
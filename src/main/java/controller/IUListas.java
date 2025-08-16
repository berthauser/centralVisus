package controller;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import consumer.ListaAccess;
import io.vertx.core.Future;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Lista;

public class IUListas {
	@FXML
    private Button btnRegistrar;
    @FXML
    private Button btnCancelar;
    @FXML
    private JFXTextField txtDescripcion;
    @FXML
    private JFXTextField txtMargen;

    private Stage stage;
    private Lista lista;
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
		
		txtMargen.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});
		
		txtMargen.getValidators().add(validator);
		
		txtMargen.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtMargen.validate();
			}
		});
		
		btnRegistrar.disableProperty().bind(txtDescripcion.textProperty().isEmpty());

		btnRegistrar.setOnAction(reg -> {
			Future<Integer> handlerResult = (lista.getIdlista() == 0)
					? ListaAccess.insertLista(txtDescripcion.getText(), Double.parseDouble(txtMargen.getText()))
					: ListaAccess.updateLista(lista.getIdlista(), txtDescripcion.getText(), Double.parseDouble(txtMargen.getText()));
			handlerResult.onComplete(ar -> {
				if (ar.succeeded()) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							// if you change the UI, do it here !
							lista.setDescripcion(txtDescripcion.getText());
							lista.setMargen(Double.parseDouble(txtMargen.getText()));
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

	public void setLista (Lista lista) {
		this.lista = lista;
		this.txtDescripcion.setText(lista.getDescripcion());
		this.txtMargen.setText(String.valueOf(lista.getMargen()));
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

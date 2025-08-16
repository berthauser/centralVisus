package controller;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.CompoundValidationDecoration;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;
import org.controlsfx.validation.decoration.StyleClassValidationDecoration;

import com.jfoenix.controls.JFXTextField;

import consumer.RubroAccess;
import io.vertx.core.Future;
import javafx.application.Platform;
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
	
	private final ValidationSupport validationSupport = new ValidationSupport();
	
	@FXML
	private void initialize() {
		
		Platform.runLater(new Runnable() {
			public void run() {
				txtRubro.requestFocus();
			}
		});

		validationSupport.setValidationDecorator(new StyleClassValidationDecoration());
		validationSupport.setValidationDecorator(new CompoundValidationDecoration(
				new GraphicValidationDecoration(), 
				new StyleClassValidationDecoration()));

		validationSupport.registerValidator(txtRubro, false, Validator.createEmptyValidator(
				"Se requiere un valor",
				Severity.ERROR));

		//		btnRegistrar.disableProperty().bind(Bindings.createBooleanBinding(() -> 
		//	    txtRubro.getText().trim().isEmpty()));

		//		btnRegistrar.disableProperty().bind(Bindings.isEmpty(txtRubro.textProperty()));

		//		btnRegistrar.disableProperty().bind(
		//			    txtRubro.textProperty().isEmpty() 
		//			    .or(textField2.textProperty().isEmpty())
		//			    .or(textField3.textProperty().isEmpty())
		//			);

		btnRegistrar.disableProperty().bind(txtRubro.textProperty().isEmpty());

		btnRegistrar.setOnAction(reg -> {
			if (!validationSupport.isInvalid()) {
				Future<Integer> handlerResult = (rubro.getIdrubro() == 0)?
						RubroAccess.insertRubro(txtRubro.getText()) :
							RubroAccess.updateRubro(rubro.getIdrubro(),txtRubro.getText());
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
			}
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
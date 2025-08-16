package controller;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.CompoundValidationDecoration;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;
import org.controlsfx.validation.decoration.StyleClassValidationDecoration;

import com.jfoenix.controls.JFXTextField;

import consumer.DepositoAccess;
import io.vertx.core.Future;
import javafx.application.Platform;
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
	private boolean btnRegistrarClicked = false;
	
	private Deposito deposito;
	
	private final ValidationSupport validationSupport = new ValidationSupport();
	
	@FXML
	private void initialize() {
		
		Platform.runLater(new Runnable() {
			public void run() {
				txtDepositos.requestFocus();
			}
		});

		validationSupport.setValidationDecorator(new StyleClassValidationDecoration());
		validationSupport.setValidationDecorator(new CompoundValidationDecoration(
				new GraphicValidationDecoration(), 
				new StyleClassValidationDecoration()));

		validationSupport.registerValidator(txtDepositos, false, Validator.createEmptyValidator(
				"Se requiere un valor",
				Severity.ERROR));

		//		btnRegistrar.disableProperty().bind(Bindings.createBooleanBinding(() -> 
		//	    txtDepositos.getText().trim().isEmpty()));

		//		btnRegistrar.disableProperty().bind(Bindings.isEmpty(txtDepositos.textProperty()));

		//		btnRegistrar.disableProperty().bind(
		//			    txtDepositos.textProperty().isEmpty() 
		//			    .or(textField2.textProperty().isEmpty())
		//			    .or(textField3.textProperty().isEmpty())
		//			);

		btnRegistrar.disableProperty().bind(txtDepositos.textProperty().isEmpty());

		btnRegistrar.setOnAction(reg -> {
			if (!validationSupport.isInvalid()) {
				Future<Integer> handlerResult = (deposito.getIddeposito() == 0)?
						DepositoAccess.insertDeposito(txtDepositos.getText()) :
							DepositoAccess.updateDeposito(deposito.getIddeposito(), txtDepositos.getText());
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

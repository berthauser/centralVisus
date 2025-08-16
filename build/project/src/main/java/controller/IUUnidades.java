package controller;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.CompoundValidationDecoration;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;
import org.controlsfx.validation.decoration.StyleClassValidationDecoration;

import com.jfoenix.controls.JFXTextField;

import consumer.UnidadAccess;
import io.vertx.core.Future;
import javafx.application.Platform;
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
	
    private final ValidationSupport validationSupport = new ValidationSupport();
	
	@FXML
	private void initialize() {
		
		Platform.runLater(new Runnable() {
			public void run() {
				txtUnidades.requestFocus();
			}
		});

		validationSupport.setValidationDecorator(new StyleClassValidationDecoration());
		validationSupport.setValidationDecorator(new CompoundValidationDecoration(
				new GraphicValidationDecoration(), 
				new StyleClassValidationDecoration()));

		validationSupport.registerValidator(txtUnidades, false, Validator.createEmptyValidator(
				"Se requiere un valor",	Severity.ERROR));
		
		validationSupport.registerValidator(txtAbreviatura, false, Validator.createEmptyValidator(
				"Se requiere un valor",	Severity.ERROR));

		btnRegistrar.disableProperty().bind(txtUnidades.textProperty().isEmpty());
		btnRegistrar.disableProperty().bind(txtAbreviatura.textProperty().isEmpty());

		btnRegistrar.setOnAction(reg -> {
			if (!validationSupport.isInvalid()) {
				Future<Integer> handlerResult = (unidad.getIdunidad() == 0)?
						UnidadAccess.insert(txtUnidades.getText(), txtAbreviatura.getText()) :
							UnidadAccess.update(unidad.getIdunidad(),txtUnidades.getText(), txtAbreviatura.getText());
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

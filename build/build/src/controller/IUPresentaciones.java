package controller;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.CompoundValidationDecoration;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;
import org.controlsfx.validation.decoration.StyleClassValidationDecoration;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import consumer.PresentacionAccess;
import io.vertx.core.Future;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Presentacion;

public class IUPresentaciones {
	@FXML
	private Label lblHeader;
	@FXML
	private Button btnRegistrar;
	@FXML
	private Button btnCancelar;
	@FXML
	private JFXTextField txtContenedor;
	@FXML
	private JFXTextField txtUnidades;
	@FXML
	private JFXTextField txtValorUnidades;
	@FXML
	private JFXRadioButton rbExistencia;
	@FXML
	private JFXRadioButton rbCantidad;
	@FXML
	private ToggleGroup tgTipoPosicion;

	private Stage stage;
	private boolean btnRegistrarClicked = false;
	private String decremento = "Cantidad";
	
	private Presentacion presentacion;
	
    private final ValidationSupport validationSupport = new ValidationSupport();
	
	@FXML
	private void initialize() {
		
		Platform.runLater(new Runnable() {
			public void run() {
				txtContenedor.requestFocus();
			}
		});

		validationSupport.setValidationDecorator(new StyleClassValidationDecoration());
		validationSupport.setValidationDecorator(new CompoundValidationDecoration(
				new GraphicValidationDecoration(), 
				new StyleClassValidationDecoration()));

		validationSupport.registerValidator(txtContenedor, false, Validator.createEmptyValidator(
				"Se requiere un valor",	Severity.ERROR));
		
		validationSupport.registerValidator(txtUnidades, false, Validator.createEmptyValidator(
				"Se requiere un valor",	Severity.ERROR));
		
		validationSupport.registerValidator(txtValorUnidades, false, Validator.createEmptyValidator(
				"Se requiere un valor",	Severity.ERROR));

		btnRegistrar.disableProperty().bind(txtContenedor.textProperty().isEmpty());
		btnRegistrar.disableProperty().bind(txtUnidades.textProperty().isEmpty());
		btnRegistrar.disableProperty().bind(txtValorUnidades.textProperty().isEmpty());

		rbCantidad.setOnAction(event -> {
			if (rbCantidad.isSelected()) {
				rbExistencia.setSelected(false);
				decremento = rbCantidad.getText();
			}
		});
		
		rbExistencia.setOnAction(event -> {
			if (rbExistencia.isSelected()) {
				rbCantidad.setSelected(false);
				decremento = rbExistencia.getText();
			}
		});
		
		btnRegistrar.setOnAction(reg -> {
			if (!validationSupport.isInvalid()) {
				System.out.println(txtContenedor.getText() + ' ' + txtUnidades.getText() + ' ' + txtValorUnidades.getText() + ' ' + decremento);
				Future<Integer> handlerResult = (presentacion.getIdpresentacion() == 0)?
						PresentacionAccess.insert(txtContenedor.getText(), Double.parseDouble(txtUnidades.getText()), Double.parseDouble(txtValorUnidades.getText()), decremento) :
						PresentacionAccess.update(presentacion.getIdpresentacion(),txtContenedor.getText(), Double.parseDouble(txtUnidades.getText()), Double.parseDouble(txtValorUnidades.getText()), decremento);
						handlerResult.onComplete(ar -> {
							if (ar.succeeded()) {
								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										// if you change the UI, do it here !
										presentacion.setContenedor(txtContenedor.getText());
										presentacion.setUnidades(Double.parseDouble(txtUnidades.getText()));
										presentacion.setValor_umedida(Double.parseDouble(txtValorUnidades.getText()));
										if (rbExistencia.isSelected()) {
											presentacion.setTipo_decremento("Existencia");
										} else {
											presentacion.setTipo_decremento("Cantidad");	
										}
										System.out.println(presentacion.getTipo_decremento());
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

	public void setPresentacion(Presentacion presentacion) {
		this.presentacion = presentacion;
		this.txtContenedor.setText(presentacion.getContenedor());
		this.txtUnidades.setText(String.valueOf(presentacion.getUnidades()));
		this.txtValorUnidades.setText(String.valueOf(presentacion.getValor_umedida()));
		if (presentacion.getTipo_decremento() == null) {
			rbExistencia.setSelected(true);			
		} else {
			switch (presentacion.getTipo_decremento()) {
			case "Cantidad":
				rbCantidad.setSelected(true);
				rbExistencia.setSelected(false);
				break;
			case "Existencia":
				rbExistencia.setSelected(true);
				rbCantidad.setSelected(false);
				break;
			} // switch
		}
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
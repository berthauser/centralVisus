package controller;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.CompoundValidationDecoration;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;
import org.controlsfx.validation.decoration.StyleClassValidationDecoration;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import consumer.UbicacionAccess;
import io.vertx.core.Future;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Deposito;
import model.Ubicacion;

public class IUUbicaciones {
	@FXML
	private JFXTextField txtDescripcion;
	@FXML
	private JFXTextField txtNombre;
	@FXML
	private JFXComboBox<Deposito> cmbDeposito;
	@FXML
	private JFXTextField txtDeposito;
	@FXML
	private JFXTextField txtFilas;
	@FXML
	private JFXTextField txtColumnas;
	@FXML
	private Button btnRegistrar;
	@FXML
	private Button btnCancelar;
	
	private Stage stage;
	private boolean btnRegistrarClicked = false;
	private Integer idDeposito;
	private Ubicacion ubicacion;
	
	private final ValidationSupport validationSupport = new ValidationSupport();
	
	ObservableList<Deposito> depositosData = FXCollections.observableArrayList();
	
	@FXML
	private void initialize() {

		Platform.runLater(new Runnable() {
			public void run() {
				txtDescripcion.requestFocus();
			}
		});

		validationSupport.setValidationDecorator(new StyleClassValidationDecoration());
		validationSupport.setValidationDecorator(new CompoundValidationDecoration(
				new GraphicValidationDecoration(), 
				new StyleClassValidationDecoration()));

		validationSupport.registerValidator(txtDescripcion, false, Validator.createEmptyValidator(
				"Se requiere un valor",	Severity.ERROR));

		validationSupport.registerValidator(txtNombre, false, Validator.createEmptyValidator(
				"Se requiere un valor",	Severity.ERROR));

		validationSupport.registerValidator(cmbDeposito, false, Validator.createEmptyValidator(
				"Se requiere un valor",	Severity.ERROR));

		btnRegistrar.disableProperty().bind(txtDescripcion.textProperty().isEmpty());
		btnRegistrar.disableProperty().bind(txtNombre.textProperty().isEmpty());
		btnRegistrar.disableProperty().bind(cmbDeposito.itemsProperty().isNull());

		UbicacionAccess.getDepositos(depositosData);
		cmbDeposito.setItems(depositosData);
		
		cmbDeposito.setOnAction(e -> {
			valueChanged(cmbDeposito);
		});
		
		btnRegistrar.setOnAction(reg -> {
			if (!validationSupport.isInvalid()) {
				Future<Integer> handlerResult = (ubicacion.getIdubicacion() == 0)?
						UbicacionAccess.insert(txtDescripcion.getText(), txtNombre.getText(), idDeposito, Integer.parseInt(txtFilas.getText()), Integer.parseInt(txtColumnas.getText())) :
							UbicacionAccess.update(ubicacion.getIdubicacion(), txtDescripcion.getText(), txtNombre.getText(), idDeposito, Integer.parseInt(txtFilas.getText()), Integer.parseInt(txtColumnas.getText()));
						handlerResult.onComplete(ar -> {
							if (ar.succeeded()) {
								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										// if you change the UI, do it here !
										ubicacion.setDescripcion(txtDescripcion.getText());
										ubicacion.setNumero(txtNombre.getText());
										ubicacion.setIddeposito(idDeposito);
										ubicacion.setTot_filas(Integer.parseInt(txtFilas.getText()));
										ubicacion.setTot_columnas(Integer.parseInt(txtColumnas.getText()));
										ubicacion.setDeposito(txtDeposito.getText());
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

	public void valueChanged(JFXComboBox<Deposito> list) {
		Deposito deposito = list.getValue();
		idDeposito = deposito.getIddeposito();
		txtDeposito.setText(deposito.getDescripcion());
	}
	
	/**
	 * Sets the stage of this dialog.
	 *
	 * @param dialogStage
	 */
	public void setDialogStage(Stage stage) {
		this.stage = stage;
	}

	public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
		this.txtDescripcion.setText(ubicacion.getDescripcion());
		this.txtNombre.setText(ubicacion.getNumero());
		this.txtDeposito.setText(ubicacion.getDeposito());
		this.txtFilas.setText(String.valueOf((ubicacion.getTot_filas())));
		this.txtColumnas.setText(String.valueOf((ubicacion.getTot_columnas())));
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
package controller;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.CompoundValidationDecoration;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;
import org.controlsfx.validation.decoration.StyleClassValidationDecoration;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import consumer.LineaAccess;
import io.vertx.core.Future;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Linea;
import model.Rubro;

public class IULineas {
	@FXML
    private Label lblHeader;
    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnCancelar;
    @FXML
    private JFXTextField txtDescripcion;
    @FXML
    private JFXComboBox<Rubro> cmbRubros;
    @FXML
    private JFXTextField txtRubro;

    private Stage stage;
	private boolean btnRegistrarClicked = false;
	private Integer idRubro;
	private Linea linea;
	
	private final ValidationSupport validationSupport = new ValidationSupport();
	
	ObservableList<Rubro> rubrosData = FXCollections.observableArrayList();
	
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

		validationSupport.registerValidator(cmbRubros, false, Validator.createEmptyValidator(
				"Se requiere un valor",	Severity.ERROR));

		btnRegistrar.disableProperty().bind(txtDescripcion.textProperty().isEmpty());
		btnRegistrar.disableProperty().bind(cmbRubros.itemsProperty().isNull());

		LineaAccess.getRubros(rubrosData);
		cmbRubros.setItems(rubrosData);
		
		cmbRubros.setOnAction(e -> {
			valueChanged(cmbRubros);
//			Rubro rubro = cmbRubros.getSelectionModel().getSelectedItem();
//			idRubro = rubro.getIdrubro();
//			txtRubro.setText(rubro.getDescripcion());
		});
		
		btnRegistrar.setOnAction(reg -> {
			if (!validationSupport.isInvalid()) {
				Future<Integer> handlerResult = (linea.getIdlinea() == 0)?
						LineaAccess.insert(txtDescripcion.getText(), idRubro) :
							LineaAccess.update(linea.getIdlinea(), txtDescripcion.getText(), idRubro);
						handlerResult.onComplete(ar -> {
							if (ar.succeeded()) {
								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										// if you change the UI, do it here !
										linea.setDescripcion(txtDescripcion.getText());
										linea.setIdrubro(idRubro);
										linea.setRubro(txtRubro.getText());
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

	
	public void valueChanged(JFXComboBox<Rubro> list) {
		Rubro rubro = list.getValue();
		idRubro = rubro.getIdrubro();
		txtRubro.setText(rubro.getDescripcion());
	}
	
	/**
	 * Sets the stage of this dialog.
	 *
	 * @param dialogStage
	 */
	public void setDialogStage(Stage stage) {
		this.stage = stage;
	}

	public void setLinea(Linea linea) {
		this.linea = linea;
		this.txtDescripcion.setText(linea.getDescripcion());
		this.txtRubro.setText(linea.getRubro());
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
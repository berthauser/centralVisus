package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import consumer.LineaAccess;
import io.vertx.core.Future;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
	private static final String MSG = "Requerido";
	private RequiredFieldValidator validator = new RequiredFieldValidator();
	
	/**
	 * Propiedad boolean modifiedProperty verifica si el usuario cambio cualquiera
	 * de los atributos de texto del formulario. Reseteamos esa bandera cada vez que
	 * se selecciona algo en el tableview y la volvemos a usar en una expresión bind
	 * para controlar la propiedad disable del botón "Modificar".
	 */

	private final BooleanProperty modifiedProperty = new SimpleBooleanProperty(false);
	
	ObservableList<Rubro> rubrosData = FXCollections.observableArrayList();
	
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
		
		cmbRubros.getValidators().add(validator);

		cmbRubros.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				cmbRubros.validate();
			}
		});
		
		btnRegistrar.disableProperty()
				.bind(txtDescripcion.textProperty().isEmpty().or(cmbRubros.valueProperty().isNull()));

		LineaAccess.getRubros(rubrosData);
		cmbRubros.setItems(rubrosData);
		
		cmbRubros.setOnAction(e -> {
			valueChanged(cmbRubros);
//			Rubro rubro = cmbRubros.getSelectionModel().getSelectedItem();
//			idRubro = rubro.getIdrubro();
//			txtRubro.setText(rubro.getDescripcion());
		});
		
		btnRegistrar.setOnAction(reg -> {
			Future<Integer> handlerResult = (linea.getIdlinea() == 0)
					? LineaAccess.insert(txtDescripcion.getText(), idRubro)
					: LineaAccess.update(linea.getIdlinea(), txtDescripcion.getText(), idRubro);
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
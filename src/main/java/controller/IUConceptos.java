package controller;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import consumer.ConceptoAccess;
import model.Concepto;
import io.vertx.core.Future;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
/*
 * IUConceptos: Ins-Upd 
 * La misma clase para INS y UPD
 */
public class IUConceptos {
	
	@FXML
	private Label lblHeader;
	@FXML
	private JFXTextField txtDescripcion;
	@FXML
	private Button btnRegistrar;
	@FXML
	private Button btnCancelar;

	private Stage stage;

	private boolean btnRegistrarClicked = false;

	private Concepto concepto;

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

    	btnRegistrar.disableProperty().bind(txtDescripcion.textProperty().isEmpty());

		btnRegistrar.setOnAction(reg -> {
			Future<Integer> handlerResult = (concepto.getIdconcepto().get() == 0)
					? ConceptoAccess.insertConcepto(txtDescripcion.getText())
					: ConceptoAccess.updateConcepto(concepto.getIdconcepto().get(), txtDescripcion.getText());

			handlerResult.onComplete(ar -> {
				if (ar.succeeded()) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							// if you change the UI, do it here !
							concepto.setDescripcion(txtDescripcion.getText());
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

		/**
	     * Called when the user clicks cancel.
	     */
		btnCancelar.setOnAction(can -> {
			stage.close();
		});

	}

	/**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage stage) {
        this.stage = stage;
    }
	
    /**
     * Sets the person to be edited in the dialog.
     *
     * @param whisky
     */
    public void setConcepto(Concepto concepto) {
        this.concepto = concepto;
        this.txtDescripcion.setText(concepto.getDescripcion().getValue());
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
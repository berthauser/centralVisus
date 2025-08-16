package controller;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import consumer.PresentacionAccess;
import io.vertx.core.Future;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
				txtContenedor.requestFocus();
			}
		});

		validator.setMessage(MSG);
		
		txtContenedor.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});

		txtContenedor.getValidators().add(validator);

		txtContenedor.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtContenedor.validate();
			}
		});
		
		txtUnidades.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});
		
		txtUnidades.getValidators().add(validator);
		
		txtUnidades.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtUnidades.validate();
			}
		});
		
		txtValorUnidades.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});

		txtValorUnidades.getValidators().add(validator);

		txtValorUnidades.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtValorUnidades.validate();
			}
		});

		btnRegistrar.disableProperty().bind(txtContenedor.textProperty().isEmpty()
				.or(txtUnidades.textProperty().isEmpty().or(txtValorUnidades.textProperty().isEmpty())));

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
			System.out.println(txtContenedor.getText() + ' ' + txtUnidades.getText() + ' ' + txtValorUnidades.getText()
					+ ' ' + decremento);
			Future<Integer> handlerResult = (presentacion.getIdpresentacion() == 0)
					? PresentacionAccess.insert(txtContenedor.getText(), Double.parseDouble(txtUnidades.getText()),
							Double.parseDouble(txtValorUnidades.getText()), decremento)
					: PresentacionAccess.update(presentacion.getIdpresentacion(), txtContenedor.getText(),
							Double.parseDouble(txtUnidades.getText()), Double.parseDouble(txtValorUnidades.getText()),
							decremento);
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
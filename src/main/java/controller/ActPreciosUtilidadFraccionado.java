package controller;

import java.util.Optional;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import consumer.ActPreciosUtilidadAccess;
import io.vertx.core.Future;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import jfxtras.styles.jmetro.FlatAlert;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class ActPreciosUtilidadFraccionado {
	
	@FXML
	private Button btnActualizar;
	@FXML
	private Button btnCancelar;
	@FXML
	private JFXTextField txtUtilidadFraccionado;
	@FXML
	private Label lblTransaccion;	
	
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
	public void initialize() {
		
		validator.setMessage(MSG);
		
		txtUtilidadFraccionado.getValidators().add(validator);

		txtUtilidadFraccionado.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtUtilidadFraccionado.validate();
			}
		});

		txtUtilidadFraccionado.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});
		
		Platform.runLater(() -> txtUtilidadFraccionado.requestFocus());
		
		btnCancelar.setOnAction(can -> {
			((Node) (can.getSource())).getScene().getWindow().hide();
		});
		
		btnActualizar.setOnAction(e -> {
			JMetro jm = new JMetro(Style.DARK);
			FlatAlert alert = new FlatAlert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Confirme por favor!");
			alert.setContentText("¿Desea actualizar la Utilidad de todos los productos fraccionados?");
			jm.setScene(alert.getDialogPane().getScene());

			Optional<ButtonType> result = alert.showAndWait();

			if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
				Future<Integer> updateResult = ActPreciosUtilidadAccess.updateUtilidadFraccionado(Double.valueOf(txtUtilidadFraccionado.getText()));
				updateResult.onComplete(ar -> {
					if (ar.succeeded()) {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								lblTransaccion.setVisible(true);
							}
						});
					} else {
						System.out.println(ar.cause());
					}
				});
			}
		});
	}

}
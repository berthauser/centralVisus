package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import org.json.simple.parser.ParseException;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import consumer.ActPreciosUtilidadAccess;
import consumer.ArticuloAccess;
import io.vertx.core.Future;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import jfxtras.styles.jmetro.FlatAlert;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import model.Linea;

public class ActPreciosLinea {

	@FXML
	private JFXComboBox<Linea> cmbLineas;
	@FXML
	private JFXTextField txtUtilidad;
	@FXML
	private Button btnActualizar;
	@FXML
	private Button btnCancelar;
	@FXML
	private Label lblTransaccion;
	
	private Integer idLinea;
	private static final String MSG = "Requerido";
	private RequiredFieldValidator validator = new RequiredFieldValidator();
	private ObservableList<Linea> listLineas = FXCollections.observableArrayList();
	
	/**
	 * Propiedad boolean modifiedProperty verifica si el usuario cambio cualquiera
	 * de los atributos de texto del formulario. Reseteamos esa bandera cada vez que
	 * se selecciona algo en el tableview y la volvemos a usar en una expresión bind
	 * para controlar la propiedad disable del botón "Modificar".
	 */

	private final BooleanProperty modifiedProperty = new SimpleBooleanProperty(false);
	
	@FXML
	public void initialize() throws FileNotFoundException, IOException, ParseException {
		
		validator.setMessage(MSG);
		
		// Leo las líneas que están en el inventario
		ArticuloAccess.getLineas(listLineas);
		cmbLineas.setItems(listLineas);
		
		cmbLineas.getValidators().add(validator);
		cmbLineas.focusedProperty().addListener((o, oldVal, newVal) -> {
	        if (!newVal) {
	            cmbLineas.validate();
	        }
	    });

		cmbLineas.valueProperty().addListener((obs, oldItem, newItem) -> {
			if (!(newItem == null)) {
            	idLinea = newItem.getIdlinea();
            }
        });
		
		txtUtilidad.getValidators().add(validator);

		txtUtilidad.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal) {
				txtUtilidad.validate();
			}
		});

		txtUtilidad.setOnKeyReleased(e -> {
			modifiedProperty.set(true);
		});
		
		Platform.runLater(() -> cmbLineas.requestFocus());
		
		btnCancelar.setOnAction(can -> {
			((Node) (can.getSource())).getScene().getWindow().hide();
		});
		
		btnActualizar.setOnAction(e -> {
			JMetro jm = new JMetro(Style.DARK);
			FlatAlert alert = new FlatAlert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Confirme por favor!");
			alert.setContentText("¿Desea actualizar la Utilidad de la Línea seleccionada?");
			jm.setScene(alert.getDialogPane().getScene());

			Optional<ButtonType> result = alert.showAndWait();

			if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
				Future<Integer> updateResult = ActPreciosUtilidadAccess.updateUtilidadPorLinea(idLinea, Double.valueOf(txtUtilidad.getText()));
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

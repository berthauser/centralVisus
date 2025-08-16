package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Nombre para los Depasitos de Mercaderaa
 * 
 * @author ErnestoAndresZapataI
 *
 */
public class Deposito {
	private IntegerProperty iddeposito;
	private StringProperty descripcion;
	
	public Deposito() {
		iddeposito = new SimpleIntegerProperty();
		descripcion = new SimpleStringProperty();
	}

	public Deposito(Integer iddeposito, String descripcion) {
		this.iddeposito = new SimpleIntegerProperty(iddeposito);
		this.descripcion = new SimpleStringProperty(descripcion);
	}

	public IntegerProperty iddepositoProperty() {
		return this.iddeposito;
	}
	
	public int getIddeposito() {
		return this.iddepositoProperty().get();
	}
	
	public void setIddeposito(final int iddeposito) {
		this.iddepositoProperty().set(iddeposito);
	}
	
	public StringProperty descripcionProperty() {
		return this.descripcion;
	}
	
	public String getDescripcion() {
		return this.descripcionProperty().get();
	}

	public void setDescripcion(final String descripcion) {
		this.descripcionProperty().set(descripcion);
	}

	@Override
	public String toString() {
		return  descripcionProperty().get();
	}

}
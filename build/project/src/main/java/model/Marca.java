package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Marca {
	private IntegerProperty idmarca;
	private StringProperty descripcion;
	
	public Marca() {
		idmarca = new SimpleIntegerProperty();
		descripcion = new SimpleStringProperty();
	}

	public Marca(Integer idmarca, String descripcion) {
		this.idmarca = new  SimpleIntegerProperty(idmarca);
		this.descripcion = new SimpleStringProperty(descripcion);
	}

	public IntegerProperty idmarcaProperty() {
		return this.idmarca;
	}
	
	public int getIdmarca() {
		return idmarcaProperty().get();
	}
	
	public void setIdmarca(final int idmarca) {
		this.idmarcaProperty().set(idmarca);
	}
	
	public StringProperty descripcionProperty() {
		return descripcion;
	}
	
	public String getDescripcion() {
		return descripcionProperty().get();
	}
	
	public void setDescripcion(final String descripcion) {
		descripcionProperty().set(descripcion);
	}

	@Override
	public String toString() {
		return descripcionProperty().get();
	}
	
}
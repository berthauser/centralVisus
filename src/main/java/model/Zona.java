package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Nombre para las Zonas de venta
 * 
 * @author ErnestoAndresZapataI
 *
 */
public class Zona {
	private IntegerProperty idzona;
	private StringProperty descripcion;
	
	public Zona() {
		idzona= new SimpleIntegerProperty();
		descripcion = new SimpleStringProperty();
	}

	public Zona(Integer idzona, String descripcion) {
		this.idzona = new SimpleIntegerProperty(idzona);
		this.descripcion = new SimpleStringProperty(descripcion);
	}

	public IntegerProperty idzonaProperty() {
		return this.idzona;
	}
	
	public int getIdzona() {
		return this.idzonaProperty().get();
	}
	
	public void setIdzona(final int idzona) {
		this.idzonaProperty().set(idzona);
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
package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Los Rubros o Familias de productos 
 * son las categoraas en las que se 
 * clasifica el stock.
 * 
 * @author ErnestoAndresZapataI
 *
 */
public class Rubro {
	private IntegerProperty idrubro;
	private StringProperty descripcion;

	//Constructor sin argumentos 
	public Rubro() {
		idrubro = new SimpleIntegerProperty();
		descripcion = new SimpleStringProperty();
	}

	// Contructor para las propiedades JavaFX	
	public Rubro(Integer idrubro, String descripcion) {
		this.idrubro = new SimpleIntegerProperty(idrubro);
		this.descripcion = new SimpleStringProperty(descripcion);
	}

	// Getter y Setter para las propiedades JavaFX
	public IntegerProperty idrubroProperty() {
		return idrubro;
	}
	
	public int getIdrubro() {
		return idrubroProperty().get();
	}
	
	public void setIdrubro(final int idrubro) {
		idrubroProperty().set(idrubro);
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
	// END Getter y Setter para las propiedades JavaFX

	@Override
	public String toString() {
		return descripcionProperty().get();
	}

	
}
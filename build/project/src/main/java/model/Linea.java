package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Laneas de productos. Subcategoraa de Rubros.
 * @author ErnestoAndresZapataI
 *
 */

public class Linea {
	private IntegerProperty idlinea;
	private StringProperty descripcion;
	private IntegerProperty idrubro;
	// atributos auxiliares	
	private StringProperty rubro;	
	
	public Linea() {
		idlinea = new SimpleIntegerProperty();
		descripcion = new SimpleStringProperty();
		idrubro = new SimpleIntegerProperty();
		rubro = new SimpleStringProperty();
	}

	public Linea(Integer idlinea, String descripcion, String rubro) {
		this.idlinea = new SimpleIntegerProperty(idlinea);
		this.descripcion = new SimpleStringProperty(descripcion);
		this.rubro = new SimpleStringProperty(rubro);
	}

	public IntegerProperty idlineaProperty() {
		return this.idlinea;
	}
	
	public int getIdlinea() {
		return this.idlineaProperty().get();
	}
	
	public void setIdlinea(final int idlinea) {
		this.idlineaProperty().set(idlinea);
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
	
	public IntegerProperty idrubroProperty() {
		return this.idrubro;
	}
	
	public int getIdrubro() {
		return this.idrubroProperty().get();
	}
	
	public void setIdrubro(final int idrubro) {
		this.idrubroProperty().set(idrubro);
	}

	public StringProperty rubroProperty() {
		return this.rubro;
	}
	
	public String getRubro() {
		return this.rubroProperty().get();
	}
	
	public void setRubro(final String rubro) {
		this.rubroProperty().set(rubro);
	}

	@Override
	public String toString() {
		return descripcionProperty().get();
	}
	
	
}
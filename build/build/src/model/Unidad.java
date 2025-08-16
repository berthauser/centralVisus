package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Unidad {
	private IntegerProperty idunidad;
	private StringProperty nombre;
	private StringProperty abreviatura;
	
	public Unidad() {
		idunidad = new SimpleIntegerProperty();
		nombre = new SimpleStringProperty();
		abreviatura = new SimpleStringProperty();
	}

	public Unidad(Integer idunidad, String nombre, String abreviatura) {
		this.idunidad = new SimpleIntegerProperty(idunidad);
		this.nombre = new SimpleStringProperty(nombre);
		this.abreviatura = new SimpleStringProperty(abreviatura);
	}

	public IntegerProperty idunidadProperty() {
		return this.idunidad;
	}
	
	public int getIdunidad() {
		return this.idunidadProperty().get();
	}
	
	public void setIdunidad(final int idunidad) {
		this.idunidadProperty().set(idunidad);
	}
	
	public StringProperty nombreProperty() {
		return this.nombre;
	}
	
	public String getNombre() {
		return this.nombreProperty().get();
	}
	
	public void setNombre(final String nombre) {
		this.nombreProperty().set(nombre);
	}
	
	public StringProperty abreviaturaProperty() {
		return this.abreviatura;
	}
	
	public String getAbreviatura() {
		return this.abreviaturaProperty().get();
	}
	
	public void setAbreviatura(final String abreviatura) {
		this.abreviaturaProperty().set(abreviatura);
	}

	@Override
	public String toString() {
		return nombreProperty().get();
	}
	
	
}
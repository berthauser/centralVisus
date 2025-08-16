package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Localidad {
	private IntegerProperty idlocalidad;
	private StringProperty nombre;
	private IntegerProperty codigoPostal;
	private StringProperty provincia;
	
	public Localidad() {
		idlocalidad = new SimpleIntegerProperty();
		nombre = new SimpleStringProperty();
		codigoPostal = new SimpleIntegerProperty();
		provincia = new SimpleStringProperty();
	}
	
	public Localidad(Integer idlocalidad, String nombre, Integer codigoPostal, String provincia) {
		this.idlocalidad = new SimpleIntegerProperty(idlocalidad);
		this.nombre = new SimpleStringProperty(nombre);
		this.codigoPostal = new SimpleIntegerProperty(codigoPostal);
		this.provincia = new SimpleStringProperty(provincia);
	}

	public IntegerProperty idlocalidadProperty() {
		return this.idlocalidad;
	}
	
	public int getIdlocalidad() {
		return this.idlocalidadProperty().get();
	}
	
	public void setIdlocalidad(final int idlocalidad) {
		this.idlocalidadProperty().set(idlocalidad);
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
	
	public IntegerProperty codigoPostalProperty() {
		return this.codigoPostal;
	}
	
	public int getCodigoPostal() {
		return this.codigoPostalProperty().get();
	}
	
	public void setCodigoPostal(final int codigoPostal) {
		this.codigoPostalProperty().set(codigoPostal);
	}
	
	public StringProperty provinciaProperty() {
		return this.provincia;
	}
	
	public String getProvincia() {
		return this.provinciaProperty().get();
	}
	
	public void setProvincia(final String provincia) {
		this.provinciaProperty().set(provincia);
	}

}
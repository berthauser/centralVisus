package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Concepto {
	
	private final IntegerProperty idconcepto;
	private final StringProperty descripcion;
	
	public Concepto() {
    	this.idconcepto = new SimpleIntegerProperty();
		this.descripcion = new SimpleStringProperty();
	}

	public Concepto(String descripcion) {
		this();
		this.descripcion.set(descripcion);
	}

	public StringProperty getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion.set(descripcion);
	}

	public IntegerProperty getIdconcepto() {
		return idconcepto;
	}
	
}
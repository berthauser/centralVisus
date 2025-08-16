package model;

/**
 * Bancos
 */

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Banco {
	private IntegerProperty idbanco;
	private IntegerProperty codigo;
	private StringProperty nombre;
	
	public Banco() {
		idbanco = new SimpleIntegerProperty();
		codigo = new SimpleIntegerProperty();
		nombre = new SimpleStringProperty();
	}
	
	public Banco(Integer idbanco, Integer codigo, String nombre) {
		this.idbanco = new SimpleIntegerProperty(idbanco);
		this.codigo = new SimpleIntegerProperty(codigo);
		this.nombre = new SimpleStringProperty(nombre);
	}

	public IntegerProperty idbancoProperty() {
		return this.idbanco;
	}
	
	public int getIdbanco() {
		return this.idbancoProperty().get();
	}
	
	public void setIdbanco(final int idbanco) {
		this.idbancoProperty().set(idbanco);
	}
	
	public IntegerProperty codigoProperty() {
		return this.codigo;
	}
	
	public int getCodigo() {
		return this.codigoProperty().get();
	}
	
	public void setCodigo(final int codigo) {
		this.codigoProperty().set(codigo);
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
	
}
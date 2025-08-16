package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Modelo para las listas de precios
 * 
 * @author bertHauser
 *
 */
public class Lista {
	private IntegerProperty idlista;
	private StringProperty descripcion;
	private DoubleProperty margen;
	
	public Lista () {
		idlista = new SimpleIntegerProperty();
		descripcion = new SimpleStringProperty();
		margen = new SimpleDoubleProperty();
	}

	public Lista(Integer idlista, String descripcion, Double margen) {
		this.idlista = new SimpleIntegerProperty(idlista);
		this.descripcion = new SimpleStringProperty(descripcion);
		this.margen = new SimpleDoubleProperty(margen);
	}

	public IntegerProperty idlistaProperty() {
		return this.idlista;
	}
	
	public int getIdlista() {
		return this.idlistaProperty().get();
	}
	
	public void setIdlista(final int idlista) {
		this.idlistaProperty().set(idlista);
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

	public DoubleProperty margenProperty() {
		return this.margen;
	}
	
	public double getMargen() {
		return this.margenProperty().get();
	}
	
	public void setMargen(final double margen) {
		this.margenProperty().set(margen);
	}

	@Override
	public String toString() {
		return "Lista [idlista=" + idlista + ", descripcion=" + descripcion + ", margen=" + margen + "]";
	}
	
}
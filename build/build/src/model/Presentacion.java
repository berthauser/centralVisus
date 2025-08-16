package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Packaging del producto. Hormas, blister, caja, ristra, etc.
 * @author ErnestoAndresZapataI
 *
 */
public class Presentacion {
	private IntegerProperty idpresentacion;
	private StringProperty contenedor;
	private DoubleProperty unidades;
	private DoubleProperty valor_umedida;
	private StringProperty tipo_decremento;
	
	public Presentacion() {
		idpresentacion = new SimpleIntegerProperty();
		contenedor = new SimpleStringProperty();
		unidades = new SimpleDoubleProperty();
		valor_umedida = new SimpleDoubleProperty();
		tipo_decremento = new SimpleStringProperty();
	}

	public Presentacion(Integer idpresentacion, String contenedor, Double unidades, Double valor_umedida, String tipo_decremento) {
		this.idpresentacion = new SimpleIntegerProperty(idpresentacion);
		this.contenedor = new SimpleStringProperty(contenedor);
		this.unidades = new SimpleDoubleProperty(unidades);
		this.valor_umedida = new SimpleDoubleProperty(valor_umedida);
		this.tipo_decremento = new SimpleStringProperty(tipo_decremento);
	}

	public IntegerProperty idpresentacionProperty() {
		return this.idpresentacion;
	}
	
	public int getIdpresentacion() {
		return this.idpresentacionProperty().get();
	}
	
	public void setIdpresentacion(final int idpresentacion) {
		this.idpresentacionProperty().set(idpresentacion);
	}
	
	public StringProperty contenedorProperty() {
		return this.contenedor;
	}
	
	public String getContenedor() {
		return this.contenedorProperty().get();
	}
	
	public void setContenedor(final String contenedor) {
		this.contenedorProperty().set(contenedor);
	}
	
	public DoubleProperty unidadesProperty() {
		return this.unidades;
	}
	
	public double getUnidades() {
		return this.unidadesProperty().get();
	}
	
	public void setUnidades(final double unidades) {
		this.unidadesProperty().set(unidades);
	}
	
	public DoubleProperty valor_umedidaProperty() {
		return this.valor_umedida;
	}
	
	public double getValor_umedida() {
		return this.valor_umedidaProperty().get();
	}
	
	public void setValor_umedida(final double valor_umedida) {
		this.valor_umedidaProperty().set(valor_umedida);
	}
	
	public StringProperty tipo_decrementoProperty() {
		return this.tipo_decremento;
	}
	
	public String getTipo_decremento() {
		return this.tipo_decrementoProperty().get();
	}
	
	public void setTipo_decremento(final String tipo_decremento) {
		this.tipo_decrementoProperty().set(tipo_decremento);
	}

	@Override
	public String toString() {
		return contenedorProperty().get();
	}
	
}
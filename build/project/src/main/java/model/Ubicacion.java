package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * Ubicacian fasica de la mercaderaa dentro del depasito.
 * Los atributos tot_????? valores tipo matriz para ubicar
 * una mercaderaa dentro de una estanteraa. 
 * 
 * @author ErnestoAndresZapataI
 *
 */
public class Ubicacion {
	private IntegerProperty idubicacion;
	private StringProperty descripcion;
	private StringProperty numero;
	private IntegerProperty iddeposito;
	private IntegerProperty tot_filas;
	private IntegerProperty tot_columnas;
	// atributos auxiliares	
	private StringProperty deposito;	
	
	public Ubicacion() {
		idubicacion = new SimpleIntegerProperty();
		descripcion = new SimpleStringProperty();
		numero = new SimpleStringProperty();
		deposito = new SimpleStringProperty();
		iddeposito = new SimpleIntegerProperty();
		tot_filas = new SimpleIntegerProperty();
		tot_columnas = new SimpleIntegerProperty();
	}

	public Ubicacion(Integer idubicacion, String descripcion, String numero, String deposito, Integer iddeposito, Integer tot_filas, Integer tot_columnas) {
		this.idubicacion = new SimpleIntegerProperty(idubicacion);
		this.descripcion = new SimpleStringProperty(descripcion);
		this.numero = new SimpleStringProperty(numero);
		this.iddeposito = new SimpleIntegerProperty(iddeposito);
		this.deposito = new SimpleStringProperty(deposito);
		this.tot_filas = new SimpleIntegerProperty(tot_filas);
		this.tot_columnas = new SimpleIntegerProperty(tot_columnas);
	}

	public IntegerProperty idubicacionProperty() {
		return this.idubicacion;
	}
	
	public int getIdubicacion() {
		return this.idubicacionProperty().get();
	}
	
	public void setIdubicacion(final int idubicacion) {
		this.idubicacionProperty().set(idubicacion);
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
	
	public StringProperty numeroProperty() {
		return this.numero;
	}
	
	public String getNumero() {
		return this.numeroProperty().get();
	}
	
	public void setNumero(final String numero) {
		this.numeroProperty().set(numero);
	}
	
	public IntegerProperty iddepositoProperty() {
		return this.iddeposito;
	}
	
	public int getIddeposito() {
		return this.iddepositoProperty().get();
	}
	
	public void setIddeposito(final int iddeposito) {
		this.iddepositoProperty().set(iddeposito);
	}
	
	public IntegerProperty tot_filasProperty() {
		return this.tot_filas;
	}
	
	public int getTot_filas() {
		return this.tot_filasProperty().get();
	}
	
	public void setTot_filas(final int tot_filas) {
		this.tot_filasProperty().set(tot_filas);
	}
	
	public IntegerProperty tot_columnasProperty() {
		return this.tot_columnas;
	}
	
	public int getTot_columnas() {
		return this.tot_columnasProperty().get();
	}
	
	public void setTot_columnas(final int tot_columnas) {
		this.tot_columnasProperty().set(tot_columnas);
	}

	public StringProperty depositoProperty() {
		return this.deposito;
	}
	
	public String getDeposito() {
		return this.depositoProperty().get();
	}
	
	public void setDeposito(final String Deposito) {
		this.depositoProperty().set(Deposito);
	}
	
	
}
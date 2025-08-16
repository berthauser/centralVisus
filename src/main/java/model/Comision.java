package model;

// LocalDate representa una fecha en formato ISO (yyyy-MM-dd) sin tiempo
/**
 * @author berthauser
 * 
 * 
 * 
 * 
 * 
 */
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Comision {
	private final IntegerProperty idcomision;
	private final StringProperty tipo_comision;
	private final StringProperty descripcion;
	private final DoubleProperty porcentaje;
	private final DoubleProperty desde_monto;
	private final DoubleProperty hasta_monto;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private final SimpleObjectProperty<LocalDate> inicio_vigencia;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private final SimpleObjectProperty<LocalDate> fin_vigencia;
	private final StringProperty concepto;
	
	public Comision() {
		 idcomision = new SimpleIntegerProperty();
		 tipo_comision = new SimpleStringProperty();
		 descripcion = new SimpleStringProperty();
		 porcentaje = new SimpleDoubleProperty();
		 desde_monto = new SimpleDoubleProperty();
		 hasta_monto = new SimpleDoubleProperty();
		 inicio_vigencia = new SimpleObjectProperty<>();
		 fin_vigencia = new SimpleObjectProperty<>();
		 concepto = new SimpleStringProperty();
	}

	public Comision(Integer idcomision, String tipo_comision, String descripcion, Double porcentaje, Double desde_monto,
			Double hasta_monto, LocalDate inicio_vigencia, LocalDate fin_vigencia, String concepto) {
		this();
		this.idcomision.set(idcomision);
		this.tipo_comision.set(tipo_comision);
		this.descripcion.set(descripcion);
		this.porcentaje.set(porcentaje);
		this.desde_monto.set(desde_monto);
		this.hasta_monto.set(hasta_monto);
		this.inicio_vigencia.set(inicio_vigencia);
		this.fin_vigencia.set(fin_vigencia);
		this.concepto.set(concepto);
	}

	public final IntegerProperty idcomisionProperty() {
		return this.idcomision;
	}
	
	public final int getIdcomision() {
		return this.idcomisionProperty().get();
	}
	
	public final void setIdcomision(final int idcomision) {
		this.idcomisionProperty().set(idcomision);
	}
	
	public final StringProperty tipo_comisionProperty() {
		return this.tipo_comision;
	}
	
	public final String getTipo_comision() {
		return this.tipo_comisionProperty().get();
	}

	public final void setTipo_comision(final String tipo_comision) {
		this.tipo_comisionProperty().set(tipo_comision);
	}
	
	public final StringProperty descripcionProperty() {
		return this.descripcion;
	}
	
	public final String getDescripcion() {
		return this.descripcionProperty().get();
	}
	
	public final void setDescripcion(final String descripcion) {
		this.descripcionProperty().set(descripcion);
	}
	
	public final DoubleProperty porcentajeProperty() {
		return this.porcentaje;
	}
	
	public final double getPorcentaje() {
		return this.porcentajeProperty().get();
	}
	
	public final void setPorcentaje(final double porcentaje) {
		this.porcentajeProperty().set(porcentaje);
	}
	
	public final DoubleProperty desde_montoProperty() {
		return this.desde_monto;
	}
	
	public final double getDesde_monto() {
		return this.desde_montoProperty().get();
	}
	
	public final void setDesde_monto(final double desde_monto) {
		this.desde_montoProperty().set(desde_monto);
	}
	
	public final DoubleProperty hasta_montoProperty() {
		return this.hasta_monto;
	}
	
	public final double getHasta_monto() {
		return this.hasta_montoProperty().get();
	}
	
	public final void setHasta_monto(final double hasta_monto) {
		this.hasta_montoProperty().set(hasta_monto);
	}
	
	public final SimpleObjectProperty<LocalDate> inicio_vigenciaProperty() {
		return this.inicio_vigencia;
	}
	
	public final LocalDate getInicio_vigencia() {
		return this.inicio_vigenciaProperty().get();
	}
	
	public final void setInicio_vigencia(final LocalDate inicio_vigencia) {
		this.inicio_vigenciaProperty().set(inicio_vigencia);
	}
	
	public final SimpleObjectProperty<LocalDate> fin_vigenciaProperty() {
		return this.fin_vigencia;
	}
	
	public final LocalDate getFin_vigencia() {
		return this.fin_vigenciaProperty().get();
	}
	
	public final void setFin_vigencia(final LocalDate fin_vigencia) {
		this.fin_vigenciaProperty().set(fin_vigencia);
	}

	public final StringProperty conceptoProperty() {
		return this.concepto;
	}
	
	public final String getConcepto() {
		return this.conceptoProperty().get();
	}
	
	public final void setConcepto(final String concepto) {
		this.conceptoProperty().set(concepto);
	}

	@Override
	public String toString() {
		return "Comision [idcomision=" + idcomision + ", tipo_comision=" + tipo_comision + ", descripcion="
				+ descripcion + ", porcentaje=" + porcentaje + ", desde_monto=" + desde_monto + ", hasta_monto="
				+ hasta_monto + ", inicio_vigencia=" + inicio_vigencia + ", fin_vigencia=" + fin_vigencia
				+ ", concepto=" + concepto + "]";
	}
	
}
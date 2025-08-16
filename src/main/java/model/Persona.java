package model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Persona {
	/**
	 * @author ErnestoAndresZapataI
	 * @date 20/02/2022
	 * @version 2.0
	 * @copyright BertHäuser
	 * @description modelo inicial de Personas
	 * 
	 */
	private IntegerProperty nrodocumento;
	private StringProperty tipo_persona;
	private StringProperty apellido_persona;
	private StringProperty nombre_persona;
	private StringProperty situacion_fiscal;
	private LongProperty cuit;
	private StringProperty sexo;
	private StringProperty[] telefonos;
	private StringProperty[] email;
	private BooleanProperty is_jubilado;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private ObjectProperty<LocalDate> fecha_baja;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private ObjectProperty<LocalDate> fecha_ingreso;
	private DoubleProperty limite_credito;
	private IntegerProperty limite_facturasvencidas;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private ObjectProperty<LocalDate> fecha_ultimacompra;
	private DoubleProperty pago_minimo;
	private StringProperty tipo_transporte;
	private DoubleProperty saldo_ctacte;
	private BooleanProperty adherente;
	private StringProperty parentesco;
	private IntegerProperty nrodocumento_titular;
	private StringProperty observaciones;
	private StringProperty estado;
	
	
	public Persona() {
		nrodocumento = new SimpleIntegerProperty();
		tipo_persona = new SimpleStringProperty();
		apellido_persona = new SimpleStringProperty();
		nombre_persona = new SimpleStringProperty();
		situacion_fiscal = new SimpleStringProperty();
		cuit = new SimpleLongProperty();
		sexo = new SimpleStringProperty();
//		telefonos = new SimpleStringProperty(); 
//		email = new SimpleStringProperty();
		is_jubilado = new SimpleBooleanProperty();
		fecha_baja = new SimpleObjectProperty<>();
		fecha_ingreso = new SimpleObjectProperty<>();
		limite_credito = new SimpleDoubleProperty();
		limite_facturasvencidas = new SimpleIntegerProperty();
		fecha_ultimacompra = new SimpleObjectProperty<>();
		pago_minimo = new SimpleDoubleProperty();
		tipo_transporte = new SimpleStringProperty();
		saldo_ctacte = new SimpleDoubleProperty();
		adherente = new SimpleBooleanProperty();
		parentesco = new SimpleStringProperty();
		nrodocumento_titular = new SimpleIntegerProperty();
		observaciones = new SimpleStringProperty();
		estado = new SimpleStringProperty();
	}
	
	
	public Persona(Integer nrodocumento, String tipo_persona, String apellido_persona,
			String nombre_persona, String situacion_fiscal, Long cuit, String sexo,
			String[] telefonos, String[] email, Boolean is_jubilado, LocalDate fecha_baja,
			LocalDate fecha_ingreso, Double limite_credito, Integer limite_facturasvencidas,
			LocalDate fecha_ultimacompra, Double pago_minimo, String tipo_transporte,
			Double saldo_ctacte, Boolean adherente, String parentesco,
			Integer nrodocumento_titular, String observaciones, String estado) {
		this();
		this.nrodocumento.set(nrodocumento);
		this.tipo_persona.set(tipo_persona);
		this.apellido_persona.set(apellido_persona);
		this.nombre_persona.set(nombre_persona);
		this.situacion_fiscal.set(situacion_fiscal);
		this.cuit.set(cuit);
		this.sexo.set(sexo);
//		this.telefonos.set(telefonos);
//		this.email.set(email);
		this.is_jubilado.set(is_jubilado);
		this.fecha_baja.set(fecha_baja);
		this.fecha_ingreso.set(fecha_ingreso);
		this.limite_credito.set(limite_credito);
		this.limite_facturasvencidas.set(limite_facturasvencidas);
		this.fecha_ultimacompra.set(fecha_ultimacompra);
		this.pago_minimo.set(pago_minimo);
		this.tipo_transporte.set(tipo_transporte);
		this.saldo_ctacte.set(saldo_ctacte);
		this.adherente.set(adherente);
		this.parentesco.set(parentesco);
		this.nrodocumento_titular.set(nrodocumento_titular);
		this.observaciones.set(observaciones);
		this.estado.set(estado);
	}

	
	
	
	

}

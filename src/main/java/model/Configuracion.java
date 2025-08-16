package model;

// LocalDate representa una fecha en formato ISO (yyyy-MM-dd) sin tiempo
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Configuracion {
	private final IntegerProperty idconfiguracion;
	private final StringProperty razon_social;
	private final StringProperty nombre_fantasia;
	private final StringProperty email;
	private final StringProperty domicilio_comercial;
	private final StringProperty telefono_fijo;
	private final StringProperty telefono_movil;
	private final StringProperty localidad;
	private final StringProperty provincia;
	private final StringProperty codigo_postal;
	private final StringProperty situacion_fiscal;
	private final StringProperty domicilio_fiscal;
	private final IntegerProperty punto_venta;
	private final StringProperty iibb_convmultilateral;
	private final StringProperty cuit;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private final ObjectProperty<LocalDate> fecha_iactividades;
	private final SimpleBooleanProperty modo_produccion;
	private final SimpleBooleanProperty modo_factura;
	private final StringProperty path_facturaspdf;
	private final StringProperty path_certproduccion;
	private final StringProperty path_certhomologacion;
	private final StringProperty path_claveprivada;
	private final DoubleProperty importe_afip;
	
	public Configuracion() {
		 idconfiguracion = new SimpleIntegerProperty();
		 razon_social = new SimpleStringProperty();
		 nombre_fantasia = new SimpleStringProperty();
		 email = new SimpleStringProperty();
		 domicilio_comercial = new SimpleStringProperty();
		 telefono_fijo = new SimpleStringProperty();
		 telefono_movil = new SimpleStringProperty();
		 localidad = new SimpleStringProperty();
		 provincia = new SimpleStringProperty();
		 codigo_postal = new SimpleStringProperty();
		 situacion_fiscal = new SimpleStringProperty();
		 domicilio_fiscal = new SimpleStringProperty();
		 punto_venta = new SimpleIntegerProperty();
		 iibb_convmultilateral = new SimpleStringProperty();
		 cuit = new SimpleStringProperty();
		 modo_produccion = new SimpleBooleanProperty();
		 modo_factura = new SimpleBooleanProperty();
		 fecha_iactividades = new SimpleObjectProperty<>();
		 path_facturaspdf = new SimpleStringProperty();
		 path_certproduccion = new SimpleStringProperty();
		 path_certhomologacion = new SimpleStringProperty();
		 path_claveprivada = new SimpleStringProperty();
		 importe_afip = new SimpleDoubleProperty();
	}

	public Configuracion(Integer idconfiguracion, String razon_social, String nombre_fantasia, String email, String domicilio_comercial, 
			String telefono_fijo, String telefono_movil, String localidad, String provincia, String codigo_postal,
			String situacion_fiscal, String domicilio_fiscal, Integer punto_venta, String iibb_convmultilateral, 
			String cuit, LocalDate fecha_iactividades, Boolean modo_produccion, Boolean modo_factura, String path_facturaspdf,
			String path_certproduccion,	String path_certhomologacion, String path_claveprivada, Double importe_afip) {
		this();
		this.idconfiguracion.set(idconfiguracion);
		this.razon_social.set(razon_social);
		this.nombre_fantasia.set(nombre_fantasia);
		this.email.set(email);
		this.domicilio_comercial.set(domicilio_comercial);
		this.telefono_fijo.set(telefono_fijo);
		this.telefono_movil.set(telefono_movil);
		this.localidad.set(localidad);
		this.provincia.set(provincia);
		this.codigo_postal.set(codigo_postal);
		this.situacion_fiscal.set(situacion_fiscal);
		this.domicilio_fiscal.set(domicilio_fiscal);
		this.punto_venta.set(punto_venta);
		this.iibb_convmultilateral.set(iibb_convmultilateral);
		this.cuit.set(cuit);
		this.fecha_iactividades.set(fecha_iactividades);
		this.modo_produccion.set(modo_produccion);
		this.modo_factura.set(modo_factura);
		this.path_facturaspdf.set(path_facturaspdf);
		this.path_certproduccion.set(path_certproduccion);
		this.path_certhomologacion.set(path_certhomologacion);
		this.path_claveprivada.set(path_claveprivada);
		this.importe_afip.set(importe_afip);
	}

	public IntegerProperty idconfiguracionProperty() {
		return this.idconfiguracion;
	}
	
	public int getIdconfiguracion() {
		return this.idconfiguracionProperty().get();
	}
	
	public void setIdconfiguracion(final int idconfiguracion) {
		this.idconfiguracionProperty().set(idconfiguracion);
	}
	
	public StringProperty razon_socialProperty() {
		return this.razon_social;
	}
	
	public String getRazon_social() {
		return this.razon_socialProperty().get();
	}
	
	public void setRazon_social(final String razon_social) {
		this.razon_socialProperty().set(razon_social);
	}
	
	public StringProperty nombre_fantasiaProperty() {
		return this.nombre_fantasia;
	}
	
	public String getNombre_fantasia() {
		return this.nombre_fantasiaProperty().get();
	}
	
	public void setNombre_fantasia(final String nombre_fantasia) {
		this.nombre_fantasiaProperty().set(nombre_fantasia);
	}
	
	public StringProperty emailProperty() {
		return this.email;
	}
	
	public String getEmail() {
		return this.emailProperty().get();
	}
	
	public void setEmail(final String email) {
		this.emailProperty().set(email);
	}
	
	public StringProperty domicilio_comercialProperty() {
		return this.domicilio_comercial;
	}
	
	public String getDomicilio_comercial() {
		return this.domicilio_comercialProperty().get();
	}
	
	public void setDomicilio_comercial(final String domicilio_comercial) {
		this.domicilio_comercialProperty().set(domicilio_comercial);
	}
	
	public StringProperty telefono_fijoProperty() {
		return this.telefono_fijo;
	}
	
	public String getTelefono_fijo() {
		return this.telefono_fijoProperty().get();
	}
	
	public void setTelefono_fijo(final String telefono_fijo) {
		this.telefono_fijoProperty().set(telefono_fijo);
	}
	
	public StringProperty telefono_movilProperty() {
		return this.telefono_movil;
	}
	
	public String getTelefono_movil() {
		return this.telefono_movilProperty().get();
	}
	
	public void setTelefono_movil(final String telefono_movil) {
		this.telefono_movilProperty().set(telefono_movil);
	}
	
	public StringProperty localidadProperty() {
		return this.localidad;
	}
	
	public String getLocalidad() {
		return this.localidadProperty().get();
	}
	
	public void setLocalidad(final String localidad) {
		this.localidadProperty().set(localidad);
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
	
	public StringProperty codigo_postalProperty() {
		return this.codigo_postal;
	}
	
	public String getCodigo_postal() {
		return this.codigo_postalProperty().get();
	}
	
	public void setCodigo_postal(final String codigo_postal) {
		this.codigo_postalProperty().set(codigo_postal);
	}
	
	public StringProperty situacion_fiscalProperty() {
		return this.situacion_fiscal;
	}
	
	public String getSituacion_fiscal() {
		return this.situacion_fiscalProperty().get();
	}
	
	public void setSituacion_fiscal(final String situacion_fiscal) {
		this.situacion_fiscalProperty().set(situacion_fiscal);
	}
	
	public StringProperty domicilio_fiscalProperty() {
		return this.domicilio_fiscal;
	}
	
	public String getDomicilio_fiscal() {
		return this.domicilio_fiscalProperty().get();
	}
	
	public void setDomicilio_fiscal(final String domicilio_fiscal) {
		this.domicilio_fiscalProperty().set(domicilio_fiscal);
	}
	
	public IntegerProperty punto_ventaProperty() {
		return this.punto_venta;
	}
	
	public int getPunto_venta() {
		return this.punto_ventaProperty().get();
	}
	
	public void setPunto_venta(final int punto_venta) {
		this.punto_ventaProperty().set(punto_venta);
	}
	
	public StringProperty iibb_convmultilateralProperty() {
		return this.iibb_convmultilateral;
	}
	
	public String getIibb_convmultilateral() {
		return this.iibb_convmultilateralProperty().get();
	}
	
	public void setIibb_convmultilateral(final String iibb_convmultilateral) {
		this.iibb_convmultilateralProperty().set(iibb_convmultilateral);
	}
	
	public StringProperty cuitProperty() {
		return this.cuit;
	}
	
	public String getCuit() {
		return this.cuitProperty().get();
	}
	
	public void setCuit(final String cuit) {
		this.cuitProperty().set(cuit);
	}
	
	public ObjectProperty<LocalDate> fecha_iactividadesProperty() {
		return this.fecha_iactividades;
	}
	
	public LocalDate getFecha_iactividades() {
		return this.fecha_iactividadesProperty().get();
	}
	
	public void setFecha_iactividades(final LocalDate fecha_iactividades) {
		this.fecha_iactividadesProperty().set(fecha_iactividades);
	}
	
	public SimpleBooleanProperty modo_produccionProperty() {
		return this.modo_produccion;
	}
	
	public boolean isModo_produccion() {
		return this.modo_produccionProperty().get();
	}
	
	public void setModo_produccion(final boolean modo_produccion) {
		this.modo_produccionProperty().set(modo_produccion);
	}
	
	public SimpleBooleanProperty modo_facturaProperty() {
		return this.modo_factura;
	}
	
	public boolean isModo_factura() {
		return this.modo_facturaProperty().get();
	}
	
	public void setModo_factura(final boolean modo_factura) {
		this.modo_facturaProperty().set(modo_factura);
	}
	
	public StringProperty path_facturaspdfProperty() {
		return this.path_facturaspdf;
	}
	
	public String getPath_facturaspdf() {
		return this.path_facturaspdfProperty().get();
	}
	
	public void setPath_facturaspdf(final String path_facturaspdf) {
		this.path_facturaspdfProperty().set(path_facturaspdf);
	}
	
	public StringProperty path_certproduccionProperty() {
		return this.path_certproduccion;
	}
	
	public String getPath_certproduccion() {
		return this.path_certproduccionProperty().get();
	}
	
	public void setPath_certproduccion(final String path_certproduccion) {
		this.path_certproduccionProperty().set(path_certproduccion);
	}
	
	public StringProperty path_certhomologacionProperty() {
		return this.path_certhomologacion;
	}
	
	public String getPath_certhomologacion() {
		return this.path_certhomologacionProperty().get();
	}
	
	public void setPath_certhomologacion(final String path_certhomologacion) {
		this.path_certhomologacionProperty().set(path_certhomologacion);
	}
	
	public StringProperty path_claveprivadaProperty() {
		return this.path_claveprivada;
	}
	
	public String getPath_claveprivada() {
		return this.path_claveprivadaProperty().get();
	}

	public void setPath_claveprivada(final String path_claveprivada) {
		this.path_claveprivadaProperty().set(path_claveprivada);
	}
	
	public DoubleProperty importe_afipProperty() {
		return this.importe_afip;
	}
	
	public double getImporte_afip() {
		return this.importe_afipProperty().get();
	}
	
	public void setImporte_afip(final double importe_afip) {
		this.importe_afipProperty().set(importe_afip);
	}

	@Override
	public String toString() {
		return "Configuracion [idconfiguracion=" + idconfiguracion + ", razon_social=" + razon_social
				+ ", nombre_fantasia=" + nombre_fantasia + ", email=" + email + ", domicilio_comercial="
				+ domicilio_comercial + ", telefono_fijo=" + telefono_fijo + ", telefono_movil=" + telefono_movil
				+ ", localidad=" + localidad + ", provincia=" + provincia + ", codigo_postal=" + codigo_postal
				+ ", situacion_fiscal=" + situacion_fiscal + ", domicilio_fiscal=" + domicilio_fiscal + ", punto_venta="
				+ punto_venta + ", iibb_convmultilateral=" + iibb_convmultilateral + ", cuit=" + cuit
				+ ", fecha_iactividades=" + fecha_iactividades + ", modo_produccion=" + modo_produccion
				+ ", modo_factura=" + modo_factura + ", path_facturaspdf=" + path_facturaspdf + ", path_certproduccion="
				+ path_certproduccion + ", path_certhomologacion=" + path_certhomologacion + ", path_claveprivada="
				+ path_claveprivada + ", importe_afip=" + importe_afip + "]";
	}

}
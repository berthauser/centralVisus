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
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author ErnestoAndresZapataI
 * @date 24/09/2020 
 * @version 1.0
 * @copyright BertHäuser
 * @description clase para mercaderías, artículos.
 * Para los atributos que son ID externos se crean 
 * nuevos atributos con el tipo de dato que correspondan  
 * 
 * @date 08/12/2021
 * @version 2
 * @author EAZI
 * {@summary}
 * Se agregan los atributos: 
 *  margen_utilidad_minorista
 *  descuento_masivo 
 *  cantidad_minima
 *  descuento_por_cantidad
 * Se modifica el atributo:
 *  utilidad por margen_utilidad_mayorista
 */
public class Articulo {
	private IntegerProperty idarticulo;
	private StringProperty codigobarra;
	private IntegerProperty codigoempresa;
	private IntegerProperty codigoprodempresa;
	private IntegerProperty codigointerno;
	private StringProperty leyenda;
	private StringProperty tipo_producto; 
	private StringProperty tipo_posicion;
	private IntegerProperty idubicacion;
	private IntegerProperty fila;
	private IntegerProperty col;
	private DoubleProperty existencia;
	private DoubleProperty cantidad;
	private IntegerProperty stock_minimo;
	private IntegerProperty stock_optimo;
	private IntegerProperty stock_maximo;
	private BooleanProperty stock_negativo;
	private DoubleProperty merma;
	private BooleanProperty fraccionado;
	private IntegerProperty plu; // Price LookUp
	private DoubleProperty utilidad_fraccionado;
	private StringProperty leyenda_fraccionado;
	private BooleanProperty descuento_masivo;
	private DoubleProperty cantidad_minima;
	private DoubleProperty descuento_por_cantidad;
	private IntegerProperty idmarca;
	private IntegerProperty idrubro;
	private IntegerProperty idlinea;
	private IntegerProperty idunidad;
	private IntegerProperty idpresentacion;
	private LongProperty nrodoc_proveedor;
	private StringProperty tipo_persona;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private SimpleObjectProperty<LocalDate> fecha_compra;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private SimpleObjectProperty<LocalDate> fecha_baja;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private SimpleObjectProperty<LocalDate> fecha_actprecios;
	private DoubleProperty precio_costo;
	private DoubleProperty margen_utilidad_mayorista;
	private DoubleProperty margen_utilidad_minorista;
	private DoubleProperty gravamen;
	private StringProperty estado;
// atributos auxiliares	POS
	private DoubleProperty cant_comprada;
	private StringProperty Abreviatura;
	private DoubleProperty tot_a_pagar;
	private DoubleProperty valor_umedida;
	private StringProperty tipo_decremento;
// fin atributos auxiliares POS	
// atributos auxiliares	
	private transient StringProperty ubicacion;
	private transient StringProperty numero;
	private transient StringProperty deposito;
	private transient StringProperty marca;
	private transient StringProperty rubro;
	private transient StringProperty linea;
	private transient StringProperty unidad;
	private transient StringProperty presentacion;
	private transient IntegerProperty cantUnidades;
// fin atributos auxiliares 
	
	public Articulo() {
		idarticulo = new SimpleIntegerProperty();
		codigobarra = new SimpleStringProperty();
		codigoempresa = new SimpleIntegerProperty();
		codigoprodempresa = new SimpleIntegerProperty();
		codigointerno = new SimpleIntegerProperty();
		leyenda = new SimpleStringProperty();
		tipo_producto = new SimpleStringProperty();
		tipo_posicion = new SimpleStringProperty();
		idubicacion = new SimpleIntegerProperty();
		fila = new SimpleIntegerProperty();
		col = new SimpleIntegerProperty();
		existencia = new SimpleDoubleProperty();
		cantidad = new SimpleDoubleProperty();
		stock_minimo = new SimpleIntegerProperty();
		stock_optimo = new SimpleIntegerProperty();
		stock_maximo = new SimpleIntegerProperty();
		stock_negativo = new SimpleBooleanProperty();
		merma = new SimpleDoubleProperty();
		fraccionado = new SimpleBooleanProperty();
		plu = new SimpleIntegerProperty();
		utilidad_fraccionado = new SimpleDoubleProperty();
		leyenda_fraccionado = new SimpleStringProperty();
		descuento_masivo = new SimpleBooleanProperty();
		cantidad_minima = new SimpleDoubleProperty();
		descuento_por_cantidad = new SimpleDoubleProperty();
		idmarca = new SimpleIntegerProperty();
		idrubro = new SimpleIntegerProperty();
		idlinea = new SimpleIntegerProperty();
		idunidad = new SimpleIntegerProperty();
		idpresentacion = new SimpleIntegerProperty();
		nrodoc_proveedor = new SimpleLongProperty();
		tipo_persona = new SimpleStringProperty();
		fecha_compra = new SimpleObjectProperty<>();
		fecha_baja = new SimpleObjectProperty<>();
		fecha_actprecios = new SimpleObjectProperty<>();
		precio_costo = new SimpleDoubleProperty();
		margen_utilidad_mayorista = new SimpleDoubleProperty();
		margen_utilidad_minorista = new SimpleDoubleProperty();
		gravamen = new SimpleDoubleProperty();
		estado = new SimpleStringProperty();
		cant_comprada = new SimpleDoubleProperty();
		Abreviatura = new SimpleStringProperty();
		tot_a_pagar = new SimpleDoubleProperty();
		valor_umedida = new SimpleDoubleProperty();
		tipo_decremento = new SimpleStringProperty();
	}
	
	public Articulo(Integer idarticulo, String codigobarra, Integer codigoempresa, Integer codigoprodempresa,
			Integer codigointerno, String leyenda, String tipo_producto, String tipo_posicion, Integer idubicacion,
			Integer fila, Integer col, Double existencia, Double cantidad, Integer stock_minimo, Integer stock_optimo,
			Integer stock_maximo, Boolean stock_negativo, Double merma, Boolean fraccionado, Integer plu,
			Double utilidad_fraccionado, String leyenda_fraccionado, Boolean descuento_masivo, Double cantidad_minima,
			Double descuento_por_cantidad,	Integer idmarca, Integer idrubro, Integer idlinea,
			Integer idunidad, String ubicacion, String numero, String deposito, String marca, String rubro,
			String linea, String unidad, String presentacion, Integer cantUnidades, Integer idpresentacion,
			Double valor_umedida, String tipo_decremento, Long nrodoc_proveedor, String tipo_persona,
			LocalDate fecha_compra, LocalDate fecha_baja, LocalDate fecha_actprecios, Double precio_costo,
			Double margen_utilidad_mayorista, Double margen_utilidad_minorista, Double gravamen, String estado) {
		this.idarticulo = new SimpleIntegerProperty(idarticulo);
		this.codigobarra = new SimpleStringProperty(codigobarra);
		this.codigoempresa = new SimpleIntegerProperty(codigoempresa);
		this.codigoprodempresa = new SimpleIntegerProperty(codigoprodempresa);
		this.codigointerno = new SimpleIntegerProperty(codigointerno);
		this.leyenda = new SimpleStringProperty(leyenda);
		this.tipo_producto = new SimpleStringProperty(tipo_producto);
		this.tipo_posicion = new SimpleStringProperty(tipo_posicion);
		this.idubicacion = new SimpleIntegerProperty(idubicacion);
		this.fila = new SimpleIntegerProperty(fila);
		this.col = new SimpleIntegerProperty(col);
		this.existencia = new SimpleDoubleProperty(existencia);
		this.cantidad = new SimpleDoubleProperty(cantidad);
		this.stock_minimo = new SimpleIntegerProperty(stock_minimo);
		this.stock_optimo = new SimpleIntegerProperty(stock_optimo);
		this.stock_maximo = new SimpleIntegerProperty(stock_maximo);
		this.stock_negativo = new SimpleBooleanProperty(stock_negativo);
		this.merma = new SimpleDoubleProperty(merma);
		this.fraccionado = new SimpleBooleanProperty(fraccionado);
		this.plu = new SimpleIntegerProperty(plu);
		this.utilidad_fraccionado = new SimpleDoubleProperty(utilidad_fraccionado);
		this.leyenda_fraccionado = new SimpleStringProperty(leyenda_fraccionado);
		this.descuento_masivo = new SimpleBooleanProperty(descuento_masivo);
		this.cantidad_minima = new SimpleDoubleProperty(cantidad_minima);
		this.descuento_por_cantidad = new SimpleDoubleProperty(descuento_por_cantidad);
		this.idmarca = new SimpleIntegerProperty(idmarca);
		this.idrubro = new SimpleIntegerProperty(idrubro);
		this.idlinea = new SimpleIntegerProperty(idlinea);
		this.idunidad = new SimpleIntegerProperty(idunidad);
		this.ubicacion = new SimpleStringProperty(ubicacion);
		this.numero = new SimpleStringProperty(numero);
		this.deposito = new SimpleStringProperty(deposito);
		this.marca = new SimpleStringProperty(marca);
		this.rubro = new SimpleStringProperty(rubro);
		this.linea = new SimpleStringProperty(linea);
		this.unidad = new SimpleStringProperty(unidad);
		this.presentacion = new SimpleStringProperty(presentacion);
		this.cantUnidades = new SimpleIntegerProperty(cantUnidades);
		this.idpresentacion = new SimpleIntegerProperty(idpresentacion);
		this.valor_umedida = new SimpleDoubleProperty(valor_umedida);
		this.tipo_decremento = new SimpleStringProperty(tipo_decremento);
		this.nrodoc_proveedor = new SimpleLongProperty(nrodoc_proveedor);
		this.tipo_persona = new SimpleStringProperty(tipo_persona);
		this.fecha_compra = new SimpleObjectProperty<>(fecha_compra);
		this.fecha_baja = new SimpleObjectProperty<>(fecha_baja);
		this.fecha_actprecios = new SimpleObjectProperty<>(fecha_actprecios);
		this.precio_costo = new SimpleDoubleProperty(precio_costo);
		this.margen_utilidad_mayorista = new SimpleDoubleProperty(margen_utilidad_mayorista);
		this.margen_utilidad_minorista = new SimpleDoubleProperty(margen_utilidad_minorista);
		this.gravamen = new SimpleDoubleProperty(gravamen);
		this.estado = new SimpleStringProperty(estado);
	}
	
//	public Articulo(Integer idarticulo, String codigobarra, Integer codigoempresa, Integer codigoprodempresa,
//			Integer codigointerno, String leyenda, String tipo_producto, String tipo_posicion, Integer idubicacion,
//			Integer fila, Integer col, Double existencia, Double cantidad, Integer stock_minimo, Integer stock_optimo,
//			Integer stock_maximo, Boolean stock_negativo, Double merma, Boolean fraccionado, Integer plu,
//			Double utilidad_fraccionado, String leyenda_fraccionado, Boolean descuento_masivo, Double cantidad_minima,
//			Double descuento_por_cantidad,	Integer idmarca, Integer idrubro, Integer idlinea,
//			Integer idunidad, String ubicacion, String numero, String deposito, String marca, String rubro,
//			String linea, String unidad, String presentacion, Integer cantUnidades, Integer idpresentacion,
//			Double valor_umedida, String tipo_decremento, Long nrodoc_proveedor, String tipo_persona,
//			LocalDate fecha_compra, LocalDate fecha_baja, LocalDate fecha_actprecios, Double precio_costo,
//			Double margen_utilidad_minorista, Double margen_utilidad_mayorista, Double gravamen, String estado) {
//		this();
//		this.idarticulo.set(idarticulo);
//		this.codigobarra.set(codigobarra);
//		this.codigoempresa.set(codigoempresa);
//		this.codigoprodempresa.set(codigoprodempresa);
//		this.codigointerno.set(codigointerno);
//		this.leyenda.set(leyenda);
//		this.tipo_producto.set(tipo_producto);
//		this.tipo_posicion.set(tipo_posicion);
//		this.idubicacion.set(idubicacion);
//		this.fila.set(fila);
//		this.col.set(col);
//		this.existencia.set(existencia);
//		this.cantidad.set(cantidad);
//		this.stock_minimo.set(stock_minimo);
//		this.stock_optimo.set(stock_optimo);
//		this.stock_maximo.set(stock_maximo);
//		this.stock_negativo.set(stock_negativo);
//		this.merma.set(merma);
//		this.fraccionado.set(fraccionado);
//		this.plu.set(plu);
//		this.utilidad_fraccionado.set(utilidad_fraccionado);
//		this.leyenda_fraccionado.set(leyenda_fraccionado);
//		this.descuento_masivo.set(descuento_masivo);
//		this.cantidad_minima.set(cantidad_minima);
//		this.descuento_por_cantidad.set(descuento_por_cantidad);
//		this.idmarca.set(idmarca);
//		this.idrubro.set(idrubro);
//		this.idlinea.set(idlinea);
//		this.idunidad.set(idunidad);
//		this.ubicacion.set(ubicacion);
//		this.numero.set(numero);
//		this.deposito.set(deposito);
//		this.marca.set(marca);
//		this.rubro.set(rubro);
//		this.linea.set(linea);
//		this.unidad.set(unidad);
//		this.presentacion.set(presentacion);
//		this.cantUnidades.set(cantUnidades);
//		this.idpresentacion.set(idpresentacion);
//		this.valor_umedida.set(valor_umedida);
//		this.tipo_decremento.set(tipo_decremento);
//		this.nrodoc_proveedor.set(nrodoc_proveedor);
//		this.tipo_persona.set(tipo_persona);
//		this.fecha_compra.set(fecha_compra);
//		this.fecha_baja.set(fecha_baja);
//		this.fecha_actprecios.set(fecha_actprecios);
//		this.precio_costo.set(precio_costo);
//		this.margen_utilidad_minorista.set(margen_utilidad_minorista);
//		this.margen_utilidad_mayorista.set(margen_utilidad_mayorista);
//		this.gravamen.set(gravamen);
//		this.estado.set(estado);
//	}

	/**
	 * Sólo para consultar un BC...una mierda, lo sé.
	 * @param codigointerno
	 * @param leyenda
	 */
	
	public Articulo(Integer codigointerno, String leyenda) {
		this.codigointerno = new SimpleIntegerProperty(codigointerno);
		this.leyenda = new SimpleStringProperty(leyenda);
	}

	/**
	 * 
	 * @author ErnestoAndresZapataI
	 * Creo un constructor parametrizado para el tipo enumerado. 
	 * Un objeto de tipo enum no se puede crear explícitamente, por
	 * eso uso un constructor parametrizado. Y el constructor no 
	 * puede ser "public" o "protected", debe tener modificadores
	 * privados o predeterminados. Si lo creamos "public" o "protected",
	 * permitirá inicializar más de un objeto.
	 *
	 */
//	
//	public enum tipo_producto {
//		// Esto llamará al constructor enum 
//	    // con un argumento de tipo StringProperty
//		ESTANDAR("Estandar"),
//		SERVICIO("Servicio"),
//		KIT("Kit"),
//		MATRIZ("Matriz");
//		
//		 // declaro una variable privada para obtener valores
//		private final StringProperty tproducto;
//
//		// constructor enum - no puede ser public o protected
//		private tipo_producto(StringProperty tproducto) {
//			this.tproducto = tproducto;
//		}
//		// método getter
//		public StringProperty getTproducto() {
//			return tproducto;
//		}
//	}
//	
//	public enum tipo_posicion {
//		NORMAL("Normal"),
//		CONSIGNACION("Consignacion");
//
//		private final StringProperty tposicion;
//
//		private tipo_posicion(StringProperty tposicion) {
//			this.tposicion = tposicion;
//		}
//
//		public StringProperty getPosicion() {
//			return tposicion;
//		}
//	}
//	
//	public enum tipo_persona {
//		CLIENTE("Cliente"),
//		PROVEEDOR("Proveedor"),
//		TRANSPORTISTA("Transportista"),
//		EMPRESA("Empresa"),
//		STAFF("Staff"),
//		VENDEDOR("Vendedor");
//		
//		private final StringProperty tpersona;
//
//		private tipo_persona(StringProperty tpersona) {
//			this.tpersona = tpersona;
//		}
//
//		public StringProperty getTpersona() {
//			return tpersona;
//		}
//	}
//	
//	public enum estado_articulo {
//		ACTIVO("Activo"),
//		BAJA("Baja"),
//		OFERTA("Oferta");
//		
//		private final StringProperty earticulo;
//
//		private estado_articulo(StringProperty earticulo) {
//			this.earticulo = earticulo;
//		}
//
//		public StringProperty getEarticulo() {
//			return earticulo;
//		}
//	}

	public IntegerProperty idarticuloProperty() {
		return this.idarticulo;
	}
	
	public int getIdarticulo() {
		return this.idarticuloProperty().get();
	}
	
	public void setIdarticulo(final int idarticulo) {
		this.idarticuloProperty().set(idarticulo);
	}
	
	public StringProperty codigobarraProperty() {
		return this.codigobarra;
	}
	
	public String getCodigobarra() {
		return this.codigobarraProperty().get();
	}
	
	public void setCodigobarra(final String codigobarra) {
		this.codigobarraProperty().set(codigobarra);
	}
	
	public IntegerProperty codigointernoProperty() {
		return this.codigointerno;
	}
	
	public int getCodigointerno() {
		return this.codigointernoProperty().get();
	}
	
	public void setCodigointerno(final int codigointerno) {
		this.codigointernoProperty().set(codigointerno);
	}
	
	public StringProperty leyendaProperty() {
		return this.leyenda;
	}
	
	public String getLeyenda() {
		return this.leyendaProperty().get();
	}
	
	public void setLeyenda(final String leyenda) {
		this.leyendaProperty().set(leyenda);
	}
	
	public StringProperty tipo_productoProperty() {
		return this.tipo_producto;
	}
	
	public String getTipo_producto() {
		return this.tipo_productoProperty().get();
	}
	
	public void setTipo_producto(final String tipo_producto) {
		this.tipo_productoProperty().set(tipo_producto);
	}
	
	public StringProperty tipo_posicionProperty() {
		return this.tipo_posicion;
	}
	
	public String getTipo_posicion() {
		return this.tipo_posicionProperty().get();
	}
	
	public void setTipo_posicion(final String tipo_posicion) {
		this.tipo_posicionProperty().set(tipo_posicion);
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
	
	public StringProperty numeroProperty() {
		return this.numero;
	}
	
	public String getNumero() {
		return this.numeroProperty().get();
	}

	public void setNumero(final String numero) {
		this.numeroProperty().set(numero);
	}
	
	public StringProperty depositoProperty() {
		return this.deposito;
	}
	
	public String getDeposito() {
		return this.depositoProperty().get();
	}
	
	public void setDeposito(final String deposito) {
		this.depositoProperty().set(deposito);
	}
	
	public IntegerProperty filaProperty() {
		return this.fila;
	}
	
	public int getFila() {
		return this.filaProperty().get();
	}
	
	public void setFila(final int fila) {
		this.filaProperty().set(fila);
	}
	
	public IntegerProperty colProperty() {
		return this.col;
	}
	
	public int getCol() {
		return this.colProperty().get();
	}
	
	public void setCol(final int col) {
		this.colProperty().set(col);
	}
	
	public DoubleProperty existenciaProperty() {
		return this.existencia;
	}
	
	public double getExistencia() {
		return this.existenciaProperty().get();
	}
	
	public void setExistencia(final double existencia) {
		this.existenciaProperty().set(existencia);
	}
	
	public DoubleProperty cantidadProperty() {
		return this.cantidad;
	}
	
	public double getCantidad() {
		return this.cantidadProperty().get();
	}
	
	public void setCantidad(final double cantidad) {
		this.cantidadProperty().set(cantidad);
	}
	
	public IntegerProperty stock_minimoProperty() {
		return this.stock_minimo;
	}
	
	public int getStock_minimo() {
		return this.stock_minimoProperty().get();
	}
	
	public void setStock_minimo(final int stock_minimo) {
		this.stock_minimoProperty().set(stock_minimo);
	}
	
	public IntegerProperty stock_optimoProperty() {
		return this.stock_optimo;
	}
	
	public int getStock_optimo() {
		return this.stock_optimoProperty().get();
	}
	
	public void setStock_optimo(final int stock_optimo) {
		this.stock_optimoProperty().set(stock_optimo);
	}
	
	public IntegerProperty stock_maximoProperty() {
		return this.stock_maximo;
	}
	
	public int getStock_maximo() {
		return this.stock_maximoProperty().get();
	}
	
	public void setStock_maximo(final int stock_maximo) {
		this.stock_maximoProperty().set(stock_maximo);
	}
	
	public BooleanProperty stock_negativoProperty() {
		return this.stock_negativo;
	}
	
	public boolean isStock_negativo() {
		return this.stock_negativoProperty().get();
	}
	
	public void setStock_negativo(final boolean stock_negativo) {
		this.stock_negativoProperty().set(stock_negativo);
	}
	
	public DoubleProperty mermaProperty() {
		return this.merma;
	}
	
	public double getMerma() {
		return this.mermaProperty().get();
	}
	
	public void setMerma(final double merma) {
		this.mermaProperty().set(merma);
	}
	
	public BooleanProperty fraccionadoProperty() {
		return this.fraccionado;
	}
	
	public boolean isFraccionado() {
		return this.fraccionadoProperty().get();
	}
	
	public void setFraccionado(final boolean fraccionado) {
		this.fraccionadoProperty().set(fraccionado);
	}
	
	public IntegerProperty pluProperty() {
		return this.plu;
	}
	
	public int getPlu() {
		return this.pluProperty().get();
	}
	
	public void setPlu(final int plu) {
		this.pluProperty().set(plu);
	}
	
	public DoubleProperty utilidad_fraccionadoProperty() {
		return this.utilidad_fraccionado;
	}
	
	public double getUtilidad_fraccionado() {
		return this.utilidad_fraccionadoProperty().get();
	}
	
	public void setUtilidad_fraccionado(final double utilidad_fraccionado) {
		this.utilidad_fraccionadoProperty().set(utilidad_fraccionado);
	}
	
	public StringProperty leyenda_fraccionadoProperty() {
		return this.leyenda_fraccionado;
	}
	
	public String getLeyenda_fraccionado() {
		return this.leyenda_fraccionadoProperty().get();
	}
	
	public void setLeyenda_fraccionado(final String leyenda_fraccionado) {
		this.leyenda_fraccionadoProperty().set(leyenda_fraccionado);
	}
	
	public final BooleanProperty descuento_masivoProperty() {
		return this.descuento_masivo;
	}
	
	public final boolean isDescuento_masivo() {
		return this.descuento_masivoProperty().get();
	}
	
	public final void setDescuento_masivo(final boolean descuento_masivo) {
		this.descuento_masivoProperty().set(descuento_masivo);
	}
	
	public final DoubleProperty cantidad_minimaProperty() {
		return this.cantidad_minima;
	}
	
	public final double getCantidad_minima() {
		return this.cantidad_minimaProperty().get();
	}
	
	public final void setCantidad_minima(final double cantidad_minima) {
		this.cantidad_minimaProperty().set(cantidad_minima);
	}
	
	public final DoubleProperty descuento_por_cantidadProperty() {
		return this.descuento_por_cantidad;
	}
	
	public final double getDescuento_por_cantidad() {
		return this.descuento_por_cantidadProperty().get();
	}
	
	public final void setDescuento_por_cantidad(final double descuento_por_cantidad) {
		this.descuento_por_cantidadProperty().set(descuento_por_cantidad);
	}
	
	public IntegerProperty idmarcaProperty() {
		return this.idmarca;
	}
	
	public int getIdmarca() {
		return this.idmarcaProperty().get();
	}
	
	public void setIdmarca(final int idmarca) {
		this.idmarcaProperty().set(idmarca);
	}
	
	public IntegerProperty idrubroProperty() {
		return this.idrubro;
	}
	
	public int getIdrubro() {
		return this.idrubroProperty().get();
	}
	
	public void setIdrubro(final int idrubro) {
		this.idrubroProperty().set(idrubro);
	}
	
	public IntegerProperty idlineaProperty() {
		return this.idlinea;
	}

	public int getIdlinea() {
		return this.idlineaProperty().get();
	}

	public void setIdlinea(final int idlinea) {
		this.idlineaProperty().set(idlinea);
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
	
	public IntegerProperty idpresentacionProperty() {
		return this.idpresentacion;
	}
	
	public int getIdpresentacion() {
		return this.idpresentacionProperty().get();
	}
	
	public void setIdpresentacion(final int idpresentacion) {
		this.idpresentacionProperty().set(idpresentacion);
	}
	
	public LongProperty nrodoc_proveedorProperty() {
		return this.nrodoc_proveedor;
	}
	
	public long getNrodoc_proveedor() {
		return this.nrodoc_proveedorProperty().get();
	}
	
	public void setNrodoc_proveedor(final long nrodoc_proveedor) {
		this.nrodoc_proveedorProperty().set(nrodoc_proveedor);
	}
	
	public StringProperty tipo_personaProperty() {
		return this.tipo_persona;
	}
	
	public String getTipo_persona() {
		return this.tipo_personaProperty().get();
	}
	
	public void setTipo_persona(final String tipo_persona) {
		this.tipo_personaProperty().set(tipo_persona);
	}
	
	public DoubleProperty precio_costoProperty() {
		return this.precio_costo;
	}
	
	public double getPrecio_costo() {
		return this.precio_costoProperty().get();
	}
	
	public void setPrecio_costo(final double precio_costo) {
		this.precio_costoProperty().set(precio_costo);
	}
	
	public final DoubleProperty margen_utilidad_minoristaProperty() {
		return this.margen_utilidad_minorista;
	}
	
	public final double getMargen_utilidad_minorista() {
		return this.margen_utilidad_minoristaProperty().get();
	}
	
	public final void setMargen_utilidad_minorista(final double margen_utilidad_minorista) {
		this.margen_utilidad_minoristaProperty().set(margen_utilidad_minorista);
	}
	
	public final DoubleProperty margen_utilidad_mayoristaProperty() {
		return this.margen_utilidad_mayorista;
	}

	public final double getMargen_utilidad_mayorista() {
		return this.margen_utilidad_mayoristaProperty().get();
	}
	
	public final void setMargen_utilidad_mayorista(final double margen_utilidad_mayorista) {
		this.margen_utilidad_mayoristaProperty().set(margen_utilidad_mayorista);
	}
	
	public DoubleProperty gravamenProperty() {
		return this.gravamen;
	}
	
	public double getGravamen() {
		return this.gravamenProperty().get();
	}
	
	public void setGravamen(final double gravamen) {
		this.gravamenProperty().set(gravamen);
	}
	
	public StringProperty estadoProperty() {
		return this.estado;
	}
	
	public String getEstado() {
		return this.estadoProperty().get();
	}
	
	public void setEstado(final String estado) {
		this.estadoProperty().set(estado);
	}
	
	public DoubleProperty cant_compradaProperty() {
		return this.cant_comprada;
	}
	
	public double getCant_comprada() {
		return this.cant_compradaProperty().get();
	}
	
	public void setCant_comprada(final double cant_comprada) {
		this.cant_compradaProperty().set(cant_comprada);
	}
	
	public StringProperty AbreviaturaProperty() {
		return this.Abreviatura;
	}
	
	public String getAbreviatura() {
		return this.AbreviaturaProperty().get();
	}
	
	public void setAbreviatura(final String Abreviatura) {
		this.AbreviaturaProperty().set(Abreviatura);
	}
	
	public DoubleProperty tot_a_pagarProperty() {
		return this.tot_a_pagar;
	}
	
	public double getTot_a_pagar() {
		return this.tot_a_pagarProperty().get();
	}
	
	public void setTot_a_pagar(final double tot_a_pagar) {
		this.tot_a_pagarProperty().set(tot_a_pagar);
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

	public SimpleObjectProperty<LocalDate> fecha_compraProperty() {
		return this.fecha_compra;
	}
	
	public LocalDate getFecha_compra() {
		return this.fecha_compraProperty().get();
	}
	
	public void setFecha_compra(final LocalDate fecha_compra) {
		this.fecha_compraProperty().set(fecha_compra);
	}
	
	public SimpleObjectProperty<LocalDate> fecha_bajaProperty() {
		return this.fecha_baja;
	}
	
	public LocalDate getFecha_baja() {
		return this.fecha_bajaProperty().get();
	}
	
	public void setFecha_baja(final LocalDate fecha_baja) {
		this.fecha_bajaProperty().set(fecha_baja);
	}
	
	public SimpleObjectProperty<LocalDate> fecha_actpreciosProperty() {
		return this.fecha_actprecios;
	}
	
	public LocalDate getFecha_actprecios() {
		return this.fecha_actpreciosProperty().get();
	}

	public void setFecha_actprecios(final LocalDate fecha_actprecios) {
		this.fecha_actpreciosProperty().set(fecha_actprecios);
	}

	public IntegerProperty codigoempresaProperty() {
		return this.codigoempresa;
	}
	
	public int getCodigoempresa() {
		return this.codigoempresaProperty().get();
	}
	
	public void setCodigoempresa(final int codigoempresa) {
		this.codigoempresaProperty().set(codigoempresa);
	}
	
	public IntegerProperty codigoprodempresaProperty() {
		return this.codigoprodempresa;
	}
	
	public int getCodigoprodempresa() {
		return this.codigoprodempresaProperty().get();
	}
	
	public void setCodigoprodempresa(final int codigoprodempresa) {
		this.codigoprodempresaProperty().set(codigoprodempresa);
	}

	public StringProperty ubicacionProperty() {
		return this.ubicacion;
	}
	
	public String getUbicacion() {
		return this.ubicacionProperty().get();
	}
	
	public void setUbicacion(final String ubicacion) {
		this.ubicacionProperty().set(ubicacion);
	}
	
	public StringProperty marcaProperty() {
		return this.marca;
	}
	
	public String getMarca() {
		return this.marcaProperty().get();
	}
	
	public void setMarca(final String marca) {
		this.marcaProperty().set(marca);
	}
	
	public StringProperty rubroProperty() {
		return this.rubro;
	}
	
	public String getRubro() {
		return this.rubroProperty().get();
	}
	
	public void setRubro(final String rubro) {
		this.rubroProperty().set(rubro);
	}
	
	public StringProperty lineaProperty() {
		return this.linea;
	}

	public String getLinea() {
		return this.lineaProperty().get();
	}

	public void setLinea(final String linea) {
		this.lineaProperty().set(linea);
	}
	
	public StringProperty unidadProperty() {
		return this.unidad;
	}
	
	public String getUnidad() {
		return this.unidadProperty().get();
	}
	
	public void setUnidad(final String unidad) {
		this.unidadProperty().set(unidad);
	}
	
	public StringProperty presentacionProperty() {
		return this.presentacion;
	}
	
	public String getPresentacion() {
		return this.presentacionProperty().get();
	}

	public void setPresentacion(final String presentacion) {
		this.presentacionProperty().set(presentacion);
	}

	public IntegerProperty cantUnidadesProperty() {
		return this.cantUnidades;
	}
	
	public int getCantUnidades() {
		return this.cantUnidadesProperty().get();
	}
	
	public void setCantUnidades(final int cantUnidades) {
		this.cantUnidadesProperty().set(cantUnidades);
	}

	
	
}
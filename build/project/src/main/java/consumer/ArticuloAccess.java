package consumer;

import java.util.NoSuchElementException;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import javafx.collections.ObservableList;
import model.Articulo;

public class ArticuloAccess {
//	private static final String HOST = "192.168.0.15";
	private static final String HOST = "66.97.32.208";
	private static final int PORT = 8090;
	
	public static void getArticulos(ObservableList<Articulo> articuloData) {
		WebClient client = WebClient.create(Vertx.vertx());
		client
		.get(PORT, HOST, "/api/articulos/central/getall")
		.send(ar -> {
			if (ar.succeeded()) {
				articuloData.clear();

				HttpResponse<Buffer> response = ar.result();

				response.bodyAsJsonArray().iterator().forEachRemaining(articulo -> {
//				response.bodyAsJsonArray().forEach(articulo -> { // ESTO ANDA
					JsonObject jo = (JsonObject) articulo;
					articuloData.add(new Articulo(jo.getInteger("idarticulo"), jo.getString("codigobarra"), 
							jo.getInteger("codigoempresa"), jo.getInteger("codigoprodempresa"), 
							jo.getInteger("codigointerno"),	jo.getString("leyenda"), 
							jo.getString("tipo_producto"),	jo.getString("tipo_posicion"), 
							jo.getInteger("idubicacion"),	jo.getInteger("fila"), jo.getInteger("col"),
							jo.getDouble("existencia"), jo.getDouble("cantidad"),
							jo.getInteger("stock_minimo"), jo.getInteger("stock_optimo"),
							jo.getInteger("stock_maximo"), jo.getBoolean("stock_negativo"),
							jo.getDouble("merma"), jo.getBoolean("fraccionado"),
							jo.getInteger("plu"), jo.getDouble("utilidad_fraccionado"),
							jo.getString("leyenda_fraccionado"), jo.getInteger("idmarca"),
							jo.getInteger("idrubro"), jo.getInteger("idunidad"),
							jo.getString("ubicacion"), jo.getString("marca"),
							jo.getString("rubro"), jo.getString("unidad"),
							jo.getString("presentacion"), jo.getInteger("cantUnidades"),
							jo.getInteger("idpresentacion"), jo.getLong("nrodoc_proveedor"), 
							jo.getString("tipo_persona"), jo.getInstant("fecha_compra"), 
							jo.getInstant("fecha_baja"), jo.getInstant("fecha_actprecios"), 
							jo.getDouble("precio_costo"), jo.getDouble("utilidad"), 
							jo.getDouble("gravamen"), jo.getString("estado")
							));
				});
				System.out.println("Received response with status code " + response.statusCode());
				System.out.println(response.bodyAsJsonArray());
//				System.out.println(response.bodyAsString());
			} else {
				System.out.println("Something went wrong " + ar.cause().getMessage());
			}
		});
	}
	
	public static void getDistinctByStateBaja(ObservableList<Articulo> articuloData) {
		WebClient client = WebClient.create(Vertx.vertx());
		client
		.get(PORT, HOST, "/api/articulos/central/getalldistinctbystatebaja")
		.send(ar -> {
			if (ar.succeeded()) {
				articuloData.clear();
				
				HttpResponse<Buffer> response = ar.result();
				
				response.bodyAsJsonArray().iterator().forEachRemaining(articulo -> {
					JsonObject jo = (JsonObject) articulo;
					articuloData.add(new Articulo(jo.getInteger("idarticulo"), jo.getString("codigobarra"), 
							jo.getInteger("codigoempresa"), jo.getInteger("codigoprodempresa"), 
							jo.getInteger("codigointerno"),	jo.getString("leyenda"), 
							jo.getString("tipo_producto"),	jo.getString("tipo_posicion"), 
							jo.getInteger("idubicacion"),	jo.getInteger("fila"), jo.getInteger("col"),
							jo.getDouble("existencia"), jo.getDouble("cantidad"),
							jo.getInteger("stock_minimo"), jo.getInteger("stock_optimo"),
							jo.getInteger("stock_maximo"), jo.getBoolean("stock_negativo"),
							jo.getDouble("merma"), jo.getBoolean("fraccionado"),
							jo.getInteger("plu"), jo.getDouble("utilidad_fraccionado"),
							jo.getString("leyenda_fraccionado"), jo.getInteger("idmarca"),
							jo.getInteger("idrubro"), jo.getInteger("idunidad"),
							jo.getString("ubicacion"), jo.getString("marca"),
							jo.getString("rubro"), jo.getString("unidad"),
							jo.getString("presentacion"), jo.getInteger("cantUnidades"),
							jo.getInteger("idpresentacion"), jo.getLong("nrodoc_proveedor"), 
							jo.getString("tipo_persona"), jo.getInstant("fecha_compra"), 
							jo.getInstant("fecha_baja"), jo.getInstant("fecha_actprecios"), 
							jo.getDouble("precio_costo"), jo.getDouble("utilidad"), 
							jo.getDouble("gravamen"), jo.getString("estado")
							));
				});
				System.out.println("Received response with status code " + response.statusCode());
				System.out.println(response.bodyAsJsonArray());
//				System.out.println(response.bodyAsString());
			} else {
				System.out.println("Something went wrong " + ar.cause().getMessage());
			}
		});
	}
	
	public static Future<String> updStateBaja(String codigobarra) {
		Promise<String> promise = Promise.promise();
		WebClient client = WebClient.create(Vertx.vertx());
		client
		.put(PORT, HOST, "/api/articulos/central/updatestatebaja/" + codigobarra)
		.sendJsonObject(new JsonObject(), ar -> {
			if(ar.succeeded()) {
				promise.complete();
			} else {
				promise.fail(new NoSuchElementException("Fallo de operación de modificación " + ar.cause().getMessage()));
			}
		});
		return promise.future();
	}
	
	public static Future<Integer> insert(String codigobarra, Integer codigoempresa, Integer codigoprodempresa, Integer codigointerno, String leyenda, String tipo_producto, String tipo_posicion, Integer idubicacion, 
			Integer fila, Integer col, Double existencia, Double cantidad, Integer stock_minimo, Integer stock_optimo, Integer stock_maximo, Boolean stock_negativo, 
			Double merma, Boolean fraccionado, Integer plu,	Double utilidad_fraccionado, String leyenda_fraccionado, Integer idmarca, Integer idrubro, Integer idunidad, 
			Integer idpresentacion, Long nrodocumento,	String tipo_persona, Double precio_costo, 
//			Instant fecha_compra, Instant fecha_baja, Instant fecha_actprecios 
			Double utilidad, Double gravamen, String estado_articulo) {
		
		Promise<Integer> promise = Promise.promise();
		WebClient client = WebClient.create(Vertx.vertx());
		client
		.post(PORT, HOST, "/api/articulos/central/insertone")
		.sendJsonObject(new JsonObject()
				.put("codigobarra", codigobarra) 
				.put("codigoempresa", codigoempresa) 
				.put("codigoprodempresa", codigoprodempresa) 
				.put("codigointerno", codigointerno) 
				.put("leyenda", leyenda) 
				.put("tipo_producto", tipo_producto)
				.put("tipo_posicion", tipo_posicion)
				.put("idubicacion", idubicacion)
				.put("fila", fila)
				.put("col", col)
				.put("existencia", existencia)
				.put("cantidad", cantidad)
				.put("stock_minimo", stock_minimo)
				.put("stock_optimo", stock_optimo)
				.put("stock_maximo", stock_maximo)
				.put("stock_negativo", stock_negativo)
				.put("merma", merma)
				.put("fraccionado", fraccionado)
				.put("plu", plu)
				.put("utilidad_fraccionado", utilidad_fraccionado)
				.put("leyenda_fraccionado", leyenda_fraccionado)
				.put("idmarca", idmarca)
				.put("idrubro", idrubro)
				.put("idunidad", idunidad)
				.put("idpresentacion", idpresentacion)
				.put("nrodoc_proveedor", nrodocumento)
				.put("tpersona_proveedor", tipo_persona)
//				.put("fecha_compra", fecha_compra)
//				.put("fecha_baja", fecha_baja)
//				.put("fecha_actprecios", fecha_actprecios)
				.put("precio_costo", precio_costo)
				.put("utilidad", utilidad)
				.put("gravamen", gravamen)
				.put("estado_articulo", estado_articulo), ar -> {
					if(ar.succeeded()) {
						System.out.println(ar.result().bodyAsString());
						promise.complete(ar.result().bodyAsJsonObject().getInteger("idarticulo"));
					} else {
						promise.fail(new NoSuchElementException("Fallo de operación de Inserción " + ar.cause().getMessage()));
					}
				});
		return promise.future();
	}

	public static Future<Integer> update(int id, String codigobarra, Integer codigoempresa, Integer codigoprodempresa, Integer codigointerno, String leyenda, String tipo_producto, String tipo_posicion, Integer idubicacion, 
			Integer fila, Integer col, Double existencia, Double cantidad, Integer stock_minimo, Integer stock_optimo, Integer stock_maximo, Boolean stock_negativo, 
			Double merma, Boolean fraccionado, Integer plu,	Double utilidad_fraccionado, String leyenda_fraccionado, Integer idmarca, Integer idrubro, Integer idunidad, 
			Integer idpresentacion, Long nrodocumento,	String tipo_persona, Double precio_costo, 
//			Instant fecha_compra, Instant fecha_baja, Instant fecha_actprecios 
			Double utilidad, Double gravamen, String estado_articulo) {

		Promise<Integer> promise = Promise.promise();
		WebClient client = WebClient.create(Vertx.vertx());
		client
		.put(PORT, HOST, "/api/articulos/" + id)
		.sendJsonObject(new JsonObject()
				.put("codigobarra", codigobarra) 
				.put("codigoempresa", codigoempresa) 
				.put("codigoprodempresa", codigoprodempresa) 
				.put("codigointerno", codigointerno) 
				.put("leyenda", leyenda) 
				.put("tipo_producto", tipo_producto)
				.put("tipo_posicion", tipo_posicion)
				.put("idubicacion", idubicacion)
				.put("fila", fila)
				.put("col", col)
				.put("existencia", existencia)
				.put("cantidad", cantidad)
				.put("stock_minimo", stock_minimo)
				.put("stock_optimo", stock_optimo)
				.put("stock_maximo", stock_maximo)
				.put("stock_negativo", stock_negativo)
				.put("merma", merma)
				.put("fraccionado", fraccionado)
				.put("plu", plu)
				.put("utilidad_fraccionado", utilidad_fraccionado)
				.put("leyenda_fraccionado", leyenda_fraccionado)
				.put("idmarca", idmarca)
				.put("idrubro", idrubro)
				.put("idunidad", idunidad)
				.put("idpresentacion", idpresentacion)
				.put("nrodoc_proveedor", nrodocumento)
				.put("tpersona_proveedor", tipo_persona)
//				.put("fecha_compra", fecha_compra)
//				.put("fecha_baja", fecha_baja)
//				.put("fecha_actprecios", fecha_actprecios)
				.put("precio_costo", precio_costo)
				.put("utilidad", utilidad)
				.put("gravamen", gravamen)
				.put("estado_articulo", estado_articulo), ar -> {
					if(ar.succeeded()) {
						System.out.println(ar.result().bodyAsString());
						promise.complete(id);
					} else {
						promise.fail(new NoSuchElementException("Fallo de operación Update " + ar.cause().getMessage()));
					}
				});
		return promise.future();
	}

	public static Future<Integer> getMaxPLU() {
		Promise<Integer> promise = Promise.promise();
		WebClient client = WebClient.create(Vertx.vertx());
		client
		.get(PORT, HOST, "/api/articulos/maxplu")
		.send(ar -> {
			if (ar.succeeded()) {
				promise.complete(ar.result().bodyAsJsonObject().getInteger("plu"));
			} else {
				System.out.println("Something went wrong " + ar.cause().getMessage());
			}
		});
		return promise.future();
	}

}
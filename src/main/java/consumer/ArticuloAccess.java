package consumer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.NoSuchElementException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import javafx.collections.ObservableList;
import model.Articulo;
import model.Linea;
import model.Marca;
import model.Rubro;

public class ArticuloAccess {
//	private static final String host = "66.97.32.208";
	private static String host;
	private static Integer port;
	
	private static JsonObject jo = new JsonObject();
	
	public static void getArticulos(ObservableList<Articulo> articuloData)
			throws FileNotFoundException, IOException, ParseException {

		leerConfiguracionJson();

		WebClient client = WebClient.create(Vertx.vertx());
		client.get(port, host, "/api/articulos/central/getall").send(ar -> {
			if (ar.succeeded()) {

				articuloData.clear();

				HttpResponse<Buffer> response = ar.result();

//				response.bodyAsJsonArray().forEach(articulo -> { // ESTO ANDA
				response.bodyAsJsonArray().iterator().forEachRemaining(articulo -> {
					JsonObject jo = (JsonObject) articulo;
					articuloData.add(new Articulo(jo.getInteger("idarticulo"), jo.getString("codigobarra"),
							jo.getInteger("codigoempresa"), jo.getInteger("codigoprodempresa"),
							jo.getInteger("codigointerno"), jo.getString("leyenda"), jo.getString("tipo_producto"),
							jo.getString("tipo_posicion"), jo.getInteger("idubicacion"), jo.getInteger("fila"),
							jo.getInteger("col"), jo.getDouble("existencia"), jo.getDouble("cantidad"),
							jo.getInteger("stock_minimo"), jo.getInteger("stock_optimo"), jo.getInteger("stock_maximo"),
							jo.getBoolean("stock_negativo"), jo.getDouble("merma"), jo.getBoolean("fraccionado"),
							jo.getInteger("plu"), jo.getDouble("utilidad_fraccionado"),
							jo.getString("leyenda_fraccionado"), jo.getBoolean("descuento_masivo"), 
							jo.getDouble("cantidad_minima"), jo.getDouble("descuento_por_cantidad"),
							jo.getInteger("idmarca"), jo.getInteger("idrubro"),
							jo.getInteger("idlinea"), jo.getInteger("idunidad"), jo.getString("ubicacion"),
							jo.getString("numero"), jo.getString("deposito"), jo.getString("marca"),
							jo.getString("rubro"), jo.getString("linea"), jo.getString("unidad"),
							jo.getString("presentacion"), jo.getInteger("cantUnidades"),
							jo.getInteger("idpresentacion"), jo.getDouble("valor_umedida"),
							jo.getString("tipo_decremento"), jo.getLong("nrodoc_proveedor"),
							jo.getString("tipo_persona"), LocalDate.parse(jo.getString("fecha_compra")),
							LocalDate.parse(jo.getString("fecha_baja")), LocalDate.parse(jo.getString("fecha_actprecios")), 
							jo.getDouble("precio_costo"), jo.getDouble("margen_utilidad_mayorista"), 
							jo.getDouble("margen_utilidad_minorista"), jo.getDouble("gravamen"), jo.getString("estado")));
				});
				System.out.println("Received response with status code " + response.statusCode());
//				System.out.println(response.bodyAsJsonArray());
			} else {
				System.out.println("Something went wrong " + ar.cause().getMessage());
			}
		});
	}
	
	public static void getRubros(ObservableList<Rubro> rubroData) throws FileNotFoundException, IOException, ParseException {

		leerConfiguracionJson();

		WebClient client = WebClient.create(Vertx.vertx());
		client.get(port, host, "/api/articulos/central/rubros/getall").send(ar -> {
			if (ar.succeeded()) {
				rubroData.clear();
				HttpResponse<Buffer> response = ar.result();
				response.bodyAsJsonArray().forEach(rubro -> {
					JsonObject jo = (JsonObject) rubro;
					rubroData.add(new Rubro(jo.getInteger("idrubro"), jo.getString("descripcion")));
				});
				System.out.println("Received response with status code " + response.statusCode());
				System.out.println(response.bodyAsJsonArray());
			} else {
				System.out.println("Something went wrong " + ar.cause().getMessage());
			}
		});
	}
	
	public static void getLineas(ObservableList<Linea> listLineas) throws FileNotFoundException, IOException, ParseException {
		
		leerConfiguracionJson();
		
		WebClient client = WebClient.create(Vertx.vertx());
		client.get(port, host, "/api/articulos/central/lineas/getall").send(ar -> {
			if (ar.succeeded()) {
				listLineas.clear();
				HttpResponse<Buffer> response = ar.result();
				response.bodyAsJsonArray().forEach(linea -> {
					JsonObject jo = (JsonObject) linea;
					listLineas.add(new Linea(jo.getInteger("idlinea"), jo.getString("descripcion"), jo.getString("rubro")));
				});
				System.out.println("Received response with status code " + response.statusCode());
				System.out.println(response.bodyAsJsonArray());
			} else {
				System.out.println("Something went wrong " + ar.cause().getMessage());
			}
		});
	}
	
	public static void getMarcas(ObservableList<Marca> listMarcas) throws FileNotFoundException, IOException, ParseException {
		
		leerConfiguracionJson();
		
		WebClient client = WebClient.create(Vertx.vertx());
		client.get(port, host, "/api/articulos/central/marcas/getall").send(ar -> {
			if (ar.succeeded()) {
				listMarcas.clear();
				HttpResponse<Buffer> response = ar.result();
				response.bodyAsJsonArray().forEach(marca -> {
					JsonObject jo = (JsonObject) marca;
					listMarcas.add(new Marca(jo.getInteger("idmarca"), jo.getString("descripcion")));
				});
				System.out.println("Received response with status code " + response.statusCode());
				System.out.println(response.bodyAsJsonArray());
			} else {
				System.out.println("Something went wrong " + ar.cause().getMessage());
			}
		});
	}
	
	public static Future<String> getForValidateCodigoBarra(String codigoBarra) throws FileNotFoundException, IOException, ParseException {
		Promise<String> promise = Promise.promise();

		leerConfiguracionJson();
		
		WebClient client = WebClient.create(Vertx.vertx());
		client
		.get(port, host, "/api/articulos/central/getforvalidatecodigobarra/" + codigoBarra)
		.send(ar -> {
			if (ar.succeeded()) {
				
				HttpResponse<Buffer> response = ar.result();
                response.bodyAsJsonArray().forEach(art -> { //hay uno solo
                    JsonObject jo = (JsonObject) art;
                    promise.complete(jo.getString("leyenda"));
                });
			} else {
				System.out.println("Something went wrong " + ar.cause().getMessage());
			}
		});
		return promise.future();
	}
	
	public static Future<String> getForValidateCodigoInterno(String codigoInterno) throws FileNotFoundException, IOException, ParseException {
		Promise<String> promise = Promise.promise();
		
		leerConfiguracionJson();
		
		WebClient client = WebClient.create(Vertx.vertx());
		client
		.get(port, host, "/api/articulos/central/getforvalidatecodigointerno/" + codigoInterno)
		.send(ar -> {
			if (ar.succeeded()) {
				HttpResponse<Buffer> response = ar.result();
				
				response.bodyAsJsonArray().iterator().forEachRemaining(art -> { //hay uno solo...algunas veces (fuck)
					jo = (JsonObject) art;
				});
				promise.complete(jo.getString("leyenda"));
			} else {
				System.out.println("Something went wrong " + ar.cause().getMessage());
			}
		});
		return promise.future();
	}
	
	public static void getDistinctByStateBaja(ObservableList<Articulo> articuloData)
			throws FileNotFoundException, IOException, ParseException {

		leerConfiguracionJson();

		WebClient client = WebClient.create(Vertx.vertx());
		client.get(port, host, "/api/articulos/central/getalldistinctbystatebaja").send(ar -> {
			if (ar.succeeded()) {
				articuloData.clear();

				HttpResponse<Buffer> response = ar.result();

				System.out.println(response.bodyAsJsonArray());
				
				response.bodyAsJsonArray().iterator().forEachRemaining(articulo -> {
					JsonObject jo = (JsonObject) articulo;
					articuloData.add(new Articulo(jo.getInteger("idarticulo"), jo.getString("codigobarra"),
							jo.getInteger("codigoempresa"), jo.getInteger("codigoprodempresa"),
							jo.getInteger("codigointerno"), jo.getString("leyenda"), jo.getString("tipo_producto"),
							jo.getString("tipo_posicion"), jo.getInteger("idubicacion"), jo.getInteger("fila"),
							jo.getInteger("col"), jo.getDouble("existencia"), jo.getDouble("cantidad"),
							jo.getInteger("stock_minimo"), jo.getInteger("stock_optimo"), jo.getInteger("stock_maximo"),
							jo.getBoolean("stock_negativo"), jo.getDouble("merma"), jo.getBoolean("fraccionado"),
							jo.getInteger("plu"), jo.getDouble("utilidad_fraccionado"),
							jo.getString("leyenda_fraccionado"), jo.getBoolean("descuento_masivo"), 
							jo.getDouble("cantidad_minima"), jo.getDouble("descuento_por_cantidad"),
							jo.getInteger("idmarca"), jo.getInteger("idrubro"),
							jo.getInteger("idlinea"), jo.getInteger("idunidad"), jo.getString("ubicacion"),
							jo.getString("numero"), jo.getString("deposito"), jo.getString("marca"),
							jo.getString("rubro"), jo.getString("linea"), jo.getString("unidad"),
							jo.getString("presentacion"), jo.getInteger("cantUnidades"),
							jo.getInteger("idpresentacion"), jo.getDouble("valor_umedida"),
							jo.getString("tipo_decremento"), jo.getLong("nrodoc_proveedor"),
							jo.getString("tipo_persona"), LocalDate.parse(jo.getString("fecha_compra")),
							LocalDate.parse(jo.getString("fecha_baja")),
							LocalDate.parse(jo.getString("fecha_actprecios")), jo.getDouble("precio_costo"),
							jo.getDouble("margen_utilidad_mayorista"), jo.getDouble("margen_utilidad_minorista"), 
							jo.getDouble("gravamen"), jo.getString("estado")));
				});
				System.out.println("Received response with status code " + response.statusCode());
				System.out.println(response.bodyAsJsonArray());
				// System.out.println(response.bodyAsString());
			} else {
				System.out.println("Something went wrong " + ar.cause().getMessage());
			}
		});
	}
	
	public static Future<String> updStateBaja(String codigobarra) throws FileNotFoundException, IOException, ParseException {
		
		leerConfiguracionJson();
		
		Promise<String> promise = Promise.promise();
		WebClient client = WebClient.create(Vertx.vertx());
		client
		.put(port, host, "/api/articulos/central/updatestatebaja/" + codigobarra)
		.sendJsonObject(new JsonObject(), ar -> {
			if(ar.succeeded()) {
				promise.complete();
			} else {
				promise.fail(new NoSuchElementException("Fallo de operación de modificación " + ar.cause().getMessage()));
			}
		});
		return promise.future();
	}
	
	public static Future<Integer> insert(String codigobarra, Integer codigoempresa, Integer codigoprodempresa,
			Integer codigointerno, String leyenda, String tipo_producto, String tipo_posicion, Integer idubicacion,
			Integer fila, Integer col, Double existencia, Double cantidad, Integer stock_minimo, Integer stock_optimo,
			Integer stock_maximo, Boolean stock_negativo, Double merma, Boolean fraccionado, Integer plu,
			Double utilidad_fraccionado, String leyenda_fraccionado, Boolean descuento_masivo, Double cantidad_minima,
			Double descuento_por_cantidad, Integer idmarca, Integer idrubro, Integer idlinea,
			Integer idunidad, Integer idpresentacion, Long nrodocumento, String tipo_persona, Double precio_costo,
			String fecha_compra, String fecha_baja, String fecha_actprecios, Double margen_utilidad_mayorista, 
			Double margen_utilidad_minorista, Double gravamen, String estado) 
					throws FileNotFoundException, IOException, ParseException {

		leerConfiguracionJson();

		Promise<Integer> promise = Promise.promise();
		WebClient client = WebClient.create(Vertx.vertx());
		client.post(port, host, "/api/articulos/central/insertone")
				.sendJsonObject(new JsonObject().put("codigobarra", codigobarra).put("codigoempresa", codigoempresa)
						.put("codigoprodempresa", codigoprodempresa).put("codigointerno", codigointerno)
						.put("leyenda", leyenda).put("tipo_producto", tipo_producto).put("tipo_posicion", tipo_posicion)
						.put("idubicacion", idubicacion).put("fila", fila).put("col", col).put("existencia", existencia)
						.put("cantidad", cantidad).put("stock_minimo", stock_minimo).put("stock_optimo", stock_optimo)
						.put("stock_maximo", stock_maximo).put("stock_negativo", stock_negativo).put("merma", merma)
						.put("fraccionado", fraccionado).put("plu", plu)
						.put("utilidad_fraccionado", utilidad_fraccionado)
						.put("leyenda_fraccionado", leyenda_fraccionado).put("descuento_masivo", descuento_masivo)
						.put("cantidad_minima", cantidad_minima).put("descuento_por_cantidad", descuento_por_cantidad)
						.put("idmarca", idmarca).put("idrubro", idrubro)
						.put("idlinea", idlinea).put("idunidad", idunidad).put("idpresentacion", idpresentacion)
						.put("nrodoc_proveedor", nrodocumento).put("tipo_persona", tipo_persona).put("precio_costo", precio_costo)
						.put("fecha_compra", fecha_compra).put("fecha_baja", fecha_baja).put("fecha_actprecios", fecha_actprecios)
						.put("margen_utilidad_mayorista", margen_utilidad_mayorista).put("margen_utilidad_minorista", margen_utilidad_minorista)
						.put("gravamen", gravamen).put("estado", estado),
						ar -> {
							if (ar.succeeded()) {
								System.out.println(ar.result().bodyAsString());
//								promise.complete(ar.result().bodyAsJsonObject().getInteger("idarticulo"));
								promise.complete();
							} else {
								promise.fail(new NoSuchElementException(
										"Fallo de operación de Inserción " + ar.cause().getMessage()));
							}
						});
		return promise.future();
	}

	public static Future<Integer> updateAll(int idarticulo, String codigobarra, Integer codigoempresa,
			Integer codigoprodempresa, Integer codigointerno, String leyenda, String tipo_producto,
			String tipo_posicion, Integer idubicacion, Integer fila, Integer col, Double existencia, Double cantidad,
			Integer stock_minimo, Integer stock_optimo, Integer stock_maximo, Boolean stock_negativo, Double merma,
			Boolean fraccionado, Integer plu, Double utilidad_fraccionado, String leyenda_fraccionado, 
			Boolean descuento_masivo, Double cantidad_minima, Double descuento_por_cantidad, Integer idmarca,
			Integer idrubro, Integer idlinea, Integer idunidad, Integer idpresentacion, Long nrodocumento,
			String tipo_persona, Double precio_costo, String fecha_compra, String fecha_baja, String fecha_actprecios, 
			Double margen_utilidad_mayorista, Double margen_utilidad_minorista, Double gravamen, String estado)
					throws FileNotFoundException, IOException, ParseException {

		leerConfiguracionJson();

		Promise<Integer> promise = Promise.promise();
		WebClient client = WebClient.create(Vertx.vertx());
		client.put(port, host, "/api/articulos/central/updateall/" + idarticulo)
				// .put(port, host, "/api/articulos/central/updateall/" + idarticulo + "/" +
				// codigobarra + "/" + codigoempresa + "/" + codigoprodempresa +
				// "/" + codigointerno + "/" + leyenda + "/" + tipo_producto + "/" +
				// tipo_posicion + "/" + idubicacion + "/" + fila + "/" + col +
				// "/" + existencia + "/" + cantidad + "/" + stock_minimo + "/" + stock_optimo +
				// "/" + stock_maximo + "/" + stock_negativo +
				// "/" + merma + "/" + fraccionado + "/" + plu + "/" + utilidad_fraccionado +
				// "/" + leyenda_fraccionado + "/" + idmarca +
				// "/" + idrubro + "/" + idlinea + "/" + idunidad + "/" + idpresentacion + "/" +
				// nrodocumento + "/" + tipo_persona + "/" + precio_costo +
				// "/" + utilidad + "/" + gravamen + "/" + estado)
				.sendJsonObject(new JsonObject().put("codigobarra", codigobarra).put("codigoempresa", codigoempresa)
						.put("codigoprodempresa", codigoprodempresa).put("codigointerno", codigointerno)
						.put("leyenda", leyenda).put("tipo_producto", tipo_producto).put("tipo_posicion", tipo_posicion)
						.put("idubicacion", idubicacion).put("fila", fila).put("col", col).put("existencia", existencia)
						.put("cantidad", cantidad).put("stock_minimo", stock_minimo).put("stock_maximo", stock_maximo)
						.put("stock_optimo", stock_optimo).put("stock_negativo", stock_negativo).put("merma", merma)
						.put("fraccionado", fraccionado).put("plu", plu)
						.put("utilidad_fraccionado", utilidad_fraccionado)
						.put("leyenda_fraccionado", leyenda_fraccionado).put("descuento_masivo", descuento_masivo)
						.put("cantidad_minima", cantidad_minima).put("descuento_por_cantidad", descuento_por_cantidad)
						.put("idmarca", idmarca).put("idrubro", idrubro)
						.put("idlinea", idlinea).put("idunidad", idunidad).put("idpresentacion", idpresentacion)
						.put("nrodoc_proveedor", nrodocumento).put("tipo_persona", tipo_persona)
						.put("fecha_compra", fecha_compra).put("fecha_baja", fecha_baja)
						.put("fecha_actprecios", fecha_actprecios).put("precio_costo", precio_costo)
						.put("margen_utilidad_mayorista", margen_utilidad_mayorista).put("margen_utilidad_minorista", margen_utilidad_minorista)
						.put("gravamen", gravamen).put("estado", estado)
						.put("idarticulo", idarticulo), ar -> {
							if (ar.succeeded()) {
								System.out.println(ar.result().bodyAsString());
								promise.complete();
							} else {
								promise.fail(new NoSuchElementException(
										"Fallo de operación Update " + ar.cause().getMessage()));
							}
						});
		return promise.future();
	}

	public static Future<Integer> getMaxPLU() throws FileNotFoundException, IOException, ParseException {

		leerConfiguracionJson();

		Promise<Integer> promise = Promise.promise();
		WebClient client = WebClient.create(Vertx.vertx());
		client.get(port, host, "/api/articulos/maxplu").send(ar -> {
			if (ar.succeeded()) {
				promise.complete(ar.result().bodyAsJsonObject().getInteger("plu"));
			} else {
				System.out.println("Something went wrong " + ar.cause().getMessage());
			}
		});
		return promise.future();
	}
	
	private static void leerConfiguracionJson() throws FileNotFoundException, IOException, ParseException {
		// for static access, uses the class name directly
//		InputStream is = ArticuloAccess.class.getResourceAsStream("/application.json"); // FUNCION PARA EL IDE
		InputStream is = ArticuloAccess.class.getResourceAsStream("/resources/application.json");
		String text = new String(is.readAllBytes(), StandardCharsets.UTF_8); // readAllBytes a partir de Java 9 		
		// parsear a Java
	    JSONParser jsonParser = new JSONParser();		
	    JSONObject jsonObject = (JSONObject) jsonParser.parse(text);
		
	    host = jsonObject.get("host").toString();
		port = Integer.valueOf(jsonObject.get("port").toString());
	}

}
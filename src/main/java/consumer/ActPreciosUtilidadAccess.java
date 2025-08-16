package consumer;

/**
 * Módulo que contiene las actualizaciones de precios modificando el 
 * margen de utilidad utilizando varios filtros
 * 
 * 1. sólo productos fraccionados
 * 2. todos los productos
 * 3. por Rubro
 * 4. por Línea
 * 5. por Marca
 * 
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;

public class ActPreciosUtilidadAccess {
//	private static final String host = "66.97.32.208";
	private static String host;
	private static Integer port;
	
	
	public static Future<Integer> updateUtilidad(Double utilidad) {
		Promise<Integer> promise = Promise.promise();

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient wc = WebClient.create(Vertx.vertx());
		wc.put(port, host, "/api/articulos/central/updateUtilidad/" + utilidad).sendJsonObject(new JsonObject().put("utilidad", utilidad), ar -> {
			if (ar.succeeded()) {
				System.out.println(ar.result().bodyAsString());
				// Si todo está bien devuelvo 1
				promise.complete(1);
			} else {
				promise.fail(new NoSuchElementException("Fallo de operación Update " + ar.cause().getMessage()));
			}
		});
		return promise.future();
	}
	
	public static Future<Integer> updateUtilidadFraccionado(Double utilidadFraccionado) {
		Promise<Integer> promise = Promise.promise();

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient wc = WebClient.create(Vertx.vertx());
		wc.put(port, host, "/api/articulos/central/updateUtilidadFraccionado/" + utilidadFraccionado).sendJsonObject(new JsonObject().put("utilidad_fraccionado", utilidadFraccionado), ar -> {
			if (ar.succeeded()) {
				System.out.println(ar.result().bodyAsString());
				// Si todo está bien devuelvo 1
				promise.complete(1);
			} else {
				promise.fail(new NoSuchElementException("Fallo de operación Update " + ar.cause().getMessage()));
			}
		});
		return promise.future();
	}
	
	public static Future<Integer> updateUtilidadPorRubro(Integer idrubro, Double utilidad) {
		Promise<Integer> promise = Promise.promise();

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient wc = WebClient.create(Vertx.vertx());
		wc.put(port, host, "/api/articulos/central/updateRubro/" + utilidad + "/" + idrubro)
				.sendJsonObject(new JsonObject().put("utilidad", utilidad).put("idrubro", idrubro), ar -> {
					if (ar.succeeded()) {
						System.out.println(ar.result().bodyAsString());
						// Si todo está bien devuelvo 1
						promise.complete(1);
					} else {
						promise.fail(
								new NoSuchElementException("Fallo de operación Update " + ar.cause().getMessage()));
					}
				});
		return promise.future();
	}
	
	public static Future<Integer> updateUtilidadPorLinea(Integer idlinea, Double utilidad) {
		Promise<Integer> promise = Promise.promise();
		
		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
		WebClient wc = WebClient.create(Vertx.vertx());
		wc.put(port, host, "/api/articulos/central/updateLinea/" + utilidad + "/" + idlinea)
		.sendJsonObject(new JsonObject().put("utilidad", utilidad).put("idlinea", idlinea), ar -> {
			if (ar.succeeded()) {
				System.out.println(ar.result().bodyAsString());
				// Si todo está bien devuelvo 1
				promise.complete(1);
			} else {
				promise.fail(
						new NoSuchElementException("Fallo de operación Update " + ar.cause().getMessage()));
			}
		});
		return promise.future();
	}
	
	public static Future<Integer> updateUtilidadPorMarca(Integer idmarca, Double utilidad) {
		Promise<Integer> promise = Promise.promise();
		
		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
		WebClient wc = WebClient.create(Vertx.vertx());
		wc.put(port, host, "/api/articulos/central/updateMarca/" + utilidad + "/" + idmarca)
		.sendJsonObject(new JsonObject().put("utilidad", utilidad).put("idmarca", idmarca), ar -> {
			if (ar.succeeded()) {
				System.out.println(ar.result().bodyAsString());
				// Si todo está bien devuelvo 1
				promise.complete(1);
			} else {
				promise.fail(
						new NoSuchElementException("Fallo de operación Update " + ar.cause().getMessage()));
			}
		});
		return promise.future();
	}
	
	private static void leerConfiguracionJson() throws FileNotFoundException, IOException, ParseException {
		String path = ConfiguracionAccess.class.getClassLoader().getResource("application.json").getPath();
		JSONObject json = (JSONObject) new JSONParser().parse(new FileReader(path));
		host = (String) json.get("host");
		String portParsed = (String) json.get("port");
		port = Integer.valueOf(portParsed);
	}

}
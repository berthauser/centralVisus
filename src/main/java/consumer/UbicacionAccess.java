package consumer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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
import model.Deposito;
import model.Ubicacion;

public class UbicacionAccess {
	private static String host;
	private static Integer port;
	
	public static void getUbicaciones(ObservableList<Ubicacion> ubicacionData) {

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient client = WebClient.create(Vertx.vertx());
		client.get(port, host, "/api/ubicaciones").send(ar -> {
			if (ar.succeeded()) {
				ubicacionData.clear();
				HttpResponse<Buffer> response = ar.result();
				response.bodyAsJsonArray().forEach(ubicacion -> {
					JsonObject jo = (JsonObject) ubicacion;
					ubicacionData.add(new Ubicacion(jo.getInteger("idubicacion"), jo.getString("descripcion"),
							jo.getString("numero"), jo.getString("deposito"), jo.getInteger("iddeposito"),
							jo.getInteger("tot_filas"), jo.getInteger("tot_columnas")));
				});
				System.out.println("Received response with status code " + response.statusCode());
				System.out.println(response.bodyAsJsonArray());

			} else {
				System.out.println("Something went wrong " + ar.cause().getMessage());
			}
		});
	}
	
	public static Future<String> delete(Ubicacion ubicacion) {

		Promise<String> promise = Promise.promise();

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient client = WebClient.create(Vertx.vertx());
		client.delete(port, host, "/api/ubicaciones/" + ubicacion.getIdubicacion()).send(ar -> {
			if (ar.succeeded()) {
				promise.complete("La Ubicación" + " - " + ubicacion.getDescripcion() + " fue borrada");
			} else {
				promise.fail(new NoSuchElementException("Fallo de operación de borrado " + ar.cause().getMessage()));
			}
		});
		return promise.future();
	}
	
	public static Future<Integer> insert(String descripcion, String numero, Integer iddeposito, Integer tot_filas,
			Integer tot_columnas) {

		Promise<Integer> promise = Promise.promise();

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient client = WebClient.create(Vertx.vertx());
		client.post(port, host, "/api/ubicaciones/")
				.sendJsonObject(new JsonObject().put("descripcion", descripcion).put("numero", numero)
						.put("iddeposito", iddeposito).put("tot_filas", tot_filas).put("tot_columnas", tot_columnas),
						ar -> {
							if (ar.succeeded()) {
								System.out.println(ar.result().bodyAsString());
								promise.complete(ar.result().bodyAsJsonObject().getInteger("idubicacion"));
							} else {
								promise.fail(new NoSuchElementException(
										"Fallo de operación de Inserción " + ar.cause().getMessage()));
							}
						});
		return promise.future();
	}

	public static Future<Integer> update(int id, String descripcion, String numero, Integer iddeposito,
			Integer tot_filas, Integer tot_columnas) {

		Promise<Integer> promise = Promise.promise();

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient client = WebClient.create(Vertx.vertx());
		client.put(port, host, "/api/ubicaciones/" + id)
				.sendJsonObject(new JsonObject().put("descripcion", descripcion).put("numero", numero)
						.put("iddeposito", iddeposito).put("tot_filas", tot_filas).put("tot_columnas", tot_columnas),
						ar -> {
							if (ar.succeeded()) {
								System.out.println(ar.result().bodyAsString());
								promise.complete(id);
							} else {
								promise.fail(new NoSuchElementException(
										"Fallo de operación Update " + ar.cause().getMessage()));
							}
						});
		return promise.future();
	}
	
	public static void getDepositos(ObservableList<Deposito> depositoData) {

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient client = WebClient.create(Vertx.vertx());
		client.get(port, host, "/api/depositos").send(ar -> {
			if (ar.succeeded()) {
				depositoData.clear();
				HttpResponse<Buffer> response = ar.result();
				response.bodyAsJsonArray().forEach(deposito -> {
					JsonObject jo = (JsonObject) deposito;
					depositoData.add(new Deposito(jo.getInteger("iddeposito"), jo.getString("descripcion")));
				});
				System.out.println("Received response with status code " + response.statusCode());
				System.out.println(response.bodyAsJsonArray());
			} else {
				System.out.println("Something went wrong " + ar.cause().getMessage());
			}
		});
	}
	
	private static void leerConfiguracionJson() throws FileNotFoundException, IOException, ParseException {
		// for static access, uses the class name directly
//		InputStream is = UbicacionAccess.class.getResourceAsStream("/application.json");
		InputStream is = UbicacionAccess.class.getResourceAsStream("/resources/application.json");
		String text = new String(is.readAllBytes(), StandardCharsets.UTF_8); // readAllBytes: a partir de Java 9 		
		// parsear a Java
	    JSONParser jsonParser = new JSONParser();		
	    JSONObject jsonObject = (JSONObject) jsonParser.parse(text);
		
	    host = jsonObject.get("host").toString();
		port = Integer.valueOf(jsonObject.get("port").toString());
	}

}
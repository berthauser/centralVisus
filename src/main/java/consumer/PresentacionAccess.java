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
import model.Presentacion;

public class PresentacionAccess {
	private static String host;
	private static Integer port;
	
	public static void getPresentaciones(ObservableList<Presentacion> presentacionData) {

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient client = WebClient.create(Vertx.vertx());
		client.get(port, host, "/api/presentaciones").send(ar -> {
			if (ar.succeeded()) {
				presentacionData.clear();
				HttpResponse<Buffer> response = ar.result();
				response.bodyAsJsonArray().forEach(presentacion -> {
					JsonObject jo = (JsonObject) presentacion;
					presentacionData.add(new Presentacion(jo.getInteger("idpresentacion"), jo.getString("contenedor"),
							jo.getDouble("unidades"), jo.getDouble("valor_umedida"), jo.getString("tipo_Decremento")));
				});
				System.out.println("Received response with status code " + response.statusCode());
				System.out.println(response.bodyAsJsonArray());
//				System.out.println(response.bodyAsString());
			} else {
				System.out.println("Something went wrong " + ar.cause().getMessage());
			}
		});
	}

	public static void getOnePresentacion(ObservableList<Presentacion> presentacionData) {

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient client = WebClient.create(Vertx.vertx());
		client.get(port, host, "/api/presentaciones").send(ar -> {
			if (ar.succeeded()) {
				presentacionData.clear();
				HttpResponse<Buffer> response = ar.result();
				response.bodyAsJsonArray().forEach(presentacion -> {
					JsonObject jo = (JsonObject) presentacion;
					presentacionData.add(new Presentacion(jo.getInteger("idpresentacion"), jo.getString("contenedor"),
							jo.getDouble("unidades"), jo.getDouble("valor_umedida"), jo.getString("tipo_Decremento")));
				});
				System.out.println("Received response with status code " + response.statusCode());
				System.out.println(response.bodyAsJsonArray());
//				System.out.println(response.bodyAsString());
			} else {
				System.out.println("Something went wrong " + ar.cause().getMessage());
			}
		});
	}
	
	public static Future<String> delete(Presentacion presentacion) {
		Promise<String> promise = Promise.promise();

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient client = WebClient.create(Vertx.vertx());
		client.delete(port, host, "/api/presentaciones/" + presentacion.getIdpresentacion()).send(ar -> {
			if (ar.succeeded()) {
				promise.complete("La Presentacion" + " - " + presentacion.getContenedor() + " fue borrada");
			} else {
				promise.fail(new NoSuchElementException("Fallo de operación de borrado " + ar.cause().getMessage()));
			}
		});
		return promise.future();
	}
	
	public static Future<Integer> insert(String contenedor, Double unidades, Double valor_umedida, String decremento) {
		
		Promise<Integer> promise = Promise.promise();

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient client = WebClient.create(Vertx.vertx());
		client.post(port, host, "/api/presentaciones/").sendJsonObject(new JsonObject().put("contenedor", contenedor)
				.put("unidades", unidades).put("valor_umedida", valor_umedida).put("tipo_Decremento", decremento),
				ar -> {
					if (ar.succeeded()) {
						System.out.println(ar.result().bodyAsString());
						promise.complete(ar.result().bodyAsJsonObject().getInteger("idpresentacion"));
					} else {
						promise.fail(new NoSuchElementException(
								"Fallo de operación de Inserción " + ar.cause().getMessage()));
					}
				});
		return promise.future();
	}

	public static Future<Integer> update(int id, String contenedor, Double unidades, Double valor_umedida,
			String decremento) {

		Promise<Integer> promise = Promise.promise();

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient client = WebClient.create(Vertx.vertx());
		client.put(port, host, "/api/presentaciones/" + id)
				.sendJsonObject(new JsonObject().put("contenedor", contenedor).put("unidades", unidades)
						.put("valor_umedida", valor_umedida).put("tipo_decremento", decremento), ar -> {
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
	
	private static void leerConfiguracionJson() throws FileNotFoundException, IOException, ParseException {
		// for static access, uses the class name directly
//		InputStream is = PresentacionAccess.class.getResourceAsStream("/application.json"); // FUNCIONA PARA EL IDE
		InputStream is = PresentacionAccess.class.getResourceAsStream("/resources/application.json");
		String text = new String(is.readAllBytes(), StandardCharsets.UTF_8); // readAllBytes a partir de Java 9 		

		// parsear a Java
	    JSONParser jsonParser = new JSONParser();		
	    JSONObject jsonObject = (JSONObject) jsonParser.parse(text);
		
	    host = jsonObject.get("host").toString();
		port = Integer.valueOf(jsonObject.get("port").toString());
	}
	
}
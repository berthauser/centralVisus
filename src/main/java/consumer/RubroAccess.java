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
import model.Rubro;

public class RubroAccess {
	private static String host;
	private static Integer port;
	
	public static void getRubros(ObservableList<Rubro> rubroData) {

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient wc = WebClient.create(Vertx.vertx());
		wc.get(port, host, "/api/rubros").send(ar -> {
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
	
	public static Future<String> deleteRubro(Rubro rubro) {

		Promise<String> promise = Promise.promise();

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient client = WebClient.create(Vertx.vertx());
		client.delete(port, host, "/api/rubros/" + rubro.getIdrubro()).send(ar -> {
			if (ar.succeeded()) {
				promise.complete("El Rubro" + " - " + rubro.getDescripcion() + " fue borrado");
			} else {
				promise.fail(new NoSuchElementException("Fallo de operación de borrado " + ar.cause().getMessage()));
			}
		});
		return promise.future();
	}

	public static Future<Integer> insertRubro(String descripcion) {
		
		Promise<Integer> promise = Promise.promise();

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient client = WebClient.create(Vertx.vertx());
		client.post(port, host, "/api/rubros/").sendJsonObject(new JsonObject().put("descripcion", descripcion), ar -> {
			if (ar.succeeded()) {
				System.out.println(ar.result().bodyAsString());
				promise.complete(ar.result().bodyAsJsonObject().getInteger("idrubro"));
			} else {
				promise.fail(new NoSuchElementException("Fallo de operación de Inserción " + ar.cause().getMessage()));
			}
		});
		return promise.future();
	}

	public static Future<Integer> updateRubro(int id, String descripcion) {

		Promise<Integer> promise = Promise.promise();

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient client = WebClient.create(Vertx.vertx());
		client.put(port, host, "/api/rubros/" + id).sendJsonObject(new JsonObject().put("descripcion", descripcion),
				ar -> {
					if (ar.succeeded()) {
						System.out.println(ar.result().bodyAsString());
						promise.complete(id);
					} else {
						promise.fail(
								new NoSuchElementException("Fallo de operación Update " + ar.cause().getMessage()));
					}
				});
		return promise.future();
	}
	
	private static void leerConfiguracionJson() throws FileNotFoundException, IOException, ParseException {
		InputStream is = RubroAccess.class.getResourceAsStream("/resources/application.json");
//		InputStream is = BancoAccess.class.getResourceAsStream("/application.json"); // FUNCIONA PARA EL IDE
		String text = new String(is.readAllBytes(), StandardCharsets.UTF_8); // readAllBytes a partir de Java 9 		
		// parsear a Java
	    JSONParser jsonParser = new JSONParser();		
	    JSONObject jsonObject = (JSONObject) jsonParser.parse(text);
		
	    host = jsonObject.get("host").toString();
		port = Integer.valueOf(jsonObject.get("port").toString());
	}
	
}
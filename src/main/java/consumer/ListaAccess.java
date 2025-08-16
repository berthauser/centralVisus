package consumer;

import java.io.FileNotFoundException;
import java.io.FileReader;
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
import model.Lista;

public class ListaAccess {
	private static String host;
	private static Integer port;
	
	public static void getListas(ObservableList<Lista> listaData) {

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient client = WebClient.create(Vertx.vertx());
		client.get(port, host, "/api/listas").send(ar -> {
			if (ar.succeeded()) {
				listaData.clear();
				HttpResponse<Buffer> response = ar.result();
				response.bodyAsJsonArray().forEach(lista -> {
					JsonObject jo = (JsonObject) lista;
					listaData.add(new Lista(jo.getInteger("idlista"), jo.getString("descripcion"), jo.getDouble("margen")));
				});
				System.out.println("Received response with status code " + response.statusCode());
				System.out.println(response.bodyAsJsonArray());
			} else {
				System.out.println("Something went wrong " + ar.cause().getMessage());
			}
		});
	}
	
	public static Future<String> deleteLista(Lista lista) {

		Promise<String> promise = Promise.promise();

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient client = WebClient.create(Vertx.vertx());
		client.delete(port, host, "/api/listas/" + lista.getIdlista()).send(ar -> {
			if (ar.succeeded()) {
				promise.complete("La Lista" + " - " + lista.getDescripcion() + " fue borrada");
			} else {
				promise.fail(new NoSuchElementException("Fallo de operación de borrado " + ar.cause().getMessage()));
			}
		});
		return promise.future();
	}
	
	public static Future<Integer> insertLista(String descripcion, Double margen) {

		Promise<Integer> promise = Promise.promise();

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient client = WebClient.create(Vertx.vertx());
		client.post(port, host, "/api/listas/")
				.sendJsonObject(new JsonObject().put("descripcion", descripcion).put("margen", margen), ar -> {
					if (ar.succeeded()) {
						System.out.println(ar.result().bodyAsString());
						promise.complete(ar.result().bodyAsJsonObject().getInteger("idlista"));
					} else {
						promise.fail(new NoSuchElementException(
								"Fallo de operación de Inserción " + ar.cause().getMessage()));
					}
				});
		return promise.future();
	}

	public static Future<Integer> updateLista(int id, String descripcion, Double margen) {
		
		Promise<Integer> promise = Promise.promise();

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient client = WebClient.create(Vertx.vertx());
		client.put(port, host, "/api/listas/" + id).sendJsonObject(new JsonObject().put("descripcion", descripcion).put("margen", margen),
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
		InputStream is = LineaAccess.class.getResourceAsStream("/resources/application.json");
//		InputStream is = BancoAccess.class.getResourceAsStream("/application.json"); // FUNCIONA PARA EL IDE
		String text = new String(is.readAllBytes(), StandardCharsets.UTF_8); // readAllBytes a partir de Java 9 		
		// parsear a Java
	    JSONParser jsonParser = new JSONParser();		
	    JSONObject jsonObject = (JSONObject) jsonParser.parse(text);
		
	    host = jsonObject.get("host").toString();
		port = Integer.valueOf(jsonObject.get("port").toString());
	}
	
}
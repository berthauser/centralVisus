package consumer;

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
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import javafx.collections.ObservableList;
import model.Concepto;

public class ConceptoAccess {
	private static String host;
	private static Integer port;
	
	public static void getConceptos(ObservableList<Concepto> conceptoData) {

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient client = WebClient.create(Vertx.vertx());
		client.get(port, host, "/api/conceptos").send(ar -> {
			if (ar.succeeded()) {
				conceptoData.clear();
				HttpResponse<Buffer> response = ar.result();
				response.bodyAsJsonArray().forEach(concepto -> {
					JsonObject jo = (JsonObject) concepto;
					conceptoData.add(new Concepto(jo.getString("descripcion")));
				});
				System.out.println("Received response with status code " + response.statusCode());
				System.out.println(response.bodyAsJsonArray());
			} else {
				System.out.println("Something went wrong " + ar.cause().getMessage());
			}
		});
	}
	
	public static Future<Void> deleteConcepto(Concepto concepto) {

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		Promise<Void> promise = Promise.promise();
		WebClient client = WebClient.create(Vertx.vertx());
		client.delete(port, host, "/api/conceptos/" + concepto.getIdconcepto().intValue()).send(ar -> {
			if (ar.succeeded()) {
				System.out.println("El Concepto " + " -  " + concepto.getDescripcion().getValue() + " fue borrado");
				promise.complete();
			} else {
				promise.fail(new NoSuchElementException("Fallo de operación de borrado " + ar.cause().getMessage()));
			}
		});
		return promise.future();
	}
	
	public static Future<Integer> insertConcepto(String descripcion) {

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		Promise<Integer> promise = Promise.promise();
		WebClient client = WebClient.create(Vertx.vertx());
		client.post(port, host, "/api/conceptos/").sendJsonObject(new JsonObject().put("descripcion", descripcion),
				ar -> {
					if (ar.succeeded()) {
						System.out.println(ar.result().bodyAsString());
						promise.complete(ar.result().bodyAsJsonObject().getInteger("idconcepto"));
					} else {
						promise.fail(new NoSuchElementException(
								"Fallo de operación de Inserción " + ar.cause().getMessage()));
					}
				});
		return promise.future();
	}

	public static Future<Integer> updateConcepto(int id, String descripcion) {
		
		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
		Promise<Integer> promise = Promise.promise();
		WebClient client = WebClient.create(Vertx.vertx());
		client
		.put(port, host, "/api/conceptos/" + id)
		.sendJsonObject(new JsonObject()
				.put("descripcion", descripcion), ar -> {
					if(ar.succeeded()) {
						System.out.println(ar.result().bodyAsString());
						promise.complete(id);
					} else {
						promise.fail(new NoSuchElementException("Fallo de operación Update " + ar.cause().getMessage()));
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
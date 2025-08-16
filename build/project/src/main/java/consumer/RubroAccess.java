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
import model.Rubro;

public class RubroAccess {
//	private static final String HOST = "192.168.0.15";
	private static final String HOST = "66.97.32.208";
	private static final int PORT = 8096;
	
	public static void getRubros(ObservableList<Rubro> rubroData) {
		WebClient client = WebClient.create(Vertx.vertx());
		client
		.get(PORT, HOST, "/api/rubros")
		.send(ar -> {
			if (ar.succeeded()) {
				rubroData.clear();
				HttpResponse<Buffer> response = ar.result();
				response.bodyAsJsonArray().forEach(rubro -> {
					JsonObject jo = (JsonObject) rubro;
					rubroData.add(new Rubro(jo.getInteger("idrubro"),jo.getString("descripcion")));
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
		WebClient client = WebClient.create(Vertx.vertx());
		client
		.delete(PORT, HOST, "/api/rubros/" + rubro.getIdrubro())
		.send(ar -> {
			if(ar.succeeded()) {
				promise.complete("El Rubro" + " - " + rubro.getDescripcion() + " fue borrado");
			} else {
				promise.fail(new NoSuchElementException("Fallo de operación de borrado " + ar.cause().getMessage()));
			}
		});
		return promise.future();
	}
	
	public static Future<Integer> insertRubro(String descripcion) {
		Promise<Integer> promise = Promise.promise();
		WebClient client = WebClient.create(Vertx.vertx());
		client
		.post(PORT, HOST, "/api/rubros/")
		.sendJsonObject(new JsonObject()
				.put("descripcion", descripcion), ar -> {
					if(ar.succeeded()) {
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
		WebClient client = WebClient.create(Vertx.vertx());
		client
		.put(PORT, HOST, "/api/rubros/" + id)
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

}
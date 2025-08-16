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
import model.Unidad;

public class UnidadAccess {
//	private static final String HOST = "192.168.0.15";
	private static final String HOST = "66.97.32.208";
	private static final int PORT = 8098;
	
	public static void getUnidades(ObservableList<Unidad> unidadData) {
		WebClient client = WebClient.create(Vertx.vertx());
		client
		.get(PORT, HOST, "/api/unidades")
		.send(ar -> {
			if (ar.succeeded()) {
				unidadData.clear();
				HttpResponse<Buffer> response = ar.result();
				response.bodyAsJsonArray().forEach(Unidad -> {
					JsonObject jo = (JsonObject) Unidad;
					unidadData.add(new Unidad(jo.getInteger("idunidad"),jo.getString("nombre"),jo.getString("abreviatura")));
				});
				System.out.println("Received response with status code " + response.statusCode());
				System.out.println(response.bodyAsJsonArray());
			} else {
				System.out.println("Something went wrong " + ar.cause().getMessage());
			}
		});
	}
	
	public static Future<String> delete(Unidad unidad) {
		Promise<String> promise = Promise.promise();
		WebClient client = WebClient.create(Vertx.vertx());
		client
		.delete(PORT, HOST, "/api/unidades/" + unidad.getIdunidad())
		.send(ar -> {
			if(ar.succeeded()) {
				promise.complete("La Unidad" + " - " + unidad.getNombre() + " fue borrada");
			} else {
				promise.fail(new NoSuchElementException("Fallo de operación de borrado " + ar.cause().getMessage()));
			}
		});
		return promise.future();
	}
	
	public static Future<Integer> insert(String nombre, String abreviatura) {
		Promise<Integer> promise = Promise.promise();
		WebClient client = WebClient.create(Vertx.vertx());
		client
		.post(PORT, HOST, "/api/unidades/")
		.sendJsonObject(new JsonObject()
				.put("nombre", nombre) 
				.put("abreviatura", abreviatura), ar -> {
					if(ar.succeeded()) {
						System.out.println(ar.result().bodyAsString());
						promise.complete(ar.result().bodyAsJsonObject().getInteger("idunidad"));
					} else {
						promise.fail(new NoSuchElementException("Fallo de operación de Inserción " + ar.cause().getMessage()));
					}
				});
		return promise.future();
	}

	public static Future<Integer> update(int id, String nombre, String abreviatura) {
		Promise<Integer> promise = Promise.promise();
		WebClient client = WebClient.create(Vertx.vertx());
		client
		.put(PORT, HOST, "/api/unidades/" + id)
		.sendJsonObject(new JsonObject()
				.put("nombre", nombre)
				.put("abreviatura", abreviatura), ar -> {
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
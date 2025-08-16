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
import model.Deposito;

public class DepositoAccess {
//	private static final String HOST = "192.168.0.15";
	private static final String HOST = "66.97.32.208";
	private static final int PORT = 8092;
	
	public static void getDepositos(ObservableList<Deposito> depositoData) {
		WebClient client = WebClient.create(Vertx.vertx());
		client
		.get(PORT, HOST, "/api/depositos")
		.send(ar -> {
			if (ar.succeeded()) {
				depositoData.clear();
				HttpResponse<Buffer> response = ar.result();
				response.bodyAsJsonArray().forEach(deposito -> {
					JsonObject jo = (JsonObject) deposito;
					depositoData.add(new Deposito(jo.getInteger("iddeposito"),jo.getString("descripcion")));
				});
				System.out.println("Received response with status code " + response.statusCode());
				System.out.println(response.bodyAsJsonArray());
			} else {
				System.out.println("Something went wrong " + ar.cause().getMessage());
			}
		});
	}
	
	public static Future<String> deleteDeposito(Deposito deposito) {
		Promise<String> promise = Promise.promise();
		WebClient client = WebClient.create(Vertx.vertx());
		client
		.delete(PORT, HOST, "/api/depositos/" + deposito.getIddeposito())
		.send(ar -> {
			if(ar.succeeded()) {
				promise.complete("El Depósito" + " - " + deposito.getDescripcion() + " fue borrado");
			} else {
				promise.fail(new NoSuchElementException("Fallo de operación de borrado " + ar.cause().getMessage()));
			}
		});
		return promise.future();
	}
	
	public static Future<Integer> insertDeposito(String descripcion) {
		Promise<Integer> promise = Promise.promise();
		WebClient client = WebClient.create(Vertx.vertx());
		client
		.post(PORT, HOST, "/api/depositos/")
		.sendJsonObject(new JsonObject()
				.put("descripcion", descripcion), ar -> {
					if(ar.succeeded()) {
						System.out.println(ar.result().bodyAsString());
						promise.complete(ar.result().bodyAsJsonObject().getInteger("iddeposito"));
					} else {
						promise.fail(new NoSuchElementException("Fallo de operación de Inserción " + ar.cause().getMessage()));
					}
				});
		return promise.future();
	}

	public static Future<Integer> updateDeposito(int id, String descripcion) {
		Promise<Integer> promise = Promise.promise();
		WebClient client = WebClient.create(Vertx.vertx());
		client
		.put(PORT, HOST, "/api/depositos/" + id)
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

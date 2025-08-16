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
import model.Presentacion;

public class PresentacionAccess {
//	private static final String HOST = "192.168.0.15";
	private static final String HOST = "66.97.32.208";
	private static final int PORT = 8095;
	
	public static void getPresentaciones(ObservableList<Presentacion> presentacionData) {
		WebClient client = WebClient.create(Vertx.vertx());
		client
		.get(PORT, HOST, "/api/presentaciones")
		.send(ar -> {
			if (ar.succeeded()) {
				presentacionData.clear();
				HttpResponse<Buffer> response = ar.result();
				response.bodyAsJsonArray().forEach(presentacion -> {
					JsonObject jo = (JsonObject) presentacion;
					presentacionData.add(new Presentacion(jo.getInteger("idpresentacion"), jo.getString("contenedor"), jo.getDouble("unidades"), jo.getDouble("valor_umedida"), jo.getString("tipo_Decremento")));
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
		WebClient client = WebClient.create(Vertx.vertx());
		client
		.delete(PORT, HOST, "/api/presentaciones/" + presentacion.getIdpresentacion())
		.send(ar -> {
			if(ar.succeeded()) {
				promise.complete("La Presentacion" + " - " + presentacion.getContenedor() + " fue borrada");
			} else {
				promise.fail(new NoSuchElementException("Fallo de operación de borrado " + ar.cause().getMessage()));
			}
		});
		return promise.future();
	}
	
	public static Future<Integer> insert(String contenedor, Double unidades, Double valor_umedida, String decremento) {
		Promise<Integer> promise = Promise.promise();
		WebClient client = WebClient.create(Vertx.vertx());
		client
		.post(PORT, HOST, "/api/presentaciones/")
		.sendJsonObject(new JsonObject()
				.put("contenedor", contenedor) 
				.put("unidades", unidades) 
				.put("valor_umedida", valor_umedida) 
				.put("tipo_Decremento", decremento), ar -> {
					if(ar.succeeded()) {
						System.out.println(ar.result().bodyAsString());
						promise.complete(ar.result().bodyAsJsonObject().getInteger("idpresentacion"));
					} else {
						promise.fail(new NoSuchElementException("Fallo de operación de Inserción " + ar.cause().getMessage()));
					}
				});
		return promise.future();
	}

	public static Future<Integer> update(int id, String contenedor, Double unidades, Double valor_umedida, String decremento) {
		Promise<Integer> promise = Promise.promise();
		WebClient client = WebClient.create(Vertx.vertx());
		client
		.put(PORT, HOST, "/api/presentaciones/" + id)
		.sendJsonObject(new JsonObject()
				.put("contenedor", contenedor) 
				.put("unidades", unidades) 
				.put("valor_umedida", valor_umedida) 
				.put("tipo_decremento", decremento), ar -> {
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
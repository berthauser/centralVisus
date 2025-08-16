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
import model.Ubicacion;

public class UbicacionAccess {
//	private static final String HOST = "192.168.0.15";
	private static final String HOST = "66.97.32.208";
	private static final int PORT = 8097;
	
	public static void getUbicaciones(ObservableList<Ubicacion> ubicacionData) {
		WebClient client = WebClient.create(Vertx.vertx());
		client
		.get(PORT, HOST, "/api/ubicaciones")
		.send(ar -> {
			if (ar.succeeded()) {
				ubicacionData.clear();
				HttpResponse<Buffer> response = ar.result();
				response.bodyAsJsonArray().forEach(ubicacion -> {
					JsonObject jo = (JsonObject) ubicacion;
					ubicacionData.add(new Ubicacion(jo.getInteger("idubicacion"),jo.getString("descripcion"), jo.getString("numero"), jo.getString("deposito"), jo.getInteger("iddeposito"), jo.getInteger("tot_filas"), jo.getInteger("tot_columnas")));
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
		WebClient client = WebClient.create(Vertx.vertx());
		client
		.delete(PORT, HOST, "/api/ubicaciones/" + ubicacion.getIdubicacion())
		.send(ar -> {
			if(ar.succeeded()) {
				promise.complete("La Ubicación" + " - " + ubicacion.getDescripcion() + " fue borrada");
			} else {
				promise.fail(new NoSuchElementException("Fallo de operación de borrado " + ar.cause().getMessage()));
			}
		});
		return promise.future();
	}
	
	public static Future<Integer> insert(String descripcion, String numero, Integer iddeposito, Integer tot_filas, Integer tot_columnas) {
		Promise<Integer> promise = Promise.promise();
		WebClient client = WebClient.create(Vertx.vertx());
		client
		.post(PORT, HOST, "/api/ubicaciones/")
		.sendJsonObject(new JsonObject()
				.put("descripcion", descripcion) 
				.put("numero", numero) 
				.put("iddeposito", iddeposito) 
				.put("tot_filas", tot_filas)
				.put("tot_columnas", tot_columnas), ar -> {
					if(ar.succeeded()) {
						System.out.println(ar.result().bodyAsString());
						promise.complete(ar.result().bodyAsJsonObject().getInteger("idubicacion"));
					} else {
						promise.fail(new NoSuchElementException("Fallo de operación de Inserción " + ar.cause().getMessage()));
					}
				});
		return promise.future();
	}

	public static Future<Integer> update(int id, String descripcion, String numero, Integer iddeposito, Integer tot_filas, Integer tot_columnas) {
		Promise<Integer> promise = Promise.promise();
		WebClient client = WebClient.create(Vertx.vertx());
		client
		.put(PORT, HOST, "/api/ubicaciones/" + id)
		.sendJsonObject(new JsonObject()
				.put("descripcion", descripcion) 
				.put("numero", numero) 
				.put("iddeposito", iddeposito) 
				.put("tot_filas", tot_filas)
				.put("tot_columnas", tot_columnas), ar -> {
					if(ar.succeeded()) {
						System.out.println(ar.result().bodyAsString());
						promise.complete(id);
					} else {
						promise.fail(new NoSuchElementException("Fallo de operación Update " + ar.cause().getMessage()));
					}
				});
		return promise.future();
	}
	
	public static void getDepositos(ObservableList<Deposito> depositoData) {
		WebClient client = WebClient.create(Vertx.vertx());
		client
		.get(8092, HOST, "/api/depositos")
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

}
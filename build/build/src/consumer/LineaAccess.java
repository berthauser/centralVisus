package consumer;

import java.util.NoSuchElementException;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Linea;
import model.Rubro;

public class LineaAccess {
//	private static final String HOST = "192.168.0.15";
	private static final String HOST = "66.97.32.208";
	private static final int PORT = 8093;
	
	public static void getLineas(ObservableList<Linea> lineaData) {
		WebClient wc = WebClient.create(Vertx.vertx());
		wc
		.get(PORT, HOST, "/api/lineas")
		.send(ar -> {
			if (ar.succeeded()) {
				lineaData.clear();
				HttpResponse<Buffer> response = ar.result();
				response.bodyAsJsonArray().forEach(linea -> {
					JsonObject jo = (JsonObject) linea;
					lineaData.add(new Linea(jo.getInteger("idlinea"),jo.getString("descripcion"), jo.getString("rubro")));
				});
				System.out.println("Received response with status code " + response.statusCode());
				System.out.println(response.bodyAsJsonArray());
			} else {
				System.out.println("Something went wrong " + ar.cause().getMessage());
			}
		});
	}
	
	public static Future<String> delete(Linea linea) {
		Promise<String> promise = Promise.promise();
		WebClient wc = WebClient.create(Vertx.vertx());
		wc
		.delete(PORT, HOST, "/api/lineas/" + linea.getIdlinea())
		.send(ar -> {
			if(ar.succeeded()) {
				promise.complete("La Lónea" + " - " + linea.getDescripcion() + " fue borrada");
			} else {
				promise.fail(new NoSuchElementException("Fallo de operación de borrado " + ar.cause().getMessage()));
			}
		});
		return promise.future();
	}
	
	public static ObservableList<Linea> getLineasByRubro(int id) {
		
		ObservableList<Linea> listaLineas = FXCollections.observableArrayList();
		
		WebClient wc = WebClient.create(Vertx.vertx());
		wc
		.get(PORT, HOST, "/api/lineas/byRubro/" + id)
		.send(ar -> {
			if (ar.succeeded()) {
				
				HttpResponse<Buffer> response = ar.result();
				
				response.bodyAsJsonArray().forEach(tupla -> {
					JsonObject jo = (JsonObject) tupla;
					listaLineas.add(new Linea(jo.getInteger("idlinea"),jo.getString("descripcion"), jo.getString("rubro")));
				});
			} else {
				System.out.println("Something went wrong " + ar.cause().getMessage());
			}
		});
		return listaLineas;
	}
	
	public static Future<Integer> insert(String descripcion, Integer idrubro) {
		Promise<Integer> promise = Promise.promise();
		WebClient wc = WebClient.create(Vertx.vertx());
		wc
		.post(PORT, HOST, "/api/lineas/")
		.sendJsonObject(new JsonObject()
				.put("descripcion", descripcion) 
				.put("idrubro", idrubro), ar -> {
					if(ar.succeeded()) {
						System.out.println(ar.result().bodyAsString());
						promise.complete(ar.result().bodyAsJsonObject().getInteger("idlinea"));
					} else {
						promise.fail(new NoSuchElementException("Fallo de operación de Inserción " + ar.cause().getMessage()));
					}
				});
		return promise.future();
	}

	public static Future<Integer> update(int id, String descripcion, Integer idrubro) {
		Promise<Integer> promise = Promise.promise();
		WebClient wc = WebClient.create(Vertx.vertx());
		wc
		.put(PORT, HOST, "/api/lineas/" + id)
		.sendJsonObject(new JsonObject()
				.put("descripcion", descripcion) 
				.put("idrubro", idrubro) , ar -> {
					if(ar.succeeded()) {
						System.out.println(ar.result().bodyAsString());
						promise.complete(id);
					} else {
						promise.fail(new NoSuchElementException("Fallo de operación Update " + ar.cause().getMessage()));
					}
				});
		return promise.future();
	}
	
	public static void getRubros(ObservableList<Rubro> rubroData) {
		WebClient wc = WebClient.create(Vertx.vertx());
		wc
		.get(8096, HOST, "/api/rubros")
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

}
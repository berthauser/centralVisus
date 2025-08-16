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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Linea;
import model.Rubro;

public class LineaAccess {
//	private static final String host = "66.97.32.208";
	private static String host;
	private static Integer port;
	
	public static void getLineas(ObservableList<Linea> lineaData) {

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient wc = WebClient.create(Vertx.vertx());
		wc.get(port, host, "/api/lineas").send(ar -> {
			if (ar.succeeded()) {
				lineaData.clear();
				HttpResponse<Buffer> response = ar.result();
				response.bodyAsJsonArray().forEach(linea -> {
					JsonObject jo = (JsonObject) linea;
					lineaData.add(
							new Linea(jo.getInteger("idlinea"), jo.getString("descripcion"), jo.getString("rubro")));
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

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient wc = WebClient.create(Vertx.vertx());
		wc.delete(port, host, "/api/lineas/" + linea.getIdlinea()).send(ar -> {
			if (ar.succeeded()) {
				promise.complete("La Línea" + " - " + linea.getDescripcion() + " fue borrada");
			} else {
				promise.fail(new NoSuchElementException("Fallo de operación de borrado " + ar.cause().getMessage()));
			}
		});
		return promise.future();
	}
	
	public static ObservableList<Linea> getLineasByRubro(int id) {
		ObservableList<Linea> listaLineas = FXCollections.observableArrayList();

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient wc = WebClient.create(Vertx.vertx());
		wc.get(port, host, "/api/lineas/byRubro/" + id).send(ar -> {
			if (ar.succeeded()) {

				HttpResponse<Buffer> response = ar.result();

				response.bodyAsJsonArray().forEach(tupla -> {
					JsonObject jo = (JsonObject) tupla;
					listaLineas.add(
							new Linea(jo.getInteger("idlinea"), jo.getString("descripcion"), jo.getString("rubro")));
				});
			} else {
				System.out.println("Something went wrong " + ar.cause().getMessage());
			}
		});
		return listaLineas;
	}
	
	public static Future<Integer> insert(String descripcion, Integer idrubro) {
		Promise<Integer> promise = Promise.promise();

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient wc = WebClient.create(Vertx.vertx());
		wc.post(port, host, "/api/lineas/")
				.sendJsonObject(new JsonObject().put("descripcion", descripcion).put("idrubro", idrubro), ar -> {
					if (ar.succeeded()) {
						System.out.println(ar.result().bodyAsString());
						promise.complete(ar.result().bodyAsJsonObject().getInteger("idlinea"));
					} else {
						promise.fail(new NoSuchElementException(
								"Fallo de operación de Inserción " + ar.cause().getMessage()));
					}
				});
		return promise.future();
	}

	public static Future<Integer> update(int id, String descripcion, Integer idrubro) {
		Promise<Integer> promise = Promise.promise();

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient wc = WebClient.create(Vertx.vertx());
		wc.put(port, host, "/api/lineas/" + id)
				.sendJsonObject(new JsonObject().put("descripcion", descripcion).put("idrubro", idrubro), ar -> {
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
	
	private static void leerConfiguracionJson() throws FileNotFoundException, IOException, ParseException {
		// for static access, uses the class name directly
		InputStream is = LineaAccess.class.getResourceAsStream("/resources/application.json");
//		InputStream is = LineaAccess.class.getResourceAsStream("/application.json");
		String text = new String(is.readAllBytes(), StandardCharsets.UTF_8); // readAllBytes: a partir de Java 9 		
		// parsear a Java
	    JSONParser jsonParser = new JSONParser();		
	    JSONObject jsonObject = (JSONObject) jsonParser.parse(text);
		
	    host = jsonObject.get("host").toString();
		port = Integer.valueOf(jsonObject.get("port").toString());
	}

}
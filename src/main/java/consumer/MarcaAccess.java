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
import model.Marca;

public class MarcaAccess {
	private static String host;
	private static Integer port;
	
	public static void getMarcas(ObservableList<Marca> marcaData) {
		WebClient client = WebClient.create(Vertx.vertx());

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		client.get(port, host, "/api/marcas").send(ar -> {
			if (ar.succeeded()) {
				marcaData.clear();
				HttpResponse<Buffer> response = ar.result();
				response.bodyAsJsonArray().forEach(Marca -> {
					JsonObject jo = (JsonObject) Marca;
					marcaData.add(new Marca(jo.getInteger("idmarca"), jo.getString("descripcion")));
				});
				System.out.println("Received response with status code " + response.statusCode());
				System.out.println(response.bodyAsJsonArray());
			} else {
				System.out.println("Something went wrong " + ar.cause().getMessage());
			}
		});
	}

	public static Future<String> delete(Marca Marca) {
		
		Promise<String> promise = Promise.promise();

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient client = WebClient.create(Vertx.vertx());
		client.delete(port, host, "/api/marcas/" + Marca.getIdmarca()).send(ar -> {
			if (ar.succeeded()) {
				promise.complete("La Marca" + " - " + Marca.getDescripcion() + " fue borrada");
			} else {
				promise.fail(new NoSuchElementException("Fallo de operación de borrado " + ar.cause().getMessage()));
			}
		});
		return promise.future();
	}
	
	public static Future<Integer> insert(String descripcion) {

		Promise<Integer> promise = Promise.promise();

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient client = WebClient.create(Vertx.vertx());
		client.post(port, host, "/api/marcas/").sendJsonObject(new JsonObject().put("descripcion", descripcion), ar -> {
			if (ar.succeeded()) {
				System.out.println(ar.result().bodyAsString());
				promise.complete(ar.result().bodyAsJsonObject().getInteger("idmarca"));
			} else {
				promise.fail(new NoSuchElementException("Fallo de operación de Inserción " + ar.cause().getMessage()));
			}
		});
		return promise.future();
	}

	public static Future<Integer> update(int id, String descripcion) {

		Promise<Integer> promise = Promise.promise();

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient client = WebClient.create(Vertx.vertx());
		client.put(port, host, "/api/marcas/" + id).sendJsonObject(new JsonObject().put("descripcion", descripcion),
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
		// for static access, uses the class name directly
		InputStream is = MarcaAccess.class.getResourceAsStream("/resources/application.json");
//		InputStream is = MarcaAccess.class.getResourceAsStream("/application.json");
		String text = new String(is.readAllBytes(), StandardCharsets.UTF_8); // readAllBytes: a partir de Java 9 		
		// parsear a Java
	    JSONParser jsonParser = new JSONParser();		
	    JSONObject jsonObject = (JSONObject) jsonParser.parse(text);
		
	    host = jsonObject.get("host").toString();
		port = Integer.valueOf(jsonObject.get("port").toString());
	}
	
}
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
import model.Banco;

public class BancoAccess {
	private static String host;
	private static Integer port;
	
	public static void getBancos(ObservableList<Banco> listBancos) {

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient wc = WebClient.create(Vertx.vertx());
		wc.get(port, host, "/api/bancos/central").send(ar -> {
			if (ar.succeeded()) {
				listBancos.clear();
				HttpResponse<Buffer> response = ar.result();
				response.bodyAsJsonArray().forEach(banco -> {
					JsonObject jo = (JsonObject) banco;
					listBancos.add(new Banco(jo.getInteger("idbanco"), jo.getInteger("codigo"), jo.getString("nombre")));
				});
				System.out.println("Received response with status code " + response.statusCode());
				System.out.println(response.bodyAsJsonArray());
			} else {
				System.out.println("Something went wrong " + ar.cause().getMessage());
			}
		});
	}
	
	public static Future<String> delete(Banco banco) {

		Promise<String> promise = Promise.promise();

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient wc = WebClient.create(Vertx.vertx());
		wc.delete(port, host, "/api/bancos/delete/" + banco.getIdbanco()).send(ar -> {
			if (ar.succeeded()) {
				promise.complete("El Banco " + " - " + banco.getNombre() + " fue borrado");
			} else {
				promise.fail(new NoSuchElementException("Fallo de operación de borrado " + ar.cause().getMessage()));
			}
		});
		return promise.future();
	}
	
	public static Future<Integer> insert(Integer codigo, String nombre) {
		Promise<Integer> promise = Promise.promise();

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient wc = WebClient.create(Vertx.vertx());
		wc.post(port, host, "/api/bancos/central/").sendJsonObject(
				new JsonObject().put("codigo", codigo).put("descripcion", nombre), ar -> {
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

	public static Future<Integer> update(Integer idbanco, Integer codigo, String nombre) {
		Promise<Integer> promise = Promise.promise();

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient wc = WebClient.create(Vertx.vertx());
		wc.put(port, host, "/api/bancos/central/update/" + idbanco).sendJsonObject(
				new JsonObject().put("idbanco", idbanco).put("codigo", codigo).put("nombre", nombre), ar -> {
					if (ar.succeeded()) {
						System.out.println(ar.result().bodyAsString());
						promise.complete(idbanco);
					} else {
						promise.fail(
								new NoSuchElementException("Fallo de operación Update " + ar.cause().getMessage()));
					}
				});
		return promise.future();
	}
	
	private static void leerConfiguracionJson() throws FileNotFoundException, IOException, ParseException {
		// for static access, uses the class name directly
//		InputStream is = BancoAccess.class.getClass().getResourceAsStream("/application.json");
		InputStream is = BancoAccess.class.getResourceAsStream("/resources/application.json");
//		InputStream is = BancoAccess.class.getResourceAsStream("/application.json"); // FUNCIONA PARA EL IDE
		String text = new String(is.readAllBytes(), StandardCharsets.UTF_8); // readAllBytes a partir de Java 9 		

		// parsear a Java
	    JSONParser jsonParser = new JSONParser();		
	    JSONObject jsonObject = (JSONObject) jsonParser.parse(text);
		
	    host = jsonObject.get("host").toString();
		port = Integer.valueOf(jsonObject.get("port").toString());
	}

}
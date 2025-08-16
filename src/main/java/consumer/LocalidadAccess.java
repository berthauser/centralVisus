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
import model.Localidad;

public class LocalidadAccess {
	
	private static String host;
	private static Integer port;
	
	public static void getLocalidades(ObservableList<Localidad> listLocalidades) {

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient wc = WebClient.create(Vertx.vertx());
		wc.get(port, host, "/api/localidades/central").send(ar -> {
			if (ar.succeeded()) {
				listLocalidades.clear();
				HttpResponse<Buffer> response = ar.result();
				response.bodyAsJsonArray().forEach(linea -> {
					JsonObject jo = (JsonObject) linea;
					listLocalidades.add(
							new Localidad(jo.getInteger("idlocalidad"), jo.getString("nombre"), jo.getInteger("codigo_postal"), jo.getString("provincia")));
				});
				System.out.println("Received response with status code " + response.statusCode());
				System.out.println(response.bodyAsJsonArray());
			} else {
				System.out.println("Something went wrong " + ar.cause().getMessage());
			}
		});
	}
	
	public static Future<String> delete(Localidad localidad) {

		Promise<String> promise = Promise.promise();

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient wc = WebClient.create(Vertx.vertx());
		wc.delete(port, host, "/api/localidades/central/" + localidad.getIdlocalidad()).send(ar -> {
			if (ar.succeeded()) {
				promise.complete("La Localidad " + " - " + localidad.getNombre() + " fue borrada");
			} else {
				promise.fail(new NoSuchElementException("Fallo de operación de borrado " + ar.cause().getMessage()));
			}
		});
		return promise.future();
	}
	
	public static Future<Integer> insert(String nombre, Integer codigoPostal, String provincia) {
		Promise<Integer> promise = Promise.promise();

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient wc = WebClient.create(Vertx.vertx());
		wc.post(port, host, "/api/localidades/central")
				.sendJsonObject(new JsonObject().put("nombre", nombre).put("codigo_postal", codigoPostal).put("provincia", provincia), ar -> {
					if (ar.succeeded()) {
						System.out.println(ar.result().bodyAsString());
						promise.complete(ar.result().bodyAsJsonObject().getInteger("idlocalidad"));
					} else {
						promise.fail(new NoSuchElementException(
								"Fallo de operación de Inserción " + ar.cause().getMessage()));
					}
				});
		return promise.future();
	}

	public static Future<Integer> update(int id, String nombre, Integer codigoPostal, String provincia) {
		Promise<Integer> promise = Promise.promise();

		try {
			leerConfiguracionJson();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		WebClient wc = WebClient.create(Vertx.vertx());
		wc.put(port, host, "/api/localidades/central/" + id)
				.sendJsonObject(new JsonObject().put("nombre", nombre).put("codigo_postal", codigoPostal).put("provincia", provincia), ar -> {
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
		String path = ConfiguracionAccess.class.getClassLoader().getResource("application.json").getPath();
		JSONObject json = (JSONObject) new JSONParser().parse(new FileReader(path));
		host = (String) json.get("host");
		String portParsed = (String) json.get("port");
		port = Integer.valueOf(portParsed);
	}

}
package consumer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
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
import model.Configuracion;

public class PersonaAccess {
//	private static final String host = "200.58.100.15";
	private static String host;
	private static Integer port;
//	private static final Logger logger = LogManager.getLogger(ConfiguracionAccess.class);
	private static ObservableList<Configuracion> configuracionList = FXCollections.observableArrayList();
	
	public static Future<ObservableList<Configuracion>> getConfiguracion() throws IOException, ParseException {

		leerConfiguracionJson();

		Promise<ObservableList<Configuracion>> promise = Promise.promise(); // nuevo
		
		WebClient client = WebClient.create(Vertx.vertx());
		client.get(port, host, "/api/configuracion").send(asyncResult -> {
			if (asyncResult.succeeded()) {

				configuracionList.clear();

				HttpResponse<Buffer> response = asyncResult.result();

				response.bodyAsJsonArray().iterator().forEachRemaining(configuracion -> {
					JsonObject jo = (JsonObject) configuracion;

					configuracionList.add(new Configuracion(jo.getInteger("idconfiguracion"),
							jo.getString("razon_social"), jo.getString("nombre_fantasia"), jo.getString("email"),
							jo.getString("domicilio_comercial"), jo.getString("telefono_fijo"),
							jo.getString("telefono_movil"), jo.getString("localidad"), jo.getString("provincia"),
							jo.getString("codigo_postal"), jo.getString("situacion_fiscal"),
							jo.getString("domicilio_fiscal"), jo.getInteger("punto_venta"),
							jo.getString("iibb_convmultilateral"), jo.getString("cuit"),
							LocalDate.parse(jo.getString("fecha_iactividades")), jo.getBoolean("modo_produccion"),
							jo.getBoolean("modo_factura"), jo.getString("path_facturaspdf"),
							jo.getString("path_certproduccion"), jo.getString("path_certhomologacion"),
							jo.getString("path_claveprivada"), jo.getDouble("importe_afip")));
				});
				promise.complete(configuracionList);
				System.out.println("Received response with status code " + response.statusCode());
				System.out.println(response.bodyAsJsonArray());
			} else {
				promise.fail("Algo anduvo mal " + asyncResult.cause().getMessage());
			}
		});
		return promise.future();
	}
	
	public static Future<Integer> insert(String razon_social, String nombre_fantasia, String email,
			String domicilio_comercial, String telefono_fijo, String telefono_movil, String localidad, String provincia,
			String codigo_postal, String situacion_fiscal, String domicilio_fiscal, Integer punto_venta,
			String iibb_convmultilateral, String cuit, String fecha_iactividades, Boolean modo_produccion,
			Boolean modo_factura, String path_facturaspdf, String path_certproduccion, String path_certhomologacion,
			String path_claveprivada, Double importe_afip) throws FileNotFoundException, IOException, ParseException {

		leerConfiguracionJson();

		Promise<Integer> promise = Promise.promise();
		WebClient client = WebClient.create(Vertx.vertx());
		client.post(port, host, "/api/configuracion/").sendJsonObject(new JsonObject().put("razon_social", razon_social)
				.put("nombre_fantasia", nombre_fantasia).put("email", email)
				.put("domicilio_comercial", domicilio_comercial).put("telefono_fijo", telefono_fijo)
				.put("telefono_movil", telefono_movil).put("localidad", localidad).put("provincia", provincia)
				.put("codigo_postal", codigo_postal).put("situacion_fiscal", situacion_fiscal)
				.put("domicilio_fiscal", domicilio_fiscal).put("punto_venta", punto_venta)
				.put("iibb_convmultilateral", iibb_convmultilateral).put("cuit", cuit)
				.put("fecha_iactividades", fecha_iactividades).put("modo_produccion", modo_produccion)
				.put("modo_factura", modo_factura).put("path_facturaspdf", path_facturaspdf)
				.put("path_certproduccion", path_certproduccion).put("path_certhomologacion", path_certhomologacion)
				.put("path_claveprivada", path_claveprivada).put("importe_afip", importe_afip), ar -> {
					if (ar.succeeded()) {
						System.out.println(ar.result().bodyAsString());
						promise.complete(ar.result().bodyAsJsonObject().getInteger("idconfiguracion"));
					} else {
						promise.fail(new NoSuchElementException(
								"Fallo de operación de Inserción " + ar.cause().getMessage()));
					}
				});
		return promise.future();
	}

	public static Future<Integer> updateAll(Integer idconfiguracion, String razon_social, String nombre_fantasia,
			String email, String domicilio_comercial, String telefono_fijo, String telefono_movil, String localidad,
			String provincia, String codigo_postal, String situacion_fiscal, String domicilio_fiscal,
			Integer punto_venta, String iibb_convmultilateral, String cuit, String fecha_iactividades,
			Boolean modo_produccion, Boolean modo_factura, String path_facturaspdf, String path_certproduccion,
			String path_certhomologacion, String path_claveprivada, Double importe_afip)
			throws FileNotFoundException, IOException, ParseException {

		leerConfiguracionJson();

		Promise<Integer> promise = Promise.promise();
		WebClient client = WebClient.create(Vertx.vertx());
		client.put(port, host, "/api/configuracion/" + idconfiguracion)
				.sendJsonObject(new JsonObject().put("razon_social", razon_social)
						.put("nombre_fantasia", nombre_fantasia).put("email", email)
						.put("domicilio_comercial", domicilio_comercial).put("telefono_fijo", telefono_fijo)
						.put("telefono_movil", telefono_movil).put("localidad", localidad).put("provincia", provincia)
						.put("codigo_postal", codigo_postal).put("situacion_fiscal", situacion_fiscal)
						.put("domicilio_fiscal", domicilio_fiscal).put("punto_venta", punto_venta)
						.put("iibb_convmultilateral", iibb_convmultilateral).put("cuit", cuit)
						.put("fecha_iactividades", fecha_iactividades).put("modo_produccion", modo_produccion)
						.put("modo_factura", modo_factura).put("path_facturaspdf", path_facturaspdf)
						.put("path_certproduccion", path_certproduccion)
						.put("path_certhomologacion", path_certhomologacion).put("path_claveprivada", path_claveprivada)
						.put("importe_afip", importe_afip).put("idconfiguracion", idconfiguracion), ar -> {
							if (ar.succeeded()) {
								System.out.println(ar.result().bodyAsString());
								promise.complete();
							} else {
								promise.fail(new NoSuchElementException(
										"Fallo de operación Update " + ar.cause().getMessage()));
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
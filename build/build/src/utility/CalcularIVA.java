package utility;

import io.vertx.core.Future;
import io.vertx.core.Promise;

public class CalcularIVA {

	private Double precio;

	public CalcularIVA(Double precio) {
		this.precio = precio;
	}

	public Future<Double> calcular_iva() { 
		Promise<Double> promise = Promise.promise();
		Double alicuota = 0.21;
		Double iva = precio * alicuota;
		promise.complete(iva);
		return promise.future();
	}

}
package utility;

/* Algoritmo para CUIT/CUIL
 * XY � 12345678 � Z
 * XY: Indican el Tipo (Hombre, Mujer, Sociedad o Empresa)
 * 12345678: Numero de DNI
 * Z: D�gito de Verificaci�n
 * 
 * Se determina XY de la siguiente manera:
 * Hombre = 20
 * Mujer = 27
 * Empresa o Sociedad = 30
 * 
 * Se multiplica XY 12345678 por un n�mero de forma separada:
 *  Dado XY = 20, a modo de ejemplo.
 *  2 * 5 = 10
 *  0 * 4 = 0
 *  1 * 3 = 3
 *  2 * 2 = 4
 *  3 * 7 = 21
 *  4 * 6 = 24
 *  5 * 5 = 25
 *  6 * 4 = 24
 *  7 * 3 = 21
 *  8 * 2 = 16
 *  
 *  Ahora se suman los resultados de las multiplicaciones como se muestra a continuaci�n:
 *  10 + 0 + 3 + 4 + 21 + 24 + 25 + 24 + 21 + 16 = 148
 *  El resultado calculado en el paso anterior se divide por 11 (once) y se obtiene el resto de dicha divisi�n.
 *  148 / 11 = 13 (Divisi�n Entera)
 *  Resto: 148 � (13 * 11) = 5
 *  
 *  Una vez determinado el resto se aplican las siguientes reglas:
 *  Si el resto es igual a 0 (cero), entonces Z (D�gito de Verificaci�n) es igual a 0 (cero).
 *  Si el resto es igual a 1 (uno) ocurre lo siguiente:
 *  � Si es Hombre, entonces Z (D�gito de Verificaci�n) es igual a 9 (nueve) y XY es igual a 23 (veintitr�s).
 *  � Si es Mujer, entonces Z (D�gito de Verificaci�n) es igual a 4 (cuatro) y XY es igual a 23 (veintitr�s).
 *  � En cualquier otro caso Z (D�gito de Verificaci�n) es igual a 11 (once) menos el resto del cociente.
 *  
 *  Resto = 5
 *  11 � 5 = 6
 *  Z = 6
 * 
 */

public class CalcularCuitl {
	private int[] cuitl = {0,0,0,0,0,0,0,0,0,0,0};
	private final int n10 = 2 + 8;
	private final int n11 = n10 + 1;
	
	public void setTipoDNI(String tipo, String DNI) {
		String lengthDNI;

		try {
			for (int con = 0; con < n11; con++)
				cuitl[con] = 0;
			lengthDNI = String.format("%02d", Integer.valueOf(tipo)) + String.format("%08d", Integer.valueOf(DNI));

			if (lengthDNI.length() != n10)
				return;
		} catch (Exception e) {
			return;
		}

		for (int con = 0; con < n10; con++)
			cuitl[con] = Integer.parseInt(lengthDNI.substring(con, con + 1));

		int digitoVerificador = calcularDigitoVerificador();
		if (digitoVerificador == n10) { // Si el DV es 10, el DV se tiene que recalcular con Tipo = 23 o 33
			cuitl[1] = 3; // Tipo 23-DNI-x para Personas. Tipo 33-X-x para Empresas
			digitoVerificador = calcularDigitoVerificador();
		}
		
		if (digitoVerificador == n11)
			digitoVerificador = 0; // Si el DV es 11 el DV debe ser 0.
		cuitl[n10] = digitoVerificador;
}

	private int calcularDigitoVerificador() {
		int[] serie = { 5, 4, 3, 2, 7, 6, 5, 4, 3, 2 };
		int datoEntero = 0;
		int datoResto = 0;
		int sumatoria = 0;
		for (int con = 0; con < n10; con++)
			sumatoria = sumatoria + (serie[con] * cuitl[con]);
		datoEntero = (sumatoria / (n11)); // parte entera
		datoResto = sumatoria - (datoEntero * (n11)); // resto entero
		return (n11) - datoResto; // Los restos 0 y 1 dan un DV de 11 y 10...es un error y hay que repararlo
	}

	public String getCuitl() {
		StringBuffer cuitlString = new StringBuffer();
		for (int con = 0; con < (n11); con++)
			cuitlString = cuitlString.append(cuitl[con]);
		return cuitlString.toString();
	}

	public Boolean verificarCUITL(String cuitlX) {
		Boolean correcto = false;
		if (cuitlX.length() == n11) {
			for (int con = 0; con < n10; con++)
				cuitl[con] = Integer.parseInt(cuitlX.substring(con, con + 1));
			int digitoVerificador = calcularDigitoVerificador();
			if (digitoVerificador == n11)
				digitoVerificador = 0;
			if (Integer.parseInt(cuitlX.substring(n10, n11)) == digitoVerificador)
				correcto = true;
		}
		return correcto;
	}
}

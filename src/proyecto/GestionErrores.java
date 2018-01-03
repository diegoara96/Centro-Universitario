package proyecto;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**Clase de gestion de los errores durante la ejecucion del programa.
 * Se encarga de procesar el error y guardarlo en avisos.txt.
 * 
 * Tipos de error posibles:
 * 		1) Error en comando: depende de cada comando
 * 		2) Comando erroneo: un comando que no existe o con argumentos invalidos
 *
 */
public class GestionErrores {

	public final static String nombreFichero = "avisos.txt";
	public final static String INSERTA_PERSONA = "IP";
	public final static String CREAR_GRUPO_ASIGNATURA = "CGA";
	public final static String MATRICULAR_ALUMNO = "MAT";
	public final static String ASIGNAR_GRUPO = "AGRUPO";
	public final static String EVALUAR_ASIGNATURA = "EVALUA";
	public final static String CALENDARIO_OCUPACION_AULA = "OCUPAULA";
	public final static String EXPEDIENTE_ALUMNO = "EXP";
	
	
	private static void nuevaLinea(String linea) {
		try {
			// Escribe una linea en el fichero de avisos
			// Si avisos.txt aun no existe, lo crea
			FileWriter fw = new FileWriter(nombreFichero, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(linea);
			bw.newLine();
			bw.close();
			fw.close();
		} catch (IOException e) {
			// Error al leer el fichero
			e.printStackTrace();
		}
				
		
	}
	
	public static void errorComando(String siglas, String aviso) {
		nuevaLinea(siglas + " -- " + aviso);
		
	}
	
	public static void errorOtro(String error) {
		nuevaLinea(error);
	}
	
	public static void comandoErroneo(String comando) {
		nuevaLinea("Comando incorrecto: " + comando);
	}
	
}

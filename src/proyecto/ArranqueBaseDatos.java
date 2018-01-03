package proyecto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import objetos.*;

public class ArranqueBaseDatos {
	public static LinkedHashMap<String, Alumno> alumnos = new LinkedHashMap<String, Alumno>();
	public static LinkedHashMap<String, Profesor> profesores = new LinkedHashMap<String, Profesor>();
	public static LinkedHashMap<String, Aula> aulas = new LinkedHashMap<String, Aula>();
	public static LinkedHashMap<String, Pod> pod = new LinkedHashMap<String, Pod>();
	public static LinkedHashMap<String, Asignatura> asignaturas = new LinkedHashMap<String, Asignatura>();
	public static String cursoAcademico;
	public static int semanaInicioCurso;

	
	/** Lee y almacena los datos en el lugar correspondiente de los ficheros:
	 *  cursoAcademico.txt, aulas.txt, asinaturas.txt, pod.txt, profesores.txt y alumnos.txt
	 * 
	 * @throws IOException
	 */
	public static void arranque() throws IOException {

		// lectura curso academico

		ArrayList<ArrayList<String>> listacursoAcademico = leer(Comandos.CURSOACADEMICO);
		cursoAcademico = listacursoAcademico.get(0).get(0).toString();
		String semanaini = listacursoAcademico.get(0).get(1).toString();
		semanaInicioCurso = Integer.parseInt(semanaini);

		// lectura aulas

		ArrayList<ArrayList<String>> listaAulas = leer(Comandos.AULAS);
		for (int i = 0; i < listaAulas.size(); i++) {
			String clave = listaAulas.get(i).get(0).toString();
			aulas.put(clave, new Aula(listaAulas.get(i).get(0).toString(), listaAulas.get(i).get(1).toString(),
					listaAulas.get(i).get(2).toString()));
		}

		// lectura asignaturas

		ArrayList<ArrayList<String>> listaAsignaturas = leer(Comandos.ASIGNATURAS);

		for (int i = 0; i < listaAsignaturas.size(); i++) {
			
			String clave = listaAsignaturas.get(i).get(0);		
		//	String pre_requisitos = listaAsignaturas.get(i).get(5);
		
				//Aqui vamos si han metido todos los campos seguidos sin que falte ninguno tal y como hemos planeado
			asignaturas.put(clave,
					new Asignatura(listaAsignaturas.get(i).get(0).toString(), listaAsignaturas.get(i).get(1),
							listaAsignaturas.get(i).get(2), listaAsignaturas.get(i).get(3),
							listaAsignaturas.get(i).get(4), listaAsignaturas.get(i).get(5),
							listaAsignaturas.get(i).get(6), listaAsignaturas.get(i).get(7),
							listaAsignaturas.get(i).get(8), listaAsignaturas.get(i).get(9)));
			}
		
		
		// lectura pod

		ArrayList<ArrayList<String>> listaPod = leer(Comandos.POD);
		
		for (int i = 0; i < listaPod.size(); i++) {
			String clave = listaPod.get(i).get(0).trim();
			
			pod.put(clave, new Pod(listaPod.get(i).get(0).toString(), listaPod.get(i).get(1).toString()));
		} 
		
		// lectura profesores

		ArrayList<ArrayList<String>> listaProfesores = leer(Comandos.PROFESORES);
		
		for (int i = 0; i < listaProfesores.size(); i++) {
			String clave = listaProfesores.get(i).get(0).toString();
			//System.out.println(clave+listaProfesores.get(i).size());
			profesores.put(clave,
					new Profesor(listaProfesores.get(i).get(0).toString(), listaProfesores.get(i).get(1).toString(),
							listaProfesores.get(i).get(2).toString(), listaProfesores.get(i).get(3).toString(),
							listaProfesores.get(i).get(4).toString(), 
							listaProfesores.get(i).get(5)));
		}

		// lectura alumnos
		ArrayList<ArrayList<String>> listaAlumnos = leer(Comandos.ALUMNOS);
		
		for (int i = 0; i < listaAlumnos.size(); i++) {
			String clave = listaAlumnos.get(i).get(0).toString();
			alumnos.put(clave,
					new Alumno(listaAlumnos.get(i).get(0), listaAlumnos.get(i).get(1),
							listaAlumnos.get(i).get(2), listaAlumnos.get(i).get(3),
							listaAlumnos.get(i).get(4), listaAlumnos.get(i).get(5),
							listaAlumnos.get(i).get(6)));
		}

	}

	/**Esta funcion se usa cuando tengamos varios campos separados por el
	 * delimitador '*'
	 * 
	 * @param archivo.txt
	 *            nombre del fichero
	 * @return arraylist de arraylist
	 * @throws IOException
	 */
	public static ArrayList<ArrayList<String>> leer(String archivo) throws IOException {

		// Declaracion de variables
		ArrayList<String> lista =  new ArrayList<String>();
		ArrayList<ArrayList<String>> listas = new ArrayList<ArrayList<String>>();
		String lineas;
		FileInputStream fichero = null;
		InputStreamReader isr =null;
		// bloque try-catch si sale un error intenta el catch (mensaje de error y salir)
		try {
			fichero = new FileInputStream(archivo);
			isr = new InputStreamReader(fichero,"ISO-8859-1");
		} catch (FileNotFoundException e) {
			// en caso de error imprime mensaje y sale del programa
			File generarfichero = new File(archivo);
			
			return listas;
			
		}
		BufferedReader lectura = new BufferedReader(isr);

		// lectura linea a linea del fichero hasta el final y lo guarda en el araylist
		lineas=lectura.readLine();
		if(lineas.contains("ï»¿")) {
			lineas=lineas.substring(3, lineas.length());
		}
		
		lista.add(lineas.trim());
		while ((lineas = lectura.readLine()) != null) {
			
			if (lineas.isEmpty())lista.add(null);
			else if (lineas.toCharArray()[0] != '*') {
				//System.out.println(lineas);
				lista.add(lineas.trim());
				
				
			} else {
				
				listas.add(lista);
				lista= new ArrayList<String>();
				
				
			}
			

		}
		listas.add(lista);
	
		lectura.close(); // cerramos el objeto
		return listas;

	}
	
	
	/**Lee el fichero de ejecucion (ejecucion.txt)
	 * 
	 * @throws IOException
	 */
	public static void lecturaejecucion() throws IOException {
		String lineas;
		String archivo= Comandos.EJECUCION;
		FileReader fichero = null;
		// bloque try-catch si sale un error intenta el catch (mensaje de error y salir)
		try {
			fichero = new FileReader(archivo);
		} catch (FileNotFoundException e) {
			// en caso de error imprime mensaje y sale del programa
			GestionErrores.errorOtro("Fichero de ejecucion no existente");
			System.exit(1);
		}
		
	    BufferedReader lectura = new BufferedReader(fichero);
	    
		while ((lineas = lectura.readLine()) != null) {
			if (lineas.isEmpty())break;
			
			if(lineas.toCharArray()[0]=='*'||lineas.toCharArray()[5]=='*') {				
				continue;
				
			}
			else {
				 
				lineas=lineas.replaceAll("\\s+"," ");
				String prueba[]=lineas.trim().split(" ");
				try {
					Integer.parseInt(prueba[0]);
				}
				catch(NumberFormatException e) {
					lineas="0 ".concat(lineas);
				}
				String imput[]=lineas.trim().split(" ");
				
				Comandos.comandos(imput);
				
			}	

		} 
		//aqui llamanos a la funcion de sobreescribir los ficheros 
	}
	
	
	
	
	
	
	
	
	/**Vuelca el contenido del LinkedHashMap al fichero de texto correspondiente
	 * 
	 * @param nombreFichero (String): nombre del fichero, con su extensión (.txt)
	 * @param mapa (LinkedHashMap): mapa a escribir en el fichero especificado
	 */
	public static void sobreescribirFicheroAlumnos(String nombreFichero, LinkedHashMap<String, Alumno> mapa) {
		Set<String> claves = mapa.keySet();
		String ultima=null;
		for(String clave : claves){ 
			ultima=clave;
		}
		try {
			FileWriter fw = new FileWriter(nombreFichero);
			BufferedWriter bw= new BufferedWriter(fw);
			for(String clave : claves){ 
				
				
				bw.write(mapa.get(clave.toString()).toTexto());
			if(clave!=ultima) {	bw.write("\n*\n");
			
			}
			else {bw.write("\n");
			
			}
		
		
			} 
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	/**Vuelca el contenido del LinkedHashMap al fichero de texto correspondiente
	 * 
	 * @param nombreFichero (String): nombre del fichero, con su extensión (.txt)
	 * @param mapa (LinkedHashMap): mapa a escribir en el fichero especificado
	 */
	public static void sobreescribirFicheroProfesores(String nombreFichero, LinkedHashMap<String, Profesor> mapa) {
		Set<String> claves = mapa.keySet();
		String ultima=null;
		for(String clave : claves){ 
			ultima=clave;
		}
		try {
			FileWriter fw = new FileWriter(nombreFichero);
			BufferedWriter bw= new BufferedWriter(fw);
			for(String clave : claves){ 
				
				
				bw.write(mapa.get(clave.toString()).toTexto());
			if(clave!=ultima) {	bw.write("\n*\n");
			
			}
			else {bw.write("\n");
			
			}
		
		
			} 
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	
	
	
	/**Vuelca el contenido del LinkedHashMap al fichero de texto correspondiente
	 * 
	 * @param nombreFichero (String): nombre del fichero, con su extensión (.txt)
	 * @param mapa (LinkedHashMap): mapa a escribir en el fichero especificado
	 */
	public static void sobreescribirFicheroAsignaturas(String nombreFichero, LinkedHashMap<String, Asignatura> mapa) {
		Set<String> claves = mapa.keySet();
		String ultima=null;
		for(String clave : claves){ 
			ultima=clave;
		}
		try {
			FileWriter fw = new FileWriter(nombreFichero);
			BufferedWriter bw= new BufferedWriter(fw);
			for(String clave : claves){ 
				
				
				bw.write(mapa.get(clave.toString()).toTexto());
			if(clave!=ultima) {	bw.write("\n*\n");
			
			}
			else {bw.write("\n");
			
			}
		
		
			} 
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
}

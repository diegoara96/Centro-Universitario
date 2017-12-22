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
import java.util.LinkedHashMap;

import objetos.*;

public class ArranqueBaseDatos {
	public static LinkedHashMap<String, Alumno> alumnos = new LinkedHashMap<String, Alumno>();
	public static LinkedHashMap<String, Profesor> profesores = new LinkedHashMap<String, Profesor>();
	public static LinkedHashMap<String, Aula> aulas = new LinkedHashMap<String, Aula>();
	public static LinkedHashMap<String, Pod> pod = new LinkedHashMap<String, Pod>();
	// Atencion la clave para el Pod tiene que ser un concat de
	// dni+asignatura+tipoGrupo
	public static LinkedHashMap<String, Asignatura> asignaturas = new LinkedHashMap<String, Asignatura>();
	public static String cursoAcademico;
	public static int semanaInicioCurso;

	public static void arranque() throws IOException {

		// lectura curso academico

		ArrayList<ArrayList<String>> listacursoAcademico = leer("cursoAcademico.txt");
		cursoAcademico = listacursoAcademico.get(0).get(0).toString();
		String semanaini = listacursoAcademico.get(0).get(1).toString();
		semanaInicioCurso = Integer.parseInt(semanaini);

		// lectura aulas

		ArrayList<ArrayList<String>> listaAulas = leer("aulas.txt");
		for (int i = 0; i < listaAulas.size(); i++) {
			String clave = listaAulas.get(i).get(0).toString();
			aulas.put(clave, new Aula(listaAulas.get(i).get(0).toString(), listaAulas.get(i).get(1).toString(),
					listaAulas.get(i).get(2).toString()));
		}

		// lectura asignaturas

		ArrayList<ArrayList<String>> listaAsignaturas = leer("asignaturas.txt");

		for (int i = 0; i < listaAsignaturas.size(); i++) {
			
			String clave = listaAsignaturas.get(i).get(4);		
			String pre_requisitos = listaAsignaturas.get(i).get(5);
			
			if(pre_requisitos==null ) {
				//si pre_requisitos es null significa que en la linea que irian las asignaturas de pre requisitos han metido una linea en blanco
				//metemos en el constructor null en su posicion
				asignaturas.put(clave,
						new Asignatura(listaAsignaturas.get(i).get(0).toString(), listaAsignaturas.get(i).get(1),
								listaAsignaturas.get(i).get(2), listaAsignaturas.get(i).get(3),
								listaAsignaturas.get(i).get(4), null,
								listaAsignaturas.get(i).get(6), listaAsignaturas.get(i).get(7),
								listaAsignaturas.get(i).get(8), listaAsignaturas.get(i).get(9)));
				
			}else if(pre_requisitos.length()==1){
				//hay veces que pueden saltarse la linea en la que van los pre requisitos y saltan al siguiente campo que son horas, por lo que si
				//pre_requisitos es de tama�o 1 metieron una hora, por lo que en el constructor en pre requisitos metemos null
				asignaturas.put(clave,
						new Asignatura(listaAsignaturas.get(i).get(0).toString(), listaAsignaturas.get(i).get(1),
								listaAsignaturas.get(i).get(2), listaAsignaturas.get(i).get(3),
								listaAsignaturas.get(i).get(4), null,
								listaAsignaturas.get(i).get(5), listaAsignaturas.get(i).get(6),
								listaAsignaturas.get(i).get(7), listaAsignaturas.get(i).get(8)));		
			}
			else{
				//Aqui vamos si han metido todos los campos seguidos sin que falte ninguno tal y como hemos planeado
			asignaturas.put(clave,
					new Asignatura(listaAsignaturas.get(i).get(0).toString(), listaAsignaturas.get(i).get(1),
							listaAsignaturas.get(i).get(2), listaAsignaturas.get(i).get(3),
							listaAsignaturas.get(i).get(4), listaAsignaturas.get(i).get(5),
							listaAsignaturas.get(i).get(6), listaAsignaturas.get(i).get(7),
							listaAsignaturas.get(i).get(8), listaAsignaturas.get(i).get(9)));
			}
			}

		// lectura pod

		ArrayList<ArrayList<String>> listaPod = leer("pod.txt");
		for (int i = 0; i < listaPod.size(); i++) {
			String clave = listaPod.get(i).get(0).trim()
					.concat(listaPod.get(i).get(1).trim().concat(listaPod.get(i).get(2).trim()));
			
			pod.put(clave, new Pod(listaPod.get(i).get(0).toString(), listaPod.get(i).get(1).toString(),
					listaPod.get(i).get(2).toString(), listaPod.get(i).get(3).toString()));
		} 
		
		// lectura profesores

		ArrayList<ArrayList<String>> listaProfesores = leer("profesores.txt");
		for (int i = 0; i < listaProfesores.size(); i++) {
			String clave = listaProfesores.get(i).get(0).toString();
			profesores.put(clave,
					new Profesor(listaProfesores.get(i).get(0).toString(), listaProfesores.get(i).get(1).toString(),
							listaProfesores.get(i).get(2).toString(), listaProfesores.get(i).get(3).toString(),
							listaProfesores.get(i).get(4).toString(), listaProfesores.get(i).get(5).toString()));
		}

		// lectura alumnos
		ArrayList<ArrayList<String>> listaAlumnos = leer("alumnos.txt");
		for (int i = 0; i < listaAlumnos.size(); i++) {
			String clave = listaAlumnos.get(i).get(0).toString();
			alumnos.put(clave,
					new Alumno(listaAlumnos.get(i).get(0), listaAlumnos.get(i).get(1),
							listaAlumnos.get(i).get(2), listaAlumnos.get(i).get(3),
							listaAlumnos.get(i).get(4), listaAlumnos.get(i).get(5),
							listaAlumnos.get(i).get(6)));
		}

	}

	/**
	 * Esta funcion se usa cuando tengamos varios campos separados por el
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
			
			System.exit(1);
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
	
	
	
	public static void lecturaejecucion() throws IOException {
		String lineas;
		String archivo= "ejecucion.txt";
		FileReader fichero = null;
		// bloque try-catch si sale un error intenta el catch (mensaje de error y salir)
		try {
			fichero = new FileReader(archivo);
		} catch (FileNotFoundException e) {
			// en caso de error imprime mensaje y sale del programa
			System.out.println("444");
			System.exit(1);
		}
		
		BufferedReader lectura = new BufferedReader(fichero);
		while ((lineas = lectura.readLine()) != null) {
			if (lineas.isEmpty())break;
			
			if(lineas.toCharArray()[0]=='*'||lineas.toCharArray()[1]=='*') {
				//System.out.println(lineas.toCharArray()[0]);
				continue;
			}
			else {
			//	System.out.println(lineas.toCharArray()[0]);
				lineas=lineas.replaceAll("\\s+"," ");
				String imput[]=lineas.trim().split(" ");
				for (int i=0;i<imput.length;i++) {
					System.out.println(imput[i].trim());
				}
			//	System.out.println(imput[2]);
				Comandos.comandos(imput);
			}	

		}
		//aqui llamanos a la funcion de sobreescribir los ficheros 
	}
	
	/**
	 * Vuelca el contenido del LinkedHashMap al fichero de texto correspondiente
	 * @param nombreFichero (String): nombre del fichero, con su extensión (.txt)
	 * @param mapa (LinkedHashMap): mapa a escribir en el fichero especificado
	 */
	private static void sobreescribirFichero(String nombreFichero, LinkedHashMap<String, EscribibleEnFichero> mapa) {
		
	}
	
}

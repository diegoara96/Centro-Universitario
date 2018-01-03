package proyecto;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.BufferedReader;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import objetos.Alumno;
import objetos.Notas;
import objetos.OcupacionAula;
import objetos.Persona;
import objetos.Profesor;
import proyecto.GestionErrores;

public class Comandos {

	// declaramos las variables constantes para nuestra comodidad
	public final static String ALUMNOS = "alumnos.txt";
	public final static String PROFESORES = "profesores.txt";
	public final static String ASIGNATURAS = "asignaturas.txt";
	public final static String AULAS = "aulas.txt";
	public final static String POD = "pod.txt";
	public final static String CURSOACADEMICO = "cursoAcademico.txt";
	public final static String EJECUCION = "ejecucion.txt";

	/**
	 * Realiza las operaciones necesarias para llevar a cabo las ordenes que se le
	 * indiquen
	 * 
	 * @param imput:
	 *            array de las lineas del fichero ejecucion.txt que contendran las
	 *            operaciones que habra que llevar a cabo y los parametros
	 *            necesarios para completarlas
	 */

	public static void comandos(String imput[]) {
		String comando = imput[1].toLowerCase();
		switch (comando) {

		case "insertapersona":
			insertaPersona(imput);
			break;
		case "asignagrupo":
			asignaGrupo(imput);
			break;
		case "matricula":
			matricularalumno(imput);
			break;
		case "creagrupoasig":
			creaGrupoAsig(imput);
			break;
		case "evalua":
			evaluarAsig(imput);
			break;
		case "expediente": // WIP
			obtenerExpediente(imput);
			break;
		case "ocupacionaula": // WIP
			calendarioAulas(imput);
			break;

		default: // gestion de errores funcion mal escrita
			GestionErrores.comandoErroneo(imput[1]);
			break;

		}

	}

	/**
	 * Introduce un alumno o profesor nuevo al sistema
	 * 
	 * @param imput:
	 *            array que contiene los datos de la linea correspondiente en el
	 *            fichero de ejecuciones (ejecucion.txt)
	 */
	public static void insertaPersona(String imput[]) {

		if (imput.length < 9) {
			GestionErrores.errorComando(GestionErrores.INSERTA_PERSONA, "numero de argumentos incorrecto");
			return;
		}

		String parametros[] = new String[imput.length];
		String nombre;
		int b = 0, i;

		/*
		 * b es el numero de parametros que tiene cada comando
		 * "comando + perfil + dni +nombre ...." en general b=0 comando, b=1 perfil, b=2
		 * dni, b=3 nombre, b=4 fecha nacimiento para profesor b=5 categoria, b=6
		 * departamento para alumno b=5 fecha de ingreso
		 */

		for (i = 1; i < parametros.length; i++) {
			if (imput[i].contains("\"")) {
				nombre = imput[i];
				for (int a = i + 1; a < 10; a++) {
					nombre = nombre.concat(" ").concat(imput[a]);
					if (imput[a].contains("\"")) {
						parametros[b] = nombre.substring(1, nombre.length() - 1);

						i = a;

						break;
					}
				}

			} else {
				parametros[b] = imput[i];
			}

			b++;
		}

		if (parametros[1].contains("profesor")) {

			if (b != 7) {
				GestionErrores.errorComando(GestionErrores.INSERTA_PERSONA, "numero de argumentos incorrecto");
				return;
			}

			if (Persona.comprobarDNI(parametros[2]) == false) {
				GestionErrores.errorComando(GestionErrores.INSERTA_PERSONA, "dni incorrecto");
				return;
			}

			if (ArranqueBaseDatos.profesores.containsKey(parametros[2])) {
				GestionErrores.errorComando(GestionErrores.INSERTA_PERSONA, "Profesor ya existente");
				return;
			}

			if (Persona.comprobarFecha(parametros[4]) == false) {
				GestionErrores.errorComando(GestionErrores.INSERTA_PERSONA, "Fecha incorrecta");
				return;
			}

			else {
				ArranqueBaseDatos.profesores.put(parametros[2],
						new Profesor(parametros[2], parametros[3], parametros[4], parametros[5], parametros[6]));

				ArranqueBaseDatos.sobreescribirFicheroProfesores(PROFESORES, ArranqueBaseDatos.profesores);

			}
		}

		if (parametros[1].contains("alumno")) {
			if (b != 6) {
				GestionErrores.errorComando(GestionErrores.INSERTA_PERSONA, "numero de argumentos incorrecto");
			}

			if (Persona.comprobarDNI(parametros[2]) == false) {
				GestionErrores.errorComando(GestionErrores.INSERTA_PERSONA, "dni incorrecto");
				return;
			}

			if (ArranqueBaseDatos.alumnos.containsKey(parametros[2])) {
				GestionErrores.errorComando(GestionErrores.INSERTA_PERSONA, "Alumno ya existente");
				return;
			}

			if (Persona.comprobarFecha(parametros[4]) == false) {
				GestionErrores.errorComando(GestionErrores.INSERTA_PERSONA, "Fecha incorrecta");
				return;
			}
			if (Persona.comprobarFechaIngreso(parametros[4], parametros[5]) == false) {
				GestionErrores.errorComando(GestionErrores.INSERTA_PERSONA, "Fecha ingreso incorrecta");
				return;
			}

			else {
				ArranqueBaseDatos.alumnos.put(parametros[2],
						new Alumno(parametros[2], parametros[3], parametros[4], parametros[5]));

				ArranqueBaseDatos.sobreescribirFicheroAlumnos(ALUMNOS, ArranqueBaseDatos.alumnos);

			}

		}

	}

	/**
	 * Asigna un grupo de una asignautra a un profesor o alumno
	 * 
	 * @param imput:
	 *            array que contiene los datos de la linea correspondiente en el
	 *            fichero de ejecuciones (ejecucion.txt)
	 */
	public static void asignaGrupo(String[] imput) {
		if (imput.length != 7) {
			GestionErrores.errorComando(GestionErrores.ASIGNAR_GRUPO, "numero de argumentos incorrecto");
		}
		String parametros[] = new String[imput.length];
		for (int i = 1; i < parametros.length; i++) {

			parametros[i - 1] = imput[i];
		}

		if (parametros[1].contains("profesor")) {

			// profesor inexistente
			if (!ArranqueBaseDatos.profesores.containsKey(parametros[2])) {
				GestionErrores.errorComando(GestionErrores.ASIGNAR_GRUPO, "profesor inexistente");
				return;
			}
			// asignatura inexistente
			if (!ArranqueBaseDatos.asignaturas.containsKey(parametros[3])) {
				GestionErrores.errorComando(GestionErrores.ASIGNAR_GRUPO, "Asignatura inexistente");
				return;
			}
			// tipo de grupo incorrecto
			if (parametros[4].toCharArray()[0] != 'A' && parametros[4].toCharArray()[0] != 'B') {
				GestionErrores.errorComando(GestionErrores.ASIGNAR_GRUPO, "Tipo de grupo incorrecto");
				return;
			}
			// grupo inexistente
			if (parametros[4].toCharArray()[0] == 'A') {
				if (!ArranqueBaseDatos.asignaturas.get(parametros[3]).getIdgrupoA()
						.contains(Integer.parseInt(parametros[5]))) {
					GestionErrores.errorComando(GestionErrores.ASIGNAR_GRUPO, "Grupo inexistente");
					return;
				}
			}

			if (parametros[4].toCharArray()[0] == 'B') {
				if (!ArranqueBaseDatos.asignaturas.get(parametros[3]).getIdgrupoB()
						.contains(Integer.parseInt(parametros[5]))) {
					GestionErrores.errorComando(GestionErrores.ASIGNAR_GRUPO, "Grupo inexistente");
					return;
				}
			}
			// no presente en pod profesor
			int posicion = 0;
			if (!ArranqueBaseDatos.pod.get(parametros[2]).getAsignatura().contains(parametros[3].trim())) {
				GestionErrores.errorComando(GestionErrores.ASIGNAR_GRUPO, "Asignatura no presente en POD del profesor");
				return;
			}
			// posicion del grupo correspontiente a la asignatura en el pod
			boolean grupo = false;
			for (int i = 0; i < ArranqueBaseDatos.pod.get(parametros[2]).getAsignatura().size(); i++) {
				if (ArranqueBaseDatos.pod.get(parametros[2]).getAsignatura(i).trim().equals(parametros[3].trim())) {
					if (ArranqueBaseDatos.pod.get(parametros[2])
							.getTipoGrupo(i) == parametros[4].trim().toCharArray()[0]) {
						posicion = i;
						grupo = true;
					}
				}
			}
			if (grupo == false) {
				GestionErrores.errorComando(GestionErrores.ASIGNAR_GRUPO, "Tipo grupo no presente en POD del profesor");
				return;
			}

			// grupo ya asignado
			Set<String> claves = ArranqueBaseDatos.profesores.keySet();

			for (String clave : claves) {
				ArrayList<String> repetido = ArranqueBaseDatos.profesores.get(clave).getSiglasAsignatura();
				for (int i = 0; i < repetido.size(); i++) {
					if (repetido.get(i).equals(parametros[3])) {

						if (ArranqueBaseDatos.profesores.get(clave).getTipoGrupo(i) == parametros[4].toCharArray()[0]) {

							if (ArranqueBaseDatos.profesores.get(clave).getId_Grupo(i) == Integer
									.parseInt(parametros[5])) {

								GestionErrores.errorComando(GestionErrores.ASIGNAR_GRUPO, "Grupo ya asignado");
								return;
							}
						}
					}
				}

			}

			// Número de grupos superior al contemplado en POD
			float nummaxgrupos = ArranqueBaseDatos.pod.get(parametros[2]).getNumeroGrupos(posicion);
			float numerogruposdados = 0;

			for (int i = 0; i < ArranqueBaseDatos.profesores.get(parametros[2]).getSiglasAsignatura().size(); i++) {

				if (parametros[3].trim()
						.equals(ArranqueBaseDatos.profesores.get(parametros[2]).getSiglas_Asignatura(i))) {

					if (ArranqueBaseDatos.profesores.get(parametros[2])
							.getTipoGrupo(i) == parametros[4].toCharArray()[0]) {
						numerogruposdados++;

					}
				}
			}

			if (nummaxgrupos < (numerogruposdados + 1)) {

				GestionErrores.errorComando(GestionErrores.ASIGNAR_GRUPO,
						"Número de grupos superior al contemplado en POD");
				return;
			}
			// solape de horas
			int cuatrimestre = ArranqueBaseDatos.asignaturas.get(parametros[3]).getCuatrimestre();
			for (int i = 0; i < ArranqueBaseDatos.profesores.get(parametros[2]).getSiglasAsignatura().size(); i++) {
				if (cuatrimestre == ArranqueBaseDatos.asignaturas
						.get(ArranqueBaseDatos.profesores.get(parametros[2]).getSiglas_Asignatura(i))
						.getCuatrimestre()) {
					if (ArranqueBaseDatos.asignaturas
							.get(ArranqueBaseDatos.profesores.get(parametros[2]).getSiglas_Asignatura(i))
							.getdia(ArranqueBaseDatos.profesores.get(parametros[2]).getTipoGrupo(i),
									ArranqueBaseDatos.profesores.get(parametros[2])
											.getId_Grupo(i)) == ArranqueBaseDatos.asignaturas.get(parametros[3]).getdia(
													parametros[4].trim().toCharArray()[0],
													Integer.parseInt(parametros[5]))) {

						int hora = Integer.parseInt(ArranqueBaseDatos.asignaturas
								.get(ArranqueBaseDatos.profesores.get(parametros[2]).getSiglas_Asignatura(i))
								.gethora(ArranqueBaseDatos.profesores.get(parametros[2]).getTipoGrupo(i),
										ArranqueBaseDatos.profesores.get(parametros[2]).getId_Grupo(i)));
						int horaGrupo = Integer.parseInt(ArranqueBaseDatos.asignaturas.get(parametros[3])
								.gethora(parametros[4].toCharArray()[0], Integer.parseInt(parametros[5])));
						int duracionGrupo = ((parametros[4].toCharArray()[0] == 'A')
								? ArranqueBaseDatos.asignaturas.get(parametros[3]).getDuracion_GrupoA()
								: ArranqueBaseDatos.asignaturas.get(parametros[3]).getDuracionGrupoB());
						int duracionsolape = ((ArranqueBaseDatos.profesores.get(parametros[2]).getTipoGrupo(i) == 'A')
								? ArranqueBaseDatos.asignaturas
										.get(ArranqueBaseDatos.profesores.get(parametros[2]).getSiglas_Asignatura(i))
										.getDuracion_GrupoA()
								: ArranqueBaseDatos.asignaturas
										.get(ArranqueBaseDatos.profesores.get(parametros[2]).getSiglas_Asignatura(i))
										.getDuracionGrupoB());
						if (horaGrupo == hora || (horaGrupo + duracionGrupo) == (hora + 1)
								|| (horaGrupo + 1) == (hora + duracionsolape)) {
							GestionErrores.errorComando(GestionErrores.ASIGNAR_GRUPO, "Solape profesor");

							return;
						}
					}
				}
			}

			// agregamos el grupo al profesor
			ArranqueBaseDatos.profesores.get(parametros[2]).asignagrupo(parametros[3], parametros[4].toCharArray()[0],
					Integer.parseInt(parametros[5]));

			ArranqueBaseDatos.sobreescribirFicheroProfesores(PROFESORES, ArranqueBaseDatos.profesores);

		}

		if (parametros[1].contains("alumno")) {

			// alumno inexistente
			if (!ArranqueBaseDatos.alumnos.containsKey(parametros[2])) {
				GestionErrores.errorComando(GestionErrores.ASIGNAR_GRUPO, "alumno inexistente");
				return;
			}
			// asignatura inexistente
			if (!ArranqueBaseDatos.asignaturas.containsKey(parametros[3])) {
				GestionErrores.errorComando(GestionErrores.ASIGNAR_GRUPO, "Asignatura inexistente");
				return;
			}
			// tipo de grupo incorrecto
			if (parametros[4].toCharArray()[0] != 'A' && parametros[4].toCharArray()[0] != 'B') {
				GestionErrores.errorComando(GestionErrores.ASIGNAR_GRUPO, "Tipo de grupo incorrecto");
				return;
			}

			// Alumno no matriculado

			if (!ArranqueBaseDatos.alumnos.get(parametros[2]).getSiglasAsignaturaActual()
					.contains(parametros[3].trim())) {

				GestionErrores.errorComando(GestionErrores.ASIGNAR_GRUPO, "Alumno no matriculado");
				return;
			}
			// Solape alumno

			int cuatrimestre = ArranqueBaseDatos.asignaturas.get(parametros[3]).getCuatrimestre();
			int curso = ArranqueBaseDatos.asignaturas.get(parametros[3]).getCurso();

			for (int i = 0; i < ArranqueBaseDatos.alumnos.get(parametros[2]).getSiglasAsignaturaActual().size(); i++) {
				if (curso == ArranqueBaseDatos.asignaturas
						.get(ArranqueBaseDatos.alumnos.get(parametros[2]).getSiglas_Asignatura_Actual(i)).getCurso()) {
					if (cuatrimestre == ArranqueBaseDatos.asignaturas
							.get(ArranqueBaseDatos.alumnos.get(parametros[2]).getSiglas_Asignatura_Actual(i))
							.getCuatrimestre()) {
						
						if (ArranqueBaseDatos.alumnos.get(parametros[2]).getTipoGrupo(i) == '0') {
							continue;
						} 
						if (ArranqueBaseDatos.asignaturas.get(parametros[3]).getdia(
								parametros[4].trim().toCharArray()[0],
								Integer.parseInt(parametros[5])) == ArranqueBaseDatos.asignaturas
										.get(ArranqueBaseDatos.alumnos.get(parametros[2])
												.getSiglas_Asignatura_Actual(i))
										.getdia(ArranqueBaseDatos.alumnos.get(parametros[2]).getTipoGrupo(i),
												ArranqueBaseDatos.alumnos.get(parametros[2]).getId_Grupo(i))) {
							
							int horaSolape = Integer.parseInt(ArranqueBaseDatos.asignaturas
									.get(ArranqueBaseDatos.alumnos.get(parametros[2]).getSiglas_Asignatura_Actual(i))
									.gethora(ArranqueBaseDatos.alumnos.get(parametros[2]).getTipoGrupo(i),
											ArranqueBaseDatos.alumnos.get(parametros[2]).getId_Grupo(i)));

							int horaGrupo = Integer.parseInt(ArranqueBaseDatos.asignaturas.get(parametros[3])
									.gethora(parametros[4].toCharArray()[0], Integer.parseInt(parametros[5])));
							int duracionGrupo = ((parametros[4].toCharArray()[0] == 'A')
									? ArranqueBaseDatos.asignaturas.get(parametros[3]).getDuracion_GrupoA()
									: ArranqueBaseDatos.asignaturas.get(parametros[3]).getDuracionGrupoB());
							int duracionsolape = ((ArranqueBaseDatos.alumnos.get(parametros[2]).getTipoGrupo(i) == 'A')
									? ArranqueBaseDatos.asignaturas.get(
											ArranqueBaseDatos.alumnos.get(parametros[2]).getSiglas_Asignatura_Actual(i))
											.getDuracion_GrupoA()
									: ArranqueBaseDatos.asignaturas.get(
											ArranqueBaseDatos.alumnos.get(parametros[2]).getSiglas_Asignatura_Actual(i))
											.getDuracionGrupoB());

							if (horaGrupo == horaSolape || (horaGrupo + duracionGrupo) == (horaSolape + 1)
									|| (horaGrupo + 1) == (horaSolape + duracionsolape)) {
								GestionErrores.errorComando(GestionErrores.ASIGNAR_GRUPO, "Solape alumno");

								return;
							}
						}
					}
				}

			}

			// aula ocupada

			int maximoPersonasA = 40;
			int maximoPersonasB = 20;
			int capacidad = 0;
			int capacidadAula = ArranqueBaseDatos.aulas
					.get(ArranqueBaseDatos.asignaturas.get(parametros[3])
							.getclase(parametros[4].trim().toCharArray()[0], Integer.parseInt(parametros[5])))
					.getCapacidad();
			if (ArranqueBaseDatos.aulas
					.get(ArranqueBaseDatos.asignaturas.get(parametros[3])
							.getclase(parametros[4].trim().toCharArray()[0], Integer.parseInt(parametros[5])))
					.getTipo_Grupo().toCharArray()[0] == 'A') {
				capacidad = ((capacidadAula < maximoPersonasA) ? capacidadAula : maximoPersonasA);
			} else
				capacidad = ((capacidadAula < maximoPersonasB) ? capacidadAula : maximoPersonasB);

			Set<String> claves = ArranqueBaseDatos.alumnos.keySet();
			int numeropersonas = 0;

			for (String clave : claves) {
				for (int i = 0; i < ArranqueBaseDatos.alumnos.get(clave).getSiglasAsignaturaActual().size(); i++) {
					if (ArranqueBaseDatos.alumnos.get(clave).getSiglas_Asignatura_Actual(i).equals(parametros[3])) {
						if ((ArranqueBaseDatos.alumnos.get(clave).getTipoGrupo(i) == parametros[4].toCharArray()[0])) {
							if (ArranqueBaseDatos.alumnos.get(clave).getId_Grupo(i) == Integer
									.parseInt(parametros[5])) {
								numeropersonas++;

							}
						}
					}

				}

			}

			if (capacidad < numeropersonas + 1) {
				GestionErrores.errorComando(GestionErrores.ASIGNAR_GRUPO, "Aula completa");
				return;
			}

			// si llega aqui todo correcto
			// guardamos el grupo en el alumno
			ArranqueBaseDatos.alumnos.get(parametros[2]).asignargrupo(parametros[3], parametros[4].toCharArray()[0],
					Integer.parseInt(parametros[5]));

			ArranqueBaseDatos.sobreescribirFicheroAlumnos(ALUMNOS, ArranqueBaseDatos.alumnos);

		}

	}

	/**
	 * Matricular a un alumno en una asignatura
	 * 
	 * @param imput:
	 *            array que contiene los datos de la linea correspondiente en el
	 *            fichero de ejecuciones (ejecucion.txt)
	 * 
	 */
	public static void matricularalumno(String imput[]) {
		if (imput.length != 4) {
			GestionErrores.errorComando(GestionErrores.MATRICULAR_ALUMNO, "numero de argumentos incorrecto");
			return;
		}

		String parametros[] = new String[imput.length];
		for (int i = 1; i < parametros.length; i++) {

			parametros[i - 1] = imput[i].trim();
		}

		if (!ArranqueBaseDatos.alumnos.containsKey(parametros[1])) {
			GestionErrores.errorComando(GestionErrores.MATRICULAR_ALUMNO, "alumno inexistente");
			return;
		}

		if (!ArranqueBaseDatos.asignaturas.containsKey(parametros[2])) {
			GestionErrores.errorComando(GestionErrores.MATRICULAR_ALUMNO, "asignatura inexistente");
			return;
		}

		// se comprueba que el alumno no está matriculado en la asignatura buscando
		// coincidencias en la lista sigla asignatura actual
		for (int i = 0; i < ArranqueBaseDatos.alumnos.get(parametros[1]).getSiglasAsignaturaActual().size(); i++) {
			if (ArranqueBaseDatos.alumnos.get(parametros[1]).getSiglas_Asignatura_Actual(i).equals(parametros[2])) {
				GestionErrores.errorComando(GestionErrores.MATRICULAR_ALUMNO, "Ya es alumno de la asignatura indicada");
				return;
			}
		}

		// comprobamos que todas las siglas de las asignaturas requeridas estan en
		// asignaturas superadas
		for (int i = 0; i < ArranqueBaseDatos.asignaturas.get(parametros[2]).getPreRequisitos().size(); i++) {
			if (!ArranqueBaseDatos.alumnos.get(parametros[1]).getSiglasAsignaturaSuperada()
					.contains(ArranqueBaseDatos.asignaturas.get(parametros[2]).getPre_Requisitos(i))) {
				GestionErrores.errorComando(GestionErrores.MATRICULAR_ALUMNO, "No cumple requisitos");
				return;
			}
		}
		ArranqueBaseDatos.alumnos.get(parametros[1]).matricula(parametros[2]);
		ArranqueBaseDatos.sobreescribirFicheroAlumnos(ALUMNOS, ArranqueBaseDatos.alumnos);

	}

	/**
	 * Asigna un grupo de una asignatura a un alumno matriculado en ella
	 * 
	 * @param imput:
	 *            array que contiene los datos de la linea correspondiente en el
	 *            fichero de ejecuciones (ejecucion.txt)
	 */
	public static void creaGrupoAsig(String[] imput) {
		/*
		 * parametro 0 = comando parametro 1 = sigla asignatura parametro 2 = tipogrupo
		 * parametro 3 = idgrupo parametro 4 = dia parametro 5 = horainicio parametro 6
		 * = aula
		 */
		if (imput.length != 8) {
			GestionErrores.errorComando(GestionErrores.CREAR_GRUPO_ASIGNATURA, "numero de argumentos incorrecto");
			return;
		}

		String parametros[] = new String[imput.length];
		for (int i = 1; i < parametros.length; i++) {

			parametros[i - 1] = imput[i];
		}

		// asignatura inexistente
		if (!ArranqueBaseDatos.asignaturas.containsKey(parametros[1])) {
			GestionErrores.errorComando(GestionErrores.CREAR_GRUPO_ASIGNATURA, "Asignatura inexistente");
			return;
		}
		// tipo de grupo incorrecto
		if (parametros[2].toCharArray()[0] != 'A' && parametros[2].toCharArray()[0] != 'B') {
			GestionErrores.errorComando(GestionErrores.CREAR_GRUPO_ASIGNATURA, "Tipo de grupo incorrecto");
			return;
		}
		// grupo existente
		if (parametros[2].toCharArray()[0] == 'A') {
			if (ArranqueBaseDatos.asignaturas.get(parametros[1]).getIdgrupoA()
					.contains(Integer.parseInt(parametros[3]))) {
				GestionErrores.errorComando(GestionErrores.CREAR_GRUPO_ASIGNATURA, "Grupo ya existente");
				return;

			}
		}
		if (parametros[2].toCharArray()[0] == 'B') {
			if (ArranqueBaseDatos.asignaturas.get(parametros[1]).getIdgrupoB()
					.contains(Integer.parseInt(parametros[3]))) {
				GestionErrores.errorComando(GestionErrores.CREAR_GRUPO_ASIGNATURA, "Grupo ya existente");
				return;

			}
		}

		// dia incorrecto
		if (parametros[4].toCharArray()[0] != 'L' && parametros[4].toCharArray()[0] != 'M'
				&& parametros[4].toCharArray()[0] != 'X' && parametros[4].toCharArray()[0] != 'J'
				&& parametros[4].toCharArray()[0] != 'V') {

			GestionErrores.errorComando(GestionErrores.CREAR_GRUPO_ASIGNATURA, "Dia incorrecto");
			return;
		}

		// aula no existente
		if (!ArranqueBaseDatos.aulas.containsKey(parametros[6])) {
			GestionErrores.errorComando(GestionErrores.CREAR_GRUPO_ASIGNATURA, "Aula no existente");
			return;
		}

		// solape de aula
		Set<String> claves = ArranqueBaseDatos.asignaturas.keySet();

		for (String clave : claves) {

			if (parametros[2].toCharArray()[0] == 'A') {

				for (int i = 0; i < ArranqueBaseDatos.asignaturas.get(clave).getClasegrupoA().size(); i++) {
					if (parametros[4].trim().toCharArray()[0] == ArranqueBaseDatos.asignaturas.get(clave)
							.getDiagrupoA(i)) {
						if (ArranqueBaseDatos.asignaturas.get(clave).getclaseA(i).equals(parametros[6].trim())) {
							if (ArranqueBaseDatos.asignaturas.get(clave).getCuatrimestre()==ArranqueBaseDatos.asignaturas.get(parametros[1].trim()).getCuatrimestre()) {
								
								int horaSolape = Integer.parseInt(ArranqueBaseDatos.asignaturas
										.get(clave).gethora('A', ArranqueBaseDatos.asignaturas.get(clave).getIdgrupoB(i)));
										

								int horaGrupo = Integer.parseInt(parametros[5]);
								int duracionGrupo = ((parametros[2].toCharArray()[0] == 'A')
										? ArranqueBaseDatos.asignaturas.get(parametros[1]).getDuracion_GrupoA()
										: ArranqueBaseDatos.asignaturas.get(parametros[1]).getDuracionGrupoB());
								int duracionsolape = ArranqueBaseDatos.asignaturas.get(clave).getDuracionGrupoA();

								if (horaGrupo == horaSolape || (horaGrupo + duracionGrupo) == (horaSolape + 1)
										|| (horaGrupo + 1) == (horaSolape + duracionsolape)) {
									GestionErrores.errorComando(GestionErrores.CREAR_GRUPO_ASIGNATURA,
											"Solape de Aula");

									return;
								}

							}
						}
					}
				}

			}
			if (parametros[2].toCharArray()[0] == 'B') {

				for (int i = 0; i < ArranqueBaseDatos.asignaturas.get(clave).getClasegrupoB().size(); i++) {
					if (parametros[4].trim().toCharArray()[0] == ArranqueBaseDatos.asignaturas.get(clave)
							.getDiagrupoB(i)) {
						if (ArranqueBaseDatos.asignaturas.get(clave).getclaseB(i).equals(parametros[6].trim())) {
						if (ArranqueBaseDatos.asignaturas.get(clave).getCuatrimestre()==ArranqueBaseDatos.asignaturas.get(parametros[1].trim()).getCuatrimestre()) {
							
								int horaSolape = Integer.parseInt(ArranqueBaseDatos.asignaturas
										.get(clave).gethora('B', ArranqueBaseDatos.asignaturas.get(clave).getIdgrupoB(i)));
										

								int horaGrupo = Integer.parseInt(parametros[5]);
								int duracionGrupo = ((parametros[2].toCharArray()[0] == 'A')
										? ArranqueBaseDatos.asignaturas.get(parametros[1]).getDuracion_GrupoA()
										: ArranqueBaseDatos.asignaturas.get(parametros[1]).getDuracionGrupoB());
								int duracionsolape = ArranqueBaseDatos.asignaturas.get(clave).getDuracionGrupoB();

								if (horaGrupo == horaSolape || (horaGrupo + duracionGrupo) == (horaSolape + 1)
										|| (horaGrupo + 1) == (horaSolape + duracionsolape)) {
									GestionErrores.errorComando(GestionErrores.CREAR_GRUPO_ASIGNATURA,
											"Solape de Aula");

									return;
								}

							}
						}
					}}
				}

			

		}
		ArranqueBaseDatos.asignaturas.get(parametros[1]).creaGrupo(parametros[2].trim().toCharArray()[0],
				Integer.parseInt(parametros[3]), parametros[4].trim().toCharArray()[0], parametros[5].trim(),
				parametros[6].trim());
		ArranqueBaseDatos.sobreescribirFicheroAsignaturas(ASIGNATURAS, ArranqueBaseDatos.asignaturas);
	}

	/**
	 * A�adir una nota a una asignatura
	 * 
	 * @param imput:
	 *            array que contiene los datos de la linea correspondiente en el
	 *            fichero de ejecuciones (ejecucion.txt)
	 */
	public static void evaluarAsig(String[] imput) {
		if (imput.length != 5) {
			GestionErrores.errorComando(GestionErrores.EVALUAR_ASIGNATURA, "numero de argumentos incorrecto");
			return;
		}

		String parametros[] = new String[imput.length];
		for (int i = 1; i < parametros.length; i++) {

			parametros[i - 1] = imput[i];
		}
		LinkedHashMap<String, Notas> notasalumnos = new LinkedHashMap<String, Notas>();

		// parametro 0 = comando parametro 1 = asignatura
		// parametro 2 = ficheronotasA parametros 3 = ficheronotasB

		// asignatura inexistente
		if (!ArranqueBaseDatos.asignaturas.containsKey(parametros[1])) {
			GestionErrores.errorComando(GestionErrores.EVALUAR_ASIGNATURA, "Asignatura inexistente");

			return;
		}
		// asignatura ya evaluada
		Set<String> claves = ArranqueBaseDatos.alumnos.keySet();

		for (String clave : claves) {
			for (int i = 0; i < ArranqueBaseDatos.alumnos.get(clave).getSiglasAsignaturaSuperada().size(); i++) {
				if (ArranqueBaseDatos.alumnos.get(clave).getSiglas_Asignaturas_Superada(i)
						.equals(parametros[1].trim())) {
					if (ArranqueBaseDatos.alumnos.get(clave).getCurso_Academico(i)
							.equals(ArranqueBaseDatos.cursoAcademico.trim())) {
						GestionErrores.errorComando(GestionErrores.EVALUAR_ASIGNATURA,
								"Asignatura ya evaluada en ese curso académico");
						return;
					}

				}
			}
		}
		String lineas;
		FileReader fichero = null;
		// bloque try-catch si sale un error intenta el catch (mensaje de error y salir)
		try {
			fichero = new FileReader(parametros[2].trim().concat(".txt"));
		} catch (FileNotFoundException e) {
			// en caso de error imprime mensaje y sale del programa

			System.exit(1);
		}

		BufferedReader lectura = new BufferedReader(fichero);
		try {
			int linea = 0;
			while ((lineas = lectura.readLine()) != null) {
				lineas = lineas.replaceAll("\\s+", " ");
				String partes[] = lineas.split(" ");

				if (!ArranqueBaseDatos.alumnos.containsKey(partes[0].trim())) {
					GestionErrores.errorComando(GestionErrores.EVALUAR_ASIGNATURA,
							"Error en linea " + linea + ": Alumno inexistente: " + partes[0].trim());
				} else {
					notasalumnos.put(partes[0], new Notas(partes[0], partes[1], linea));
				}

				linea++;

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			fichero = new FileReader(parametros[3].trim().concat(".txt"));
		} catch (FileNotFoundException e) {
			// en caso de error imprime mensaje y sale del programa
			System.exit(1);
		}
		lectura = new BufferedReader(fichero);
		try {
			int linea = 0;
			while ((lineas = lectura.readLine()) != null) {
				lineas = lineas.replaceAll("\\s+", " ");
				String partes[] = lineas.split(" ");

				if (!ArranqueBaseDatos.alumnos.containsKey(partes[0].trim())) {
					GestionErrores.errorComando(GestionErrores.EVALUAR_ASIGNATURA,
							"Error en linea " + linea + ": Alumno inexistente: " + partes[0].trim());
				} else {
					if (!notasalumnos.containsKey(partes[0])) {
						notasalumnos.put(partes[0], new Notas(partes[0], partes[1], linea));

					} else
						notasalumnos.get(partes[0]).setnotaB(partes[1].trim(), linea);

				}

				linea++;

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		claves = notasalumnos.keySet();

		for (String clave : claves) {

			// alumno no matriculado en la asignatura
			if (!ArranqueBaseDatos.alumnos.get(clave).getSiglasAsignaturaActual().contains(parametros[1].trim())) {
				GestionErrores.errorComando(GestionErrores.EVALUAR_ASIGNATURA,
						"Alumno no matriculado: " + clave.trim());
				continue;
			}

			// error en nota A/B
			if (notasalumnos.get(clave).getNotaA() > 5 || notasalumnos.get(clave).getNotaA() < 0) {
				GestionErrores.errorComando(GestionErrores.EVALUAR_ASIGNATURA,
						"Error en linea " + notasalumnos.get(clave).getLineaA() + ": Nota grupo A incorrecta");
				continue;
			}

			if (notasalumnos.get(clave).getNotaB() < 0 || notasalumnos.get(clave).getNotaB() > 5) {
				GestionErrores.errorComando(GestionErrores.EVALUAR_ASIGNATURA,
						"Error en linea " + notasalumnos.get(clave).getLineaB() + ": Nota grupo B incorrecta");
				continue;
			}

			// en el caso de que la nota sea superior a 5 se considera asignatura superada
			if (notasalumnos.get(clave).getNotaTotal() >= 5) {

				ArranqueBaseDatos.alumnos.get(clave).setAsignaturaSuperada(parametros[1],
						ArranqueBaseDatos.cursoAcademico, notasalumnos.get(clave).getNotaTotal());

				int valormaximo = ArranqueBaseDatos.alumnos.get(clave).getSiglasAsignaturaActual().size();
				for (int i = 0; i < valormaximo; i++) {

					if (ArranqueBaseDatos.alumnos.get(clave).getSiglas_Asignatura_Actual(i)
							.equals(parametros[1].trim())) {

						ArranqueBaseDatos.alumnos.get(clave).eliminarAsignatura(i);

						valormaximo--;
						i -= 1;
					}

				}

			}
			// en caso de que la nota sea inferior a 5 se considera suspensa y se elimina de
			// asignatura actual
			if (notasalumnos.get(clave).getNotaTotal() < 5) {

				int valormaximo = ArranqueBaseDatos.alumnos.get(clave).getSiglasAsignaturaActual().size();
				for (int i = 0; i < valormaximo; i++) {
					if (ArranqueBaseDatos.alumnos.get(clave).getSiglas_Asignatura_Actual(i).equals(parametros[1])) {
						ArranqueBaseDatos.alumnos.get(clave).eliminarAsignatura(i);
						valormaximo--;
					}
				}

			}

		}
		ArranqueBaseDatos.sobreescribirFicheroAlumnos(ALUMNOS, ArranqueBaseDatos.alumnos);
		return;
	}

	/**
	 * Genera el expediente de un alumno, incluyendo todas las asginaturas aprobadas
	 * 
	 * @param imput:
	 *            array que contiene los datos de la linea correspondiente en el
	 *            fichero de ejecuciones (ejecucion.txt)
	 */
	public static void obtenerExpediente(String[] imput) {
		if (imput.length != 4) {
			GestionErrores.errorComando(GestionErrores.EXPEDIENTE_ALUMNO, "numero de argumentos incorrecto");
			return;
		}

		String parametros[] = new String[imput.length];
		for (int i = 1; i < parametros.length; i++) {

			parametros[i - 1] = imput[i];
		}

		if (!ArranqueBaseDatos.alumnos.containsKey(parametros[1])) {
			GestionErrores.errorComando(GestionErrores.EXPEDIENTE_ALUMNO, "Alumno inexistente");
		}
		String alumno = parametros[1];

		ArrayList<String> lista = new ArrayList<String>();
		float notaMedia = 0;
		int numero = ArranqueBaseDatos.alumnos.get(parametros[1]).getSiglasAsignaturaSuperada().size();
		for (int i = 0; i < ArranqueBaseDatos.alumnos.get(parametros[1]).getSiglasAsignaturaSuperada().size(); i++) {
			String asignatura = ArranqueBaseDatos.alumnos.get(alumno).getSiglas_Asignaturas_Superada(i);
			float nota = 0;
			try {
				nota = Float.parseFloat(ArranqueBaseDatos.alumnos.get(alumno).getNota(i));
			} catch (NumberFormatException e) {
				if (ArranqueBaseDatos.alumnos.get(alumno).getNota(i).equals("MH"))
					nota = 10;
			}

			String cursoAcademico = ArranqueBaseDatos.alumnos.get(alumno).getCurso_Academico(i);
			int cursoAsignatura = ArranqueBaseDatos.asignaturas.get(asignatura).getCurso();
			lista.add(cursoAsignatura + "; " + asignatura + "; " + ArranqueBaseDatos.alumnos.get(alumno).getNota(i)
					+ "; " + cursoAcademico);
			notaMedia += nota / numero;
		}
		try {
			FileWriter fw = new FileWriter(parametros[2].trim());

			BufferedWriter bw = new BufferedWriter(fw);
			// Ordena por curso y por siglas de asignatura
			Collections.sort(lista);
			for (int i = 0; i < lista.size(); i++) {
				bw.write(lista.get(i));
				bw.newLine();

			}
			DecimalFormat formato = new DecimalFormat("#.00");
			bw.write("Nota Media: " + formato.format(notaMedia));
			bw.close();

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/**
	 * Genera el calendario de ocupaci�n semanal de cada aula
	 * 
	 * @param imput:
	 *            array que contiene los datos de la linea correspondiente en el
	 *            fichero de ejecuciones (ejecucion.txt)
	 */
	public static void calendarioAulas(String[] imput) {
		if (imput.length != 3) {
			GestionErrores.errorComando(GestionErrores.CALENDARIO_OCUPACION_AULA, "numero de argumentos incorrecto");
			return;
		}
		String parametros[] = new String[imput.length];
		for (int i = 1; i < parametros.length; i++) {

			parametros[i - 1] = imput[i];
		}

		// parametros 0 = comando parametros 1 = aula

		if (parametros[1].equals("*")) {
			// si el aula es * tenemos que sacar todas de forma ordenada

			// WIP
		}

		Set<String> claves = ArranqueBaseDatos.asignaturas.keySet();
		Set<String> setaulas = ArranqueBaseDatos.aulas.keySet();
		String[] aulas = setaulas.toArray(new String[setaulas.size()]);

		Arrays.sort(aulas);

		boolean salir = false;
		for (String aula : aulas) {
			if (!parametros[1].equals("*")) {
				aula = parametros[1];
				salir = true;
			}
			OcupacionAula ocupacion[][] = new OcupacionAula[10][6];
			ocupacion[0][0] = new OcupacionAula("9-10");
			ocupacion[1][0] = new OcupacionAula("10-11");
			ocupacion[2][0] = new OcupacionAula("11-12");
			ocupacion[3][0] = new OcupacionAula("12-13");
			ocupacion[4][0] = new OcupacionAula("13-14");
			ocupacion[5][0] = new OcupacionAula("14-15");
			ocupacion[6][0] = new OcupacionAula("15-16");
			ocupacion[7][0] = new OcupacionAula("16-17");
			ocupacion[8][0] = new OcupacionAula("17-18");
			ocupacion[9][0] = new OcupacionAula("18-19");

			for (String clave : claves) {

				if (ArranqueBaseDatos.aulas.get(aula).getTipo_Grupo().contains("A")) {

					for (int i = 0; i < ArranqueBaseDatos.asignaturas.get(clave).getClasegrupoA().size(); i++) {
						if (ArranqueBaseDatos.asignaturas.get(clave).getclaseA(i).equals(aula)) {
							char dia = ArranqueBaseDatos.asignaturas.get(clave).getDiagrupoA(i);
							int hora = Integer.parseInt(ArranqueBaseDatos.asignaturas.get(clave).getHoragrupoA(i));
							int duracion = ArranqueBaseDatos.asignaturas.get(clave).getDuracion_GrupoA();
							int x = 0, y = 0;
							Set<String> clavesprofe = ArranqueBaseDatos.profesores.keySet();
							switch (dia) {
							case 'L':
								x = 1;
								break;
							case 'M':
								x = 2;
								break;
							case 'X':
								x = 3;
								break;
							case 'J':
								x = 4;
								break;
							case 'V':
								x = 5;
								break;
							}
							switch (hora) {
							case 9:
								y = 0;
								break;
							case 10:
								y = 1;
								break;
							case 11:
								y = 2;
								break;
							case 12:
								y = 3;
								break;
							case 13:
								y = 4;
								break;
							case 14:
								y = 5;
								break;
							case 15:
								y = 6;
								break;
							case 16:
								y = 7;
								break;
							case 17:
								y = 8;
								break;
							case 18:
								y = 9;
								break;
							}

							// si llega aqui tenemos un grupo de una asignatura en el aula
							char tipogrupo = ArranqueBaseDatos.aulas.get(aula).getTipo_Grupo().trim().toCharArray()[0];
							int idgrupo = ArranqueBaseDatos.asignaturas.get(clave).getIdgrupoA(i);
							ocupacion[y][x] = new OcupacionAula(clave, tipogrupo, idgrupo);
							if (duracion == 2) {
								ocupacion[y + 1][x] = ocupacion[y][x];
							}
							for (String claveprofe : clavesprofe) {
								ArrayList<String> repetido = ArranqueBaseDatos.profesores.get(claveprofe)
										.getSiglasAsignatura();
								for (int a = 0; a < repetido.size(); a++) {
									if (repetido.get(a).equals(clave)) {

										if (ArranqueBaseDatos.profesores.get(claveprofe)
												.getTipoGrupo(a) == ArranqueBaseDatos.aulas.get(aula).getTipo_Grupo()
														.trim().toCharArray()[0]) {

											if (ArranqueBaseDatos.profesores.get(claveprofe).getId_Grupo(
													a) == ArranqueBaseDatos.asignaturas.get(clave).getIdgrupoA(i)) {
												String nombre = ArranqueBaseDatos.profesores.get(claveprofe).getNombre()
														.trim();

												String partes[] = nombre.split(" ");
												String siglas = Character.toString(partes[0].toCharArray()[0]);
												for (int b = 1; b < partes.length; b++) {
													if (partes[b].toCharArray()[0] == ',')
														continue;
													if (b == partes.length - 1)
														siglas = Character.toString(partes[b].toCharArray()[0])
																.concat(siglas);
													else
														siglas = siglas
																.concat(Character.toString(partes[b].toCharArray()[0]));

												}
												ocupacion[y][x].setSiglas(siglas);
												if (duracion == 2) {
													ocupacion[y + 1][x] = ocupacion[y][x];
												}

											}
										}
									}
								}
							}

						}

					}
				} else {
					for (int i = 0; i < ArranqueBaseDatos.asignaturas.get(clave).getClasegrupoB().size(); i++) {
						if (ArranqueBaseDatos.asignaturas.get(clave).getclaseB(i).equals(aula)) {
							char dia = ArranqueBaseDatos.asignaturas.get(clave).getDiagrupoB(i);
							int hora = Integer.parseInt(ArranqueBaseDatos.asignaturas.get(clave).getHoragrupoB(i));
							int duracion = ArranqueBaseDatos.asignaturas.get(clave).getDuracionGrupoB();
							int x = 0, y = 0;
							Set<String> clavesprofe = ArranqueBaseDatos.profesores.keySet();
							switch (dia) {
							case 'L':
								x = 1;
								break;
							case 'M':
								x = 2;
								break;
							case 'X':
								x = 3;
								break;
							case 'J':
								x = 4;
								break;
							case 'V':
								x = 5;
								break;
							}
							switch (hora) {
							case 9:
								y = 0;
								break;
							case 10:
								y = 1;
								break;
							case 11:
								y = 2;
								break;
							case 12:
								y = 3;
								break;
							case 13:
								y = 4;
								break;
							case 14:
								y = 5;
								break;
							case 15:
								y = 6;
								break;
							case 16:
								y = 7;
								break;
							case 17:
								y = 8;
								break;
							case 18:
								y = 9;
								break;
							}
							// si llega aqui tenemos un grupo de una asignatura en el aula
							char tipogrupo = ArranqueBaseDatos.aulas.get(aula).getTipo_Grupo().trim().toCharArray()[0];
							int idgrupo = ArranqueBaseDatos.asignaturas.get(clave).getIdgrupoB(i);
							ocupacion[y][x] = new OcupacionAula(clave, tipogrupo, idgrupo);
							if (duracion == 2) {
								ocupacion[y + 1][x] = ocupacion[y][x];
							}

							for (String claveprofe : clavesprofe) {
								ArrayList<String> repetido = ArranqueBaseDatos.profesores.get(claveprofe)
										.getSiglasAsignatura();
								for (int a = 0; a < repetido.size(); a++) {
									if (repetido.get(a).equals(clave)) {

										if (ArranqueBaseDatos.profesores.get(claveprofe)
												.getTipoGrupo(a) == ArranqueBaseDatos.aulas.get(aula).getTipo_Grupo()
														.trim().toCharArray()[0]) {

											if (ArranqueBaseDatos.profesores.get(claveprofe).getId_Grupo(
													a) == ArranqueBaseDatos.asignaturas.get(clave).getIdgrupoB(i)) {
												String nombre = ArranqueBaseDatos.profesores.get(claveprofe).getNombre()
														.trim();

												String partes[] = nombre.split(" ");
												String siglas = Character.toString(partes[0].toCharArray()[0]);
												for (int b = 1; b < partes.length; b++) {
													if (partes[b].toCharArray()[0] == ',')
														continue;
													if (b == partes.length - 1)
														siglas = Character.toString(partes[b].toCharArray()[0])
																.concat(siglas);
													else
														siglas = siglas
																.concat(Character.toString(partes[b].toCharArray()[0]));
													ocupacion[y][x].setSiglas(siglas);
													if (duracion == 2) {
														ocupacion[y + 1][x] = ocupacion[y][x];
													}

												}

											}
										}
									}
								}
							}

						}

					}
				}

			}
			for (int a = 0; a < 10; a++) {
				for (int b = 1; b < 6; b++) {
					if (ocupacion[a][b] == null)
						ocupacion[a][b] = new OcupacionAula();
					if (a == 5)
						ocupacion[a][b] = new OcupacionAula("XXXXXXXXXXXXXXXXXXXXX");
				}
			}

			sacarJtable(ocupacion, aula);
			if (salir)
				break;

		}

	}

	/**
	 * Crea una ventana con una tabla para el calendario
	 * 
	 * @param ocupacion:
	 *            horario en el que el aula esta ocupada
	 * @param aula:
	 *            aula de la que se quiere saber la ocupacion semanal
	 */

	public static void sacarJtable(OcupacionAula[][] ocupacion, String aula) {
		//
		String[] fila = { "Hora", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes" };

		JFrame ventana = new JFrame(aula);
		ventana.setLayout(new FlowLayout());
		JTable tabla = new JTable(ocupacion, fila);
		tabla.setRowHeight(35);
		JScrollPane Js = new JScrollPane(tabla);
		Js.setPreferredSize(new Dimension(900, 372));

		ventana.add(Js);
		ventana.setSize(new Dimension(1000, 500));
		ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		ventana.setVisible(true);

	}

}

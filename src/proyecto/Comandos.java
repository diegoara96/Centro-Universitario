package proyecto;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import objetos.Alumno;
import objetos.Notas;
import objetos.OcupacionAula;
import objetos.Persona;
import objetos.Profesor;
import proyecto.GestionErrores;

public class Comandos {

	public static void comandos(String imput[]) {

		switch (imput[1]) {

		case "InsertaPersona":
			insertaPersona(imput);
			break;
		case "AsignaGrupo":
			asignaGrupo(imput);
			break;
		case "Matricula":
			matricularalumno(imput);
			break;
		case "CreaGrupoAsig":
			creaGrupoAsig(imput);
			break;
		case "Evalua":
			evaluarAsig(imput);
			break;
		case "Expediente": // WIP
			// obtenerExpediente();
			break;
		case "OcupacionAula": // WIP
			CalendarioAulas(imput);
			break;

		default: // gestion de errores funcion mal escrita
			GestionErrores.comandoErroneo(imput[1]);
			break;

		}

	}

	public static void insertaPersona(String imput[]) {

		if (imput.length < 9) {
			GestionErrores.errorComando("IP", "numero de argumentos incorrecto");
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
						// System.out.println(nombre);
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
				GestionErrores.errorComando("IP", "numero de argumentos incorrecto");
				return;
			}

			if (Persona.comprobarDNI(parametros[2]) == false) {
				GestionErrores.errorComando("IP", "dni incorrecto");
				return;
			}

			if (ArranqueBaseDatos.profesores.containsKey(parametros[2])) {
				GestionErrores.errorComando("IP", "Profesor ya existente");
				return;
			}

			if (Persona.comprobarFecha(parametros[4]) == false) {
				GestionErrores.errorComando("IP", "Fecha incorrecta");
				return;
			}

			else {
				ArranqueBaseDatos.profesores.put(parametros[2],
						new Profesor(parametros[2], parametros[3], parametros[4], parametros[5], parametros[6]));
				// System.out.println(ArranqueBaseDatos.profesores);
				ArranqueBaseDatos.sobreescribirFicheroProfesores("profesores.txt", ArranqueBaseDatos.profesores);

			}
		}

		if (parametros[1].contains("alumno")) {
			if (b != 6) {
				GestionErrores.errorComando("IP", "numero de argumentos incorrecto");
			}

			if (Persona.comprobarDNI(parametros[2]) == false) {
				GestionErrores.errorComando("IP", "dni incorrecto");
				return;
			}

			if (ArranqueBaseDatos.alumnos.containsKey(parametros[2])) {
				GestionErrores.errorComando("IP", "Alumno ya existente");
				return;
			}

			if (Persona.comprobarFecha(parametros[4]) == false) {
				GestionErrores.errorComando("IP", "Fecha incorrecta");
				return;
			}
			if (Persona.comprobarFechaIngreso(parametros[4], parametros[5]) == false) {
				GestionErrores.errorComando("IP", "Fecha ingreso incorrecta");
				return;
			}

			else {
				ArranqueBaseDatos.alumnos.put(parametros[2],
						new Alumno(parametros[2], parametros[3], parametros[4], parametros[5]));
				// System.out.println(ArranqueBaseDatos.alumnos);
				// System.out.println(ArranqueBaseDatos.alumnos.get(parametros[2]).toTexto());
				ArranqueBaseDatos.sobreescribirFicheroAlumnos("alumnos.txt", ArranqueBaseDatos.alumnos);

			}

		}

	}

	public static void asignaGrupo(String[] imput) {
		if (imput.length < 7) {
			GestionErrores.errorComando("AGRUPO", "numero de argumentos incorrecto");
		}
		String parametros[] = new String[imput.length];
		for (int i = 1; i < parametros.length; i++) {

			parametros[i - 1] = imput[i];
		}

		if (parametros[1].contains("profesor")) {
			// System.out.println("profe");
			// profesor inexistente
			if (!ArranqueBaseDatos.profesores.containsKey(parametros[2])) {
				GestionErrores.errorComando("AGRUPO", "profesor inexistente");
				return;
			}
			// asignatura inexistente
			if (!ArranqueBaseDatos.asignaturas.containsKey(parametros[3])) {
				GestionErrores.errorComando("AGRUPO", "Asignatura inexistente");
				return;
			}
			// tipo de grupo incorrecto
			if (parametros[4].toCharArray()[0] != 'A' && parametros[4].toCharArray()[0] != 'B') {
				GestionErrores.errorComando("AGRUPO", "Tipo de grupo incorrecto");
				return;
			}
			// grupo inexistente
			if (parametros[4].toCharArray()[0] == 'A') {
				if (!ArranqueBaseDatos.asignaturas.get(parametros[3]).getIdgrupoA()
						.contains(Integer.parseInt(parametros[5]))) {
					GestionErrores.errorComando("AGRUPO", "Grupo inexistente");
					return;
				}
			}

			if (parametros[4].toCharArray()[0] == 'B') {
				if (!ArranqueBaseDatos.asignaturas.get(parametros[3]).getIdgrupoB()
						.contains(Integer.parseInt(parametros[5]))) {
					GestionErrores.errorComando("AGRUPO", "Grupo inexistente");
					return;
				}
			}
			// no presente en pod profesor
			int posicion = 0;
			if (!ArranqueBaseDatos.pod.get(parametros[2]).getAsignatura().contains(parametros[3].trim())) {
				GestionErrores.errorComando("AGRUPO", "Asignatura no presente en POD del profesor");
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
				GestionErrores.errorComando("AGRUPO", "Tipo grupo no presente en POD del profesor");
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

								GestionErrores.errorComando("AGRUPO", "Grupo ya asignado");
								return;
							}
						}
					}
				}

			}

			// Número de grupos superior al contemplado en POD
			float nummaxgrupos = ArranqueBaseDatos.pod.get(parametros[2]).getNumeroGrupos(posicion);
			float numerogruposdados = 0;
			// System.out.println(parametros[3]);

			for (int i = 0; i < ArranqueBaseDatos.profesores.get(parametros[2]).getSiglasAsignatura().size(); i++) {
				// System.out.println(parametros[3].trim()+"
				// "+ArranqueBaseDatos.profesores.get(parametros[2]).getSiglas_Asignatura(i));
				if (parametros[3].trim()
						.equals(ArranqueBaseDatos.profesores.get(parametros[2]).getSiglas_Asignatura(i))) {
					// System.out.println(ArranqueBaseDatos.profesores.get(parametros[2]).getTipoGrupo(i));

					if (ArranqueBaseDatos.profesores.get(parametros[2])
							.getTipoGrupo(i) == parametros[4].toCharArray()[0]) {
						numerogruposdados++;

					}
				}
			}

			if (nummaxgrupos < (numerogruposdados + 1)) {

				GestionErrores.errorComando("AGRUPO", "Número de grupos superior al contemplado en POD");
				return;
			}
			// solape de horas
			int cuatrimestre = ArranqueBaseDatos.asignaturas.get(parametros[3]).getCuatrimestre();
			for (int i = 0; i < ArranqueBaseDatos.profesores.get(parametros[2]).getSiglasAsignatura().size(); i++) {
				if (cuatrimestre == ArranqueBaseDatos.asignaturas
						.get(ArranqueBaseDatos.profesores.get(parametros[2]).getSiglas_Asignatura(i))
						.getCuatrimestre()) {
					String hora = ArranqueBaseDatos.asignaturas
							.get(ArranqueBaseDatos.profesores.get(parametros[2]).getSiglas_Asignatura(i))
							.gethora(ArranqueBaseDatos.profesores.get(parametros[2]).getTipoGrupo(i),
									ArranqueBaseDatos.profesores.get(parametros[2]).getId_Grupo(i));
					if (ArranqueBaseDatos.asignaturas.get(parametros[3])
							.gethora(parametros[4].toCharArray()[0], Integer.parseInt(parametros[5])).equals(hora)) {
						GestionErrores.errorComando("AGRUPO", "Solape profesor");
						return;
					}
				}
			}

			// agregamos el grupo al profesor
			ArranqueBaseDatos.profesores.get(parametros[2]).asignagrupo(parametros[3], parametros[4].toCharArray()[0],
					Integer.parseInt(parametros[5]));

			ArranqueBaseDatos.sobreescribirFicheroProfesores("profesores.txt", ArranqueBaseDatos.profesores);

		}

		if (parametros[1].contains("alumno")) {

			// alumno inexistente
			if (!ArranqueBaseDatos.alumnos.containsKey(parametros[2])) {
				GestionErrores.errorComando("AGRUPO", "alumno inexistente");
				return;
			}
			// asignatura inexistente
			if (!ArranqueBaseDatos.asignaturas.containsKey(parametros[3])) {
				GestionErrores.errorComando("AGRUPO", "Asignatura inexistente");
				return;
			}
			// tipo de grupo incorrecto
			if (parametros[4].toCharArray()[0] != 'A' && parametros[4].toCharArray()[0] != 'B') {
				GestionErrores.errorComando("AGRUPO", "Tipo de grupo incorrecto");
				return;
			}

			// Alumno no matriculado

			if (!ArranqueBaseDatos.alumnos.get(parametros[2]).getSiglasAsignaturaActual()
					.contains(parametros[3].trim())) {

				GestionErrores.errorComando("AGRUPO", "Alumno no matriculado");
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
						String hora = null;
						if (ArranqueBaseDatos.alumnos.get(parametros[2]).getTipoGrupo(i) == '0') {
							continue;
						} else
							hora = ArranqueBaseDatos.asignaturas
									.get(ArranqueBaseDatos.alumnos.get(parametros[2]).getSiglas_Asignatura_Actual(i))
									.gethora(ArranqueBaseDatos.alumnos.get(parametros[2]).getTipoGrupo(i),
											ArranqueBaseDatos.alumnos.get(parametros[2]).getId_Grupo(i));
						if (ArranqueBaseDatos.asignaturas.get(parametros[3])
								.gethora(parametros[4].toCharArray()[0], Integer.parseInt(parametros[5]))
								.equals(hora)) {
							GestionErrores.errorComando("AGRUPO", "Solape alumno");
							return;
						}
					}
				}

			}

			// aula ocupada

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

			if (ArranqueBaseDatos.aulas
					.get(ArranqueBaseDatos.asignaturas.get(parametros[3])
							.getclase(parametros[4].trim().toCharArray()[0], Integer.parseInt(parametros[5])))
					.getCapacidad() < numeropersonas + 1) {
				GestionErrores.errorComando("AGRUPO", "Aula completa");
				return;
			}

			// si llega aqui todo correcto
			// guardamos el grupo en el alumno
			ArranqueBaseDatos.alumnos.get(parametros[2]).asignargrupo(parametros[3], parametros[4].toCharArray()[0],
					Integer.parseInt(parametros[5]));

			ArranqueBaseDatos.sobreescribirFicheroAlumnos("alumnos.txt", ArranqueBaseDatos.alumnos);

		}

	}

	/**
	 * funcion encargada de matricular a un alumno en una asignatura
	 * 
	 * @param imput
	 *            datos de entrada
	 */
	public static void matricularalumno(String imput[]) {
		if (imput.length < 4) {
			GestionErrores.errorComando("MAT", "numero de argumentos incorrecto");
		}

		String parametros[] = new String[imput.length];
		for (int i = 1; i < parametros.length; i++) {

			parametros[i - 1] = imput[i].trim();
		}

		if (!ArranqueBaseDatos.alumnos.containsKey(parametros[1])) {
			GestionErrores.errorComando("MAT", "alumno inexistente");
			return;
		}

		if (!ArranqueBaseDatos.asignaturas.containsKey(parametros[2])) {
			GestionErrores.errorComando("MAT", "asignatura inexistente");
			return;
		}

		// se comprueba que el alumno no está matriculado en la asignatura buscando
		// coincidencias en la lista sigla asignatura actual
		for (int i = 0; i < ArranqueBaseDatos.alumnos.get(parametros[1]).getSiglasAsignaturaActual().size(); i++) {
			if (ArranqueBaseDatos.alumnos.get(parametros[1]).getSiglas_Asignatura_Actual(i).equals(parametros[2])) {
				GestionErrores.errorComando("MAT", "Ya es alumno de la asignatura indicada");
				return;
			}
		}

		// comprobamos que todas las siglas de las asignaturas requeridas estan en
		// asignaturas superadas
		for (int i = 0; i < ArranqueBaseDatos.asignaturas.get(parametros[2]).getPreRequisitos().size(); i++) {
			if (!ArranqueBaseDatos.alumnos.get(parametros[1]).getSiglasAsignaturaSuperada()
					.contains(ArranqueBaseDatos.asignaturas.get(parametros[2]).getPre_Requisitos(i))) {
				GestionErrores.errorComando("MAT", "No cumple requisitos");
				return;
			}
		}
		ArranqueBaseDatos.alumnos.get(parametros[1]).matricula(parametros[2]);
		ArranqueBaseDatos.sobreescribirFicheroAlumnos("alumnos.txt", ArranqueBaseDatos.alumnos);

	}

	public static void creaGrupoAsig(String[] imput) {
		/*
		 * parametro 0 = comando parametro 1 = sigla asignatura parametro 2 = tipogrupo
		 * parametro 3 = idgrupo parametro 4 = dia parametro 5 = horainicio parametro 6
		 * = aula
		 */
		if (imput.length < 8) {
			GestionErrores.errorComando("CGA", "numero de argumentos incorrecto");
		}

		String parametros[] = new String[imput.length];
		for (int i = 1; i < parametros.length; i++) {

			parametros[i - 1] = imput[i];
		}

		// asignatura inexistente
		if (!ArranqueBaseDatos.asignaturas.containsKey(parametros[1])) {
			GestionErrores.errorComando("CGA", "Asignatura inexistente");
			return;
		}
		// tipo de grupo incorrecto
		if (parametros[2].toCharArray()[0] != 'A' && parametros[2].toCharArray()[0] != 'B') {
			GestionErrores.errorComando("CGA", "Tipo de grupo incorrecto");
			return;
		}
		// grupo existente
		if (parametros[2].toCharArray()[0] == 'A') {
			if (ArranqueBaseDatos.asignaturas.get(parametros[1]).getIdgrupoA()
					.contains(Integer.parseInt(parametros[3]))) {
				GestionErrores.errorComando("CGA", "Grupo ya existente");
				return;

			}
		}
		if (parametros[2].toCharArray()[0] == 'B') {
			if (ArranqueBaseDatos.asignaturas.get(parametros[1]).getIdgrupoB()
					.contains(Integer.parseInt(parametros[3]))) {
				GestionErrores.errorComando("CGA", "Grupo ya existente");
				return;

			}
		}

		// dia incorrecto
		if (parametros[4].toCharArray()[0] != 'L' && parametros[4].toCharArray()[0] != 'M'
				&& parametros[4].toCharArray()[0] != 'X' && parametros[4].toCharArray()[0] != 'J'
				&& parametros[4].toCharArray()[0] != 'V') {

			GestionErrores.errorComando("CGA", "Dia incorrecto");
			return;
		}

		// aula no existente
		if (!ArranqueBaseDatos.aulas.containsKey(parametros[6])) {
			GestionErrores.errorComando("CGA", "Aula no existente");
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
							if (ArranqueBaseDatos.asignaturas.get(clave).getHoragrupoA(i).equals(parametros[5])) {
								if (ArranqueBaseDatos.asignaturas.get(clave).getclaseA(i)
										.equals(parametros[6].trim())) {
									GestionErrores.errorComando("CGA", "Solape de Aula");
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
							if (ArranqueBaseDatos.asignaturas.get(clave).getHoragrupoB(i)
									.equals(parametros[5].trim())) {
								if (ArranqueBaseDatos.asignaturas.get(clave).getclaseB(i)
										.equals(parametros[6].trim())) {
									GestionErrores.errorComando("CGA", "Solape de Aula");
									return;
								}
							}
						}
					}
				}

			}

		}
		ArranqueBaseDatos.asignaturas.get(parametros[1]).creaGrupo(parametros[2].trim().toCharArray()[0],
				Integer.parseInt(parametros[3]), parametros[4].trim().toCharArray()[0], parametros[5].trim(),
				parametros[6].trim());
		ArranqueBaseDatos.sobreescribirFicheroAsignaturas("asignaturas.txt", ArranqueBaseDatos.asignaturas);
	}

	public static void evaluarAsig(String[] imput) {

		String parametros[] = new String[imput.length];
		for (int i = 1; i < parametros.length; i++) {

			parametros[i - 1] = imput[i];
		}
		LinkedHashMap<String, Notas> notasalumnos = new LinkedHashMap<String, Notas>();

		// System.out.println(parametros[1]);
		// parametro 0 = comando parametro 1 = asignatura
		// parametro 2 = ficheronotasA parametros 3 = ficheronotasB

		// asignatura inexistente
		if (!ArranqueBaseDatos.asignaturas.containsKey(parametros[1])) {
			GestionErrores.errorComando("EVALUA", "Asignatura inexistente");

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
						GestionErrores.errorComando("EVALUA", "Asignatura ya evaluada en ese curso académico");
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
					GestionErrores.errorComando("EVALUA",
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
					GestionErrores.errorComando("EVALUA",
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
				GestionErrores.errorComando("EVALUA", "Alumno no matriculado: " + clave.trim());
				continue;
			}

			// error en nota A/B
			if (notasalumnos.get(clave).getNotaA() > 5 || notasalumnos.get(clave).getNotaA() < 0) {
				GestionErrores.errorComando("EVALUA",
						"Error en linea " + notasalumnos.get(clave).getLineaA() + ": Nota grupo A incorrecta");
				continue;
			}

			if (notasalumnos.get(clave).getNotaB() < 0 || notasalumnos.get(clave).getNotaB() > 5) {
				GestionErrores.errorComando("EVALUA",
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
		ArranqueBaseDatos.sobreescribirFicheroAlumnos("alumnos.txt", ArranqueBaseDatos.alumnos);
		return;
	}

	public static void CalendarioAulas(String[] imput) {
		String parametros[] = new String[imput.length];
		for (int i = 1; i < parametros.length; i++) {

			parametros[i - 1] = imput[i];
		}

		// parametros 0 = comando parametros 1 = aula

		if (parametros[1].equals("*")) {
			// si el aula es * tenemos que sacar todas de forma ordenada

			// WIP
		}
		OcupacionAula ocupacion[][] = new OcupacionAula[10][6];
		ocupacion[0][0]= new OcupacionAula("hora");
			
		Set<String> claves = ArranqueBaseDatos.asignaturas.keySet();

		for (String clave : claves) {
			for (int i = 0; i < ArranqueBaseDatos.asignaturas.get(clave).getClasegrupoA().size(); i++) {
				if (ArranqueBaseDatos.asignaturas.get(clave).getclaseA(i).equals(parametros[1])) {
					char dia = ArranqueBaseDatos.asignaturas.get(clave).getDiagrupoA(i);
					int hora = Integer.parseInt(ArranqueBaseDatos.asignaturas.get(clave).getHoragrupoA(i));
					int duracion = ArranqueBaseDatos.asignaturas.get(clave).getDuracion_GrupoA();
					int x=0,y=0;
					Set<String> clavesprofe = ArranqueBaseDatos.profesores.keySet();
					switch(dia) {
					case 'L':
						x=0;
						break;
					case 'M':
						x=1;
						break;
					case 'X':
						x=2;
						break;
					case 'J':
						x=3;
						break;
					case 'V':
						x=4;
						break;
					}
					switch(hora) {
					case 9:
						y=1;
						break;
					case 10:
						y=2;
						break;
					case 11:
						y=3;
						break;
					case 12:
						y=4;
						break;
					case 13:
						y=5;
						break;
					case 14:
						y=6;
						break;
					case 15:
						y=7;
						break;
					case 16:
						y=8;
						break;
					case 17:
						y=9;
						break;
					case 18:
						y=10;
						break;
					}
							for (String claveprofe : clavesprofe) {
								ArrayList<String> repetido = ArranqueBaseDatos.profesores.get(claveprofe).getSiglasAsignatura();
								for (int a = 0; a < repetido.size(); a++) {
									if (repetido.get(a).equals(clave)) {

										if (ArranqueBaseDatos.profesores.get(claveprofe).getTipoGrupo(a) == ArranqueBaseDatos.aulas.get(parametros[1]).getTipo_Grupo().trim().toCharArray()[0]) {

											if (ArranqueBaseDatos.profesores.get(claveprofe).getId_Grupo(a) == ArranqueBaseDatos.asignaturas.get(clave).getIdgrupoA(i)) {
												String nombre= ArranqueBaseDatos.profesores.get(claveprofe).getNombre().trim();
												System.out.println(nombre);
												String partes[]=nombre.split(" ");
												String siglas=Character.toString(partes[0].toCharArray()[0]);
												for(int b=1;b<partes.length;b++) {
													
													siglas=siglas.concat(Character.toString(partes[b].toCharArray()[0]));
													
													
												}
												ocupacion[x][y]= new OcupacionAula(clave,siglas);
												if(duracion==2) {
													ocupacion[x+1][y]=ocupacion[x][y];
												}
												
											}
										}
									}
								}
							}
					
					
				}

			}
		}
		
		for(int a=0;a<10;a++) {
			for(int b=0;b<6;b++) {
				if(ocupacion[a][b]==null) ocupacion[a][b]= new OcupacionAula();
			}
		}
		
		System.out.println(ocupacion[1][3].toString());
		String[] columnas = {"Hora","Lunes","Martes","Miercoles","Jueves","Viernes"};
		//JTable tabla= new JTable (ocupacion,columnas);
		//System.out.println(tabla);
		JFrame ventana= new JFrame (parametros[1]);
		ventana.setLayout(new FlowLayout());
		JTable tabla= new JTable (ocupacion,columnas);
		JScrollPane Js = new JScrollPane(tabla);
		Js.setPreferredSize(new Dimension(900, 182));
		
		ventana.add(Js);
		ventana.setSize(new Dimension(1000,300));
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ventana.setVisible(true);
		
	}
	
	
	

}

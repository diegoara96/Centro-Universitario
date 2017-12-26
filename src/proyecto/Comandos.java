package proyecto;

import objetos.Alumno;
import objetos.EscribibleEnFichero;
import objetos.Persona;
import objetos.Profesor;
import proyecto.GestionErrores;

public class Comandos {

	public static void comandos(String imput[]) {
		switch (imput[1]) {
		case "InsertaPersona": // WIP
			insertaPersona(imput);
			break;
		case "AsignaGrupo": // WIP
			// asignaGrupo();
			break;
		case "Matricula": // WIP
			// matricula();
			break;
		case "CreaGrupoAsig": // WIP
			// CreaGrupoAsig();
			break;
		case "Evalua": // WIP
			// evaluarAsig();
			break;
		case "Expediente":
			// obtenerExpediente();
			break;
		case "OcupacionAula": // WIP
			// CalendarioAulas();
			break;
		default: // gestion de errores funcion mal escrita
			GestionErrores.comandoErroneo(imput[1]);
			break;

		}

	}

	public static void insertaPersona(String imput[]) {
		/*
		 * for(int i=0;i<imput.length;i++) { System.out.println(imput[i]); }
		 */
		if (imput.length < 9) {
			GestionErrores.errorComando("IP", "numero de argumentos incorrecto");
		}

		String parametros[] = new String[imput.length];
		String nombre;
		int b = 0, i;

		/*
		 * b es el numero de parametros que tiene cada comando "comando + perfil + dni +nombre ...."
		 *  en general b=0 comando, b=1 perfil, b=2 dni, b=3 nombre, b=4 fecha nacimiento
		 *  para profesor b=5 categoria, b=6 departamento
		 *  para alumno b=5 fecha de ingreso
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
			
			if (Persona.comprobarDNI(parametros[2])==false) {
				GestionErrores.errorComando("IP", "dni incorrecto");
				return;
			}

			if (ArranqueBaseDatos.profesores.containsKey(parametros[2])) {
				GestionErrores.errorComando("IP", "Profesor ya existente");
				return;
			}
			
			if (Persona.comprobarFecha(parametros[4])==false) {
				GestionErrores.errorComando("IP", "Fecha incorrecta");
				return;
			}

			else {
				ArranqueBaseDatos.profesores.put(parametros[2],
				new Profesor(parametros[2], parametros[3], parametros[4], parametros[5], parametros[6]));
				//System.out.println(ArranqueBaseDatos.profesores);
				//ArranqueBaseDatos.sobreescribirFicheroProfesores("prueba.txt", ArranqueBaseDatos.profesores);
				
			}
		}

		if (parametros[1].contains("alumno")) {
			if (b != 6) {
				GestionErrores.errorComando("IP", "numero de argumentos incorrecto");
			}

			
			if (Persona.comprobarDNI(parametros[2])==false) {
				GestionErrores.errorComando("IP", "dni incorrecto");
				return;
			}

			if (ArranqueBaseDatos.alumnos.containsKey(parametros[2])) {
				GestionErrores.errorComando("IP", "Alumno ya existente");
				return;
			}
			
			if (Persona.comprobarFecha(parametros[4])==false) {
				GestionErrores.errorComando("IP", "Fecha incorrecta");
				return;
			}
			if (Persona.comprobarFechaIngreso(parametros[4], parametros[5])==false) {
				GestionErrores.errorComando("IP", "Fecha ingreso incorrecta");
				return;
			}

			else {
				ArranqueBaseDatos.alumnos.put(parametros[2],
				new Alumno(parametros[2], parametros[3], parametros[4], parametros[5]));
			//	System.out.println(ArranqueBaseDatos.alumnos);
			//	System.out.println(ArranqueBaseDatos.alumnos.get(parametros[2]).toTexto());
				ArranqueBaseDatos.sobreescribirFicheroAlumnos("alumnos.txt", ArranqueBaseDatos.alumnos);
				
			}
		
		}
		
		
	}

}

package proyecto;

import java.util.ArrayList;
import java.util.Set;

import objetos.Alumno;
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
			 asignaGrupo(imput);
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
				ArranqueBaseDatos.sobreescribirFicheroProfesores("profesores.txt", ArranqueBaseDatos.profesores);
				
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
	
	public static void  asignaGrupo(String[] imput) {
		if (imput.length < 7) {
			GestionErrores.errorComando("AGRUPO", "numero de argumentos incorrecto");
		}
		String parametros[] = new String[imput.length];
		for (int i = 1; i < parametros.length; i++) {
		
				parametros[i-1] = imput[i];
			}
		
		if (parametros[1].contains("profesor")) {
			//System.out.println("profe");
			//profesor inexistente
			if(!ArranqueBaseDatos.profesores.containsKey(parametros[2])) {
				GestionErrores.errorComando("AGRUPO", "profesor inexistente");
				return;
			}
			//asignatura inexistente
			if(!ArranqueBaseDatos.asignaturas.containsKey(parametros[3])) {
				GestionErrores.errorComando("AGRUPO", "Asignatura inexistente");
				return;
			}
			//tipo de grupo incorrecto
			if(parametros[4].toCharArray()[0]!='A'&&parametros[4].toCharArray()[0]!='B') {
				GestionErrores.errorComando("AGRUPO", "Tipo de grupo incorrecto");
				return;
			}
			//grupo inexistente
			if (parametros[4].toCharArray()[0]=='A') {
				if(!ArranqueBaseDatos.asignaturas.get(parametros[3]).getIdgrupoA().contains(Integer.parseInt(parametros[5]))) {
					GestionErrores.errorComando("AGRUPO", "Grupo inexistente");
					return;
				}
			}
			
			if (parametros[4].toCharArray()[0]=='B') {
				if(!ArranqueBaseDatos.asignaturas.get(parametros[3]).getIdgrupoB().contains(Integer.parseInt(parametros[5]))) {
					GestionErrores.errorComando("AGRUPO", "Grupo inexistente");
					return;
				}
			}
			//no presente en pod profesor
			int posicion=0;
			if(!ArranqueBaseDatos.pod.get(parametros[2]).getAsignatura().contains(parametros[3].trim())) {
				GestionErrores.errorComando("AGRUPO", "Asignatura no presente en POD del profesor");
				return;
			}
			     //posicion del grupo correspontiente a la asignatura en el pod
			boolean grupo=false;
			for(int i=0;i<ArranqueBaseDatos.pod.get(parametros[2]).getAsignatura().size();i++) {
				if(ArranqueBaseDatos.pod.get(parametros[2]).getAsignatura(i).trim().equals(parametros[3].trim())) {
					if(ArranqueBaseDatos.pod.get(parametros[2]).getTipoGrupo(i)==parametros[4].trim().toCharArray()[0]) {
						posicion=i;
						grupo=true;
					}
				}
			}
			if(grupo==false) {
				GestionErrores.errorComando("AGRUPO", "Tipo grupo no presente en POD del profesor");
				return;
			}
			
			//grupo ya asignado
			Set<String> claves = ArranqueBaseDatos.profesores.keySet();
			
			for(String clave:claves) {
		    ArrayList <String>repetido=	ArranqueBaseDatos.profesores.get(clave).getSiglasAsignatura(); 
					for(int i=0;i<repetido.size();i++) {
						if(repetido.get(i).equals(parametros[3])) {
							
							if(ArranqueBaseDatos.profesores.get(clave).getTipoGrupo(i)==parametros[4].toCharArray()[0]) {
								
								if(ArranqueBaseDatos.profesores.get(clave).getId_Grupo(i)==Integer.parseInt(parametros[5])) {
									
									GestionErrores.errorComando("AGRUPO", "Grupo ya asignado");
									return;
								}
							}
						}
					}
				
			}
			
		
			//Número de grupos superior al contemplado en POD
			float nummaxgrupos= ArranqueBaseDatos.pod.get(parametros[2]).getNumeroGrupos(posicion);
			float numerogruposdados=0;
		//	System.out.println(parametros[3]);
			
			for(int i=0;i<ArranqueBaseDatos.profesores.get(parametros[2]).getSiglasAsignatura().size();i++) {
			//	System.out.println(parametros[3].trim()+" "+ArranqueBaseDatos.profesores.get(parametros[2]).getSiglas_Asignatura(i));
				if(parametros[3].trim().equals(ArranqueBaseDatos.profesores.get(parametros[2]).getSiglas_Asignatura(i))) {
				//	System.out.println(ArranqueBaseDatos.profesores.get(parametros[2]).getTipoGrupo(i));
					
					if(ArranqueBaseDatos.profesores.get(parametros[2]).getTipoGrupo(i)==parametros[4].toCharArray()[0]) {
						numerogruposdados++;
						
						
					}
				}
			}
			
			if (nummaxgrupos<(numerogruposdados+1)) {
				
				GestionErrores.errorComando("AGRUPO", "Número de grupos superior al contemplado en POD");
				return;
			}
		//solape de horas
			int cuatrimestre=ArranqueBaseDatos.asignaturas.get(parametros[3]).getCuatrimestre();
			for(int i=0;i<ArranqueBaseDatos.profesores.get(parametros[2]).getSiglasAsignatura().size();i++) {
				if(cuatrimestre==ArranqueBaseDatos.asignaturas.get(ArranqueBaseDatos.profesores.get(parametros[2]).getSiglas_Asignatura(i)).getCuatrimestre()) {
					String hora=ArranqueBaseDatos.asignaturas.get(ArranqueBaseDatos.profesores.get(parametros[2]).getSiglas_Asignatura(i)).gethora(ArranqueBaseDatos.profesores.get(parametros[2]).getTipoGrupo(i),ArranqueBaseDatos.profesores.get(parametros[2]).getId_Grupo(i));
					if(ArranqueBaseDatos.asignaturas.get(parametros[3]).gethora(parametros[4].toCharArray()[0], Integer.parseInt(parametros[5])).equals(hora)) {
						GestionErrores.errorComando("AGRUPO", "Solape profesor");
						return;
					}
				}
			}
			
		 //agregamos el grupo al profesor
			ArranqueBaseDatos.profesores.get(parametros[2]).asignagrupo(parametros[3], parametros[4].toCharArray()[0], Integer.parseInt(parametros[5]));
			
			ArranqueBaseDatos.sobreescribirFicheroProfesores("profesores.txt", ArranqueBaseDatos.profesores);
			
		}
		
		
		if(parametros[1].contains("alumno")) {
			System.out.println("alumno");
			
			//alumno inexistente
			if(!ArranqueBaseDatos.alumnos.containsKey(parametros[2])) {
				GestionErrores.errorComando("AGRUPO", "alumno inexistente");
				return;		
		}
			//asignatura inexistente
			if(!ArranqueBaseDatos.asignaturas.containsKey(parametros[3])) {
				GestionErrores.errorComando("AGRUPO", "Asignatura inexistente");
				return;
			}
			//tipo de grupo incorrecto
			if(parametros[4].toCharArray()[0]!='A'&&parametros[4].toCharArray()[0]!='B') {
				GestionErrores.errorComando("AGRUPO", "Tipo de grupo incorrecto");
				return;
			}
			
			//Alumno no matriculado

			if(!ArranqueBaseDatos.alumnos.get(parametros[2]).getSiglasAsignaturaActual().contains(parametros[3].trim())) {
				System.out.println(ArranqueBaseDatos.alumnos.get(parametros[2]).getSiglasAsignaturaActual()+" "+parametros[3].trim()+"1");
				GestionErrores.errorComando("AGRUPO", "Alumno no matriculado");
				return;		
		}
			//Solape alumno
			
			int cuatrimestre=ArranqueBaseDatos.asignaturas.get(parametros[3]).getCuatrimestre();
			int curso = ArranqueBaseDatos.asignaturas.get(parametros[3]).getCurso();
			
			for(int i=0;i<ArranqueBaseDatos.alumnos.get(parametros[2]).getSiglasAsignaturaActual().size();i++) {
				if (curso==ArranqueBaseDatos.asignaturas.get(ArranqueBaseDatos.alumnos.get(parametros[2]).getSiglas_Asignatura_Actual(i)).getCurso()) {
					if(cuatrimestre==ArranqueBaseDatos.asignaturas.get(ArranqueBaseDatos.alumnos.get(parametros[2]).getSiglas_Asignatura_Actual(i)).getCuatrimestre()) {
						String hora =null;
						if(ArranqueBaseDatos.alumnos.get(parametros[2]).getTipoGrupo(i)=='0') {
							continue;
						}
						else hora=ArranqueBaseDatos.asignaturas.get(ArranqueBaseDatos.alumnos.get(parametros[2]).getSiglas_Asignatura_Actual(i)).gethora(ArranqueBaseDatos.alumnos.get(parametros[2]).getTipoGrupo(i),ArranqueBaseDatos.alumnos.get(parametros[2]).getId_Grupo(i));
						if(ArranqueBaseDatos.asignaturas.get(parametros[3]).gethora(parametros[4].toCharArray()[0], Integer.parseInt(parametros[5])).equals(hora)) {
							GestionErrores.errorComando("AGRUPO", "Solape alumno");
							return;
						}
					}
				}
				
				
				
			}
			
			//aula ocupada
			
			Set<String> claves= ArranqueBaseDatos.alumnos.keySet();
			int numeropersonas=0;
			
			for(String clave:claves) {
				for(int i=0;i<ArranqueBaseDatos.alumnos.get(clave).getSiglasAsignaturaActual().size();i++) {
				if(ArranqueBaseDatos.alumnos.get(clave).getSiglas_Asignatura_Actual(i).equals(parametros[3])) {
					if((ArranqueBaseDatos.alumnos.get(clave).getTipoGrupo(i)==parametros[4].toCharArray()[0])) {
						if(ArranqueBaseDatos.alumnos.get(clave).getId_Grupo(i)==Integer.parseInt(parametros[5])) {
							numeropersonas++;
							System.out.println("prueba");
						}
					}
				}
				
				}
				
			}	
			System.out.println(numeropersonas);
				if(ArranqueBaseDatos.aulas.get(ArranqueBaseDatos.asignaturas.get(parametros[3]).getclase(parametros[4].trim().toCharArray()[0], Integer.parseInt(parametros[5]))).getCapacidad()<numeropersonas+1) {
					GestionErrores.errorComando("AGRUPO", "Aula completa");
					return;
				}
				
			//si llega aqui todo correcto	
			//guardamos el grupo en el alumno
			ArranqueBaseDatos.alumnos.get(parametros[2]).asignargrupo(parametros[3], parametros[4].toCharArray()[0], Integer.parseInt(parametros[5]));
			System.out.println(ArranqueBaseDatos.alumnos.get(parametros[2]).getTipoGrupo());
			ArranqueBaseDatos.sobreescribirFicheroAlumnos("alumnos.txt", ArranqueBaseDatos.alumnos);
		
			
		}
		
	}
		
	}



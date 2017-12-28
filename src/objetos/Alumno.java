package objetos;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import proyecto.GestionErrores;

public class Alumno extends Persona implements EscribibleEnFichero {

	private ArrayList<String> cursoAcademico;
	private ArrayList<Float> notaAsignatura;
	public ArrayList<String> siglasAsignaturaActual;
	private GregorianCalendar fechaIngreso;
	private ArrayList<String> siglasAsignaturaSuperada;
	private ArrayList<Character> tipoGrupo;
	private ArrayList<Integer> idGrupo;

	public Alumno(String dni, String nombre, String email, String fechaNacimiento, String fechaIngreso,
			String asignaturasSuperadas, String docenciaRecibida) {
		super(dni, nombre, email, fechaNacimiento);
		// si aisgnaturassuperadas no esta vacia procedemos a trocear y a guardarla en
		// el objeto
		siglasAsignaturaSuperada = new ArrayList<String>();
		cursoAcademico = new ArrayList<String>();
		notaAsignatura = new ArrayList<Float>();
		siglasAsignaturaActual = new ArrayList<String>();
		tipoGrupo = new ArrayList<Character>();
		idGrupo = new ArrayList<Integer>();

		if (asignaturasSuperadas != null) {
			String[] superadas = asignaturasSuperadas.split(";");

			for (int i = 0; i < superadas.length; i++) {
				String[] campos = superadas[i].trim().split(" ");
				this.siglasAsignaturaSuperada.add(campos[0]);
				this.cursoAcademico.add(campos[1]);
				this.notaAsignatura.add(Float.parseFloat(campos[2]));
			}
		}

		// si docenciaREcibida no esta vacia procedemos a trocear y a guardarla en el
		// objeto
		if (docenciaRecibida != null) {
			String[] actuales = docenciaRecibida.split(";");

			for (int i = 0; i < actuales.length; i++) {
				String[] campos = actuales[i].trim().split(" ");
				this.siglasAsignaturaActual.add(campos[0]);
				if (campos.length != 3) {
					this.tipoGrupo.add('0');
					this.idGrupo.add(0);
				} else {
					this.tipoGrupo.add(campos[1].trim().toCharArray()[0]);
					this.idGrupo.add(Integer.parseInt(campos[2]));
				}
			}
		}

		this.fechaIngreso = Persona.fechaToGregorianCalendar(fechaIngreso);

		// System.out.println(toTexto());
		// TODO: inicializar objeto
	}

	public Alumno(String dni, String nombre, String fechaNacimiento, String fechaIngreso) {
		super(dni, nombre, null, fechaNacimiento);
		siglasAsignaturaSuperada = new ArrayList<String>();
		cursoAcademico = new ArrayList<String>();
		notaAsignatura = new ArrayList<Float>();
		siglasAsignaturaActual = new ArrayList<String>();
		tipoGrupo = new ArrayList<Character>();
		idGrupo = new ArrayList<Integer>();

		this.fechaIngreso = Persona.fechaToGregorianCalendar(fechaIngreso);

		// System.out.println(toTexto());
		// TODO: inicializar objeto

	}

	public GregorianCalendar getFechaIngreso() {
		return fechaIngreso;
	}

	public String getFechaIngresoFormateada() {
		return dateFormat.format(fechaIngreso.getTime());
	}

	public int getId_Grupo(int i) {
		return idGrupo.get(i);
	}

	public char getTipoGrupo(int i) {
		return tipoGrupo.get(i);
	}

	public String getSiglas_Asignaturas_Superada(int i) {
		return siglasAsignaturaSuperada.get(i);
	}

	public String getCurso_Academico(int i) {
		return cursoAcademico.get(i);
	}

	public float getNota(int i) {
		return notaAsignatura.get(i);
	}

	public String getSiglas_Asignatura_Actual(int i) {
		return siglasAsignaturaActual.get(i);
	}
	
	
	
	public ArrayList<String> getSiglasAsignaturaActual() {
		return siglasAsignaturaActual;
	}

	public ArrayList<String> getSiglasAsignaturaSuperada() {
		return siglasAsignaturaSuperada;
	}

	public ArrayList<Character> getTipoGrupo() {
		return tipoGrupo;
	}
	
	/**
	 * añade un grupo a un objeto alumno
	 * @param asignatura
	 * @param grupo
	 * @param idgrupo
	 */
	public void asignargrupo(String asignatura, char grupo,int idgrupo) {
		boolean repetida=false;
		for(int i=0;i<siglasAsignaturaActual.size();i++) {
			if(siglasAsignaturaActual.get(i).equals(asignatura.trim())) {
				
				if(this.tipoGrupo.get(i).equals(grupo)) {
					repetida=true;
					this.idGrupo.add(i, idgrupo);
					return;
				}
				if(this.tipoGrupo.get(i)=='0') {
					this.tipoGrupo.add(i,grupo);
					this.idGrupo.add(i,idgrupo);
					repetida=true;
					return;
				}
				
			}
		}
		if(repetida==false) {
			this.siglasAsignaturaActual.add(asignatura.trim());
			this.tipoGrupo.add(grupo);
			this.idGrupo.add(idgrupo);
		}
		
	}

	/**
	 * Convierte los atributos de este objeto a un conjunto de líneas de texto,
	 * separadas por saltos de línea (\n), para así facilitar su guardado en un
	 * fichero de texto.
	 * 
	 * @return (String): conjunto de líneas de texto con los atributos del objeto.
	 */
	public String toTexto() {
		// DNI, nombre, email, fecha de nacimiento y fecha de ingreso:
		String inicio = getDni() + "\n" + getNombre() + "\n" + getEmail() + "\n" + this.getFechaNacimientoFormateada()
				+ "\n" + this.getFechaIngresoFormateada() + "\n";

		// Asignaturas superadas (recorremos los arrays)
		StringBuffer buffer = new StringBuffer();
		if (!siglasAsignaturaSuperada.isEmpty()) {
			for (int i = 0; i < siglasAsignaturaSuperada.size(); i++) {
		//		System.out.println(i+" "+siglasAsignaturaSuperada.size());
				if (i==siglasAsignaturaSuperada.size()-1) {
					buffer.append(siglasAsignaturaSuperada.get(i) + " " + cursoAcademico.get(i) + " "
							+ notaAsignatura.get(i)+"\n");
				} else
					buffer.append(siglasAsignaturaSuperada.get(i) + " " + cursoAcademico.get(i) + " "
							+ notaAsignatura.get(i) + "; ");
			}
		} else
			buffer.append("\n");
		
		if (!siglasAsignaturaActual.isEmpty()) {
			// Docencia recibida (recorremos los arrays)
			for (int i = 0; i < siglasAsignaturaActual.size(); i++) {
				if(tipoGrupo.get(i)=='0') {
					if(i==siglasAsignaturaActual.size()-1) {
						buffer.append(siglasAsignaturaActual.get(i)+"\n");
					}
					else	buffer.append(siglasAsignaturaActual.get(i) +"; ");
				}else {		
				if(i==siglasAsignaturaActual.size()-1) {
					buffer.append(siglasAsignaturaActual.get(i) + " " + tipoGrupo.get(i) + " " + idGrupo.get(i)+"\n");
				}
				else	buffer.append(siglasAsignaturaActual.get(i) + " " + tipoGrupo.get(i) + " " + idGrupo.get(i) + "; ");
			}}
		} else
			buffer.append("\n");
		//buffer.append("\n");
		// Juntamos todo en un mismo String
		String cadena = inicio.concat(new String(buffer));
		return cadena.substring(0, cadena.length() - 1); // elimina el último salto de línea

	}

}

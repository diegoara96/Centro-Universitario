package objetos;

import java.util.GregorianCalendar;

public class Alumno extends Persona implements EscribibleEnFichero {
	
	
	private String[] cursoAcademico;
	private float[] notaAsignatura;
	private String[] siglasAsignaturaActual;
	private GregorianCalendar fechaIngreso;
	private String[] siglasAsignaturaSuperada;
	private String[] tipoGrupo;
	private String[] idGrupo;
	
	public Alumno(String dni,String nombre,String email,String fechaNacimiento,String fechaIngreso, String asignaturasSuperadas, String docenciaRecibida) {
		super(dni,nombre,email,fechaNacimiento);
		//comprobaciones de dni
		boolean dniCorrecto = Persona.comprobarDNI(dni);
		if(dniCorrecto==false)System.out.println("error en el dni");
		/**
		 * Se debera incorporar la gestion de errores pertinente asociada al dni incorrecto
		 */
		
		//comprobaciones FechaNacimiento
		boolean fechaNacimientoCorrecta = Persona.comprobarFecha(fechaNacimiento);
		if (fechaNacimientoCorrecta==false)System.out.println("error fecha nacimiento alumno");
		/**
		 *Se debera incorporar la gestion de errores pertinente asociada a la fecha de nacimiento 
		 */
		
		//comprobaciones fechaIngreso
		boolean fechaIngresoCorrecta = Persona.comprobarFechaIngreso(fechaNacimiento, fechaIngreso);
		if (fechaIngresoCorrecta==false)System.out.println("error fecha ingreso alumno");
		/**
		 * Se debera incorporar la gestion de errores pertinente asociada a la fecha de ingreso
		 */
	
		//procedemos a trocear asignaturasSuperadas para luego tener los campos facilmente manipulables
		//Procedimiento: se trocea el String asignaturasSuperadas en sus respectivos campos siglasAsignaturasSuperadas, cursoAcademico y nota
		
		String[] superadas = asignaturasSuperadas.split(";");
		String[] actuales = docenciaRecibida.split(";");

		siglasAsignaturaSuperada=new String [superadas.length];
		cursoAcademico=new String [superadas.length];
		notaAsignatura=new float [superadas.length];
		siglasAsignaturaActual=new String [actuales.length];
		tipoGrupo=new String [actuales.length];
		idGrupo=new String [actuales.length];
		
		
		for(int i=0;i<superadas.length;i++) {
			String[] campos= superadas[i].trim().split(" ");
			this.siglasAsignaturaSuperada[i]= campos[0];
			this.cursoAcademico[i] = campos[1];
			this.notaAsignatura[i] = Float.parseFloat(campos[2]);
		}	
		//Tenemos que hacer lo de trocear tambien con docenciaRecibida
		//Procedimiento: Se trocea el String docenciaRecibida en sus respectivos campos siglasAsignaturaActual y con el trim() se quitan los posibles espacios antes de las palabras
		
		for(int i=0;i<actuales.length;i++) {
			String[] campos= actuales[i].trim().split(" ");
			this.siglasAsignaturaActual[i]= campos[0];
			this.tipoGrupo[i] = campos[1];
			this.idGrupo[i] = campos[2];
		}	
		this.fechaIngreso=Persona.fechaToGregorianCalendar(fechaIngreso);
		
		//System.out.println(toTexto());
		// TODO: inicializar objeto
		
	}	
	
	public GregorianCalendar getFechaIngreso() {
		return fechaIngreso;
	}
	
	public String getFechaIngresoFormateada() {
		return dateFormat.format(fechaIngreso.getTime());
	}
	public String getId_Grupo(int i) {
		return idGrupo[i];
	}
	public String getTipoGrupo(int i) {
		return tipoGrupo[i];
	}
	public String getSiglas_Asignaturas_Superada(int i) {
		return siglasAsignaturaSuperada[i];
	}
	public String getCurso_Academico(int i) {
		return cursoAcademico[i];
	}
	public float getNota(int i) {
		return notaAsignatura[i];
	}
	public String getSiglas_Asignatura_Actual(int i) {
		return siglasAsignaturaActual[i];
	}
	
	/**
	 * Convierte los atributos de este objeto a un conjunto de líneas de texto, separadas por saltos de línea (\n),
	 * para así facilitar su guardado en un fichero de texto.
	 * @return (String): conjunto de líneas de texto con los atributos del objeto.
	 */
	public String toTexto() {
		// DNI, nombre, email, fecha de nacimiento y fecha de ingreso:
		String inicio = getDni()+ "\n" + getNombre() + "\n" + getEmail() + "\n" + this.getFechaNacimientoFormateada()
			+ "\n" +  this.getFechaIngresoFormateada() + "\n";
		
		// Asignaturas superadas (recorremos los arrays)
		StringBuffer buffer = new StringBuffer();
		for(int i = 0; i<siglasAsignaturaSuperada.length; i++) {
			buffer.append(siglasAsignaturaSuperada[i] + " " + cursoAcademico[i] + " " + notaAsignatura[i] + ";\n");
		}
		
		// Docencia recibida (recorremos los arrays)
		for(int i = 0; i<siglasAsignaturaActual.length; i++) {
			buffer.append(siglasAsignaturaActual[i] + " [" + tipoGrupo[i] + idGrupo[i] + "];\n");
		}
		
		// Juntamos todo en un mismo String
		String cadena = inicio.concat(new String(buffer));
		return cadena.substring(0, cadena.length()-2); // elimina el último salto de línea
		
	}
	
}

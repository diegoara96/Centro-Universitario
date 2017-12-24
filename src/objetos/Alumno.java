package objetos;

import java.util.GregorianCalendar;

import proyecto.GestionErrores;

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
		if(dniCorrecto==false) {
			//sacamos por avisos el error correspondiente
			GestionErrores.errorComando("IP", "Dni incorrecto");
			return;
		}
		
		
		//comprobaciones FechaNacimiento
		boolean fechaNacimientoCorrecta = Persona.comprobarFecha(fechaNacimiento);
		if (fechaNacimientoCorrecta==false) {
			//sacamos por avisos el error correspondiente
			GestionErrores.errorComando("IP", "Fecha incorrecta");
			return;
		}
		
		
		//comprobaciones fechaIngreso
		boolean fechaIngresoCorrecta = Persona.comprobarFechaIngreso(fechaNacimiento, fechaIngreso);
		if (fechaIngresoCorrecta==false) {
			//sacamos por avisos el error correspondiente
			GestionErrores.errorComando("IP", "Fecha de ingreso incorrecta");
			return;
		}
		
		//si aisgnaturassuperadas no esta vacia procedemos a trocear y a guardarla en el objeto
		
		if(asignaturasSuperadas!=null) {
		String[] superadas = asignaturasSuperadas.split(";");
		
		siglasAsignaturaSuperada=new String [superadas.length];
		cursoAcademico=new String [superadas.length];
		notaAsignatura=new float [superadas.length];
	
		for(int i=0;i<superadas.length;i++) {
			String[] campos= superadas[i].trim().split(" ");
			this.siglasAsignaturaSuperada[i]= campos[0];
			this.cursoAcademico[i] = campos[1];
			this.notaAsignatura[i] = Float.parseFloat(campos[2]);
		}	
		}
			
		//si docenciaREcibida no esta vacia procedemos a trocear y a guardarla en el objeto
		if(docenciaRecibida!= null) {
		String[] actuales = docenciaRecibida.split(";");
		
		siglasAsignaturaActual=new String [actuales.length];
		tipoGrupo=new String [actuales.length];
		idGrupo=new String [actuales.length];
		
		for(int i=0;i<actuales.length;i++) {
			String[] campos= actuales[i].trim().split(" ");
			this.siglasAsignaturaActual[i]= campos[0];
			if(campos.length!= 3) {
				this.tipoGrupo[i] = null;
				this.idGrupo[i] = null;
			}
			else {
			this.tipoGrupo[i] = campos[1];
			this.idGrupo[i] = campos[2];
			}
		}	
		}
		
		this.fechaIngreso=Persona.fechaToGregorianCalendar(fechaIngreso);
		
		//System.out.println(toTexto());
		// TODO: inicializar objeto
	}
		
		public Alumno(String dni,String nombre,String fechaNacimiento,String fechaIngreso) {
			super(dni,nombre,fechaNacimiento);
			
			
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
		if(siglasAsignaturaSuperada!=null) {
		for(int i = 0; i<siglasAsignaturaSuperada.length; i++) {
			buffer.append(siglasAsignaturaSuperada[i] + " " + cursoAcademico[i] + " " + notaAsignatura[i] + ";\n");
		}
		
		// Docencia recibida (recorremos los arrays)
		for(int i = 0; i<siglasAsignaturaActual.length; i++) {
			buffer.append(siglasAsignaturaActual[i] + " [" + tipoGrupo[i] + idGrupo[i] + "];\n");
		}
		}
		// Juntamos todo en un mismo String
		String cadena = inicio.concat(new String(buffer));
		return cadena.substring(0, cadena.length()-2); // elimina el último salto de línea
		
	}
	
}

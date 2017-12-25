package objetos;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import proyecto.GestionErrores;

public class Alumno extends Persona implements EscribibleEnFichero {
	
	
	private ArrayList<String> cursoAcademico;
	private ArrayList<Float> notaAsignatura;
	private ArrayList<String> siglasAsignaturaActual;
	private GregorianCalendar fechaIngreso;
	private ArrayList<String> siglasAsignaturaSuperada;
	private ArrayList<String> tipoGrupo;
	private ArrayList<String> idGrupo;
	
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
		
		siglasAsignaturaSuperada=new ArrayList<String>();
		cursoAcademico=new ArrayList<String>();
		notaAsignatura=new ArrayList<Float>();
	
		for(int i=0;i<superadas.length;i++) {
			String[] campos= superadas[i].trim().split(" ");
			this.siglasAsignaturaSuperada.add(campos[0]);// campos[0];
			this.cursoAcademico.add(campos[1]);
			this.notaAsignatura.add(Float.parseFloat(campos[2]));
		}	
		}
			
		//si docenciaREcibida no esta vacia procedemos a trocear y a guardarla en el objeto
		if(docenciaRecibida!= null) {
		String[] actuales = docenciaRecibida.split(";");
		
		siglasAsignaturaActual=new ArrayList<String>();
		tipoGrupo=new ArrayList<String>();
		idGrupo=new ArrayList<String>();
		
		for(int i=0;i<actuales.length;i++) {
			String[] campos= actuales[i].trim().split(" ");
			this.siglasAsignaturaActual.add(campos[0]);
			if(campos.length!= 3) {
				this.tipoGrupo.add(null);
				this.idGrupo.add( null);
			}
			else {
			this.tipoGrupo.add(campos[1]);
			this.idGrupo.add(campos[2]);
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
		return idGrupo.get(i);
	}
	public String getTipoGrupo(int i) {
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
		for(int i = 0; i<siglasAsignaturaSuperada.size(); i++) {
			buffer.append(siglasAsignaturaSuperada.get(i) + " " + cursoAcademico.get(i) + " " + notaAsignatura.get(i) + ";");
		}
		}
		buffer.append("\n");
		if(siglasAsignaturaActual!=null) {
		// Docencia recibida (recorremos los arrays)
		for(int i = 0; i<siglasAsignaturaActual.size(); i++) {
			buffer.append(siglasAsignaturaActual.get(i) + " " + tipoGrupo.get(i) + " "+ idGrupo.get(i)+ "; ");
		}
		}
		buffer.append("\n");
		
		// Juntamos todo en un mismo String
		String cadena = inicio.concat(new String(buffer));
		return cadena.substring(0, cadena.length()-2); // elimina el último salto de línea
		
	}
	
}

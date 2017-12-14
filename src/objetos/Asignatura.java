package objetos;

public class Asignatura implements EscribibleEnFichero {

	private String siglas;
	private String nombre;
	private int curso;
	private int cuatrimestre;
	private String dniCoordinador;
	private String preRequisitos;
	private int duracionGrupoA;
	private int duracionGrupoB;

	public Asignatura(String siglas, String nombre, String curso, String cuatrimestre, String dniCoordinador, String preRequisitos, String duracionGrupoA, String duracionGrupoB, String grupoA, String grupoB) {
	
		this.siglas=siglas;
		this.nombre=nombre;
		this.curso= Integer.parseInt(curso);
		this.cuatrimestre=Integer.parseInt(cuatrimestre);
		this.dniCoordinador=dniCoordinador;
		this.preRequisitos=preRequisitos;
		this.duracionGrupoA=Integer.parseInt(duracionGrupoA);
		this.duracionGrupoB=Integer.parseInt(duracionGrupoB);
		
		/**
		 * Solo a�adir que esta funcion guarda los datos correctamente
		 */
	}
	
	public String getSiglas() {
		return siglas;
	}
	public String getNombre() {
		return nombre;
	}
	public int getCurso() {
		return curso;
	}
	public int getCuatrimestre() {
		return cuatrimestre;
	}
	public String getDni_Coordinador() {
		return dniCoordinador;
	}
	public String getPre_Requisitos() {
		return preRequisitos;
	}
	public int getDuracion_GrupoA() {
		return duracionGrupoA;
	}
	public int getDuracionGrupoB() {
		return duracionGrupoB;
	}
	
	/**
	 * Convierte los atributos de este objeto a un conjunto de líneas de texto, separadas por saltos de línea (\n),
	 * para así facilitar su guardado en un fichero de texto.
	 * @return (String): conjunto de líneas de texto con los atributos del objeto.
	 */
	public String toTexto() {
		// Siglas, nombre, curso, cuatrimestre, coordinador (DNI), prerrequisitos, duración grupo A y duración grupo B:
		String inicio = siglas + "\n" + nombre + "\n" + curso
			+ "\n" +  cuatrimestre + "\n" + dniCoordinador + "\n" 
				+  preRequisitos + "\n" + duracionGrupoA + "\n" + duracionGrupoB + "\n";
		
		// TODO: Grupos??
		
		// Juntamos todo en un mismo String
		//String cadena = inicio.concat(new String(buffer));
		return inicio.substring(0, inicio.length()-2); // elimina el último salto de línea
		
		
	}
	
}

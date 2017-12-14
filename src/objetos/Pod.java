package objetos;

public class Pod implements EscribibleEnFichero {
	
	private String dni; 
	private String asignatura;
	private char tipoGrupo; //A o B
	private float numeroGrupos; // numero real de maximo 2 digitos y 2 decimales
	
	public Pod(String dni,String asignatura,String tipoGrupo,String numeroGrupos) {
		
		this.dni=dni;
		this.asignatura=asignatura;
		
		this.tipoGrupo=tipoGrupo.toCharArray()[0];
		this.numeroGrupos=Float.parseFloat(numeroGrupos);
		
		/**
		 * Se guardan los datos correctamente
		 */
	}
	
	public String getDni() {
		return dni;
	}
	
	public String getAsignatura() {
		return asignatura;
	}
	
	public char getTipoGrupo() {
		return tipoGrupo;
	}
	
	public float getNumeroGrupos() {
		return numeroGrupos;
	}
	
	/**
	 * Convierte los atributos de este objeto a un conjunto de líneas de texto, separadas por saltos de línea (\n),
	 * para así facilitar su guardado en un fichero de texto.
	 * @return (String): conjunto de líneas de texto con los atributos del objeto.
	 */
	public String toTexto() {
		// Siglas, tipo grupo y capacidad:
		return dni + "\n" + asignatura + "\n" + tipoGrupo + "\n" + numeroGrupos; // so easy en este caso
		
		
	}


}

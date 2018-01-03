package objetos;

public class Aula {

	private String siglas;
	private String tipoGrupo;
	private int capacidad;
	
	
	/**Crea el objeto aula con los datos correspondientes
	 * 
	 * @param siglas : abreviatura de las asignaturas
	 * @param tipo_Grupo : A(clases teoricas) o B(clases practicas)
	 * @param capacidad : numeros maximo de alumnos que admite el aula
	 */
	public Aula(String siglas, String tipo_Grupo, String capacidad) {
		
		this.siglas=siglas;
		this.tipoGrupo=tipo_Grupo;
		this.capacidad=Integer.parseInt(capacidad);
	
		
	}
	
	public String getSiglas() {
		return siglas;
	}
	public String getTipo_Grupo() {
		return tipoGrupo;
	}
	public int getCapacidad() {
		return capacidad;
	}
	
	/**Convierte los atributos de este objeto a un conjunto de lineas de texto, separadas por saltos de linea (\n),
	 * para asi facilitar su guardado en un fichero de texto.
	 * 
	 * @return (String) : conjunto de lineas de texto con los atributos del objeto.
	 * 
	 */
	public String toTexto() {
		// Siglas, tipo grupo y capacidad:
		return siglas + "\n" + tipoGrupo + "\n" + capacidad; // so easy en este caso
		
		
	}
	
}

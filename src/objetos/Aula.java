package objetos;

public class Aula implements EscribibleEnFichero{

	private String siglas;
	private String tipoGrupo;
	private int capacidad;
	
	public Aula(String siglas, String tipo_Grupo, String capacidad) {
		
		this.siglas=siglas;
		this.tipoGrupo=tipo_Grupo;
		this.capacidad=Integer.parseInt(capacidad);
	/**
	 * Se guardan los datos correctamente
	 */
		
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
	
	/**
	 * Convierte los atributos de este objeto a un conjunto de líneas de texto, separadas por saltos de línea (\n),
	 * para así facilitar su guardado en un fichero de texto.
	 * @return (String): conjunto de líneas de texto con los atributos del objeto.
	 */
	public String toTexto() {
		// Siglas, tipo grupo y capacidad:
		return siglas + "\n" + tipoGrupo + "\n" + capacidad; // so easy en este caso
		
		
	}
	
}

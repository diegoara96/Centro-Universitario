package objetos;

public class Notas {
	private String dni;
	private String notaA = "0";
	private int lineaA;
	private String notaB = "0";
	private int lineaB;

	
	/**Crea el objeto notas con los datos correspondientes
	 * 
	 * @param dni: dni del alumno
	 * @param notaA: nota en teoria
	 * @param lineaA
	 */
	public Notas(String dni, String notaA, int lineaA) {
		this.dni = dni;
		this.notaA = notaA;
		this.lineaA = lineaA;
	}
	/**
	 * Modificamos el objeto anhadiendo la nota del grupo B y la linea correspondiente al fichero
	 * @param notaB String
	 * @param lineaB int
	 * 
	 */
	public void setnotaB(String notaB, int lineaB) {
		this.notaB = notaB;
		this.lineaB = lineaB;
	}

	public String getDni() {
		return dni;
	}

	public float getNotaA() {
		return Float.parseFloat(notaA);
	}

	public float getNotaB() {
		return Float.parseFloat(notaB);
	}

	public int getLineaA() {
		return lineaA;
	}

	public int getLineaB() {
		return lineaB;
	}
	/**
	 * Devuelve la nota total del alumno
	 * @return float :notaTotal
	 */
	public float getNotaTotal() {
		return Float.parseFloat(notaA) + Float.parseFloat(notaB);
	}

}

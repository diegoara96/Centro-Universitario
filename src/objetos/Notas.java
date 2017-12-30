package objetos;

public class Notas {
	private String dni;
	private String notaA = "0";
	private int lineaA;
	private String notaB = "0";
	private int lineaB;

	public Notas(String dni, String notaA, int lineaA) {
		this.dni = dni;
		this.notaA = notaA;
		this.lineaA = lineaA;
	}

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

	public float getNotaTotal() {
		return Float.parseFloat(notaA) + Float.parseFloat(notaB);
	}

}

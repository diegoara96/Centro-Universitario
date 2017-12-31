package objetos;

public class OcupacionAula {
	private String asignatura;
	private String Siglas;

	public OcupacionAula(String asignatura,String siglas) {
	this.asignatura=asignatura;
	this.Siglas=siglas;
	}

 public OcupacionAula() {
	 this.asignatura=" ";
	 this.Siglas=" ";
 }
 
 public OcupacionAula(String hora) {
	 this.asignatura=hora;
	 this.Siglas=" ";
 }

	public String toString() {
		return String.format(asignatura+"\n\n"+Siglas);
	}
}

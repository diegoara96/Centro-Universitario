package objetos;

public class OcupacionAula {
	private String asignatura;
	private String Siglas;
	private char tipogrupo;
	private int idgrupo;

	public OcupacionAula(String asignatura,String siglas,char tipogrupo,int idgrupo) {
	this.asignatura=asignatura;
	this.Siglas=siglas;
	this.tipogrupo=tipogrupo;
	this.idgrupo=idgrupo;
	
	}

 public OcupacionAula() {
	 this.asignatura=" ";
	 this.Siglas=" ";
	 this.tipogrupo='0';

 }
 
 public OcupacionAula(String hora) {
	 this.asignatura=hora;
	 this.Siglas=" ";
	 this.tipogrupo='0';
	 this.idgrupo=0;
 }

	public String toString() {
		if(tipogrupo!='0') return String.format("<html>"+asignatura+"-"+tipogrupo+idgrupo+"<br>"+"</br>"+Siglas+"</html>");
			return String.format("<html>"+asignatura+"<br>"+"</br>"+Siglas+"</html>");
		
		
}
}
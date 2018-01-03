package objetos;


public class OcupacionAula {
	private String asignatura;
	private String Siglas;
	private char tipogrupo;
	private int idgrupo;

	/**Atribuye a las aulas las asignaturas que se imparten en ella
	 * 
	 * @param asignatura: nombre de la asignatura	
	 * @param siglas: siglas de la asignatura
	 * @param tipogrupo: A o B
	 * @param idgrupo
	 * 
	 */
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

 /**Convierte los atributos de este objeto a un conjunto de lineas de texto, separadas por saltos de linea (\n),
	 * para asi facilitar su guardado en un fichero de texto.
	 * 
	 * @return (String): conjunto de lineas de texto con los atributos del objeto.
	 * 
	 */
	public String toString() {
		if(tipogrupo!='0') return String.format("<html>"+asignatura+"-"+tipogrupo+idgrupo+"<br>"+"</br>"+Siglas+"</html>");
			return String.format("<html>"+asignatura+"<br>"+"</br>"+Siglas+"</html>");
		
		
}
}
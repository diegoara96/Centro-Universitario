package objetos;

import java.util.ArrayList;
/**
 * 
 * Plan de organizacion Docente
 *
 */
public class Pod  {
	
	private String dni; 
	private ArrayList<String> asignatura;
	private ArrayList<Character> tipoGrupo; //A o B
	private ArrayList<Float> numeroGrupos; // numero real de maximo 2 digitos y 2 decimales
	
	
	/**Crea el objeto pod con los datos correspondientes
	 * 
	 * @param dni : dni del profesor 
	 * @param grupos : grupos que imparte
	 */
	public Pod(String dni,String grupos) {
		this.asignatura = new ArrayList<String>();
		this.tipoGrupo = new ArrayList<Character>();
		this.numeroGrupos = new ArrayList<Float>();
		
		this.dni=dni;
		grupos=grupos.replaceAll("\\s+"," ");
        String[] grupo = grupos.split(";");
		for (int i = 0; i < grupo.length; i++) {
			String[] campos = grupo[i].trim().split(" ");
			this.asignatura.add(campos[0]);
			this.tipoGrupo.add(campos[1].trim().toCharArray()[0]);
			this.numeroGrupos.add(Float.parseFloat(campos[2]));
		}
	
		
		
	}
	
	public ArrayList<String> getAsignatura() {
		return asignatura;
	}

	public ArrayList<Character> getTipoGrupo() {
		return tipoGrupo;
	}

	public String getDni() {
		return dni;
	}
	
	public String getAsignatura(int i) {
		return asignatura.get(i);
	}
	
	public char getTipoGrupo(int i) {
		return tipoGrupo.get(i);
	}
	
	public float getNumeroGrupos(int i) {
		return numeroGrupos.get(i);
	}
	
	/**Convierte los atributos de este objeto a un conjunto de lineas de texto, separadas por saltos de linea (\n),
	 * para asi facilitar su guardado en un fichero de texto.
	 * 
	 * @return (String) : conjunto de lineas de texto con los atributos del objeto.
	 * 
	 */
	public String toTexto() {
		// Siglas, tipo grupo y capacidad:
		return dni + "\n" + asignatura + "\n" + tipoGrupo + "\n" + numeroGrupos; // so easy en este caso
		
		
	}


}

package objetos;

import java.util.ArrayList;
/**
 * asignaturas de la universidad, contiene constructores
 *
 */
public class Asignatura   {

	private String siglas;
	private String nombre;
	private int curso;
	private int cuatrimestre;
	private String dniCoordinador;
	private ArrayList<String> preRequisitos;
	private int duracionGrupoA;
	private int duracionGrupoB;
	private ArrayList<Integer> idgrupoA;
	private ArrayList<Character> diagrupoA;
	private ArrayList<String> horagrupoA;
	private ArrayList<String> clasegrupoA;
	private ArrayList<Integer> idgrupoB;
	private ArrayList<Character> diagrupoB;
	private ArrayList<String> horagrupoB;
	private ArrayList<String> clasegrupoB;
	
	
	/**Crea el objeto asignatura con los datos correspondientes
	 * 
	 * @param siglas : siglas de la asignatura
	 * @param nombre : nombre de la asignatura
	 * @param curso : numero del curso al que pertenece la asignatura
	 * @param cuatrimestre : 1 o 2 (en el que se cursa)
	 * @param dniCoordinador : dni del profesor coordinador de la asignatura
	 * @param preRequisitos : siglas de las asignaturas que hay que tener obligatoriamente superadas para poder matricularse en esta
	 * @param duracionGrupoA : 1 o 2 horas	
	 * @param duracionGrupoB : 1 o 2 horas
	 * @param grupoA :  va a contener la id del grupo y los dias, las horas de inicio y las aulas en las que se cursa 
	 * @param grupoB : va a contener la id del grupo y los dias, las horas de inicio y las aulas en las que se cursa 
	 * 
	 */
	public Asignatura(String siglas, String nombre, String curso, String cuatrimestre, String dniCoordinador,
			String preRequisitos, String duracionGrupoA, String duracionGrupoB, String grupoA, String grupoB) {

		this.idgrupoA = new ArrayList<Integer>();
		this.diagrupoA = new ArrayList<Character>();
		this.horagrupoA = new ArrayList<String>();
		this.idgrupoB = new ArrayList<Integer>();
		this.diagrupoB = new ArrayList<Character>();
		this.horagrupoB = new ArrayList<String>();
		this.preRequisitos = new ArrayList<String>();
		this.clasegrupoA= new ArrayList<String>();
		this.clasegrupoB= new ArrayList<String>();
		
		this.siglas = siglas;
		this.nombre = nombre;
		this.curso = Integer.parseInt(curso);
		this.cuatrimestre = Integer.parseInt(cuatrimestre);
		this.dniCoordinador = dniCoordinador;
		this.duracionGrupoA = Integer.parseInt(duracionGrupoA);
		this.duracionGrupoB = Integer.parseInt(duracionGrupoB);

		if (preRequisitos != null) {
			String[] requisitos = preRequisitos.split(";");

			for (int i = 0; i < requisitos.length; i++) {
				this.preRequisitos.add(requisitos[i]);
			}
		}

		String[] grupoa = grupoA.split(";");
		
		for (int i = 0; i < grupoa.length; i++) {
			String[] campos = grupoa[i].trim().split(" ");
			
			this.idgrupoA.add(Integer.parseInt(campos[0]));
			this.diagrupoA.add(campos[1].trim().toCharArray()[0]);
			this.horagrupoA.add(campos[2]);
			this.clasegrupoA.add(campos[3]);
		}
		
		
		String[] grupob = grupoB.split(";");
		for (int i = 0; i < grupob.length; i++) {
			String[] campos = grupob[i].trim().split(" ");
			this.idgrupoB.add(Integer.parseInt(campos[0]));
			this.diagrupoB.add(campos[1].trim().toCharArray()[0]);
			this.horagrupoB.add(campos[2]);
			this.clasegrupoB.add(campos[3]);
		}
		
	
		
	}
	/**
	 *  get de siglas
	 * @return String con las siglas
	 */
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

	public String getPre_Requisitos(int i) {
		return preRequisitos.get(i);
	}
	
	/**
	 * funcion desninada a devolver el arraylist de Prerequisitos
	 * @return ArrayList (String)
	 */
	public ArrayList<String> getPreRequisitos() {
		return preRequisitos;
	}

	public int getDuracion_GrupoA() {
		return duracionGrupoA;
	}

	public int getDuracionGrupoB() {
		return duracionGrupoB;
	}

	public String getDniCoordinador() {
		return dniCoordinador;
	}


	public int getDuracionGrupoA() {
		return duracionGrupoA;
	}

	public int getIdgrupoA(int i) {
		return idgrupoA.get(i);
	}

	public char getDiagrupoA(int i) {
		return diagrupoA.get(i);
	}

	public String getHoragrupoA(int i) {
		return horagrupoA.get(i);
	}

	public int getIdgrupoB(int i) {
		return idgrupoB.get(i);
	}

	public char getDiagrupoB(int i) {
		return diagrupoB.get(i);
	}

	public String getHoragrupoB(int i) {
		return horagrupoB.get(i);
	}
	

	public ArrayList<Integer> getIdgrupoA() {
		return idgrupoA;
	}

	public ArrayList<Integer> getIdgrupoB() {
		return idgrupoB;
	}
	
	
	
	public ArrayList<String> getClasegrupoA() {
		return clasegrupoA;
	}

	public ArrayList<String> getClasegrupoB() {
		return clasegrupoB;
	}
	
	public String getclaseA(int i) {
		return clasegrupoA.get(i);
	}
	
	public String getclaseB(int i) {
		return clasegrupoB.get(i);
	}
	/**
	 * Devuelve la hora del tipo de grupo y id correspontiente
	 * @param tipogrupo
	 * @param idgrupo
	 * @return String : hora
	 */
	public String gethora(char tipogrupo, int idgrupo) {
			if (tipogrupo=='A') {
				return horagrupoA.get(idgrupoA.indexOf(idgrupo));
					
			}
		
			else {
				return horagrupoB.get(idgrupoB.indexOf(idgrupo));
			}
		
	}
	/**
	 * Devuelve el dia correspondiente al tipo de grupo e idgrupo dado
	 * @param tipogrupo
	 * @param idgrupo
	 * @return char : con el dia de la semana
	 */
	public char getdia(char tipogrupo,int idgrupo) {
		if (tipogrupo=='A') {
			return diagrupoA.get(idgrupoA.indexOf(idgrupo));
		}else 
		
		
		return diagrupoB.get(idgrupoB.indexOf(idgrupo));
	}
	
	
	/** Devuelve el indice en el que se encuentra la id del grupo
	 * 
	 * @param tipogrupo : A o B
	 * @param idgrupo
	 * @return
	 */
	public String getclase(char tipogrupo,int idgrupo) {
		if (tipogrupo=='A') {
			return clasegrupoA.get(idgrupoA.indexOf(idgrupo));
		}else 
		
		
		return clasegrupoB.get(idgrupoB.indexOf(idgrupo));
	}
	
	/**Crea un grupo 
	 * 
	 * @param tipogrupo : A o B
	 * @param idgrupo : id del grupo
	 * @param dia : dias en que se cursa
	 * @param horainicio : hora de inicio de la clase del grupo (valores de 9 a 18)
	 * @param aula : aula don de se imparte la clase del grupo
	 * 
	 */
	public void creaGrupo(char tipogrupo,int idgrupo,char dia,String horainicio,String aula) {
		
		if (tipogrupo=='A') {
			this.idgrupoA.add(idgrupo);
			this.diagrupoA.add(dia);
			this.horagrupoA.add(horainicio.trim());
			this.clasegrupoA.add(aula.trim());
		}else {
			this.idgrupoB.add(idgrupo);
		    this.diagrupoB.add(dia);
		    this.horagrupoB.add(horainicio.trim());
		    this.clasegrupoB.add(aula.trim());
		}
		return ;
		
		
		
		
	}

	/**Convierte los atributos de este objeto a un conjunto de lineas de texto,
	 * separadas por saltos de linea (\n), para asi facilitar su guardado en un
	 * fichero de texto.
	 * 
	 * @return (String) : conjunto de lineas de texto con los atributos del objeto.
	 * 
	 */
	public String toTexto() {
		// Siglas, nombre, curso, cuatrimestre, coordinador (DNI), prerrequisitos,
		// duración grupo A y duración grupo B:
		String inicio = siglas + "\n" + nombre + "\n" + curso + "\n" + cuatrimestre + "\n" + dniCoordinador + "\n";
		StringBuffer buffer = new StringBuffer();
		
		if (!preRequisitos.isEmpty()) {
			for (int i = 0; i < preRequisitos.size(); i++) {
		
				if (i==preRequisitos.size()-1) {
					buffer.append(preRequisitos.get(i)+"\n");
				} else
					buffer.append(preRequisitos.get(i) + "; ");
			}
		} else
			buffer.append("\n");
		//añadimos la duracion de grupos a y b
		buffer.append(duracionGrupoA + "\n" + duracionGrupoB + "\n");
		
		//añadimos los grupos teoricos
		if (!idgrupoA.isEmpty()) {
			for (int i = 0; i < idgrupoA.size(); i++) {
		
				if (i==idgrupoA.size()-1) {
					buffer.append(idgrupoA.get(i) + " " + diagrupoA.get(i) + " "
							+ horagrupoA.get(i)+ " " + clasegrupoA.get(i)+"\n");
				} else
					buffer.append(idgrupoA.get(i) + " " + diagrupoA.get(i) + " "
							+ horagrupoA.get(i)+ " " + clasegrupoA.get(i)+ "; ");
			}
		} else
			buffer.append("\n");
		
		//añadimos los grupos practicos
		if (!idgrupoB.isEmpty()) {
			for (int i = 0; i < idgrupoB.size(); i++) {
		
				if (i==idgrupoB.size()-1) {
					buffer.append(idgrupoB.get(i) + " " + diagrupoB.get(i) + " "
							+ horagrupoB.get(i)+ " " + clasegrupoB.get(i)+"\n");
				} else
					buffer.append(idgrupoB.get(i) + " " + diagrupoB.get(i) + " "
							+ horagrupoB.get(i)+ " " + clasegrupoB.get(i)+ "; ");
			}
		} else
			buffer.append("\n");
		
		// Juntamos todo en un mismo String
		String cadena = inicio.concat(new String(buffer));
		return cadena.substring(0, cadena.length() - 1); // elimina el último salto de línea

	}

}

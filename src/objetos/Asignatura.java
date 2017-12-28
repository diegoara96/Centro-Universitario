package objetos;

import java.util.ArrayList;

public class Asignatura implements EscribibleEnFichero {

	private String siglas;
	private String nombre;
	private int curso;
	private int cuatrimestre;
	private String dniCoordinador;
	private ArrayList<String> preRequisitos;
	private int duracionGrupoA;
	private int duracionGrupoB;
	private ArrayList<Integer> idgrupoA;
	private ArrayList<String> diagrupoA;
	private ArrayList<String> horagrupoA;
	private ArrayList<String> clasegrupoA;
	private ArrayList<Integer> idgrupoB;
	private ArrayList<String> diagrupoB;
	private ArrayList<String> horagrupoB;
	private ArrayList<String> clasegrupoB;
	
	public Asignatura(String siglas, String nombre, String curso, String cuatrimestre, String dniCoordinador,
			String preRequisitos, String duracionGrupoA, String duracionGrupoB, String grupoA, String grupoB) {

		this.idgrupoA = new ArrayList<Integer>();
		this.diagrupoA = new ArrayList<String>();
		this.horagrupoA = new ArrayList<String>();
		this.idgrupoB = new ArrayList<Integer>();
		this.diagrupoB = new ArrayList<String>();
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
			//System.out.println(campos.length);
			this.idgrupoA.add(Integer.parseInt(campos[0]));
			this.diagrupoA.add(campos[1]);
			this.horagrupoA.add(campos[2]);
			this.clasegrupoA.add(campos[3]);
		}
		
		
		String[] grupob = grupoB.split(";");
		for (int i = 0; i < grupob.length; i++) {
			String[] campos = grupob[i].trim().split(" ");
			this.idgrupoB.add(Integer.parseInt(campos[0]));
			this.diagrupoB.add(campos[1]);
			this.horagrupoB.add(campos[2]);
			this.clasegrupoB.add(campos[3]);
		}
		
	//	System.out.println(idgrupoA);
		
	}

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

	public String getDiagrupoA(int i) {
		return diagrupoA.get(i);
	}

	public String getHoragrupoA(int i) {
		return horagrupoA.get(i);
	}

	public int getIdgrupoB(int i) {
		return idgrupoB.get(i);
	}

	public String getDiagrupoB(int i) {
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

	public String gethora(char tipogrupo, int idgrupo) {
			if (tipogrupo=='A') {
				return horagrupoA.get(idgrupoA.indexOf(idgrupo));
					
			}
		
			else {
				return horagrupoB.get(idgrupoB.indexOf(idgrupo));
			}
		
	}
	
	public String getclase(char tipogrupo,int idgrupo) {
		if (tipogrupo=='A') {
			return clasegrupoA.get(idgrupoA.indexOf(idgrupo));
		}else 
		
		
		return clasegrupoB.get(idgrupoB.indexOf(idgrupo));
	}
	

	/**
	 * Convierte los atributos de este objeto a un conjunto de líneas de texto,
	 * separadas por saltos de línea (\n), para así facilitar su guardado en un
	 * fichero de texto.
	 * 
	 * @return (String): conjunto de líneas de texto con los atributos del objeto.
	 */
	public String toTexto() {
		// Siglas, nombre, curso, cuatrimestre, coordinador (DNI), prerrequisitos,
		// duración grupo A y duración grupo B:
		String inicio = siglas + "\n" + nombre + "\n" + curso + "\n" + cuatrimestre + "\n" + dniCoordinador + "\n"
				+ preRequisitos + "\n" + duracionGrupoA + "\n" + duracionGrupoB + "\n";

		// TODO: Grupos??

		// Juntamos todo en un mismo String
		// String cadena = inicio.concat(new String(buffer));
		return inicio.substring(0, inicio.length() - 2); // elimina el último salto de línea

	}

}

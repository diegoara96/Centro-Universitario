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
	private ArrayList<String> idgrupoA;
	private ArrayList<String> diagrupoA;
	private ArrayList<String> horagrupoA;
	private ArrayList<String> idgrupoB;
	private ArrayList<String> diagrupoB;
	private ArrayList<String> horagrupoB;
	
	public Asignatura(String siglas, String nombre, String curso, String cuatrimestre, String dniCoordinador, String preRequisitos, String duracionGrupoA, String duracionGrupoB, String grupoA, String grupoB) {
		
		ArrayList<String> idgrupoA=new ArrayList<String>();
		ArrayList<String> diagrupoA=new ArrayList<String>();
		ArrayList<String> horagrupoA=new ArrayList<String>();
		ArrayList<String> idgrupoB=new ArrayList<String>();
		ArrayList<String> diagrupoB=new ArrayList<String>();
		ArrayList<String> horagrupoB=new ArrayList<String>();
		
		this.siglas=siglas;
		this.nombre=nombre;
		this.curso= Integer.parseInt(curso);
		this.cuatrimestre=Integer.parseInt(cuatrimestre);
		this.dniCoordinador=dniCoordinador;
		this.preRequisitos.add(preRequisitos);
		this.duracionGrupoA=Integer.parseInt(duracionGrupoA);
		this.duracionGrupoB=Integer.parseInt(duracionGrupoB);
		
		if (preRequisitos != null) {
			String[] requisitos = preRequisitos.split(";");

			for (int i = 0; i < requisitos.length; i++) {
				String[] campos = requisitos[i].trim().split(" ");
				this.siglasAsignaturaSuperada.add(campos[0]);
				this.cursoAcademico.add(campos[1]);
				this.notaAsignatura.add(Float.parseFloat(campos[2]));
			}
		}
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
	public String getPre_Requisitos() {
		return preRequisitos;
	}
	public int getDuracion_GrupoA() {
		return duracionGrupoA;
	}
	public int getDuracionGrupoB() {
		return duracionGrupoB;
	}
	
	/**
	 * Convierte los atributos de este objeto a un conjunto de líneas de texto, separadas por saltos de línea (\n),
	 * para así facilitar su guardado en un fichero de texto.
	 * @return (String): conjunto de líneas de texto con los atributos del objeto.
	 */
	public String toTexto() {
		// Siglas, nombre, curso, cuatrimestre, coordinador (DNI), prerrequisitos, duración grupo A y duración grupo B:
		String inicio = siglas + "\n" + nombre + "\n" + curso
			+ "\n" +  cuatrimestre + "\n" + dniCoordinador + "\n" 
				+  preRequisitos + "\n" + duracionGrupoA + "\n" + duracionGrupoB + "\n";
		
		// TODO: Grupos??
		
		// Juntamos todo en un mismo String
		//String cadena = inicio.concat(new String(buffer));
		return inicio.substring(0, inicio.length()-2); // elimina el último salto de línea
		
		
	}
	
}

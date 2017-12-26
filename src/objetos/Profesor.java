package objetos;

import java.util.ArrayList;

import proyecto.GestionErrores;

public class Profesor extends Persona implements EscribibleEnFichero {

	private String categoria;
	private String departamento;
	private ArrayList<String> siglasAsignatura ;
	private ArrayList<Character> tipoGrupo;
	private ArrayList <Character>idGrupo;
//	private ArrayList sigla;
	
	
	
	public Profesor(String dni, String nombre, String fechaNacimiento, String categoria, String departamento, String docenciaImpartida) {
		super(dni,nombre,fechaNacimiento);
	
		siglasAsignatura=new ArrayList<String>();
		tipoGrupo=new ArrayList<Character>();
		idGrupo=new ArrayList<Character>();
		
		if(docenciaImpartida!=null) {
		String[] docencia = docenciaImpartida.split(";");
		
		
		for(int i=0;i<docencia.length;i++) {
			
			String[] campos= docencia[i].trim().split(" ");

			if (campos[1].length()>1) {
				char algo [] =campos[1].toCharArray();
				this.siglasAsignatura.add(campos[0]);
				this.tipoGrupo.add(algo[0]);
				this.idGrupo.add(algo[1]);
				
				
			}
			else {
			this.siglasAsignatura.add(campos[0]);
			this.tipoGrupo.add(campos[1].toCharArray()[0]);
			this.idGrupo.add(campos[2].toCharArray()[0]);
			}
		}
		}
		this.categoria= categoria;
		this.departamento=departamento;
		
		//System.out.println(toTexto()+"\n");
		
	}
	public Profesor(String dni, String nombre, String fechaNacimiento, String categoria, String departamento) {
		super(dni,nombre,fechaNacimiento);
		
		siglasAsignatura=new ArrayList<String>();
		tipoGrupo=new ArrayList<Character>();
		idGrupo=new ArrayList<Character>();
		
		this.categoria= categoria;
		this.departamento=departamento;
		
		//System.out.println(toTexto()+"\n");
		
	}
	
	public String getCategoria() {
		return categoria;
	}
	public String getDepartamento() {
		return departamento;
	}
	public String getSiglas_Asignatura(int i) {
		return siglasAsignatura.get(i);
	}
	public char getId_Grupo(int i) {
		return idGrupo.get(i);
	}
	public char getTipoGrupo(int i) {
		return tipoGrupo.get(i);
	}
	
	/**
	 * Convierte los atributos de este objeto a un conjunto de líneas de texto, separadas por saltos de línea (\n),
	 * para así facilitar su guardado en un fichero de texto.
	 * @return (String): conjunto de líneas de texto con los atributos del objeto.
	 */
	public String toTexto() {
		// DNI, nombre, fecha de nacimiento, categoria y departamento:
		String inicio = getDni()+ "\n" + getNombre() + "\n" + this.getFechaNacimientoFormateada()
			+ "\n" +  categoria + "\n" + departamento + "\n";
		
		// Docencia impartida (recorremos los arrays)
		StringBuffer buffer = new StringBuffer();
		if (!siglasAsignatura.isEmpty()) {
		for(int i = 0; i<siglasAsignatura.size(); i++) {
			if(i==siglasAsignatura.size()-1) {
				buffer.append(siglasAsignatura.get(i) + " " + tipoGrupo.get(i) + " " + idGrupo.get(i) + "\n");
			}
		else buffer.append(siglasAsignatura.get(i) + " " + tipoGrupo.get(i) + " " + idGrupo.get(i) + "; ");
		}
		}else buffer.append("\n");
		// Juntamos todo en un mismo String
		String cadena = inicio.concat(new String(buffer));
		return cadena.substring(0, cadena.length()-1); // elimina el último salto de línea
		
		
	}

	
}

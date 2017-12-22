package objetos;

import proyecto.GestionErrores;

public class Profesor extends Persona implements EscribibleEnFichero {

	private String categoria;
	private String departamento;
	private String[] siglasAsignatura ;
	private char[] tipoGrupo;
	private char[] idGrupo;
	
	public Profesor(String dni, String nombre, String fechaNacimiento, String categoria, String departamento, String docenciaImpartida) {
		super(dni,nombre,fechaNacimiento);
		//comprobaciones de dni
	
		
		boolean dniCorrecto = Persona.comprobarDNI(dni);
		if (dniCorrecto==false) {
			GestionErrores.errorComando("IP", "Dni incorrecto");
			return;
		}
		/**
		 * Se deber�n insertar posteriormente la gesti�n de errores asociada a dni incorrecto
		 */
		
		//comprobaciones FechaNacimiento
		boolean fechaNacimientoCorrecta = Persona.comprobarFecha(fechaNacimiento);
		if(fechaNacimientoCorrecta==false) {
			GestionErrores.errorComando("IP", "Fecha incorrecta");
			return;
		}
		/**
		 * Se deber�n insertar posteriormente la gesti�n de errores asociada a fecha de nacimiento incorrecta
		 */
		
		String[] docencia = docenciaImpartida.split(";");
		
		siglasAsignatura=new String [docencia.length];
		tipoGrupo=new char [docencia.length];
		idGrupo=new char [docencia.length];
		
		for(int i=0;i<docencia.length;i++) {
			
			String[] campos= docencia[i].trim().split(" ");

			if (campos[1].length()>1) {
				char algo [] =campos[1].toCharArray();
				this.siglasAsignatura[i]= campos[0];
				this.tipoGrupo[i] = algo[0];
				this.idGrupo[i] = algo[1];
				
			}
			else {
			this.siglasAsignatura[i]= campos[0];
			this.tipoGrupo[i] = campos[1].toCharArray()[0];
			this.idGrupo[i] = campos[2].toCharArray()[0];
			}
		}	
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
		return siglasAsignatura[i];
	}
	public char getId_Grupo(int i) {
		return idGrupo[i];
	}
	public char getTipoGrupo(int i) {
		return tipoGrupo[i];
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
		for(int i = 0; i<siglasAsignatura.length; i++) {
			buffer.append(siglasAsignatura[i] + " " + tipoGrupo[i] + " " + idGrupo[i] + ";\n");
		}
		
		// Juntamos todo en un mismo String
		String cadena = inicio.concat(new String(buffer));
		return cadena.substring(0, cadena.length()-2); // elimina el último salto de línea
		
		
	}

	
}

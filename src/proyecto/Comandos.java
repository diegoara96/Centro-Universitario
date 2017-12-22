package proyecto;
import proyecto.GestionErrores;


public class Comandos {
	

	public static void comandos(String imput[]) {
		switch (imput [1]) {
		case "InsertaPersona" : //WIP
			insertaPersona(imput);
			break;
		case "AsignaGrupo": //WIP
			//asignaGrupo();
			break;
		case "Matricula": //WIP
			//matricula();
			break;
		case "CreaGrupoAsig": //WIP
			//CreaGrupoAsig();
			break;
		case "Evalua": //WIP
			//evaluarAsig();
			break;
		case "Expediente":
			//obtenerExpediente();
			break;
		case "OcupacionAula": //WIP
			//CalendarioAulas();
			break;
		default:  //gestion de errores funcion mal escrita
			GestionErrores.comandoErroneo(imput [1]);
			break;
		
		}
		
	}
	
	 
	public static void insertaPersona(String imput[]) {
	/*	for(int i=0;i<imput.length;i++) {
			System.out.println(imput[i]);
		}
		*/
		if (imput.length<9) {
			GestionErrores.errorComando("IP", "numero de argumentos incorrecto");
		}
		
		String parametros[] = new String[imput.length];
		String nombre;
		int b=0;
		for (int i=1;i<parametros.length ;i++) {
				if(imput[i].contains("\"")) {
					nombre=imput[i];
					for ( int a=i+1;a<10;a++) {
						nombre=nombre.concat(" ").concat(imput[a]);
						if(imput[a].contains("\"")) {
							parametros[b]=nombre.substring(1, nombre.length()-1);
							//System.out.println(nombre);
							i=a;
							
							break;
						}
					}
				
				
			}
				else {
			parametros[b]=imput[i];
			}
			System.out.println(parametros[b]);
				
				b++;
		}
		
		
		
		
		/*	//parametro 0 = comando
		parametros[0]=imput[1];
		//parametro 1 = profesor/ alumno
		parametros[1]=imput[2];
		//parametro 2 = dni
		parametros[2]=imput[3];
		// parametro 3 nombre
		parametros[3]=imput[4].concat(imput[5]);
		System.out.println(parametros[3]);
		//parametro 4 fecha nacimiento
		parametros[4]=imput[6];
		
		*/
		
		if (parametros[1].contains("profesor")) {
			
			if (imput.length==10) {
				GestionErrores.errorComando("IP", "numero de argumentos incorrecto");
			}
			
			//parametro 5 = categoria
			parametros[5]=imput[7];
			//parametro 6 ? departamento
			parametros[6]=imput[8];

			
			
			
			
			
		}
		
		if(imput[2].contains("alumno")) {
			if (imput.length==9) {
				GestionErrores.errorComando("IP", "numero de argumentos incorrecto");
			}
			System.out.println("alumno");
			
		}
	}
	
	
	
	
	
	
}

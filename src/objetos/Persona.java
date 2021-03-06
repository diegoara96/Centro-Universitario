package objetos;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;
/**
 * 
 * Clase abstracta de la que heredan Alumnos y Profesores
 * contiene dni, nombre,fecha nacimiento y email
 *
 */
public abstract class Persona  {

	private String dni;
	private String nombre;
	private GregorianCalendar fechaNacimiento;
	private String email;
	
	protected final static DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY"); // permite convertir de DD/MM/YYYY a objeto Date y viceversa
	
	
	/**Crea un objeto persona con los datos correspondientes
	 * 
	 * @param dni : dni del profesor o alumno
	 * @param nombre : nombre y apellidos del profesor o alumno
	 * @param fechaNacimiento : fecha de nacimiento del profesor o alumno
	 * 
	 */
	public Persona(String dni,String nombre,String fechaNacimiento) {
		this.dni=dni;
		this.nombre=nombre;
		this.fechaNacimiento= fechaToGregorianCalendar(fechaNacimiento);
		
	}
	
	public Persona(String dni,String nombre,String email,String fechaNacimiento) {
		this.dni=dni;
		this.nombre=nombre;
		this.fechaNacimiento= fechaToGregorianCalendar(fechaNacimiento);
		if(email==null)this.email="";
		else this.email=email;
		
		
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getDni() {
		return dni;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public GregorianCalendar getFechaNacimiento() {
		return fechaNacimiento;
	}
	
	//��El error est� en este m�todo!! devuelve una fecha incorrecta
	public String getFechaNacimientoFormateada() {

		return dateFormat.format(this.fechaNacimiento.getTime());
	}
	
	// Metodos de comprobacion de atributos
	
	/**Combprueba el dni
	 * 
	 * @param dni
	 * @return true/false si esta mal devuelve false y sino true
	 */
	public static boolean comprobarDNI(String dni) {
		char[] digitosdni= dni.toCharArray();
		//System.out.println(digitosdni[0]);
		
		// longitud correcta?
		if(dni.length() != 9) {
			return false;
		}
		
		// ocho primeros caracteres numericos?
		for (int i=0;i<8;i++) {
			
			if (Character.isDigit(digitosdni[i])) {
				continue;
			}else {
				return false;
			}
		}
		
		// letra en mayuscula?
		if(!Character.isUpperCase(digitosdni[8])) {
			return false;
		}
		
		return true;
	}
	
	
	/**Comprueba la fecha
	 * 
	 * @param fecha
	 * @return  true o false
	 */
	public static boolean comprobarFecha(String fecha) {
		
		
		try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            formatoFecha.setLenient(false);
            formatoFecha.parse(fecha);
            Date date=formatoFecha.parse(fecha);
            Date minimaldate = dateFormat.parse("01/01/1960");
			Date maximaldate = dateFormat.parse("01/01/2018");
			if(date.before(minimaldate) || date.after(maximaldate)) {
				return false;
			}
            
        } catch (ParseException e) {
            return false;
        }
		
		return true;
	}
    
		
	
	/**Comprueba la fecha de ingrese
	 * 
	 * @param fechaNacimiento
	 * @param fechaIngreso
	 * @return true o false
	 */
	public static boolean comprobarFechaIngreso(String fechaNacimiento, String fechaIngreso) {

		try {
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			//con el DateFormat no funciona porque se necesita un DateTimeFormatter y as� poder utilizar el Period, por eso lo creo 
			//con el Period te devuelve un LocalDate con el periodo entre las dos fechas y as� podemos ver los anos, meses, etc...
			
			LocalDate nacimiento  =LocalDate.parse(transformarfecha(fechaNacimiento),fmt);
			LocalDate ingreso = LocalDate.parse(transformarfecha(fechaIngreso),fmt);
			
			Period periodo = Period.between(nacimiento, ingreso);
			if(periodo.getYears() < 16 || periodo.getYears() > 60) {
				return false;
			}
		} catch (Exception e) {
			
		}
		return true;
		
	}
	
	public static GregorianCalendar fechaToGregorianCalendar(String fecha) {
		GregorianCalendar gc = new GregorianCalendar();
		String []prueba=fecha.split("/");
		int anho = Integer.parseInt(prueba[2]);
		int mes = Integer.parseInt(prueba[1]);
		int dia = Integer.parseInt(prueba[0]);
		gc.set(anho,mes-1,dia);
		//gc.setTime(date);
		return gc;

	}
	
	/**Pone la fecha en el formato correcto
	 * 
	 * @param fecha
	 * @return fecha en buen formato
	 */
	public static String transformarfecha(String fecha) {
        String prueba[] = fecha.split("/");
		
		if(prueba[0].length()!=2) {
			prueba[0]="0".concat(prueba[0]);
			fecha=prueba[0].concat("/".concat(prueba[1].concat("/".concat(prueba[2]))));
		}
		
		if(prueba[1].length()!=2) {
			prueba[1]="0".concat(prueba[1]);
			fecha=prueba[0].concat("/".concat(prueba[1].concat("/".concat(prueba[2]))));
			
		}
		
		return fecha;
	}
	
}

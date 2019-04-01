# Centros Universitarios
Proyeto realizado en el marco de la asignatura Programacion II de Ingenieria de Telecomunicaciones.Se trata de una aplicación que almacena y gestiona las personas asociadas a un centro universitario y las asignaturas que ahí se imparten.Dicha información se almacena en sus respectivos ficheros de texto, con un formato predeterminado (consultar ficheros de ejemplo). La ejecución del programa es a través de comandos independientes que se almacenan en el fichero `ejecucion.txt`.

# Ficheros de texto:
En la carpeta ficheros se encuentran los archivos que conforman la base de datos: `alumnos.txt`, `asignaturas.txt`, `aulas.txt`, `cursoAcademico.txt`, `Notas_A.txt`, `Notas_B.txt`, `pod.txt` (asigna profesores a asignaturas y grupos) y `profesores.txt`.

# Ejecución:
El fichero `ejecucion.txt` contiene todas las instrucciones que el usuario desee ejecutar. Cada instrucción será una línea, que comenzará con el número de instrucción, seguido del tipo de instrucción y de los parámetros necesarios. Para ejecutar el programa y que este ejecute dichas instrucciones, tan solo hay que invocar el método `main()` de la clase `CentrosUniversitarios`.

# Comandos:

<ul>
  <li><b>Inserta persona:</b> IP
  <li><b>Crear grupo asignatura:</b> CGA
  <li><b>Matricular alumno:</b> MAT
  <li><b>Asignar grupo:</b> AGRUPO
  <li><b>Evaluar asignatura:</b> EVALUA
  <li><b>Calendario ocupación aula:</b> OCUPAULA
  <li><b>Expediente alumno:</b> EXP
  </ul>

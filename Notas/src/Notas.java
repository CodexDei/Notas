    import javax.swing.*;
    import java.util.*;
    import static java.util.Arrays.copyOf;

    public class Notas {

        private static int numeroEstudiantes;
        private static int numeroMaterias;
        private static String [] nombresEstudiantes;
        private static String [] nombresMaterias;
        private static double [][] calificaciones;
        private static int copiaNumeroEstudiantes;
        private static int copiaNumeroMaterias;
        private static String [] copiaNombreEstudiantes;
        private static String [] copiaNombresMaterias;
        private static double copiaNotas [][];
        private static double verificarAProbacionAno;
        private static double promedio;
        private static boolean estado =  false;

        public static void main(String[] args) {

           do {

               try {

                   int menu = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la opcion que desee:\n\n"
                           + "1. Establecer materias,estudiantes y nota aprobacion minima aprobar año escolar\n"
                           + "2. Ingresar calificaciones o notas de los estudiantes\n"
                           + "3. Mostrar tabla de notas o calificaciones\n"
                           + "4. Ordenar estudiantes de mayor a menor nota\n"
                           + "5. Hacer copia de seguridad\n"
                           + "6. Ver copia de seguridad\n"
                           + "7. Salir\n"));

                   switch (menu){

                       case 1:

                           establecerMateriasEstudiantesNota();
                           break;

                       case 2:

                           ingresarCalificaciones();
                           validacionCopiaSeguridad();
                           break;

                       case 3:

                           mostrarCalificaciones();
                           validacionCopiaSeguridad();
                           break;

                       case 4:

                           ordenarNotasEstudiantes();
                           validacionCopiaSeguridad();
                           break;

                       case 5:

                           copiaTablaNotas();
                           break;

                       case 6:

                           mostrarcopiaTablaNotas();
                           break;

                       case 7:

                           estado = true;
                           break;

                       default:

                           throw new IllegalArgumentException("Ingrese solo los valores NUMERICOS del menu");
                   }

               } catch (InputMismatchException e) {

                   JOptionPane.showMessageDialog(null,
                           "Ingrese un valor valido", "¡ERROR!", JOptionPane.WARNING_MESSAGE);

               } catch (Exception e) {

                   JOptionPane.showMessageDialog(null,
                           "ERROR: " + e.getMessage(), "¡ERROR EN LA EJECUCION!", JOptionPane.WARNING_MESSAGE);

               }

           }while (!estado);
        }
        private static void establecerMateriasEstudiantesNota() {

            numeroEstudiantes = Integer.parseInt(JOptionPane.showInputDialog
                    ("Ingrese el numero de estudiantes a registrar:"));

            numeroMaterias = Integer.parseInt(JOptionPane.showInputDialog
                    ("Ingrese el numero de materias a registrar:"));

            verificarAProbacionAno = Double.parseDouble(JOptionPane.showInputDialog
                    ("Ingrese la nota minima para aprobar el año escolar"));

            calificaciones =  new double [numeroEstudiantes][numeroMaterias];

            nombresEstudiantes = new String[numeroEstudiantes];

            nombresMaterias = new String[numeroMaterias];

            // Ingresar nombres de estudiantes
            for (int i = 0; i < numeroEstudiantes; i++) {
                nombresEstudiantes[i] = JOptionPane.showInputDialog(null,
                        "Ingrese el nombre del estudiante # " + (i + 1) + ": ");
            }

            // Ingresar nombres de asignaturas
            for (int j = 0; j < numeroMaterias; j++) {
                nombresMaterias[j] = JOptionPane.showInputDialog(null,
                        "Ingrese el nombre de la asignatura # " + (j + 1) + ": ");
            }

        }

        private static void ingresarCalificaciones() {

            if (nombresEstudiantes != null && nombresMaterias != null){
                // Ingresar calificaciones
                for (int i = 0; i < numeroEstudiantes; i++) {
                    for (int j = 0; j < numeroMaterias; j++) {
                        calificaciones[i][j] = Double.parseDouble(JOptionPane.showInputDialog(null,
                                "Ingrese la calificación de " + nombresEstudiantes[i] +
                                        " en " + nombresMaterias[j] + ": "));
                    }
                }

            }else {

                JOptionPane.showMessageDialog(null,
                        "Ingrese primero los datos de estudiantes y materias","RECUERDE",JOptionPane.WARNING_MESSAGE);
                establecerMateriasEstudiantesNota();
            }
        }

        private static void mostrarCalificaciones() {

            if (calificaciones != null){

                // Mostrar información
                StringBuilder resultado = new StringBuilder("\nInformación registrada:\n\n");

                resultado.append("            ");
                for (String asignatura : nombresMaterias) {
                    resultado.append(String.format("%-20s", asignatura));
                }
                resultado.append("Promedio       ");
                resultado.append("Estatus año escolar\n");

                for (int i = 0; i < numeroEstudiantes; i++) {

                    resultado.append(String.format("%-20s", nombresEstudiantes[i]));

                    double suma = 0;
                    for (int j = 0; j < numeroMaterias; j++) {
                        resultado.append(String.format("%-27.2f", calificaciones[i][j]));
                        suma += calificaciones[i][j];
                    }
                    promedio = suma / numeroMaterias;
                    resultado.append(String.format("%-20.2f", promedio));

                    if (promedio >= verificarAProbacionAno) {
                        resultado.append(String.format("%-20s", "APROBADO"));
                    } else {
                        resultado.append(String.format("%-20s", "REPROBADO"));
                    }
                    resultado.append("\n");
                }

                JOptionPane.showMessageDialog(null, resultado.toString(), "Información Registrada",
                        JOptionPane.INFORMATION_MESSAGE);

                //               estado = JOptionPane.showConfirmDialog(null, "¿Desea registrar más estudiantes?", "Continuar",
                //                       JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

            }else{
                JOptionPane.showMessageDialog
                    (null, "Ingrese primero los datos estudiantes, materias o calificanes, alguno de estos datos hacen falta"
                    ,"RECUERDE",JOptionPane.WARNING_MESSAGE);

                if(nombresEstudiantes == null || nombresMaterias == null){

                    establecerMateriasEstudiantesNota();
                }
                if (calificaciones == null){

                    ingresarCalificaciones();
                }
            }
        }

        private static void ordenarNotasEstudiantes() {

            if (calificaciones != null) {

                // Crear un array temporal para almacenar las notas promedio y los nombres de los estudiantes
                double[] promediosTemp = new double[numeroEstudiantes];
                String[] nombresTemp = new String[numeroEstudiantes];

                // Calcular los promedios de los estudiantes y almacenarlos en el array temporal
                for (int i = 0; i < numeroEstudiantes; i++) {
                    double suma = 0;
                    for (int j = 0; j < numeroMaterias; j++) {
                        suma += calificaciones[i][j];
                    }
                    promediosTemp[i] = suma / numeroMaterias;
                    nombresTemp[i] = nombresEstudiantes[i];
                }

                // Ordenar los estudiantes por sus promedios en orden descendente
                for (int i = 0; i < numeroEstudiantes - 1; i++) {
                    for (int j = 0; j < numeroEstudiantes - i - 1; j++) {
                        if (promediosTemp[j] < promediosTemp[j + 1]) {
                            // Intercambiar los promedios
                            double temp = promediosTemp[j];
                            promediosTemp[j] = promediosTemp[j + 1];
                            promediosTemp[j + 1] = temp;
                            // Intercambiar los nombres de los estudiantes
                            String tempNombre = nombresTemp[j];
                            nombresTemp[j] = nombresTemp[j + 1];
                            nombresTemp[j + 1] = tempNombre;
                        }
                    }
                }

                // Mostrar las calificaciones ordenadas
                StringBuilder resultado = new StringBuilder("\nInformación registrada:\n\n");
                resultado.append("            ");
                for (String asignatura : nombresMaterias) {
                    resultado.append(String.format("%-20s", asignatura));
                }
                resultado.append("Promedio       ");
                resultado.append("Estatus año escolar\n");

                for (int i = 0; i < numeroEstudiantes; i++) {
                    resultado.append(String.format("%-20s", nombresTemp[i]));
                    for (int j = 0; j < numeroMaterias; j++) {
                        resultado.append(String.format("%-27.2f", calificaciones[buscarEstudiante(nombresTemp[i])][j]));
                    }
                    resultado.append(String.format("%-20.2f", promediosTemp[i]));
                    if (promediosTemp[i] >= verificarAProbacionAno) {
                        resultado.append(String.format("%-20s", "APROBADO"));
                    } else {
                        resultado.append(String.format("%-20s", "REPROBADO"));
                    }
                    resultado.append("\n");
                }

                JOptionPane.showMessageDialog(null, resultado.toString(), "Información Registrada", JOptionPane.INFORMATION_MESSAGE);

        }else {

                JOptionPane.showMessageDialog
                        (null,"Ingrese primero los datos de los estudiantes, asignaturas o calificaciones " +
                                        "de los estudiantes para poder ordenarlas, faltan todos o alguno de estos datos"
                                ,"RECUERDE...",JOptionPane.WARNING_MESSAGE);

                if (nombresEstudiantes == null || nombresMaterias == null){

                    establecerMateriasEstudiantesNota();
                }
                if (calificaciones == null){

                    ingresarCalificaciones();
                }
              }
        }

        // Método para buscar el índice de un estudiante en el arreglo de nombres de estudiantes
        private static int buscarEstudiante (String nombre){
            for (int i = 0; i < numeroEstudiantes; i++) {
                if (nombresEstudiantes[i].equals(nombre)) {
                    return i;
                }
            }
            return -1; // Retorna -1 si el estudiante no se encuentra
        }
        
        private static void copiaTablaNotas(){

            if (calificaciones != null){

                copiaNumeroEstudiantes = numeroEstudiantes;
                copiaNumeroMaterias = numeroMaterias;

                copiaNotas = new double[copiaNumeroEstudiantes][copiaNumeroMaterias];

                copiaNombreEstudiantes = Arrays.copyOf(nombresEstudiantes,numeroEstudiantes);
                copiaNombresMaterias = Arrays.copyOf(nombresMaterias,numeroMaterias);

                for (int fila = 0 ; fila < calificaciones.length ; fila++){

                    System.arraycopy(calificaciones[fila], 0, copiaNotas[fila], 0, calificaciones[fila].length);
                }

                JOptionPane.showMessageDialog
                        (null,"Se ha creado una copia de la tabla por seguridad",
                                "COPIA DE LA TABLA NOTAS",JOptionPane.INFORMATION_MESSAGE);

            }else {

                JOptionPane.showMessageDialog
                        (null,"Ingrese primero los datos de los estudiantes, asignaturas o calificaciones " +
                                        "de los estudiantes para poder ordenarlas, faltan todos o alguno de estos datos"
                                ,"RECUERDE...",JOptionPane.WARNING_MESSAGE);

                if (nombresEstudiantes == null || nombresMaterias == null){

                    establecerMateriasEstudiantesNota();
                }
                if (calificaciones == null){

                    ingresarCalificaciones();
                }
            }
        }

        private static void mostrarcopiaTablaNotas(){

            if (copiaNotas != null){

                // Mostrar información
                StringBuilder resultado = new StringBuilder("\nInformación registrada:\n\n");

                resultado.append("            ");
                for (String asignatura : copiaNombresMaterias) {
                    resultado.append(String.format("%-20s", asignatura));
                }
                resultado.append("Promedio       ");
                resultado.append("Estatus año escolar\n");

                for (int i = 0; i < copiaNumeroEstudiantes; i++) {

                    resultado.append(String.format("%-20s", copiaNombreEstudiantes[i]));

                    double suma = 0;
                    for (int j = 0; j < copiaNumeroMaterias; j++) {
                        resultado.append(String.format("%-27.2f", copiaNotas[i][j]));
                        suma += copiaNotas[i][j];
                    }
                    promedio = suma / copiaNumeroMaterias;
                    resultado.append(String.format("%-20.2f", promedio));

                    if (promedio >= verificarAProbacionAno) {
                        resultado.append(String.format("%-20s", "APROBADO"));
                    } else {
                        resultado.append(String.format("%-20s", "REPROBADO"));
                    }
                    resultado.append("\n");
                }

                JOptionPane.showMessageDialog(null, resultado.toString(), "Información Registrada",
                        JOptionPane.INFORMATION_MESSAGE);

                //               estado = JOptionPane.showConfirmDialog(null, "¿Desea registrar más estudiantes?", "Continuar",
                //                       JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

            }else{
                JOptionPane.showMessageDialog
                        (null, "NO hay una copia de seguridad creada, la crearemos en este momento"
                                ,"RECUERDE",JOptionPane.WARNING_MESSAGE);
                copiaTablaNotas();
            }
        }

        private static void validacionCopiaSeguridad(){

            boolean validadorCopiaSeguridad;

            validadorCopiaSeguridad = Arrays.deepEquals(calificaciones,copiaNotas);

            if(validadorCopiaSeguridad == true){

                JOptionPane.showMessageDialog(
    null,"Actualmente esta tabla de Calificaciones TIENE una copia de seguridad","INFORMACION",JOptionPane.INFORMATION_MESSAGE);
            }else {

                JOptionPane.showMessageDialog(
    null,"Actualmente esta tabla de Calificaciones NO TIENE una copia de seguridad","INFORMACION",JOptionPane.WARNING_MESSAGE);
            }
        }


   }




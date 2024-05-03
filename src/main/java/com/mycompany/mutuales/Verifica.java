package com.mycompany.mutuales;

import static interfaz.IConsultaSql.consulta_complementaria_obtenerTodosLosConceptos;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La clase Verifica se utiliza para realizar validaciones y verificaciones en
 * una línea de datos específica. Puede detectar errores relacionados con la
 * longitud de la línea, documentos, conceptos, importes, fechas y cupones.
 */
public class Verifica {

    private int longitud, nLinea;
    private final ArrayList<String> listaErrores = new ArrayList<>();
    private final ArrayList<String> listaAdvertencia = new ArrayList<>();

    private String fechaInicioStr = "", linea;
    private String nombreMutual;
    private Double importe;

    /**
     * Crea una instancia de la clase Verifica con la línea de datos
     * proporcionada y la longitud esperada.
     *
     * @param linea La línea de datos a verificar.
     * @param longitud La longitud esperada de la línea.
     */
    public Verifica(String linea, int longitud, String nombreMutual) {
        this.linea = linea;
        this.longitud = longitud;
        this.nombreMutual = nombreMutual;
    }

    /**
     * Crea una instancia de la clase Verifica con la línea de datos
     * proporcionada y una longitud predeterminada de 54 caracteres.
     *
     * @param linea La línea de datos a verificar.
     */
    public Verifica(String linea, String nombreMutual) {
        this(linea, 54, nombreMutual);
    }

    /**
     * Realiza una serie de verificaciones en la línea de datos y devuelve una
     * lista de errores detectados.
     *
     * @param nLinea El número de línea actual.
     * @return Una lista de errores detectados en la línea.
     */
    public ArrayList<String> obtenerErrores(int nLinea, int numero_mes, int numero_anio) {
        this.nLinea = nLinea;
        if (verificarLongitud()) {
            verificarDocumento(getLinea().substring(3, 16));
            verificarConcepto(getLinea().substring(16, 20) );
            verificarImporte(getLinea().substring(20, 31));
            verificarFechaInicio(getLinea().substring(31, 39), numero_mes, numero_anio);
            verificarFechaFin(getLinea().substring(39, 47));
            verificarCupon(getLinea().substring(47, 54));
        }
        return getListaErrores();
    }

    /**
     * Verifica si la longitud de la línea coincide con la longitud esperada y
     * agrega un error si no coincide.
     *
     * @return true si la longitud es correcta, de lo contrario false.
     */
    protected boolean verificarLongitud() {
        if (linea.length() != longitud) {
            agregarError("Longitud incorrecta.", linea);
            return false;
        }
        return true;
    }

    /**
     * Verifica si una cadena contiene solo caracteres numéricos.
     *
     * @param cadena La cadena a verificar.
     * @return true si la cadena contiene solo caracteres numéricos, de lo
     * contrario false.
     */
    protected boolean esNumerico(String cadena) {
        return cadena.chars().allMatch(Character::isDigit);
    }

    /**
     * Verifica si un documento contiene caracteres no numéricos y agrega un
     * error si es así.
     *
     * @param documento El documento a verificar.
     */
    protected void verificarDocumento(String documento) {
        if (!esNumerico(documento)) {
            agregarError("Documento contiene caracteres no numéricos.", documento);
        }
    }

    /**
     * Verifica si un concepto contiene caracteres no numéricos y agrega un
     * error si es así.
     *
     * @param concepto El concepto a verificar.
     */
    protected void verificarConcepto(String concepto) {
        if (!esNumerico(concepto)) {
            agregarError("Concepto contiene caracteres no numéricos.", concepto);
        }
        if (!tieneConceptoAsociado(concepto, "A")) {
            agregarError("El concepto no esta asociado a la mutual o al prestamo.", concepto);
        }
    }

    /**
     * Verifica si un importe contiene caracteres no numéricos y agrega un error
     * si es así.
     *
     */
    protected void verificarImporte(String importeStr) {
        if (!esNumerico(importeStr)) {
            agregarError("Importe contiene caracteres no numéricos.", importeStr);
        } else {
            this.importe = Double.parseDouble(importeStr) / 100;
        }
    }

    /**
     * Verifica si una fecha de inicio es válida y si no es mayor que la fecha
     * actual.
     *
     * @param fechaInicio La fecha de inicio a verificar.
     */
    protected void verificarFechaInicio(String fechaInicio, int numero_mes, int numero_anio) {
        if (!esNumerico(fechaInicio)) {
            agregarError("Fecha Inicio contiene caracteres no numéricos. formato DDMMAAA", fechaInicio);
        } else if (!esFormatoFecha(fechaInicio)) {
            agregarError("Fecha Inicio no válida en formato DDMMAAA.", fechaInicio);
        } else {
            if (esFechaEnMes(fechaInicio, numero_mes, numero_anio)) {
                fechaInicioStr = fechaInicio;
            } else {
                System.out.println("");
                agregarError("Fecha Inicio no esta en el mes a controlar.", fechaInicio);
            }
        }
    }

    /**
     * Verifica si una fecha de fin es válida y compara con la fecha de inicio.
     *
     * @param fechaFin La fecha de fin a verificar.
     */
    protected void verificarFechaFin(String fechaFin) {
        if (!esNumerico(fechaFin)) {
            agregarError("Fecha fin contiene caracteres no numéricos. formato DDMMAAA", fechaFin);
        } else if (!esFormatoFecha(fechaFin)) {
            agregarError("Fecha fin no válida en formato DDMMAAA.", fechaFin);
        } else if (!fechaInicioStr.isEmpty()) {
            compararFechas(fechaInicioStr, fechaFin);
        }
    }

    /**
     * Verifica si un cupón contiene caracteres no numéricos y agrega un error
     * si es así.
     *
     * @param cupon El cupón a verificar.
     */
    protected void verificarCupon(String cupon) {
        if (!esNumerico(cupon.trim())) {
            agregarError("Cupon contiene caracteres no numéricos.", cupon);
        }
    }

    /**
     * Comprueba si una fecha cumple con un formato de fecha específico.
     *
     * @param fecha La fecha a verificar en formato DDMMAAAA.
     * @return true si la fecha es válida en el formato especificado, de lo
     * contrario false.
     */
    public static boolean esFormatoFecha(String fecha) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("ddMMyyyy");
        formatoFecha.setLenient(false);  // Hace que la validación sea estricta

        try {
            formatoFecha.parse(fecha);
            return true;  // La fecha es válida
        } catch (ParseException e) {
            return false; // La fecha no es válida
        }
    }

    /**
     * Compara dos fechas y agrega un error si la fecha de inicio es mayor que
     * la fecha de fin.
     *
     * @param fechaInicioStr La fecha de inicio en formato DDMMAAAA.
     * @param fechaFinStr La fecha de fin en formato DDMMAAAA.
     */
    private void compararFechas(String fechaInicioStr, String fechaFinStr) {
        LocalDate fechaInicio = obtenerFechaDesdeCadena(fechaInicioStr);
        LocalDate fechaFin = obtenerFechaDesdeCadena(fechaFinStr);

        if (fechaInicio.isAfter(fechaFin)) {
            agregarError("Fecha de inicio mayor que la fecha de fin.", fechaFinStr);
        } else if (fechaInicio.isEqual(fechaFin)) {
            //agregarError("Las fechas son iguales.", fechaFinStr);
            //agregar a lista de Wsrninh
        }
    }

    private boolean esFechaEnMes(String fechaStr, int numero_mes, int numero_anio) {
        System.out.println("FECHA ARCHIVO");
        System.out.println(fechaStr);
        System.out.println("NUMERO DE MES");
        System.out.println(numero_mes);
        LocalDate fechaInicio = obtenerFechaDesdeCadena(fechaStr);
        System.out.println("FECHA MES");
        System.out.println(fechaInicio.getMonthValue());
        System.out.println("NUMERO DE AÑO");
        System.out.println(numero_anio);
        System.out.println("FECHA AÑO");
        System.out.println(fechaInicio.getYear());
        // Comparar el número de mes con el mes de la fecha
        
        return 
                (fechaInicio.getYear() < numero_anio) || 
                ((fechaInicio.getYear() == numero_anio && fechaInicio.getMonthValue() <= numero_mes));
    }

    private static LocalDate obtenerFechaDesdeCadena(String fechaStr) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("ddMMyyyy");
        return LocalDate.parse(fechaStr, formato);
    }

    public ArrayList<String> getListaAdvertencia() {
        return listaAdvertencia;

    }

    /**
     * Agrega un error a la lista de errores con información sobre el número de
     * línea y el mensaje de error.
     *
     * @param mensaje El mensaje de error.
     */
    protected void agregarError(String mensaje, String referencia) {
        listaErrores.add("ERROR Fechas:" + obtenerFechaArchivo() + "   *Linea:" + nLinea + " " + mensaje + "\t" + referencia);
    }

    protected void agregarAdvertencia(String mensaje, String referencia) {
        listaAdvertencia.add(obtenerFechaArchivo() + "   *Linea:" + nLinea + " " + mensaje + "\t" + referencia);
    }

    /**
     * Obtiene la línea de datos actual.
     *
     * @return La línea de datos.
     */
    public String getLinea() {
        return linea;
    }

    /**
     * Establece el número de línea actual.
     *
     * @param nLinea El número de línea.
     */
    public void setnLinea(int nLinea) {
        this.nLinea = nLinea;
    }

    /**
     * Obtiene el número de línea actual.
     *
     * @return El número de línea.
     */
    public int getnLinea() {
        return nLinea;
    }

    /**
     * Obtiene la lista de errores detectados.
     *
     * @return Una lista de errores.
     */
    public ArrayList<String> getListaErrores() {
        return listaErrores;
    }

    public Double getImporte() {
        return importe;
    }

    private String obtenerFechaArchivo() {
        String fechaArchivo = linea.substring(31, 47);
        return fechaArchivo.substring(0, 2)
                + "/" + fechaArchivo.substring(2, 4)
                + "/" + fechaArchivo.substring(4, 8)
                + "-" + fechaArchivo.substring(8, 10)
                + "/" + fechaArchivo.substring(10, 12)
                + "/" + fechaArchivo.substring(12, 16);
    }

//    protected boolean tieneBasura() {
//
//        String lineaSinEspacios = getLinea().replace(" ", "");
//
//        if (!esNumerico(lineaSinEspacios)) {
//            System.out.println("LINEA CON CARACTERES OCULTOS:");
//            System.out.println(getLinea());
//
//            // Obtener y mostrar los caracteres no numéricos
//            System.out.print("Caracteres no numéricos: ");
//            for (char caracter : getCaracteresNoNumericos(lineaSinEspacios)) {
//                System.out.print(caracter + " ");
//                agregarError("Contiene caracter ocultos: " + caracter, getLinea());
//
//            }
//            System.out.println();
//
//            return true;
//        }
//        return false;
//    }
    private List<Character> getCaracteresNoNumericos(String texto) {
        List<Character> caracteresNoNumericos = new ArrayList<>();

        for (char caracter : texto.toCharArray()) {
            if (!Character.isDigit(caracter)) {
                caracteresNoNumericos.add(caracter);
            }
        }

        return caracteresNoNumericos;
    }

    protected boolean tieneConceptoAsociado(String concepto, String tipo_archivo) {
        List<String> conceptosEncontrados = new ArrayList<>();

        try (PreparedStatement pstmt = DataBase.getInstance(true).getPreparedStatement(consulta_complementaria_obtenerTodosLosConceptos)) {
            int id = Utilidades.obtenerIdMutual(this.nombreMutual);
            pstmt.setInt(1, id);
            pstmt.setString(2, tipo_archivo);

            try (ResultSet result = pstmt.executeQuery()) {
                while (result.next()) { // Mover el cursor al primer resultado
                    String conceptoResultado = result.getString("concepto1");
                    String concepto2Resultado = result.getString("concepto2");
                    conceptosEncontrados.add(concepto2Resultado);
                    conceptosEncontrados.add(conceptoResultado);
                }
            }

            // Compara si el concepto pasado como parámetro está contenido en los resultados obtenidos
            if (conceptosEncontrados.contains(concepto)) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Verifica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

}

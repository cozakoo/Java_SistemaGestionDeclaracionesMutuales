package com.mycompany.mutuales;

import static interfaz.IConsultaSql.consulta_complementaria_obtenerTodosLosConceptos;
import static interfaz.IConsultaSql.consulta_obtenerTodosLosConceptos1;
import static interfaz.IConsultaSql.consulta_obtenerTodosLosConceptos2;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La clase abstracta Archivo proporciona una estructura base para procesar
 * archivos y verificar su contenido en busca de errores. Las clases que heredan
 * de esta clase deben implementar el método abstracto leerYVerificarContenido
 * para realizar la lectura y verificación específica del archivo.
 */
public class Archivo {

    private String rutaArchivo; // Ruta del archivo a procesar
    private ArrayList<String> listaTotalErrores = new ArrayList<>(); // Lista de errores encontrados en el archivo
    private int totalRegistros;
    private double totalImporte;
    private int contadorLongitudIncorrecta = 0;
    private String nombreMutual;
    private int numero_mes;
    private int numero_anio;

    private boolean es_concepto1 = false;
    private boolean es_concepto2 = false;
    
    private int conceptoPresentado = 0;
    /**
     * Constructor de la clase Archivo.
     *
     * @param rutaArchivo La ruta del archivo que se va a procesar.
     */
    
    
    public Archivo(String rutaArchivo, String nombreMutual, int numero_mes, int numero_anio) {
        this.rutaArchivo = rutaArchivo;
        this.nombreMutual = nombreMutual;
        this.numero_mes = numero_mes;
        this.numero_anio = numero_anio;
        this.leerYVerificarContenido();
    }

    /**
     * Obtiene la ruta del archivo que se está procesando.
     *
     * @return La ruta del archivo.
     */
    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public double getTotalImporte() {
        return totalImporte;
    }

    public int getConceptoPresentado() {
        return conceptoPresentado;
    }

    public void setConceptoPresentado(int conceptoPresentado) {
        this.conceptoPresentado = conceptoPresentado;
    }

       
    
    public boolean getEsConcepto1() {
        return es_concepto1;
    }

    public boolean getEsConcepto2() {
        return es_concepto1;
    }

    public void setEs_concepto2(boolean es_concepto2) {
        this.es_concepto2 = es_concepto2;
    }

    public void setEs_concepto1(boolean es_concepto1) {
        this.es_concepto1 = es_concepto1;
    }

    public int getNumero_anio() {
        return numero_anio;
    }

    public String getNombreMutual() {
        return nombreMutual;
    }

    public int getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(int totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    /**
     * Obtiene la lista de errores totales encontrados en el archivo.
     *
     * @return La lista de errores.
     */
    public ArrayList<String> getListaTotalErrores() {
        return listaTotalErrores;
    }

    public void setTotalImporte(double totalImporte) {
        this.totalImporte = totalImporte;
    }

    /**
     * Lista todos los errores encontrados en el archivo, imprimiéndolos en la
     * consola.
     */
    public void listarErrores() {
        for (String error : this.listaTotalErrores) {
            System.out.println(error);
        }
    }

    /**
     * Agrega una lista de errores de línea a la lista total de errores.
     *
     * @param erroresLinea La lista de errores de línea a agregar.
     */
    protected void agregarError(ArrayList<String> erroresLinea) {
        this.listaTotalErrores.addAll(erroresLinea);
        for (String error : erroresLinea) {
            if (error.toLowerCase().contains("longitud")) {
                contadorLongitudIncorrecta++;
            }
        }
    }

    public int getContadorLongitudIncorrecta() {
        return contadorLongitudIncorrecta;
    }

    /**
     * Imprime una línea del archivo junto con su número de línea en la consola.
     *
     * @param nLinea El número de línea.
     * @param linea La línea del archivo a imprimir.
     */
    protected void imprimirLinea(int nLinea, String linea) {
        System.out.println("Línea " + nLinea + ": " + linea);
    }

    /**
     * Este método abstracto debe ser implementado por las clases hijas para
     * realizar la lectura y verificación específica del contenido del archivo.
     */
    protected void leerYVerificarContenido() {
    }

    public int getNumero_mes() {
        return numero_mes;
    }

    public void obtenerConceptoPresentado(String linea) {
        String concepto = linea.substring(16, 20);

        try {
            if (esConcepto1(concepto)) {
                setEs_concepto1(true);
            } else {
                if (esConcepto2(concepto)) {
                    setEs_concepto2(true);
                }
            }
            
            setConceptoPresentado(Integer.parseInt(concepto));
        } catch (SQLException ex) {
            Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean esConcepto1(String concepto) throws SQLException {
        try (PreparedStatement pstmt = DataBase.getInstance(true).getPreparedStatement(consulta_obtenerTodosLosConceptos1)) {
            pstmt.setInt(1, Integer.parseInt(concepto));

            try (ResultSet result = pstmt.executeQuery()) {
                while (result.next()) { // Mover el cursor al primer resultado
                    int count = result.getInt(1); // Obtener el valor de la columna "count"
                    return count > 0; // Si count es mayor que 0, significa que el concepto existe en la tabla y devolvemos true
                }
            }
        }
        return false; // Si no se encontró ningún resultado en la consulta, devolvemos false
    }

    public boolean esConcepto2(String concepto) throws SQLException {
        try (PreparedStatement pstmt = DataBase.getInstance(true).getPreparedStatement(consulta_obtenerTodosLosConceptos2)) {
            pstmt.setInt(1, Integer.parseInt(concepto));

            try (ResultSet result = pstmt.executeQuery()) {
                while (result.next()) { // Mover el cursor al primer resultado
                    int count = result.getInt(1); // Obtener el valor de la columna "count"
                    return count > 0; // Si count es mayor que 0, significa que el concepto existe en la tabla y devolvemos true
                }
            }
        }
        return false; // Si no se encontró ningún resultado en la consulta, devolvemos false
    }
}

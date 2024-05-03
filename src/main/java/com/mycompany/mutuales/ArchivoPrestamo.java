package com.mycompany.mutuales;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * La clase ArchivoPrestamo es una implementación concreta de la clase Archivo
 * que se especializa en la lectura y verificación de archivos de préstamos.
 */
public class ArchivoPrestamo extends Archivo {

    /**
     * Constructor de la clase ArchivoPrestamo.
     *
     * @param rutaArchivo La ruta del archivo de préstamos que se va a procesar.
     */
    public ArchivoPrestamo(String rutaArchivo, String nombreMutual, int numero_mes, int numero_nio) {
        super(rutaArchivo, nombreMutual, numero_mes, numero_nio);
    }

    /**
     * Lee y verifica el contenido del archivo de préstamos. Este método se
     * encarga de abrir el archivo, leer línea por línea, realizar la
     * verificación de cada línea y agregar los errores encontrados a la lista
     * total de errores.
     */
    @Override
    protected void leerYVerificarContenido() {
        double importeParcial = leerArchivosYCalcularImporte();
        if (getListaTotalErrores().isEmpty()) {
            setTotalImporte(importeParcial);
        }
        System.out.println("......TERMINÉ DE LEER LOS PRÉSTAMOS\n");
    }

    private double leerArchivosYCalcularImporte() {
        double importeParcial = 0;
        boolean cargoImporte = true;
        String nombreMutual = getNombreMutual();
        try (BufferedReader br = new BufferedReader(new FileReader(getRutaArchivo()))) {
            String linea;
            int nLinea = 0;
            System.out.println("\nLEYENDO PRÉSTAMOS......");
            
            while ((linea = br.readLine()) != null) {
                nLinea++;
                
                if (nLinea == 1){
                    obtenerConceptoPresentado(linea);
                }
                                
                System.out.println(linea);
//                imprimirLinea(nLinea, linea);
                Verifica vp = new Verifica(linea, nombreMutual);
                ArrayList<String> erroresLinea = vp.obtenerErrores(nLinea, getNumero_mes(), getNumero_anio());
                ArrayList<String> advertenciaLinea = vp.getListaAdvertencia();

                if (!erroresLinea.isEmpty()) {
                    agregarError(erroresLinea);
                    cargoImporte = false;
                } else {
                    if (cargoImporte) {
                        importeParcial += vp.getImporte();
                    }
                }
            }
            setTotalRegistros(nLinea);
        } catch (IOException e) {
            System.out.println("ERROR al leer el préstamo: " + e.getMessage());
        }
        return importeParcial;
    }

}

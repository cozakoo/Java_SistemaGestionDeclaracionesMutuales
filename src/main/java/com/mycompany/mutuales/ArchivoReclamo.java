package com.mycompany.mutuales;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * La clase ArchivoReclamo es una implementación concreta de la clase Archivo
 * que se especializa en la lectura y verificación de archivos de reclamos.
 */
public class ArchivoReclamo extends Archivo {

    /**
     * Constructor de la clase ArchivoReclamo.
     *
     * @param rutaArchivo La ruta del archivo de reclamos que se va a procesar.
     */
    public ArchivoReclamo(String rutaArchivo, String nombreMutual, int numero_mes, int numero_nio) {
        super(rutaArchivo, nombreMutual, numero_mes, numero_nio);
    }

    /**
     * Lee y verifica el contenido del archivo de reclamos. Este método se
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
        System.out.println("......TERMINÉ DE LEER LOS RECLAMOS\n");
    }

    private double leerArchivosYCalcularImporte() {
        double importeParcial = 0;
        boolean cargoImporte = true;
        String nombreMutual = getNombreMutual();

        try (BufferedReader br = new BufferedReader(new FileReader(getRutaArchivo()))) {
            String linea;
            int nLinea = 0;
            System.out.println("\nLEYENDO RECLAMO......");

            while ((linea = br.readLine()) != null) {
                nLinea++;

                if (nLinea == 1){
                    obtenerConceptoPresentado(linea);
                }

                imprimirLinea(nLinea, linea);
                
                VerificaReclamo vr = new VerificaReclamo(linea, nombreMutual);
                ArrayList<String> erroresLinea = vr.obtenerErrores(nLinea, getNumero_mes(), getNumero_anio());
                
                
                if (!erroresLinea.isEmpty()) {
                    agregarError(erroresLinea);
                    cargoImporte = false;
                } else {
                    if (cargoImporte) {
                        importeParcial += vr.getImporte();
                    }
                }
            }

            setTotalRegistros(nLinea);
        } catch (IOException e) {
            System.out.println("ERROR al leer el reclamo: " + e.getMessage());

        }
        return importeParcial;
    }


}

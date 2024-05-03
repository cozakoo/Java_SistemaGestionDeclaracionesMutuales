package com.mycompany.mutuales;

import java.util.ArrayList;

/**
 * La clase `VerificaReclamo` se utiliza para realizar verificaciones en líneas
 * de reclamo en un archivo específico. Hereda de la clase `Verifica` y agrega
 * verificaciones adicionales específicas para líneas de reclamo.
 */
public class VerificaReclamo extends Verifica {

    /**
     * Constructor de la clase `VerificaReclamo`. Inicializa la instancia de la
     * clase con una línea de reclamo dada y una longitud específica de 57
     * caracteres.
     *
     * @param linea La línea de reclamo que se verificará.
     */
    public VerificaReclamo(String linea, String nombreMutual) {
        super(linea, 57, nombreMutual);
    }

    /**
     * Realiza verificaciones en la línea de reclamo y genera una lista de
     * errores si se encuentran problemas en la línea. Además de las
     * verificaciones heredadas de la clase padre, esta función verifica la
     * cuota.
     *
     * @param nLinea El número de línea actual que se está verificando.
     * @return Una lista de errores encontrados en la línea de reclamo.
     */
    @Override
    public ArrayList<String> obtenerErrores(int nLinea, int numero_mes, int numero_anio) {
        setnLinea(nLinea);

//         if (!tieneBasura()) {
        if (verificarLongitud()) {

            verificarDocumento(getLinea().substring(3, 16));
            verificarConcepto(getLinea().substring(16, 20));
            verificarImporte(getLinea().substring(20, 31));
            verificarFechaInicio(getLinea().substring(31, 39), numero_mes, numero_anio);
            verificarFechaFin(getLinea().substring(39, 47));
            verificarCupon(getLinea().substring(47, 54));
            verificarCuota(getLinea().substring(54, 57));
        }
//         }
        return getListaErrores();
    }

    @Override
    protected void verificarFechaInicio(String fechaInicio, int numero_mes, int numero_anio) {

        if (!esNumerico(fechaInicio)) {
            agregarError("Fecha Inicio contiene caracteres no numéricos. formato DDMMAAA", fechaInicio);
        } else if (!esFormatoFecha(fechaInicio)) {
            agregarError("Fecha Inicio no válida en formato DDMMAAA.", fechaInicio);
        }
    }

    /**
     * Verifica si el campo de cuota en la línea de reclamo está en blanco. Si
     * es así, se agrega un error a la lista de errores.
     *
     * @param cuota El campo de cuota en la línea de reclamo.
     */
    private void verificarCuota(String cuota) {
        if (cuota.equals("   ")) {
            agregarError("Cuota en blanco.", cuota);
        }
    }

    @Override
    protected void verificarConcepto(String concepto) {

        if (!esNumerico(concepto)) {
            agregarError("Concepto contiene caracteres no numéricos.", concepto);
        }
        if (!tieneConceptoAsociado(concepto, "R")) {
            agregarError("El concepto no esta asociado a la mutual o al reclamo.", concepto);
        }
    }

}

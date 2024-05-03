package com.mycompany.mutuales;

import java.io.BufferedWriter;
import java.io.IOException;
import javax.swing.JTable;

public class Txt {

    private String 
            titulo, 
            mutual_asociacion, 
            mes_control, 
            tipoArchivo, 
            nombreArchivo, 
            errores, 
            archivoCompleto;

    public Txt(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public void agregarCabecera(String mutual, String mes, String tipoArchivo) {
        this.titulo
                = "               CONTROL DE ARCHIVOS";
        this.mutual_asociacion
                = "Mutual/Asociacion        " + mutual.replace(" ", "");
        this.mes_control
                = "Mes de Control             " + mes;
        this.tipoArchivo
                = "Tipo de archivo            " + tipoArchivo;
    }

    public void agregarErrores(String errores) {
        this.errores = errores;
        concatenarArchivo();
    }

    private void concatenarArchivo() {
        this.archivoCompleto
                = this.titulo + "\n"
                + this.mutual_asociacion + "\n"
                + this.mes_control + "\n"
                + this.tipoArchivo + "\n\n"
                + this.errores;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public String getArchivoCompleto() {
        return archivoCompleto;
    }
    
   public String generarLongitud(String textoFijo, int  longitudDeseada){
        while (textoFijo.length() < longitudDeseada) {
            textoFijo += " ";
        }
        return textoFijo;
   }

    public void escribirEncabezados(BufferedWriter writer, JTable jTableIInforme) throws IOException {
        for (int i = 0; i < jTableIInforme.getColumnCount(); i++) {
            String columnName = jTableIInforme.getColumnName(i);

            switch (i) {
                case 0:
                case 1:
                    writer.write(columnName + "  ");
                    break;

                case 2:
                    writer.write(generarLongitud(columnName, 30));
                   
                    break;
                case 3:
                    writer.write(generarLongitud(columnName, 20));
                    break;
                case 4:
                    writer.write(columnName);
                    break;
                default:
                    // Manejar otras columnas si es necesario
                    break;
            }
        }

        writer.newLine();
        writer.write("------------------------------------------------------------------------");
        writer.newLine();
    }

    public void escribirDatos(BufferedWriter writer, JTable jTableIInforme) throws IOException {
        for (int i = 0; i < jTableIInforme.getRowCount(); i++) {
            for (int j = 0; j < jTableIInforme.getColumnCount(); j++) {
                String valor = jTableIInforme.getValueAt(i, j).toString();
                switch (j) {
                    case 0:
                        writer.write(valor + "\t");
                        break;
                    case 1:
                        writer.write(valor + "\t");
                        break;
                    case 2:
                        writer.write(generarLongitud(valor, 30));
                    break;
                    case 3:
                        writer.write(valor + "\t");
                        break;
                    case 4:
                        writer.write(valor);
                        break;
                    default:
                        // Manejar otras columnas si es necesario
                        break;
                }
            }
            writer.newLine();
        }
    }

}

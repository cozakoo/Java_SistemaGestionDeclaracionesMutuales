package com.mycompany.mutuales;

import interfaz.IConsultaSql;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author dgc06
 */
public class Excel {
    
     public void generarCamposSql() throws SQLException, FileNotFoundException, IOException{
        
        
            ResultSet resultSet = DataBase.getInstance(true).consulta(IConsultaSql.consulta_infome_Listado);

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Datos");

            // Agregar encabezados
            Row headerRow = sheet.createRow(0);
            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                Cell cell = headerRow.createCell(i - 1);
                cell.setCellValue(resultSet.getMetaData().getColumnName(i));
            }

            // Agregar datos
            int rowNum = 1;
            while (resultSet.next()) {
                Row dataRow = sheet.createRow(rowNum++);
                for (int i = 1; i <= columnCount; i++) {
                    Cell cell = dataRow.createCell(i - 1);
                    cell.setCellValue(resultSet.getString(i));
                }
            }

            // Guardar el libro de trabajo en un archivo
            try (FileOutputStream fileOut = new FileOutputStream("datos.xlsx")) {
                workbook.write(fileOut);
 
                System.out.println("Archivo Excel generado correctamente.");
            }
        } 
    
     public void Imprimir(){
         
     }
}

package com.mycompany.mutuales;

import static interfaz.IConsultaSql.consulta_infome_Listado;
import static interfaz.IConsultaSql.consulta_informe_alta;
import static interfaz.IConsultaSql.consulta_informe_existe_condicion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Informe {

    private int codigo, concepto;
    private String nombreMutual, archivo, tipo, fecha;

    public Informe(int codigo, int concepto, String nombreMutual, 
            String archivo, String tipo, String fecha) {
        this.codigo = codigo;
        this.concepto = concepto;
        this.nombreMutual = nombreMutual;
        this.archivo = archivo;
        this.tipo = tipo;
        this.fecha = fecha;
    }

    public void insertarEnBaseDeDatos() {

        try {
            PreparedStatement pstmt;
            pstmt = DataBase.getInstance(true).
                    getPreparedStatement(consulta_informe_alta);
            pstmt.setInt(1, this.codigo);
            pstmt.setInt(2, this.concepto);
            pstmt.setString(3, this.nombreMutual);
            pstmt.setString(4, this.archivo);
            pstmt.setString(5, this.tipo);
            pstmt.setString(6, this.fecha);
            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException ex) {
            System.err.println(ex);
            Utilidades.mensajeError("Error al insertar Complementaria en la base de datos");
        }

    }

    public boolean existe() throws SQLException {
        PreparedStatement pstmt = DataBase.getInstance(true).getPreparedStatement(consulta_infome_Listado + consulta_informe_existe_condicion);
            pstmt.setInt(1, this.codigo);
            pstmt.setString(2, this.archivo);
            pstmt.setString(3, this.tipo);
            pstmt.setString(4, this.fecha);

            ResultSet rs = pstmt.executeQuery();
            return rs.next();
          
            
        }

}

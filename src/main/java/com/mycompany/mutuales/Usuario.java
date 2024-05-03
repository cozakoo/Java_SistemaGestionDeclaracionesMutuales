package com.mycompany.mutuales;

import enumeradores.RolUsuario;

import static interfaz.IConsultaSql.consulta_usuario_alta;
import static interfaz.IConsultaSql.consulta_usuario_existe;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import org.mindrot.jbcrypt.BCrypt;

public class Usuario {

    RolUsuario rol;
    private String nombre, contasenia;

    public Usuario(String nombre, String contasenia) {
        this.nombre = nombre;
        this.contasenia = contasenia;
    }

    public Usuario(String nombre, String contasenia, RolUsuario rol) {
        this.nombre = nombre;
        this.contasenia = contasenia;
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public String getContasenia() {
        return contasenia;
    }

    public void insertarEnBaseDeDatos() {
        try {
            if (!existeUsuario()) {
                String sql = consulta_usuario_alta;
                PreparedStatement pstmt = DataBase.getInstance(true).getPreparedStatement(sql);
                pstmt.setString(1, this.nombre);

                // Encripta la contraseña antes de almacenarla
                String hashedPassword = BCrypt.hashpw(this.contasenia, BCrypt.gensalt());
                pstmt.setString(2, hashedPassword);

                if (this.rol == RolUsuario.administrador) {
                    pstmt.setBoolean(3, true);
                } else {
                    pstmt.setBoolean(3, false);
                }
                pstmt.setTimestamp(4, Utilidades.obtenerFechaActual());

                pstmt.executeUpdate();
                pstmt.close();
            } else {
                Utilidades.mensajeError("Usuario ya existe");
            }
        } catch (SQLException ex) {
            Utilidades.mensajeError("Error al insertar el usuario en la base de datos");
        }
    }

    private boolean existeUsuario() {
        boolean existe = false;
        try {
            PreparedStatement pstmt = DataBase.getInstance(true).getPreparedStatement(consulta_usuario_existe);
            pstmt.setString(1, this.nombre);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                existe = count > 0;
            }
            pstmt.close();
        } catch (SQLException ex) {
            
            Utilidades.mensajeError("Error al verificar la existencia del usuario:");
        }
        return existe;
        
    }

}

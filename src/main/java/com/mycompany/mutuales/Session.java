package com.mycompany.mutuales;

import enumeradores.RolUsuario;

public class Session {
    
    private static Session instance;
    private static RolUsuario rol;
    private static String userName;

    public Session(String username, RolUsuario rol) {
     Session.rol = rol;
     Session.userName =  username;
    }
    
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session(userName, rol);
        }
        return instance;
    }

    public static RolUsuario getRol() {
        return rol;
    }

    public static String getUserName() {
        return userName;
    }
}

package com.tenpines.tusLibros.web.TransferObjects;

/**
 * Created by Aye on 29/09/16.
 */
public class UsuarioPasswordTO {

    public Long idUsuario;
    public String password;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public UsuarioPasswordTO(Long idUsuario, String password){
        setIdUsuario(idUsuario);
        setPassword(password);
    }

}

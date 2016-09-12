package com.tenpines;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by pino on 12/09/16.
 */

@Entity
@Table(name="cliente")
public class Cliente {

    @Id
    @Column("client_id")
    private Long cliente_id;

    public Long getClienteId(){
        return cliente_id;

    }

    public void setClientId(Long cliente_id){
        this.cliente_id = cliente_id;
    }

    @Column(name="password")
    private String password;

    public String getPassword(){
        return password;
    }

    public void setPassword(String a_password){
        this.password = a_password;
    }


}

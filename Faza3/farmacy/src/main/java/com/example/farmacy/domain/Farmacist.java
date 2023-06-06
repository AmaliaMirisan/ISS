package com.example.farmacy.domain;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name = "farmacisti")
public class Farmacist implements com.example.farmacy.domain.Entity<Integer>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    private String username;
    private String password;

    public Farmacist(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public Farmacist() {
    }
    @Override
    public Integer getId(){
        return id;
    }

    @Override
    public void setId(Integer id){
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package com.example.farmacy.domain;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "personal")
public class PersonalMedical implements com.example.farmacy.domain.Entity<Integer>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    private String username;
    private String password;
    @Column(name = "sectie")
    @Enumerated(EnumType.STRING)
    private Sectie sectie;
    public PersonalMedical() {
    }
    public PersonalMedical(String username, String password, Sectie sectie) {
        this.username = username;
        this.password = password;
        this.sectie =sectie;
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

    public Sectie getSectie() {
        return sectie;
    }

    public void setSectie(Sectie sectie) {
        this.sectie = sectie;
    }
}
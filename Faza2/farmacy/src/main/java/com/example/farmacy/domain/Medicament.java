package com.example.farmacy.domain;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name = "medicamente")
public class Medicament implements com.example.farmacy.domain.Entity<Integer>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    private String denumire;
    private Integer cantitate;
    public Medicament(String denumire, Integer cantitate) {
        this.denumire = denumire;
        this.cantitate =cantitate;
    }
    public Medicament() {

    }
    @Override
    public Integer getId(){
        return id;
    }

    @Override
    public void setId(Integer id){
        this.id = id;
    }
    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public Integer getCantitate() {
        return cantitate;
    }

    public void setCantitate(Integer cantitate) {
        this.cantitate = cantitate;
    }

}

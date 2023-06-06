package com.example.farmacy.domain;
import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "comenzi")
public class Comanda implements com.example.farmacy.domain.Entity<Integer>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_personal_medical")
    private PersonalMedical personalMedical;
    @ManyToOne
    @JoinColumn(name = "id_farmacist")
    private Farmacist farmacist;
    @ManyToOne
    @JoinColumn(name = "id_medicament")
    private Medicament medicament;
    private int cantitate;
    private String data;

    public Sectie getSectie() {
        return sectie;
    }

    public void setSectie(Sectie sectie) {
        this.sectie = sectie;
    }
    @Column(name = "sectie")
    @Enumerated(EnumType.STRING)
    private Sectie sectie;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    public Comanda() {
    }
    public Comanda(int idPersonal, int idFarmacist, int idMedicament, int cantitate, String data, Sectie sectie, Status status) {
        this.personalMedical=new PersonalMedical();
        personalMedical.setId(idPersonal);
        this.farmacist=new Farmacist();
        farmacist.setId(idFarmacist);
        this.medicament=new Medicament();
        medicament.setId(idMedicament);
        this.cantitate =cantitate;
        this.data =data;
        this.sectie=sectie;
        this.status=status;
    }
    @Override
    public Integer getId(){
        return id;
    }

    @Override
    public void setId(Integer id){
        this.id = id;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public PersonalMedical getPersonalMedical() {
        return personalMedical;
    }

    public void setPersonalMedical(PersonalMedical medicalStaff) {
        this.personalMedical = medicalStaff;
    }

    public Farmacist getFarmacist() {
        return farmacist;
    }

    public void setFarmacist(Farmacist farmacist) {
        this.farmacist = farmacist;
    }

    public Medicament getMedicament() {
        return medicament;
    }

    public void setMedicament(Medicament medicine) {
        this.medicament = medicine;
    }
}
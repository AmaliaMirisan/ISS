package com.example.farmacy.service;

import com.example.farmacy.domain.*;
import com.example.farmacy.repository.*;
import javafx.collections.ObservableList;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Service{
    private static Service instance;
    private final RepoDbManager managerRepo;
    private final RepoDbFarmacist farmacistRepo;
    private final RepoDbPersonalMedical personalMedicalRepo;
    private final RepoDbMedicament medicamenteRepo;
    private final RepoDbComanda comandaRepo;


    public Service(RepoDbManager managerRepo,
                   RepoDbFarmacist farmacisttRepo,
                   RepoDbPersonalMedical personalMedicalRepo,
                   RepoDbMedicament medicamenteRepo,
                   RepoDbComanda comandaRepo)
    {
        this.managerRepo =managerRepo;
        this.farmacistRepo =farmacisttRepo;
        this.personalMedicalRepo =personalMedicalRepo;
        this.medicamenteRepo=medicamenteRepo;
        this.comandaRepo =comandaRepo;
    }
    public static Service getInstance(SessionFactory sessionFactory){
        if(instance == null){
            instance = new Service(new RepoDbManager(sessionFactory),
                    new RepoDbFarmacist(sessionFactory),
                    new RepoDbPersonalMedical(sessionFactory),
                    new RepoDbMedicament(sessionFactory),
                    new RepoDbComanda(sessionFactory));
        }
        return instance;
    }

    public Iterable<Manager> findAllManagers() {
        return managerRepo.findAll();
    }

    public Object loginManager(Manager manager) throws Exception {
        Object managerr = null;
        try{
            managerr = managerRepo.autentificareManager(manager.getUsername(), manager.getPassword());
        } catch(Exception ex){
            throw ex;
        }
        return managerr;
    }
    public Object loginFarmacist(Farmacist farmacist) throws Exception {
        Object farmacistt = null;
        try{
            farmacistt = farmacistRepo.autentificareFarmacist(farmacist.getUsername(), farmacist.getPassword());
        } catch(Exception ex){
            throw ex;
        }
        return farmacistt;
    }
    public Object loginPersonalMedical(PersonalMedical personalMedical) throws Exception {
        Object personalMedicall = null;
        try{
            personalMedicall = personalMedicalRepo.autentificarePersonalMedical(personalMedical.getUsername(), personalMedical.getPassword());
        } catch(Exception ex){
            throw ex;
        }
        return personalMedicall;}
   /* public PersonalMedical loginPersonalMedical(PersonalMedical personalMedical) throws Exception {
        PersonalMedical loggedPersonal = null;
        try {
            loggedPersonal = (PersonalMedical) personalMedicalRepo.autentificarePersonalMedical(personalMedical.getUsername(), personalMedical.getPassword());
        } catch (Exception ex) {
            throw ex;
        }
        return loggedPersonal;
    }*/


    //-------------------------------MEDICAMENTE-------------------------------//
    public Iterable<Medicament> findAllMedicamente() {
    return medicamenteRepo.findAll();
}

    public void addMedicament(String denumire, Integer cantitate) throws RepoException {
    Medicament med = new Medicament(denumire,cantitate);
    medicamenteRepo.add(med);
}
    public void updateMedicament(Medicament med) throws RepoException{
        medicamenteRepo.update(med);
    }
    public void deleteMedicament(Medicament med) throws RepoException {
        medicamenteRepo.delete(med.getId());

    }


//-------------------------------FARMACISTI-------------------------------//

    public Iterable<Farmacist> findAllFarmacisti() {
        return farmacistRepo.findAll();
    }

    public void addFarmacist(String username, String password) throws RepoException {
        Farmacist f = new Farmacist(username,password);
        farmacistRepo.add(f);
    }
    public void updateFarmacist(Farmacist f) throws RepoException{
        farmacistRepo.update(f);
    }
    public void deleteFarmacist(Farmacist f) throws RepoException {
        farmacistRepo.delete(f.getId());
    }

//-------------------------------COMENZI-------------------------------//
    public Iterable<Comanda> findAllComenzi() {
    return comandaRepo.findAll();
}
    public Iterable<Comanda> getComenziNeonorate()    {
        List<Comanda> comenzi = (List<Comanda>) comandaRepo.findAll();
        List<Comanda> comenzi_filtrate = new ArrayList<>();
        Status verif = Status.NEONORATA;
        comenzi_filtrate = comenzi.stream()
                .filter(comanda -> {
                    try {
                        return comanda.getStatus().equals(verif) ;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
        return comenzi_filtrate;
    }
    public Iterable<Comanda> getComenziSectie(PersonalMedical personalMedical)    {
        List<Comanda> comenzi = (List<Comanda>) comandaRepo.findAll();
        List<Comanda> comenzi_filtrate = new ArrayList<>();
        Sectie verif = personalMedical.getSectie();
        comenzi_filtrate = comenzi.stream()
                .filter(comanda -> {
                    try {
                        return comanda.getSectie().equals(verif) ;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
        return comenzi_filtrate;
    }
    public Iterable<Comanda> filtreazaComenzi(ObservableList<Comanda> listaComenziNeonorate)    {
        List<Comanda> comenzi = new ArrayList<>(listaComenziNeonorate);
        List<Comanda> comenziFiltrate = new ArrayList<>();

        // Sorteaza comenzile în funcție de data sosirii
        comenzi.sort(Comparator.comparing(Comanda::getData));

        for (Comanda comanda : comenzi) {
            // Adauga comenzile in lista filtrata
            comenziFiltrate.add(comanda);
        }

        return comenziFiltrate;
    }
    public void updateStatus(Comanda comanda) throws RepoException {
        comandaRepo.update(comanda);
    }

    public void addComanda(Comanda c) throws RepoException {
        comandaRepo.add(c);
    }


//-------------------------------PERSONAL-------------------------------//
    public Iterable<PersonalMedical> findAllPersonalMedical() {
    return personalMedicalRepo.findAll();
}
    public void addPersonalMedical(String username, String password, Sectie sectie) throws RepoException {
        PersonalMedical p = new PersonalMedical(username,password,sectie);
        personalMedicalRepo.add(p);
    }
    public void updatePersonalMedical(PersonalMedical p) throws RepoException{
        personalMedicalRepo.update(p);
    }
    public void deletePersonalMedical(PersonalMedical p) throws RepoException {
        personalMedicalRepo.delete(p.getId());
    }

}

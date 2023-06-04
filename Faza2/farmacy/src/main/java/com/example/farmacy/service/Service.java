package com.example.farmacy.service;

import com.example.farmacy.domain.*;
import com.example.farmacy.repository.*;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
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
    public Iterable<Farmacist> findAllFarmacisti() {
        return farmacistRepo.findAll();
    }
    public Iterable<PersonalMedical> findAllPersonalMedical() {
        return personalMedicalRepo.findAll();
    }
    public Iterable<Medicament> findAllMedicamente() {
        return medicamenteRepo.findAll();
    }
    public Iterable<Comanda> findAllComenzi() {
        return comandaRepo.findAll();
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
}

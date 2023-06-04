package com.example.farmacy.repository;

import com.example.farmacy.domain.Medicament;
import com.example.farmacy.domain.PersonalMedical;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class RepoDbMedicament implements RepoMedicament {
    private final SessionFactory sessionFactory;
    public RepoDbMedicament(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Medicament entity) throws RepoException {

    }

    @Override
    public void delete(Integer integer) throws RepoException {

    }

    @Override
    public Medicament findOne(Integer integer) throws RepoException {
        return null;
    }

    @Override
    public Iterable<Medicament> findAll() {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Iterable<Medicament> meds = session.createQuery("from Medicament", Medicament.class).list();
            session.getTransaction().commit();
            return meds;
        }
    }
}

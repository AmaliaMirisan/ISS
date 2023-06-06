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
        try (Session session = sessionFactory.openSession()) {
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
    }

    }
    @Override
    public void update(Medicament entity) throws RepoException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Medicament existingMedicament = session.get(Medicament.class, entity.getId());

            if (existingMedicament != null) {
                existingMedicament.setDenumire(entity.getDenumire());
                existingMedicament.setCantitate(entity.getCantitate());

                session.update(existingMedicament);
            }
            session.getTransaction().commit();
        }
    }
    @Override
    public void delete(Integer id) throws RepoException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Medicament medicament = session.get(Medicament.class, id);

            if (medicament != null) {
                session.delete(medicament);
            } else {
                throw new RepoException("Medicamentul cu ID-ul specificat nu exista!");
            }

            session.getTransaction().commit();
        }
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

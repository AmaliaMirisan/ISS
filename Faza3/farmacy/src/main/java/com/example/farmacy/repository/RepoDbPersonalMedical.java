package com.example.farmacy.repository;

import com.example.farmacy.domain.Medicament;
import com.example.farmacy.domain.PersonalMedical;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class RepoDbPersonalMedical implements RepoPersonalMedical {
    private final SessionFactory sessionFactory;
    public RepoDbPersonalMedical(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(PersonalMedical entity) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(PersonalMedical entity) throws RepoException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            PersonalMedical existingPersonalMedical = session.get(PersonalMedical.class, entity.getId());

            if (existingPersonalMedical != null) {
                existingPersonalMedical.setUsername(entity.getUsername());
                existingPersonalMedical.setPassword(entity.getPassword());
                existingPersonalMedical.setSectie(entity.getSectie());

                session.update(existingPersonalMedical);
            }
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Integer id) throws RepoException{
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            PersonalMedical personalMedical = session.get(PersonalMedical.class, id);

            if (personalMedical != null) {
                session.delete(personalMedical);
            } else {
                throw new RepoException("Personalul medical cu ID-ul specificat nu exista!");
            }

            session.getTransaction().commit();
        }
    }

    @Override
    public PersonalMedical findOne(Integer id) {
        return null;
    }

    @Override
    public Iterable<PersonalMedical> findAll() {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Iterable<PersonalMedical> personall = session.createQuery("from PersonalMedical", PersonalMedical.class).list();
            session.getTransaction().commit();
            return personall;
        }
    }
    public Object autentificarePersonalMedical(String username, String password) throws Exception{
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Object personalMedicall = session.createQuery("from PersonalMedical where username = :username and password = :password")
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .uniqueResult();
            session.getTransaction().commit();
            if(personalMedicall == null){
                throw new Exception("Credentiale incorecte!");
            }
            return personalMedicall;
        }
    }
}

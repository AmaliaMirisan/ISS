package com.example.farmacy.repository;

import com.example.farmacy.domain.Farmacist;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class RepoDbFarmacist implements RepoFarmacist {
    private final SessionFactory sessionFactory;

    public RepoDbFarmacist(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Farmacist entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Integer id) {
    }

    @Override
    public Farmacist findOne(Integer id) {
        return null;
    }

    @Override
    public Iterable<Farmacist> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Iterable<Farmacist> farmacisti = session.createQuery("from Farmacist", Farmacist.class).list();
            session.getTransaction().commit();
            return farmacisti;
        }
    }

    public Object autentificareFarmacist(String username, String password) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Object farmacist = session.createQuery("from Farmacist where username = :username and password = :password")
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .uniqueResult();
            session.getTransaction().commit();
            if (farmacist == null) {
                throw new Exception("Credentiale incorecte!");
            }
            return farmacist;
        }
    }
}

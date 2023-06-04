package com.example.farmacy.repository;

import com.example.farmacy.domain.Manager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class RepoDbManager implements RepoManager {
    private final SessionFactory sessionFactory;
    public RepoDbManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Manager entity) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Integer id) {
    }

    @Override
    public Manager findOne(Integer id) {
        return null;
    }

    @Override
    public Iterable<Manager> findAll() {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Iterable<Manager> users = session.createQuery("from Manager", Manager.class).list();
            session.getTransaction().commit();
            return users;
        }
    }
    public Object autentificareManager(String username, String password) throws Exception{
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Object user = session.createQuery("from Manager where username = :username and password = :password")
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .uniqueResult();
            session.getTransaction().commit();
            if (user == null) {
                throw new Exception("Credentile incorecte!");
            }
            return user;
        }
    }
}
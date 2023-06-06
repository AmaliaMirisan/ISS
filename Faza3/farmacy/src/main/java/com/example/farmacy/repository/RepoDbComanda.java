package com.example.farmacy.repository;

import com.example.farmacy.domain.Comanda;
import com.example.farmacy.domain.Medicament;
import com.example.farmacy.domain.Status;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class RepoDbComanda implements RepoComanda {
    private final SessionFactory sessionFactory;
    public RepoDbComanda(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Comanda entity) throws RepoException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Comanda entity) throws RepoException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Comanda existingComanda = session.get(Comanda.class, entity.getId());

            if (existingComanda != null) {
                existingComanda.setStatus(Status.ONORATA);

                session.update(existingComanda);
            }
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Integer integer) throws RepoException {

    }

    @Override
    public Comanda findOne(Integer integer) throws RepoException {
        return null;
    }

    @Override
    public Iterable<Comanda> findAll() {
       /* try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Iterable<Comanda> comenzi = session.createQuery("from Comanda", Comanda.class).list();
            session.getTransaction().commit();
            return comenzi;
        }*/
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Comanda> comenzi = session.createQuery("from Comanda", Comanda.class).list();
            session.getTransaction().commit();

            for (Comanda comanda : comenzi) {
                // Obține valoarea statusului din baza de date și seteaz-o în obiectul Comanda
                Status status = comanda.getStatus(); // sau folosește metoda pentru a obține statusul din altă sursă
                comanda.setStatus(status);
            }

            return comenzi;
        }
    }
}

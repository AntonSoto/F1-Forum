package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.Circuito;
import es.udc.asi.notebook_rest.model.domain.Note;
import es.udc.asi.notebook_rest.model.domain.User;
import es.udc.asi.notebook_rest.model.repository.util.GenericDaoJpa;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class CircuitoDaoJpa extends GenericDaoJpa implements CircuitoDao {

  @Override
  public void create(Circuito note) {
    entityManager.persist(note);
  }

  @Override
  public void delete(Circuito note) {
    entityManager.remove(note);
  }

  @Override
  public Collection<Circuito> findAll() {
    return entityManager.createQuery("from Circuito ", Circuito.class).getResultList();
  }

  @Override
  public Circuito findById(String id) {
    return entityManager.find(Circuito.class, id);
  }

}

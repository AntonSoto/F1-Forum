package es.udc.asi.notebook_rest.model.repository;


import es.udc.asi.notebook_rest.model.domain.Piloto;
import es.udc.asi.notebook_rest.model.domain.Note;
import es.udc.asi.notebook_rest.model.domain.User;
import es.udc.asi.notebook_rest.model.repository.util.GenericDaoJpa;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class PilotoDaoJpa extends GenericDaoJpa implements PilotoDao {

  @Override
  public void create(Piloto note) {
    entityManager.persist(note);
  }

  @Override
  public void delete(Piloto note) {
    entityManager.remove(note);
  }

  @Override
  public Collection<Piloto> findAll() {
    return entityManager.createQuery("from Piloto ", Piloto.class).getResultList();
  }

  @Override
  public Piloto findById(String id) {
    return entityManager.find(Piloto.class, id);
  }

}

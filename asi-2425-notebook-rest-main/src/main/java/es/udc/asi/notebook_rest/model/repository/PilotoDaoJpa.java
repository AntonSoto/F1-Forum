package es.udc.asi.notebook_rest.model.repository;


import es.udc.asi.notebook_rest.model.domain.Piloto;
import es.udc.asi.notebook_rest.model.domain.Note;
import es.udc.asi.notebook_rest.model.domain.User;
import es.udc.asi.notebook_rest.model.domain.Valoracion;
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
  public Collection<Piloto> findAll() {
    return entityManager.createQuery("from Piloto ", Piloto.class).getResultList();
  }

  @Override
  public Piloto findById(String id) {
    return entityManager.find(Piloto.class, id);
  }
/*
  @Override
  public Piloto findByCampeonato(Long ano) {
    return entityManager.createQuery(
        "select p from Piloto p join p. gp where gp.id = :gran_premio_id", Valoracion.class)
      .setParameter("gran_premio_id", id).getResultList();
  }
  }
*/
}

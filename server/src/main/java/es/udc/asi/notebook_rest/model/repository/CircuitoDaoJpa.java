package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.*;
import es.udc.asi.notebook_rest.model.repository.util.GenericDaoJpa;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class CircuitoDaoJpa extends GenericDaoJpa implements CircuitoDao {

  @Override
  public void create(Circuito circuito) {
    entityManager.persist(circuito);
  }

  @Override
  public void delete(Circuito circuito) {
    entityManager.remove(circuito);
  }

  @Override
  public Collection<Circuito> findAll() {
    return entityManager.createQuery("from Circuito ", Circuito.class).getResultList();
  }

  @Override
  public Circuito findById(String id) {
    return entityManager.find(Circuito.class, id);
  }

  @Override
  public void update(Circuito circuito) {
    entityManager.merge(circuito);

  }

  @Override
  public Collection<Circuito> findByAno(Long ano){
    return entityManager.createQuery(
        "select c from Circuito c join c.grandesPremios gp where gp.campeonato.ano = :gp_ano", Circuito.class)
      .setParameter("gp_ano", ano).getResultList();
  }

}

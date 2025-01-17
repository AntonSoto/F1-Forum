package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.Category;
import es.udc.asi.notebook_rest.model.domain.GranPremio;
import es.udc.asi.notebook_rest.model.repository.util.GenericDaoJpa;

import java.time.LocalDate;

public class GranPremioDaoJpa extends GenericDaoJpa implements GranPremioDao {

  @Override
  public void create(GranPremio granPremio) {
    entityManager.persist(granPremio);
  }

  /*@Override
  public GranPremio findByFechaHoraCarrera(LocalDate date) {
    return entityManager.createQuery("from GranPremio ", Category.class).getResultList();
  }*/


}

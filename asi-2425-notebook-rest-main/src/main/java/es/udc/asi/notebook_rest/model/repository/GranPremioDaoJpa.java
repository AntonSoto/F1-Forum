package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.Category;
import es.udc.asi.notebook_rest.model.domain.GranPremio;
import es.udc.asi.notebook_rest.model.domain.Note;
import es.udc.asi.notebook_rest.model.repository.util.GenericDaoJpa;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class GranPremioDaoJpa extends GenericDaoJpa implements GranPremioDao {

  @Override
  public void create(GranPremio granPremio) {
    entityManager.persist(granPremio);
  }

  @Override
  public GranPremio findById(Long id) {
    return entityManager.find(GranPremio.class, id);
  }


}

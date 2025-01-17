package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.Campeonato;
import es.udc.asi.notebook_rest.model.domain.Category;
import es.udc.asi.notebook_rest.model.repository.util.GenericDaoJpa;
import org.springframework.stereotype.Repository;

@Repository
public class CampeonatoDaoJpa extends GenericDaoJpa implements CampeonatoDao{

  public void create(Campeonato campeonato) {
    entityManager.persist(campeonato);
  }

  @Override
  public Campeonato findById(Long ano) {
    return entityManager.find(Campeonato.class, ano);
  }

}

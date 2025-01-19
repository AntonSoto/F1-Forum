package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.CampeonatoConstructor;
import es.udc.asi.notebook_rest.model.domain.CampeonatoPiloto;
import es.udc.asi.notebook_rest.model.repository.util.GenericDaoJpa;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class CampeonatoPilotoDaoJpa extends GenericDaoJpa implements CampeonatoPilotoDao {

  @Override
  public Collection<CampeonatoPiloto> findAllByAno(Long ano){
    return entityManager.createQuery(
        "select cp from CampeonatoPiloto cp where cp.campeonato.ano = :ano", CampeonatoPiloto.class)
      .setParameter("ano", ano).getResultList();
  }

}

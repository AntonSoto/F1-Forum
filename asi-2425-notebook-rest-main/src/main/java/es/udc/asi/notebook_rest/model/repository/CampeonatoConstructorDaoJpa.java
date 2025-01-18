package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.CampeonatoConstructor;
import es.udc.asi.notebook_rest.model.domain.Valoracion;
import es.udc.asi.notebook_rest.model.repository.util.GenericDaoJpa;
import org.springframework.stereotype.Repository;

import java.util.Collection;
@Repository
public class CampeonatoConstructorDaoJpa extends GenericDaoJpa implements CampeonatoConstructorDao {

  @Override
  public Collection<CampeonatoConstructor> findAllByAno(Long ano){
    return entityManager.createQuery(
        "select cc from CampeonatoConstructor cc where cc.campeonato.ano = :ano", CampeonatoConstructor.class)
      .setParameter("ano", ano).getResultList();
  }

}

package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.PilotoConstructor;
import es.udc.asi.notebook_rest.model.domain.Valoracion;
import es.udc.asi.notebook_rest.model.repository.util.GenericDaoJpa;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class PilotoConstructorDaoJpa extends GenericDaoJpa implements PilotoConstructorDao {

  public Collection<PilotoConstructor> findPilotoConstructorByPilotoAndConstructor(String Pid, String Cid){
    return entityManager.createQuery(
        "select pc from PilotoConstructor pc where pc.piloto.id = :Pid AND pc.constructor.id = :Cid",
        PilotoConstructor.class)
      .setParameter("Pid", Pid).setParameter("Cid", Cid).getResultList();
  }

}

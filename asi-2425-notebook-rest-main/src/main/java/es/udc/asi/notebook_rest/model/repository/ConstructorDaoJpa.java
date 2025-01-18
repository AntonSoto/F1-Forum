package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.Circuito;
import es.udc.asi.notebook_rest.model.domain.Constructor;
import es.udc.asi.notebook_rest.model.domain.Note;
import es.udc.asi.notebook_rest.model.exception.NotFoundException;
import es.udc.asi.notebook_rest.model.repository.util.GenericDaoJpa;
import es.udc.asi.notebook_rest.model.service.dto.CircuitoDTO;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class ConstructorDaoJpa extends GenericDaoJpa implements ConstructorDao {

  @Override
  public void create(Constructor constructor) {
    entityManager.persist(constructor);
  }

  @Override
  public Constructor findById(String id) {
    return entityManager.find(Constructor.class, id);
  }

  @Override
  public List<Object[]> findByCampeonatoAno(Long ano) {
    return entityManager.createQuery(
        "SELECT cst, cc " +
          "FROM Constructor cst " +
          "JOIN cst.campeonatoConstructores cc " +
          "WHERE cc.campeonato = :ano", Object[].class)
      .setParameter("ano", ano)
      .getResultList();
  }

}

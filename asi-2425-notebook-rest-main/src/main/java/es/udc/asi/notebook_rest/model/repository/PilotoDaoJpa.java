package es.udc.asi.notebook_rest.model.repository;


import es.udc.asi.notebook_rest.model.domain.Piloto;
import es.udc.asi.notebook_rest.model.domain.Note;
import es.udc.asi.notebook_rest.model.domain.User;
import es.udc.asi.notebook_rest.model.domain.Valoracion;
import es.udc.asi.notebook_rest.model.repository.util.GenericDaoJpa;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class PilotoDaoJpa extends GenericDaoJpa implements PilotoDao {

  @Override
  public void create(Piloto piloto) {
    entityManager.persist(piloto);
  }

  @Override
  public Collection<Piloto> findAll() {
    return entityManager.createQuery("from Piloto ", Piloto.class).getResultList();
  }

  @Override
  public Piloto findById(String id) {
    return entityManager.find(Piloto.class, id);
  }

  @Override
  public List<Object[]> findByCampeonatoAno(Long ano) {
    return entityManager.createQuery(
        "SELECT p, cp, cst " +
          "FROM Piloto p " +
          "JOIN p.campeonatoPilotos cp " +
          "JOIN cp.campeonato c " +
          "JOIN p.pilotoConstructor pc " +
          "JOIN pc.constructor cst " +
          "WHERE c.ano = :ano", Object[].class)
      .setParameter("ano", ano)
      .getResultList();
  }

}

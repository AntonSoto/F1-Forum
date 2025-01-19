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

  public void update(Piloto piloto) {entityManager.merge(piloto);}

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
    List<Object[]> lista = entityManager.createQuery(
      "SELECT cp, p, pc, c " +
        "FROM CampeonatoPiloto cp " +
        "JOIN cp.piloto p " +
        "JOIN PilotoConstructor pc ON p.id = pc.piloto.id " +
        "JOIN pc.constructor c " +
        "WHERE cp.campeonato.ano = :ano " +
        "AND pc.id IN ( " +
        "    SELECT MIN(pc2.id) " +
        "    FROM PilotoConstructor pc2 " +
        "    GROUP BY pc2.piloto.id " +
        ") " +
        "ORDER BY cp.piloto.id", Object[].class)
      .setParameter("ano", ano)
      .getResultList();

    System.out.println(lista);
    return lista;
  }

}

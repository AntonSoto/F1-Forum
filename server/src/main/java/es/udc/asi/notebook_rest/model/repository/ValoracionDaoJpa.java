package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.Valoracion;
import es.udc.asi.notebook_rest.model.repository.util.GenericDaoJpa;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class ValoracionDaoJpa extends GenericDaoJpa implements ValoracionDao{

  @Override
  public void create(Valoracion valoracion) {
    entityManager.persist(valoracion);
  }

  @Override
  public void delete(Valoracion valoracion) {
    entityManager.remove(valoracion);
  }

  @Override
  public void update(Valoracion valoracion) {
    entityManager.merge(valoracion);
  }

  @Override
  public Collection<Valoracion> findAllByGranPremio(Long id){
    return entityManager.createQuery(
      "select v from Valoracion v join v.granPremio gp where gp.id = :gran_premio_id", Valoracion.class)
      .setParameter("gran_premio_id", id).getResultList();
  }

  @Override
  public List<Object[]> findAllByUserWithGranPremio(String login) {
    return entityManager.createQuery(
        "SELECT v, gp " +
          "FROM Valoracion v " +
          "JOIN v.usuario user " +
          "JOIN v.granPremio gp " +
          "WHERE user.login = :user_login", Object[].class)
      .setParameter("user_login", login)
      .getResultList();
  }

  @Override
  public Valoracion findById(Long id) {
    return entityManager.find(Valoracion.class, id);
  }

  @Override
  public void deleteById(Long id) {
    Valoracion valoracion = findById(id);
    delete(valoracion);
  }
}

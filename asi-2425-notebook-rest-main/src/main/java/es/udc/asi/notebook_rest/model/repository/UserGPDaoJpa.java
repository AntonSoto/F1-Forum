package es.udc.asi.notebook_rest.model.repository;


import es.udc.asi.notebook_rest.model.domain.Note;
import es.udc.asi.notebook_rest.model.domain.UserGP;
import es.udc.asi.notebook_rest.model.domain.Valoracion;
import es.udc.asi.notebook_rest.model.repository.util.GenericDaoJpa;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class UserGPDaoJpa extends GenericDaoJpa implements UserGPDao {

  @Override
  public void create(UserGP userGP) {
    entityManager.persist(userGP);
  }

  @Override
  public void delete(UserGP userGP) {
    entityManager.remove(userGP);
  }

  @Override
  public UserGP findById(Long id) {
    return entityManager.find(UserGP.class, id);
  }

  @Override
  public void deleteById(Long id) {
    UserGP userGP = findById(id);
    delete(userGP);
  }

  public Collection<UserGP> findOneByUserAndGP(Long userid, Long GPid){
    return entityManager.createQuery(
        "select ugp from UserGP ugp where ugp.user.id = :userid AND ugp.granPremio.id = :GPid", UserGP.class)
      .setParameter("userid", userid).setParameter("GPid", GPid).getResultList();
  }

}

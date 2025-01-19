package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.UserGP;

import java.util.Collection;

public interface UserGPDao {

  void create(UserGP userGP);

  public void delete(UserGP userGP);

  void deleteById(Long id);

  Collection<UserGP> findOneByUserAndGP(Long userid, Long GPid);

  UserGP findById(Long id);
}

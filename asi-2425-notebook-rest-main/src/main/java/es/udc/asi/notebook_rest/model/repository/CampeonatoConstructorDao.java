package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.CampeonatoConstructor;

import java.util.Collection;

public interface CampeonatoConstructorDao {

  public Collection<CampeonatoConstructor> findAllByAno(Long ano);

}

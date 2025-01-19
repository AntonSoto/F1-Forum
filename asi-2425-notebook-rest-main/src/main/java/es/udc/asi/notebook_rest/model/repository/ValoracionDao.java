package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.Valoracion;

import java.util.Collection;
import java.util.List;

public interface ValoracionDao {

  void create(Valoracion valoracion);

  void delete(Valoracion valoracion);

  void update(Valoracion valoracion);

  Collection<Valoracion> findAllByGranPremio(Long id);

  public List<Object[]> findAllByUserWithGranPremio(String login);



  Valoracion findById(Long id);

  void deleteById(Long id);

}

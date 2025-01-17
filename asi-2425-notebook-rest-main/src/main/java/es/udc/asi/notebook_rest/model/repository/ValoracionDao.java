package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.Note;
import es.udc.asi.notebook_rest.model.domain.Valoracion;

import java.util.Collection;

public interface ValoracionDao {

  void create(Valoracion valoracion);

  void delete(Valoracion valoracion);

  void update(Valoracion valoracion);

  Collection<Valoracion> findAllByGranPremio(Long id);

  Collection<Valoracion> findAllByUser(String login);



  Valoracion findById(Long id);

  void deleteById(Long id);

}

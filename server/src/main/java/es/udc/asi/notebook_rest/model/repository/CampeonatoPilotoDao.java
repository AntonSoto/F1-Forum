package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.CampeonatoConstructor;
import es.udc.asi.notebook_rest.model.domain.CampeonatoPiloto;

import java.util.Collection;

public interface CampeonatoPilotoDao {

  public Collection<CampeonatoPiloto> findAllByAno(Long ano);

}

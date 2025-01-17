package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.Campeonato;
import es.udc.asi.notebook_rest.model.domain.Category;

public interface CampeonatoDao {

  void create(Campeonato campeonato);

  Campeonato findById(Long ano);

}

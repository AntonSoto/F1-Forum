package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.GranPremio;

public interface GranPremioDao {

  void create(GranPremio granPremio);

  GranPremio findById(Long id);

}

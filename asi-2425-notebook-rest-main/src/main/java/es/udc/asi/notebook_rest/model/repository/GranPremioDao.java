package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.Category;
import es.udc.asi.notebook_rest.model.domain.GranPremio;

import java.time.LocalDate;

public interface GranPremioDao {

  void create(GranPremio granPremio);
/*
  GranPremio findByFechaHoraCarrera(LocalDate date);
*/
}

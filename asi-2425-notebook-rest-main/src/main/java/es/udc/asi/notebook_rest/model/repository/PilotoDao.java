package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.Piloto;

import java.util.Collection;

public interface PilotoDao {

  void create(Piloto note);

  Collection<Piloto> findAll();

  Piloto findById(String id);
/*
  Piloto findByCampeonato(Long ano);
*/
}

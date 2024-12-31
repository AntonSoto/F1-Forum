package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.Circuito;

import java.util.Collection;

public interface CircuitoDao {

  void create(Circuito note);

  void delete(Circuito note);

  Collection<Circuito> findAll();

  Circuito findById(String id);

}

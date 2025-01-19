package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.Circuito;

import java.util.Collection;

public interface CircuitoDao {

  void create(Circuito circuito);

  void update(Circuito circuito);

  void delete(Circuito circuito);

  Collection<Circuito> findAll();

  Collection<Circuito> findByAno(Long ano);

  Circuito findById(String id);

}

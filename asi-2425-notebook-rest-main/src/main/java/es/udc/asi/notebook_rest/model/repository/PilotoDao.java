package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.Piloto;

import java.util.Collection;
import java.util.List;

public interface PilotoDao {

  void create(Piloto note);

  Collection<Piloto> findAll();

  Piloto findById(String id);

  public List<Object[]> findByCampeonatoAno(Long ano);
}

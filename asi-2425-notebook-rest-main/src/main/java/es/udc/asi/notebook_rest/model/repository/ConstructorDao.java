package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.Constructor;
import es.udc.asi.notebook_rest.model.domain.Note;

import java.util.Collection;
import java.util.List;

public interface ConstructorDao {

  void create(Constructor constructor);

  Constructor findById(String id);

  List<Object[]> findByCampeonatoAno(Long ano);

}

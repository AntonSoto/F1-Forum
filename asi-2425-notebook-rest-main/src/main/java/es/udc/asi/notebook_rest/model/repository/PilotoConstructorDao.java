package es.udc.asi.notebook_rest.model.repository;

import es.udc.asi.notebook_rest.model.domain.PilotoConstructor;

import java.util.Collection;

public interface PilotoConstructorDao {

  Collection<PilotoConstructor> findPilotoConstructorByPilotoAndConstructor(String Pid, String Cid);

}

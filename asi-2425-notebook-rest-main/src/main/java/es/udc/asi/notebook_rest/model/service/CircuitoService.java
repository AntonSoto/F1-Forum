package es.udc.asi.notebook_rest.model.service;


import es.udc.asi.notebook_rest.model.domain.Circuito;
import es.udc.asi.notebook_rest.model.domain.Note;
import es.udc.asi.notebook_rest.model.domain.User;
import es.udc.asi.notebook_rest.model.exception.NotFoundException;
import es.udc.asi.notebook_rest.model.repository.CircuitoDao;
import es.udc.asi.notebook_rest.model.repository.UserDao;
import es.udc.asi.notebook_rest.model.service.dto.CircuitoDTO;
import es.udc.asi.notebook_rest.model.service.dto.NoteDTO;
import es.udc.asi.notebook_rest.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class CircuitoService {

  @Autowired
  private CircuitoDao circuitoDAO;


  @Transactional(readOnly = false)
  public CircuitoDTO create(CircuitoDTO circuito) {
    Circuito bdCircuito = new Circuito(circuito.getId(), circuito.getNombreCircuito(), circuito.getLatitud(),
      circuito.getLongitud(), circuito.getLocalidad(), circuito.getPais() /*,circuito.getGrandesPremios()*/);
    /*if (circuito.getGrandesPremios() != null) {
      circuito.getGrandesPremios().forEach(gp -> {
        bdCircuito.getGrandesPremios().add(granPremioDAO.findById(gp.getId()));
      });
    }*/
    circuitoDAO.create(bdCircuito);
    return new CircuitoDTO(bdCircuito);
  }

  public CircuitoDTO findById(String id) throws NotFoundException {
    Circuito circuito = circuitoDAO.findById(id);
    if (circuito == null) {
      throw new NotFoundException(id, Circuito.class);
    }
    return new CircuitoDTO(circuito);
  }

  public Collection<CircuitoDTO> findAll() {
    Stream<Circuito> circuitos;
    circuitos = circuitoDAO.findAll().stream();

    return circuitos.map(circuito -> new CircuitoDTO(circuito)).collect(Collectors.toList());
  }
}

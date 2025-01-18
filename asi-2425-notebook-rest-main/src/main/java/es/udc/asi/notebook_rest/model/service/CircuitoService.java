package es.udc.asi.notebook_rest.model.service;


import es.udc.asi.notebook_rest.model.domain.Circuito;
import es.udc.asi.notebook_rest.model.domain.GranPremio;
import es.udc.asi.notebook_rest.model.domain.Note;
import es.udc.asi.notebook_rest.model.domain.User;
import es.udc.asi.notebook_rest.model.exception.NotFoundException;
import es.udc.asi.notebook_rest.model.repository.CampeonatoDao;
import es.udc.asi.notebook_rest.model.repository.CircuitoDao;
import es.udc.asi.notebook_rest.model.repository.UserDao;
import es.udc.asi.notebook_rest.model.service.dto.CircuitoDTO;
import es.udc.asi.notebook_rest.model.service.dto.NoteDTO;
import es.udc.asi.notebook_rest.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class CircuitoService {

  @Autowired
  private CircuitoDao circuitoDAO;

  @Autowired
  private CampeonatoDao campeonatoDAO;


  /*@Transactional(readOnly = false)
  public CircuitoDTO create(CircuitoDTO circuito) {

    Circuito bdCircuito = new Circuito(
      circuito.getId(),
      circuito.getNombreCircuito(),
      circuito.getLatitud(),
      circuito.getLongitud(),
      circuito.getLocalidad(),
      circuito.getPais()
    );

    if (circuito.getGrandesPremios() != null) {
      circuito.getGrandesPremios().forEach(gp -> {
        bdCircuito.getGrandesPremios().add(granPremioDao.findByFechaHoraCarrera(gp.getFechaHoraCarrera()));
      });
    }

    circuitoDAO.create(bdCircuito);
    return new CircuitoDTO(bdCircuito);
  }*/

  @Transactional(readOnly = false)
  public CircuitoDTO create(CircuitoDTO circuitoDTO) {

    Circuito bdCircuito = new Circuito(
      circuitoDTO.getId(),
      circuitoDTO.getNombreCircuito(),
      circuitoDTO.getLatitud(),
      circuitoDTO.getLongitud(),
      circuitoDTO.getLocalidad(),
      circuitoDTO.getPais()
    );


    // Crear y asignar los grandes premios al circuito
    List<GranPremio> grandesPremios = circuitoDTO.getGrandesPremios().stream()
      .map(gpDTO -> {
        GranPremio gp = new GranPremio();
        gp.setNumOrden(gpDTO.getNumOrden());
        gp.setFechaHoraLibres1(gpDTO.getFechaHoraLibres1());
        gp.setFechaHoraLibres2(gpDTO.getFechaHoraLibres2());
        gp.setFechaHoraLibres3(gpDTO.getFechaHoraLibres3());
        gp.setCampeonato(campeonatoDAO.findById(gpDTO.getAno()));
        gp.setFechaHoraSprint(gpDTO.getFechaHoraSprint());
        gp.setFechaHoraClasificacion(gpDTO.getFechaHoraClasificacion());
        gp.setFechaHoraCarrera(gpDTO.getFechaHoraCarrera());
        gp.setCircuito(bdCircuito);
        return gp;
      })
      .collect(Collectors.toList());

    bdCircuito.setGrandesPremios(grandesPremios);

    // Guardar el circuito en la base de datos
    circuitoDAO.create(bdCircuito);

    // Retornar el DTO del circuito creado
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

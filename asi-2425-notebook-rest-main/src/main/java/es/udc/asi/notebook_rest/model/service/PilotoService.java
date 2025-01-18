package es.udc.asi.notebook_rest.model.service;

import es.udc.asi.notebook_rest.model.domain.*;
import es.udc.asi.notebook_rest.model.exception.NotFoundException;
import es.udc.asi.notebook_rest.model.repository.CampeonatoDao;
import es.udc.asi.notebook_rest.model.repository.PilotoDao;
import es.udc.asi.notebook_rest.model.service.dto.CircuitoDTO;
import es.udc.asi.notebook_rest.model.service.dto.NoteDTO;
import es.udc.asi.notebook_rest.model.service.dto.PilotoDTO;
import es.udc.asi.notebook_rest.model.service.dto.ValoracionDTO;
import es.udc.asi.notebook_rest.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class PilotoService {

  @Autowired
  private PilotoDao pilotoDao;

  @Autowired
  private CampeonatoDao campeonatoDao;

  @Transactional(readOnly = false)
  public PilotoDTO create(PilotoDTO pilotoDTO) {

    Piloto piloto = new Piloto(
      pilotoDTO.getId(),
      pilotoDTO.getNacionalidad(),
      pilotoDTO.getNombreCompleto()
    );

    Campeonato campeonato = campeonatoDao.findById(pilotoDTO.getAno());

    Constructor constructor = new Constructor(
      pilotoDTO.getConstructorId(),
      pilotoDTO.getConstructorNombre(),
      pilotoDTO.getConstructorNacionalidad()
    );

    CampeonatoConstructor campeonatoConstructor = new CampeonatoConstructor(
      campeonato, constructor
    );

    PilotoConstructor pilotoConstructor = new PilotoConstructor(piloto, constructor);

    CampeonatoPiloto campeonatoPiloto = new CampeonatoPiloto(
      pilotoDTO.getPuntos(),
      pilotoDTO.getVictorias(),
      campeonato,
      piloto
    );

    pilotoDao.create(piloto);
    return new PilotoDTO(piloto);
  }

  @Transactional(readOnly = false)
  public List<PilotoDTO> findByCampeonatoAno(Long ano) {

    List<Object[]> resultados = pilotoDao.findByCampeonatoAno(ano);

    return resultados.stream().map(resultado -> {
      Piloto piloto = (Piloto) resultado[0];
      CampeonatoPiloto campeonatoPiloto = (CampeonatoPiloto) resultado[1];
      Constructor constructor = (Constructor) resultado[2];
      return new PilotoDTO(piloto, campeonatoPiloto, constructor);
    }).collect(Collectors.toList());
  }


}

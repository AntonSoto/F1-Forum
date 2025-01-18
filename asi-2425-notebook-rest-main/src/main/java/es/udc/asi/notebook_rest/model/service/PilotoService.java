package es.udc.asi.notebook_rest.model.service;

import es.udc.asi.notebook_rest.model.domain.*;
import es.udc.asi.notebook_rest.model.exception.NotFoundException;
import es.udc.asi.notebook_rest.model.repository.CampeonatoDao;
import es.udc.asi.notebook_rest.model.repository.CampeonatoPilotoDao;
import es.udc.asi.notebook_rest.model.repository.ConstructorDao;
import es.udc.asi.notebook_rest.model.repository.PilotoDao;
import es.udc.asi.notebook_rest.model.service.dto.*;
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
public class PilotoService {

  @Autowired
  private PilotoDao pilotoDao;

  @Autowired
  private CampeonatoDao campeonatoDao;

  @Autowired
  private ConstructorDao constructorDao;

  @Autowired
  private CampeonatoPilotoDao cameponatoPilotoDao;

  @Transactional(readOnly = false)
  public PilotoDTO create(PilotoDTO pilotoDTO) {
    Piloto piloto;
    if(pilotoDao.findById(pilotoDTO.getId()) == null) {
      piloto = new Piloto(
        pilotoDTO.getId(),
        pilotoDTO.getNacionalidad(),
        pilotoDTO.getNombreCompleto()
      );
    }else{
      piloto = pilotoDao.findById(pilotoDTO.getId());
    }

    Campeonato campeonato = campeonatoDao.findById(pilotoDTO.getAno());

    Collection<CampeonatoPiloto> campeonatosPorAno = cameponatoPilotoDao.findAllByAno(campeonato.getAno());

    boolean checkIfExists = false;

    for (CampeonatoPiloto campeonatoPiloto : campeonatosPorAno) {
      if(campeonatoPiloto.getPiloto().getId().equals(pilotoDTO.getId())) {
        checkIfExists = true;
      }
    }

    if(!checkIfExists) {

      CampeonatoPiloto campeonatoPiloto = new CampeonatoPiloto(
        pilotoDTO.getPuntos(),
        pilotoDTO.getVictorias()
      );

      piloto.getCampeonatoPilotos().add(campeonatoPiloto);
      campeonato.getCampeonatoPilotos().add(campeonatoPiloto);

      campeonatoPiloto.setPiloto(piloto);
      campeonatoPiloto.setCampeonato(campeonato);

    }else{
      throw new IllegalArgumentException("Constructor ya existente");
    }

    Constructor constructor = constructorDao.findById(pilotoDTO.getConstructorId());

    if(constructor == null) {
      throw new IllegalArgumentException("Constructor ya existente");
    }else{

      PilotoConstructor pilotoConstructor = new PilotoConstructor();

      piloto.getPilotoConstructor().add(pilotoConstructor);

      constructor.getConstructorPiloto().add(pilotoConstructor);

      pilotoConstructor.setPiloto(piloto);
      pilotoConstructor.setConstructor(constructor);
      
      pilotoDao.create(piloto);
      return new PilotoDTO(piloto);
    }

  }

  @Transactional(readOnly = true)
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

package es.udc.asi.notebook_rest.model.service;


import es.udc.asi.notebook_rest.model.domain.*;
import es.udc.asi.notebook_rest.model.repository.CampeonatoDao;
import es.udc.asi.notebook_rest.model.repository.ConstructorDao;
import es.udc.asi.notebook_rest.model.service.dto.ConstructorDTO;
import es.udc.asi.notebook_rest.model.service.dto.NoteDTO;
import es.udc.asi.notebook_rest.model.service.dto.ValoracionDTO;
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
public class ConstructorService {

  @Autowired
  private ConstructorDao constructorDao;

  @Autowired
  private CampeonatoDao campeonatoDao;

  public Collection<ConstructorDTO> findByCampeonatoAno(Long ano) {
    List<Object[]> resultados;

    resultados = constructorDao.findByCampeonatoAno(ano);
    return resultados.stream().map(resultado -> {
      Constructor constructor = (Constructor) resultado[0];
      CampeonatoConstructor campeonatoConstructor = (CampeonatoConstructor) resultado[1];
      return new ConstructorDTO(constructor, campeonatoConstructor);
    }).collect(Collectors.toList());
  }

  @Transactional(readOnly = false)
  public ConstructorDTO create(ConstructorDTO constructorDTO) {
    Constructor constructor;
    if(constructorDao.findById(constructorDTO.getId()) == null) {
        constructor = new Constructor(
        constructorDTO.getId(),
        constructorDTO.getNombre(),
        constructorDTO.getNacionalidad());
    }else{
       constructor = constructorDao.findById(constructorDTO.getId());
    }


    CampeonatoConstructor campeonatoConstructor = new CampeonatoConstructor(
      constructorDTO.getPuntos(),
      constructorDTO.getVictorias()
    );

    Campeonato campeonato = campeonatoDao.findById(constructorDTO.getAno());

    constructor.getCampeonatoConstructores().add(campeonatoConstructor);
    campeonato.getCampeonatoConstructors().add(campeonatoConstructor);

    campeonatoConstructor.setConstructor(constructor);
    campeonatoConstructor.setCampeonato(campeonato);

    constructorDao.create(constructor);
    return new ConstructorDTO(constructor);
  }

}

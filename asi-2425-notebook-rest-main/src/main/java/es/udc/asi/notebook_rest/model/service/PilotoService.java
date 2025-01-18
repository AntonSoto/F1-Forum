package es.udc.asi.notebook_rest.model.service;

import es.udc.asi.notebook_rest.model.domain.Constructor;
import es.udc.asi.notebook_rest.model.domain.Piloto;
import es.udc.asi.notebook_rest.model.domain.PilotoConstructor;
import es.udc.asi.notebook_rest.model.repository.PilotoDao;
import es.udc.asi.notebook_rest.model.service.dto.CircuitoDTO;
import es.udc.asi.notebook_rest.model.service.dto.PilotoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class PilotoService {

  @Autowired
  private PilotoDao pilotoDao;

  @Transactional(readOnly = false)
  public PilotoDTO create(PilotoDTO pilotoDTO) {

    Piloto piloto = new Piloto(
      pilotoDTO.getId(),
      pilotoDTO.getNacionalidad(),
      pilotoDTO.getNombreCompleto()
    );

    Constructor constructor = new Constructor(
      pilotoDTO.getConstructorId(),
      pilotoDTO.getConstructorNombre(),
      pilotoDTO.getConstructorNacionalidad()
    );

    PilotoConstructor pilotoConstructor = new PilotoConstructor(piloto, constructor);

    pilotoDao.create(piloto);
    return new PilotoDTO(piloto);


  }




}

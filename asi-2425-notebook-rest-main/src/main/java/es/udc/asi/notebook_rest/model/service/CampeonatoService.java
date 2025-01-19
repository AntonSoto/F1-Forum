package es.udc.asi.notebook_rest.model.service;

import es.udc.asi.notebook_rest.model.domain.Campeonato;
import es.udc.asi.notebook_rest.model.domain.Category;
import es.udc.asi.notebook_rest.model.exception.NotFoundException;
import es.udc.asi.notebook_rest.model.repository.CampeonatoDao;
import es.udc.asi.notebook_rest.model.repository.CategoryDao;
import es.udc.asi.notebook_rest.model.service.dto.CampeonatoDTO;
import es.udc.asi.notebook_rest.model.service.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class CampeonatoService {

  @Autowired
  private CampeonatoDao campeonatoDao;

  @Transactional(readOnly = false)
  public CampeonatoDTO create(CampeonatoDTO campeonato) {
    Campeonato bdCampeonato = new Campeonato(campeonato.getAno());
    campeonatoDao.create(bdCampeonato);
    return new CampeonatoDTO(bdCampeonato);
  }

  public CampeonatoDTO findById(Long ano) throws NotFoundException {
    Campeonato campeonato = campeonatoDao.findById(ano);
    if (campeonato == null) {
      throw new NotFoundException(ano.toString(), Campeonato.class);
    }
    return new CampeonatoDTO(campeonato);
  }
}

package es.udc.asi.notebook_rest.model.service;

import es.udc.asi.notebook_rest.model.domain.*;
import es.udc.asi.notebook_rest.model.exception.ModelException;
import es.udc.asi.notebook_rest.model.exception.NotFoundException;
import es.udc.asi.notebook_rest.model.repository.*;
import es.udc.asi.notebook_rest.model.service.dto.*;
import es.udc.asi.notebook_rest.model.service.util.ImageService;
import es.udc.asi.notebook_rest.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class PilotoService {

  @Autowired
  private ImageService imageService;

  @Autowired
  private PilotoDao pilotoDao;

  @Autowired
  private CampeonatoDao campeonatoDao;

  @Autowired
  private ConstructorDao constructorDao;

  @Autowired
  private CampeonatoPilotoDao cameponatoPilotoDao;

  @Autowired
  private PilotoConstructorDao pilotoConstructorDao;

  @Transactional(readOnly = false)
  public PilotoDTO create(PilotoDTO pilotoDTO) {
    Piloto piloto;
    if(pilotoDao.findById(pilotoDTO.getId()) == null) {
      piloto = new Piloto(
        pilotoDTO.getId(),
        pilotoDTO.getNombreCompleto(),
        pilotoDTO.getNacionalidad()

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
      throw new IllegalArgumentException("Piloto ya existente");
    }

    Constructor constructor = constructorDao.findById(pilotoDTO.getConstructorId());

    if(constructor == null) {
      throw new IllegalArgumentException("Constructor no existente");
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
      Piloto piloto = (Piloto) resultado[1];
      CampeonatoPiloto campeonatoPiloto = (CampeonatoPiloto) resultado[0];
      Constructor constructor = (Constructor) resultado[3];
      return new PilotoDTO(piloto, campeonatoPiloto, constructor);
    }).collect(Collectors.toList());
  }

  @Transactional(readOnly = false)
  public void guardarImagenDePiloto(String id, MultipartFile file) throws ModelException {
    Piloto piloto = pilotoDao.findById(id);
    if (piloto == null) {
      throw new NotFoundException(id, Piloto.class);
    }

    if (file.isEmpty()) {
      throw new ModelException("No se ha enviado ninguna imagen");
    }

    String nombreFichero = imageService.saveImage(file, id, piloto.getClass().getSimpleName());
    piloto.setNombreImagen(nombreFichero);
    pilotoDao.update(piloto);
  }

  public ImageDTO recuperarImagenDePiloto(String id) throws ModelException {
    Piloto piloto = pilotoDao.findById(id);
    if (piloto == null) {
      throw new NotFoundException(id, Piloto.class);
    }

    return imageService.getImage(piloto.getClass().getSimpleName(), id);
  }

  public void eliminarImagenDePiloto(String id) throws ModelException {
    Piloto piloto = pilotoDao.findById(id);
    if (piloto == null) {
      throw new NotFoundException(id, Constructor.class);
    }

    imageService.getImage(id, piloto.getClass().getName());
  }

}

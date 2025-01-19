package es.udc.asi.notebook_rest.model.service;


import es.udc.asi.notebook_rest.model.domain.*;
import es.udc.asi.notebook_rest.model.exception.ModelException;
import es.udc.asi.notebook_rest.model.exception.NotFoundException;
import es.udc.asi.notebook_rest.model.repository.CampeonatoConstructorDao;
import es.udc.asi.notebook_rest.model.repository.CampeonatoDao;
import es.udc.asi.notebook_rest.model.repository.ConstructorDao;
import es.udc.asi.notebook_rest.model.service.dto.*;
import es.udc.asi.notebook_rest.model.service.util.ImageService;
import es.udc.asi.notebook_rest.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

  @Autowired
  private ImageService imageService;


  @Autowired
  private CampeonatoConstructorDao campeonatoConstructorDao;

  public Collection<ConstructorDTO> findByCampeonatoAno(Long ano) {
    List<Object[]> resultados;

    resultados = constructorDao.findByCampeonatoAno(ano);
    return resultados.stream().map(resultado -> {
      Constructor constructor = (Constructor) resultado[0];
      CampeonatoConstructor campeonatoConstructor = (CampeonatoConstructor) resultado[1];
      return new ConstructorDTO(constructor, campeonatoConstructor);
    }).collect(Collectors.toList());
  }

  public ConstructorDTO findById(String id) throws NotFoundException {
    Constructor constructor = constructorDao.findById(id);
    if (constructor == null) {
      throw new NotFoundException(id, Circuito.class);
    }
    return new ConstructorDTO(constructor);
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
    Campeonato campeonato = campeonatoDao.findById(constructorDTO.getAno());

    Collection<CampeonatoConstructor> campeonatosPorAno = campeonatoConstructorDao.findAllByAno(campeonato.getAno());

    boolean checkIfExists = false;

    for (CampeonatoConstructor campeonatoConstructor : campeonatosPorAno) {
      if(campeonatoConstructor.getConstructor().getId().equals(constructorDTO.getId())) {
        checkIfExists = true;
      }
    }

    if(!checkIfExists) {

      CampeonatoConstructor campeonatoConstructor = new CampeonatoConstructor(
        constructorDTO.getPuntos(),
        constructorDTO.getVictorias()
      );

      constructor.getCampeonatoConstructores().add(campeonatoConstructor);
      campeonato.getCampeonatoConstructors().add(campeonatoConstructor);

      campeonatoConstructor.setConstructor(constructor);
      campeonatoConstructor.setCampeonato(campeonato);

      constructorDao.create(constructor);
      return new ConstructorDTO(constructor);
    }else{
      throw new IllegalArgumentException("Constructor ya existente");
    }

  }

  @Transactional(readOnly = false)
  public void guardarImagenDeConstructor(String id, MultipartFile file) throws ModelException {
    Constructor constructor = constructorDao.findById(id);
    if (constructor == null) {
      throw new NotFoundException(id, Constructor.class);
    }

    if (file.isEmpty()) {
      throw new ModelException("No se ha enviado ninguna imagen");
    }

    String nombreFichero = imageService.saveImage(file, id, constructor.getClass().getSimpleName());
    constructor.setNombreImagen(nombreFichero);
    constructorDao.update(constructor);
  }

  public ImageDTO recuperarImagenDeConstructor(String id) throws ModelException {
    Constructor constructor = constructorDao.findById(id);
    if (constructor == null) {
      throw new NotFoundException(id, Constructor.class);
    }
    System.out.println("ESTOY TESTING");
    System.out.println("----------------------------------------------------------------------------");
    System.out.println(constructor.getNombreImagen());
    System.out.println(id);
    System.out.println("ACABO EL TESTING TESTING");
    System.out.println("----------------------------------------------------------------------------");
    return imageService.getImage(constructor.getClass().getSimpleName(), id );
  }

  @Transactional(readOnly = false)
  public void eliminarImagenDeConstructor(String id) throws ModelException {
    Constructor constructor = constructorDao.findById(id);
    if (constructor == null) {
      throw new NotFoundException(id, Constructor.class);
    }

     imageService.deleteImage(id, constructor.getClass().getSimpleName());
     constructor.setNombreImagen(null);
     constructorDao.update(constructor);
  }


}

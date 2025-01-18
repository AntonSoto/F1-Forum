package es.udc.asi.notebook_rest.model.service;

import es.udc.asi.notebook_rest.model.domain.GranPremio;
import es.udc.asi.notebook_rest.model.domain.Note;
import es.udc.asi.notebook_rest.model.domain.User;
import es.udc.asi.notebook_rest.model.domain.Valoracion;
import es.udc.asi.notebook_rest.model.exception.NotFoundException;
import es.udc.asi.notebook_rest.model.exception.OperationNotAllowed;
import es.udc.asi.notebook_rest.model.repository.GranPremioDao;
import es.udc.asi.notebook_rest.model.repository.UserDao;
import es.udc.asi.notebook_rest.model.repository.ValoracionDao;
import es.udc.asi.notebook_rest.model.service.dto.NoteDTO;
import es.udc.asi.notebook_rest.model.service.dto.UserDTOPrivate;
import es.udc.asi.notebook_rest.model.service.dto.ValoracionDTO;
import es.udc.asi.notebook_rest.security.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ValoracionService {

  @Autowired
  private ValoracionDao valoracionDAO;

  @Autowired
  private UserService userService;

  @Autowired
  private UserDao userDAO;

  @Autowired
  private GranPremioDao granPremioDAO;

  @Transactional(readOnly = false)
  public ValoracionDTO create(ValoracionDTO valoracion) {
    LocalDateTime ahora = LocalDateTime.now();
    User currentUser = userDAO.findById(userService.getCurrentUserWithAuthority().getId());
    Valoracion bdValoracion = new Valoracion(valoracion.getPuntuacion(), ahora, valoracion.getComentario(), currentUser);

    //bdValoracion.setGranPremio(granPremioDAO.findById(valoracion.getGranPremio()));

    GranPremio granPremio = granPremioDAO.findById(valoracion.getGranPremio());
    if (granPremio == null) {
      throw new IllegalArgumentException("El GranPremio con el ID proporcionado no existe");
    }

    bdValoracion.setGranPremio(granPremio);

    valoracionDAO.create(bdValoracion);
    return new ValoracionDTO(bdValoracion);
  }

  @Transactional(readOnly = false)
  public ValoracionDTO update(@Valid ValoracionDTO valoracion) throws NotFoundException, OperationNotAllowed {
    Valoracion bdValoracion = valoracionDAO.findById(valoracion.getId());
    if (bdValoracion == null) {
      throw new NotFoundException(valoracion.getId().toString(), Valoracion.class);
    }

    UserDTOPrivate currentUser = userService.getCurrentUserWithAuthority();
    if (!bdValoracion.getUsuario().getId().equals(currentUser.getId())) {
      throw new OperationNotAllowed("Current user is not the note owner");
    }

    bdValoracion.setComentario(valoracion.getComentario());
    bdValoracion.setPuntuacion(valoracion.getPuntuacion());

    valoracionDAO.update(bdValoracion);
    return new ValoracionDTO(bdValoracion);
  }

  @Transactional(readOnly = false)
  public void deleteById(Long id) throws NotFoundException, OperationNotAllowed {
    Valoracion valoracion = valoracionDAO.findById(id);
    if (valoracion == null) {
      throw new NotFoundException(id.toString(), Note.class);
    }

    UserDTOPrivate currentUser = userService.getCurrentUserWithAuthority();
    if (!currentUser.getId().equals(valoracion.getUsuario().getId())) {
      throw new OperationNotAllowed("Current user is not the note owner");
    }

    valoracionDAO.deleteById(id);
  }

  public Collection<ValoracionDTO> findAllByGranPremio(Long id) {
    Stream<Valoracion> valoraciones;
    valoraciones = valoracionDAO.findAllByGranPremio(id).stream();

    return valoraciones.map(valoracion -> new ValoracionDTO(valoracion)).collect(Collectors.toList());
  }

  public Collection<ValoracionDTO> findAllByUser() {
    List<Object[]> resultados = valoracionDAO.findAllByUserWithGranPremio(SecurityUtils.getCurrentUserLogin());

    return resultados.stream().map(resultado -> {
      Valoracion valoracion = (Valoracion) resultado[0];
      GranPremio granPremio = (GranPremio) resultado[1];
      return new ValoracionDTO(valoracion, granPremio);
    }).collect(Collectors.toList());
  }

}

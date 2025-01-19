package es.udc.asi.notebook_rest.model.service;


import es.udc.asi.notebook_rest.model.domain.GranPremio;
import es.udc.asi.notebook_rest.model.domain.Note;
import es.udc.asi.notebook_rest.model.domain.User;
import es.udc.asi.notebook_rest.model.domain.UserGP;
import es.udc.asi.notebook_rest.model.exception.NotFoundException;
import es.udc.asi.notebook_rest.model.exception.OperationNotAllowed;
import es.udc.asi.notebook_rest.model.repository.GranPremioDao;
import es.udc.asi.notebook_rest.model.repository.UserDao;
import es.udc.asi.notebook_rest.model.repository.UserGPDao;
import es.udc.asi.notebook_rest.model.service.dto.NoteDTO;
import es.udc.asi.notebook_rest.model.service.dto.UserDTOPrivate;
import es.udc.asi.notebook_rest.model.service.dto.UserGPDTO;
import es.udc.asi.notebook_rest.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserGPService {

  @Autowired
  private UserGPDao userGPDao;

  @Autowired
  private UserDao userDAO;

  @Autowired
  private GranPremioDao granPremioDao;

  @Autowired
  private UserService userService;

  @Transactional(readOnly = false)
  public UserGPDTO create(UserGPDTO userGP) {
    User currentUser = userDAO.findById(userService.getCurrentUserWithAuthority().getId());
    GranPremio granPremio = granPremioDao.findById(userGP.getGpId());

    UserGP bdUserGP = new UserGP(currentUser, granPremio);

    userGPDao.create(bdUserGP);
    return new UserGPDTO(bdUserGP);
  }

  @Transactional(readOnly = false)
  public void deleteById(Long id) throws NotFoundException, OperationNotAllowed {
    UserGP userGP = userGPDao.findById(id);
    if (userGP == null) {
      throw new NotFoundException(id.toString(), UserGP.class);
    }

    UserDTOPrivate currentUser = userService.getCurrentUserWithAuthority();
    if (!currentUser.getId().equals(userGP.getUser().getId())) {
      throw new OperationNotAllowed("Current user is not the note owner");
    }

    userGPDao.deleteById(id);
  }

  public Collection<UserGPDTO> findOneByUserAndGP(Long GPid) {
    Stream<UserGP> visualizaciones;
    visualizaciones = userGPDao.findOneByUserAndGP(userService.getCurrentUserWithAuthority().getId(),
      GPid).stream();


    return visualizaciones.map(visualizacion -> new UserGPDTO(visualizacion)).collect(Collectors.toList());
  }

}

package es.udc.asi.notebook_rest.model.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.asi.notebook_rest.model.domain.User;
import es.udc.asi.notebook_rest.model.domain.UserAuthority;
import es.udc.asi.notebook_rest.model.exception.NotFoundException;
import es.udc.asi.notebook_rest.model.exception.OperationNotAllowed;
import es.udc.asi.notebook_rest.model.exception.UserLoginExistsException;
import es.udc.asi.notebook_rest.model.repository.NoteDao;
import es.udc.asi.notebook_rest.model.repository.UserDao;
import es.udc.asi.notebook_rest.model.service.dto.UserDTOPrivate;
import es.udc.asi.notebook_rest.model.service.dto.UserDTOPublic;
import es.udc.asi.notebook_rest.model.service.dto.UserWithNotesDTO;
import es.udc.asi.notebook_rest.security.SecurityUtils;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserService {

  @Autowired
  private UserDao userDAO;

  @Autowired
  private NoteDao noteDAO;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @PreAuthorize("hasAuthority('ADMIN')")
  public List<UserDTOPublic> findAll() {
    Stream<UserDTOPublic> users = userDAO.findAll().stream().map(user -> new UserDTOPublic(user));
    if (SecurityUtils.getCurrentUserIsAdmin()) {
      return users.collect(Collectors.toList());
    }
    return users.filter(user -> user.isActive()).collect(Collectors.toList());
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  public UserWithNotesDTO findOne(Long id) throws NotFoundException {
    User user = userDAO.findById(id);
    if (user == null) {
      throw new NotFoundException(id.toString(), User.class);
    }
    return new UserWithNotesDTO(user);
  }

  @Transactional(readOnly = false)
  public void registerUser(String login, String password, String nombre, String apellidos, Date fechaNacimiento) throws UserLoginExistsException {
    registerUser(login, password, nombre, apellidos, fechaNacimiento,false);
  }

  @Transactional(readOnly = false)
  public void registerUser(String login, String password, String nombre, String apellidos, Date fechaNacimiento, boolean isAdmin) throws UserLoginExistsException {
    if (userDAO.findByLogin(login) != null) {
      throw new UserLoginExistsException(login);
    }

    User user = new User();
    String encryptedPassword = passwordEncoder.encode(password);

    user.setLogin(login);
    user.setPassword(encryptedPassword);
    user.setNombrePila(nombre);
    user.setApellidos(apellidos);
    user.setFechaNacimiento(fechaNacimiento);

    user.setAuthority(UserAuthority.USER);
    if (isAdmin) {
      user.setAuthority(UserAuthority.ADMIN);
    }

    userDAO.create(user);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Transactional(readOnly = false)
  public UserDTOPublic updateActive(Long id, boolean active) throws NotFoundException, OperationNotAllowed {
    User user = userDAO.findById(id);
    if (user == null) {
      throw new NotFoundException(id.toString(), User.class);
    }

    UserDTOPrivate currentUser = getCurrentUserWithAuthority();
    if (currentUser.getId().equals(user.getId())) {
      throw new OperationNotAllowed("The user cannot activate/deactive itself");
    }

    user.setActive(active);
    userDAO.update(user);
    return new UserDTOPublic(user);
  }


  @Transactional(readOnly = false)
  public UserDTOPublic changePassword(Long id, String password) throws NotFoundException, OperationNotAllowed {
    User user = userDAO.findById(id);
    if (user == null) {
      throw new NotFoundException(id.toString(), User.class);
    }

    UserDTOPrivate currentUser = getCurrentUserWithAuthority();
    if (currentUser.getId().equals(user.getId())) {

      String encryptedPassword = passwordEncoder.encode(password);
      user.setPassword(encryptedPassword);
      userDAO.update(user);
      return new UserDTOPublic(user);

    }else{
      throw new OperationNotAllowed("No se puede marcar como visto por otro usuario");
    }

  }

  public UserDTOPrivate getCurrentUserWithAuthority() {
    String currentUserLogin = SecurityUtils.getCurrentUserLogin();
    if (currentUserLogin != null) {
      return new UserDTOPrivate(userDAO.findByLogin(currentUserLogin));
    }
    return null;
  }

  @Transactional(readOnly = false)
  public void deleteById(Long id) throws NotFoundException, OperationNotAllowed {
    User theUser = userDAO.findById(id);
    if (theUser == null) {
      throw new NotFoundException(id.toString(), User.class);
    }
    UserDTOPrivate currentUser = getCurrentUserWithAuthority();

      if (currentUser.getId().equals(theUser.getId()) || (currentUser.getAuthority().equals("ADMIN") && theUser.getAuthority().equals(UserAuthority.USER))) {
        userDAO.delete(theUser);
      }else{
        throw new OperationNotAllowed("The user cannot remove others if he isn't admin or other admins");

      }

  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Transactional(readOnly = false)
  public UserDTOPublic updateAdmin(Long id, boolean makeAdmin) throws NotFoundException, OperationNotAllowed {
    User user = userDAO.findById(id);
    if (user == null) {
      throw new NotFoundException(id.toString(), User.class);
    }

    UserDTOPrivate currentUser = getCurrentUserWithAuthority();
    if (currentUser.getId().equals(user.getId())) {
      throw new OperationNotAllowed("The user cannot activate/deactive itself");
    }
    UserAuthority authority;
    if(makeAdmin){
      authority = UserAuthority.ADMIN;
      user.setAuthority(authority);
      userDAO.update(user);
      return new UserDTOPublic(user);
    }else{
      authority = UserAuthority.USER;
      user.setAuthority(authority);
      userDAO.update(user);
      return new UserDTOPublic(user);
    }

  }

}

package es.udc.asi.notebook_rest.web;

import es.udc.asi.notebook_rest.model.exception.NotFoundException;
import es.udc.asi.notebook_rest.model.exception.OperationNotAllowed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import es.udc.asi.notebook_rest.model.exception.UserLoginExistsException;
import es.udc.asi.notebook_rest.model.service.UserService;
import es.udc.asi.notebook_rest.model.service.dto.LoginDTO;
import es.udc.asi.notebook_rest.model.service.dto.UserDTOPrivate;
import es.udc.asi.notebook_rest.security.JWTToken;
import es.udc.asi.notebook_rest.security.TokenProvider;
import es.udc.asi.notebook_rest.web.exceptions.CredentialsAreNotValidException;
import es.udc.asi.notebook_rest.web.exceptions.RequestBodyNotValidException;
import jakarta.validation.Valid;

/**
 * Este controlador va por separado que el UserResource porque se encarga de
 * tareas relacionadas con la autenticación, registro, etc.
 *
 * <p>
 * También permite a cada usuario logueado en la aplicación obtener información
 * de su cuenta
 */
@RestController
@RequestMapping("/api")
public class AccountResource {
  private final Logger logger = LoggerFactory.getLogger(AccountResource.class);

  @Autowired
  private TokenProvider tokenProvider;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserService userService;

  @PostMapping("/authenticate")
  public JWTToken authenticate(@Valid @RequestBody LoginDTO loginDTO) throws CredentialsAreNotValidException {

    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        loginDTO.getLogin(), loginDTO.getPassword());
    try {
      Authentication authentication = authenticationManager.authenticate(authenticationToken);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      String jwt = tokenProvider.createToken(authentication);
      return new JWTToken(jwt);
    } catch (AuthenticationException e) {
      logger.warn(e.getMessage(), e);
      throw new CredentialsAreNotValidException(e.getMessage());
    }
  }

  @GetMapping("/account")
  public UserDTOPrivate getAccount() {
    return userService.getCurrentUserWithAuthority();
  }

  @PostMapping("/register")
  public void registerAccount(@Valid @RequestBody UserDTOPrivate account, Errors errors)
      throws UserLoginExistsException, RequestBodyNotValidException {
    if (errors.hasErrors()) {
      throw new RequestBodyNotValidException(errors);
    }

    userService.registerUser(account.getLogin(), account.getPassword(), account.getNombrePila(), account.getApellidos(), account.getFechaNacimiento(), false);
  }

  @PutMapping("/changePwd")
  public void changePassword(@Valid @RequestBody UserDTOPrivate account, Errors errors)
    throws RequestBodyNotValidException, NotFoundException, OperationNotAllowed {
    if (errors.hasErrors()) {
      throw new RequestBodyNotValidException(errors);
    }

    userService.changePassword(account.getId(), account.getPassword());
  }
}

package es.udc.asi.notebook_rest.model.service.dto;

import es.udc.asi.notebook_rest.model.domain.User;
import es.udc.asi.notebook_rest.model.domain.UserAuthority;

import java.util.Date;

public class UserDTOPublic {
  private Long id;
  private String login;
  private boolean active = true;
  private UserAuthority authority;
  private Date fechaNacimiento;
  private String nombrePila;
  private String apellidos;

  public UserDTOPublic() {
  }

  public UserDTOPublic(User user) {
    this.id = user.getId();
    this.login = user.getLogin();
    this.active = user.isActive();
    this.authority = user.getAuthority();
    this.fechaNacimiento = user.getFechaNacimiento();
    this.nombrePila = user.getNombrePila();
    this.apellidos = user.getApellidos();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLogin() {
    return login;
  }

  public UserAuthority getAuthority() {
    return authority;
  }

  public void setAuthority(UserAuthority authority) {
    this.authority = authority;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public Date getFechaNacimiento() {return fechaNacimiento;}

  public void setFechaNacimiento(Date fechaNacimiento) {this.fechaNacimiento = fechaNacimiento;}

  public String getNombrePila() {return nombrePila;}

  public void setNombrePila(String nombrePila) {this.nombrePila = nombrePila;}

  public String getApellidos() {return apellidos;}

  public void setApellidos(String apellidos) {this.apellidos = apellidos;}
}

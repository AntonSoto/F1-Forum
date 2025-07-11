package es.udc.asi.notebook_rest.model.domain;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "theUser")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
  @SequenceGenerator(name = "user_generator", sequenceName = "user_seq")
  private Long id;

  @Column(unique = true)
  private String login;

  private String password;

  private String nombrePila;

  private String apellidos;

  private Date fechaNacimiento;

  @Enumerated(EnumType.STRING)
  private UserAuthority authority;

  @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Valoracion> valoraciones = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserGP> Visualizacion = new ArrayList<>();

  private boolean active = true;



  public User() {
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

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserAuthority getAuthority() {
    return authority;
  }

  public void setAuthority(UserAuthority authority) {
    this.authority = authority;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {this.active = active;}

  public String getNombrePila() {return nombrePila;}

  public void setNombrePila(String nombrePila) {this.nombrePila = nombrePila;}

  public String getApellidos() {return apellidos;}

  public void setApellidos(String apellidos) {this.apellidos = apellidos;}

  public Date getFechaNacimiento() {return fechaNacimiento;}

  public void setFechaNacimiento(Date fechaNacimiento) {this.fechaNacimiento = fechaNacimiento;}

}

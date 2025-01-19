package es.udc.asi.notebook_rest.model.service.dto;

import es.udc.asi.notebook_rest.model.domain.GranPremio;
import es.udc.asi.notebook_rest.model.domain.Valoracion;

import java.time.LocalDateTime;

public class ValoracionDTO {

  private Long id;
  private Long puntuacion;
  private LocalDateTime fechaValoracion;
  private String comentario;
  private String user;
  private Long idUser;
  private String authority;
  private Long GranPremioId;
  private String granPremioNombre;


  public ValoracionDTO() {
    super();
  }

  public ValoracionDTO(Valoracion valoracion) {
    this.id = valoracion.getId();
    this.puntuacion = valoracion.getPuntuacion();
    this.fechaValoracion = valoracion.getFechaValoracion();
    this.comentario = valoracion.getComentario();
    this.user = valoracion.getUsuario().getLogin();
    this.idUser = valoracion.getUsuario().getId();
    this.authority = valoracion.getUsuario().getAuthority().toString();
    this.GranPremioId = valoracion.getGranPremio().getId();
  }

  public ValoracionDTO(Valoracion valoracion, GranPremio granPremio) {
    this.id = valoracion.getId();
    this.puntuacion = valoracion.getPuntuacion();
    this.fechaValoracion = valoracion.getFechaValoracion();
    this.comentario = valoracion.getComentario();
    this.user = valoracion.getUsuario().getLogin();
    this.idUser = valoracion.getUsuario().getId();
    this.authority = valoracion.getUsuario().getAuthority().toString();
    this.GranPremioId = granPremio.getId();
    this.granPremioNombre = granPremio.getCircuito().getId();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getPuntuacion() {
    return puntuacion;
  }

  public void setPuntuacion(Long puntuacion) {
    this.puntuacion = puntuacion;
  }

  public LocalDateTime getFechaValoracion() {
    return fechaValoracion;
  }

  public void setFechaValoracion(LocalDateTime fechaValoracion) {
    this.fechaValoracion = fechaValoracion;
  }

  public String getComentario() {
    return comentario;
  }

  public void setComentario(String comentario) {
    this.comentario = comentario;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public Long getGranPremio() {
    return GranPremioId;
  }

  public void setGranPremio(Long granPremio) {
    GranPremioId = granPremio;
  }

  public String getGranPremioNombre() {
    return granPremioNombre;
  }

  public void setGranPremioNombre(String granPremioNombre) {
    this.granPremioNombre = granPremioNombre;
  }


  public Long getIdUser() {
    return idUser;
  }

  public void setIdUser(Long idUser) {
    this.idUser = idUser;
  }

  public String getAuthority() {
    return authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }


}

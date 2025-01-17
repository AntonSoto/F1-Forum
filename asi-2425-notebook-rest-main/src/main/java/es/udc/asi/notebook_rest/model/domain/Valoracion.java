package es.udc.asi.notebook_rest.model.domain;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Valoracion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(referencedColumnName = "id")
  private GranPremio granPremio;

  @Column
  private Long puntuacion;
  private LocalDateTime  fechaValoracion;
  private String comentario;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  private User usuario;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Valoracion that = (Valoracion) o;
    return Objects.equals(id, that.id) && Objects.equals(granPremio, that.granPremio) && Objects.equals(puntuacion, that.puntuacion) && Objects.equals(fechaValoracion, that.fechaValoracion) && Objects.equals(comentario, that.comentario) && Objects.equals(usuario, that.usuario);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, granPremio, puntuacion, fechaValoracion, comentario, usuario);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public GranPremio getGranPremio() {
    return granPremio;
  }

  public void setGranPremio(GranPremio granPremio) {
    this.granPremio = granPremio;
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

  public void setFechaValoracion(LocalDateTime  fechaValoracion) {
    this.fechaValoracion = fechaValoracion;
  }

  public String getComentario() {
    return comentario;
  }

  public void setComentario(String comentario) {
    this.comentario = comentario;
  }

  public User getUsuario() {
    return usuario;
  }

  public void setUsuario(User usuario) {
    this.usuario = usuario;
  }
}

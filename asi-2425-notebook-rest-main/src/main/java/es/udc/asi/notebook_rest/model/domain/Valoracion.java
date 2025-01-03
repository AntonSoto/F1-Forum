package es.udc.asi.notebook_rest.model.domain;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Valoracion {

  /*@EmbeddedId
  private ValoracionId id;

  @MapsId("ValoracionId")
  private Long ValoracionId;*/

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @MapsId("granPremioId")
  @ManyToOne
  @JoinColumn(name = "id", referencedColumnName = "id")
  private GranPremio granPremio;

  @Column
  private Long puntuacion;
  private LocalDate fechaValoracion;
  private String comentario;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  private User usuario;

  /*@PrePersist
  private void generateGPid() {
    if (this.id.getValoracionId() == null) {
      this.id.setValoracionId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
    }
  }*/


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

  public LocalDate getFechaValoracion() {
    return fechaValoracion;
  }

  public void setFechaValoracion(LocalDate fechaValoracion) {
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
}

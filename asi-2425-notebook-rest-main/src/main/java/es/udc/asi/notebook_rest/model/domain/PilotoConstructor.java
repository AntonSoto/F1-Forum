package es.udc.asi.notebook_rest.model.domain;


import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
public class PilotoConstructor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Date fechaEntrada;
  private Date fechaSalida;

  @ManyToOne(optional = false)
  private Piloto piloto;

  @ManyToOne(optional = false, cascade = CascadeType.ALL)
  private Constructor constructor;

  public PilotoConstructor() {
    super();
  }

  public PilotoConstructor(Piloto piloto, Constructor constructor) {
    super();
    this.piloto = piloto;
    this.constructor = constructor;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    PilotoConstructor that = (PilotoConstructor) o;
    return Objects.equals(id, that.id) && Objects.equals(fechaEntrada, that.fechaEntrada) && Objects.equals(fechaSalida, that.fechaSalida) && Objects.equals(piloto, that.piloto) && Objects.equals(constructor, that.constructor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, fechaEntrada, fechaSalida, piloto, constructor);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getFechaEntrada() {
    return fechaEntrada;
  }

  public void setFechaEntrada(Date fechaEntrada) {
    this.fechaEntrada = fechaEntrada;
  }

  public Date getFechaSalida() {
    return fechaSalida;
  }

  public void setFechaSalida(Date fechaSalida) {
    this.fechaSalida = fechaSalida;
  }

  public Piloto getPiloto() {
    return piloto;
  }

  public void setPiloto(Piloto piloto) {
    this.piloto = piloto;
  }

  public Constructor getConstructor() {
    return constructor;
  }

  public void setConstructor(Constructor constructor) {
    this.constructor = constructor;
  }
}

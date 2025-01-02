package es.udc.asi.notebook_rest.model.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
public class GranPremio {

  /*@EmbeddedId
  private GranPremioId id = new GranPremioId();*/

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @MapsId("ano")
  @ManyToOne
  @JoinColumn(name = "ano", referencedColumnName = "ano")
  private Campeonato campeonato;

  @Column
  private Long numOrden;

  @Column(unique = true)
  private LocalDate fechaHoraLibres1;
  private LocalDate fechaHoraLibres2;
  private LocalDate fechaHoraLibres3;

  private LocalDate fechaHoraClasificacion;
  private LocalDate fechaHoraCarrera;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  private Circuito circuito;

  public GranPremio() {
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    GranPremio that = (GranPremio) o;
    return Objects.equals(id, that.id) && Objects.equals(campeonato, that.campeonato) && Objects.equals(numOrden, that.numOrden) && Objects.equals(fechaHoraLibres1, that.fechaHoraLibres1) && Objects.equals(fechaHoraLibres2, that.fechaHoraLibres2) && Objects.equals(fechaHoraLibres3, that.fechaHoraLibres3) && Objects.equals(fechaHoraClasificacion, that.fechaHoraClasificacion) && Objects.equals(fechaHoraCarrera, that.fechaHoraCarrera) && Objects.equals(circuito, that.circuito);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, campeonato, numOrden, fechaHoraLibres1, fechaHoraLibres2, fechaHoraLibres3, fechaHoraClasificacion, fechaHoraCarrera, circuito);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Campeonato getCampeonato() {
    return campeonato;
  }

  public void setCampeonato(Campeonato campeonato) {
    this.campeonato = campeonato;
  }

  public Long getNumOrden() {
    return numOrden;
  }

  public void setNumOrden(Long numOrden) {
    this.numOrden = numOrden;
  }

  public LocalDate getFechaHoraLibres1() {
    return fechaHoraLibres1;
  }

  public void setFechaHoraLibres1(LocalDate fechaHoraLibres1) {
    this.fechaHoraLibres1 = fechaHoraLibres1;
  }

  public LocalDate getFechaHoraLibres2() {
    return fechaHoraLibres2;
  }

  public void setFechaHoraLibres2(LocalDate fechaHoraLibres2) {
    this.fechaHoraLibres2 = fechaHoraLibres2;
  }

  public LocalDate getFechaHoraLibres3() {
    return fechaHoraLibres3;
  }

  public void setFechaHoraLibres3(LocalDate fechaHoraLibres3) {
    this.fechaHoraLibres3 = fechaHoraLibres3;
  }

  public LocalDate getFechaHoraClasificacion() {
    return fechaHoraClasificacion;
  }

  public void setFechaHoraClasificacion(LocalDate fechaHoraClasificacion) {
    this.fechaHoraClasificacion = fechaHoraClasificacion;
  }

  public LocalDate getFechaHoraCarrera() {
    return fechaHoraCarrera;
  }

  public void setFechaHoraCarrera(LocalDate fechaHoraCarrera) {
    this.fechaHoraCarrera = fechaHoraCarrera;
  }

  public Circuito getCircuito() {
    return circuito;
  }

  public void setCircuito(Circuito circuito) {
    this.circuito = circuito;
  }

  /*
  @PrePersist
  private void generateGPid() {
    if (this.id.getId() == null) {
      this.id.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
    }
  }*/

}

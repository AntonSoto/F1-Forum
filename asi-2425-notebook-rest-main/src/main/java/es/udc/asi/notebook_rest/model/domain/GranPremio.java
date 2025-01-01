package es.udc.asi.notebook_rest.model.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class GranPremio {

  @EmbeddedId
  private GranPremioId id;

  @MapsId("GPid")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long GPid;

  @MapsId("ano")
  @ManyToOne
  private Campeonato campeonato;

  @Column
  private Long numOrden;

  @Column(unique = true)
  private LocalDate fechaHoraLibres1;
  private LocalDate fechaHoraLibres2;
  private LocalDate fechaHoraLibres3;

  private LocalDate fechaHoraClasificacion;
  private LocalDate fechaHoraCarrera;

  public GranPremio() {
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    GranPremio that = (GranPremio) o;
    return Objects.equals(id, that.id) && Objects.equals(GPid, that.GPid) && Objects.equals(campeonato, that.campeonato) && Objects.equals(numOrden, that.numOrden) && Objects.equals(fechaHoraLibres1, that.fechaHoraLibres1) && Objects.equals(fechaHoraLibres2, that.fechaHoraLibres2) && Objects.equals(fechaHoraLibres3, that.fechaHoraLibres3) && Objects.equals(fechaHoraClasificacion, that.fechaHoraClasificacion) && Objects.equals(fechaHoraCarrera, that.fechaHoraCarrera);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, GPid, campeonato, numOrden, fechaHoraLibres1, fechaHoraLibres2, fechaHoraLibres3, fechaHoraClasificacion, fechaHoraCarrera);
  }


  public GranPremioId getId() {
    return id;
  }

  public void setId(GranPremioId id) {
    this.id = id;
  }

  public Long getGPid() {
    return GPid;
  }

  public void setGPid(Long GPid) {
    this.GPid = GPid;
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
}

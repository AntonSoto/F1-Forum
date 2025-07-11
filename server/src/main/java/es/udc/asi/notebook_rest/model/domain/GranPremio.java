package es.udc.asi.notebook_rest.model.domain;

import es.udc.asi.notebook_rest.model.service.dto.ValoracionDTO;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class GranPremio {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "ano", referencedColumnName = "ano")
  private Campeonato campeonato;

  @Column
  private Long numOrden;

  @Column(unique = true)
  private LocalDateTime fechaHoraLibres1;
  private LocalDateTime fechaHoraLibres2;
  private LocalDateTime fechaHoraLibres3;

  private LocalDateTime fechaHoraClasificacion;
  private LocalDateTime fechaHoraSprint;
  private LocalDateTime fechaHoraCarrera;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  private Circuito circuito;

  @OneToMany(fetch = FetchType.EAGER)
  private List<Valoracion> valoraciones = new ArrayList<>();

  @OneToMany
  private List<UserGP> visualizaciones = new ArrayList<>();

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

  public LocalDateTime  getFechaHoraLibres1() {
    return fechaHoraLibres1;
  }

  public void setFechaHoraLibres1(LocalDateTime  fechaHoraLibres1) {
    this.fechaHoraLibres1 = fechaHoraLibres1;
  }

  public LocalDateTime  getFechaHoraLibres2() {
    return fechaHoraLibres2;
  }

  public void setFechaHoraLibres2(LocalDateTime  fechaHoraLibres2) {
    this.fechaHoraLibres2 = fechaHoraLibres2;
  }

  public LocalDateTime  getFechaHoraLibres3() {
    return fechaHoraLibres3;
  }

  public void setFechaHoraLibres3(LocalDateTime  fechaHoraLibres3) {
    this.fechaHoraLibres3 = fechaHoraLibres3;
  }

  public LocalDateTime  getFechaHoraClasificacion() {
    return fechaHoraClasificacion;
  }

  public void setFechaHoraClasificacion(LocalDateTime  fechaHoraClasificacion) {
    this.fechaHoraClasificacion = fechaHoraClasificacion;
  }

  public LocalDateTime  getFechaHoraCarrera() {
    return fechaHoraCarrera;
  }

  public void setFechaHoraCarrera(LocalDateTime fechaHoraCarrera) {
    this.fechaHoraCarrera = fechaHoraCarrera;
  }

  public Circuito getCircuito() {
    return circuito;
  }

  public void setCircuito(Circuito circuito) {
    this.circuito = circuito;
  }

  public List<Valoracion> getValoraciones() {
    return valoraciones;
  }

  public void setValoraciones(List<Valoracion> valoraciones) {
    this.valoraciones = valoraciones;
  }

  public LocalDateTime getFechaHoraSprint() {
    return fechaHoraSprint;
  }

  public void setFechaHoraSprint(LocalDateTime fechaHoraSprint) {
    this.fechaHoraSprint = fechaHoraSprint;
  }
}

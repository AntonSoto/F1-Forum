package es.udc.asi.notebook_rest.model.service.dto;

import es.udc.asi.notebook_rest.model.domain.Campeonato;
import es.udc.asi.notebook_rest.model.domain.GranPremio;
import es.udc.asi.notebook_rest.model.domain.Note;
import es.udc.asi.notebook_rest.model.domain.Valoracion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

public class GranPremioDTO {

  private Long id;
  private LocalDateTime fechaHoraLibres1;
  private LocalDateTime  fechaHoraLibres2;
  private LocalDateTime  fechaHoraLibres3;
  private Long numOrden;
  private LocalDateTime  fechaHoraClasificacion;
  private LocalDateTime  fechaHoraSprint;
  private LocalDateTime  fechaHoraCarrera;
  private Long ano;

  private Collection<ValoracionDTO> valoraciones = new ArrayList<>();

  public GranPremioDTO() {
    super();
  }

  public GranPremioDTO(GranPremio granPremio) {
    this.id = granPremio.getId();
    this.fechaHoraLibres1 = granPremio.getFechaHoraLibres1();
    this.fechaHoraLibres2 = granPremio.getFechaHoraLibres2();
    this.fechaHoraLibres3 = granPremio.getFechaHoraLibres3();
    this.numOrden = granPremio.getNumOrden();
    this.fechaHoraClasificacion = granPremio.getFechaHoraClasificacion();
    this.fechaHoraSprint = granPremio.getFechaHoraSprint();
    this.fechaHoraCarrera = granPremio.getFechaHoraCarrera();
    this.ano = granPremio.getCampeonato().getAno();

    granPremio.getValoraciones().forEach(v -> {
      this.valoraciones.add(new ValoracionDTO(v));
    });
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getFechaHoraLibres1() {
    return fechaHoraLibres1;
  }

  public void setFechaHoraLibres1(LocalDateTime fechaHoraLibres1) {
    this.fechaHoraLibres1 = fechaHoraLibres1;
  }

  public LocalDateTime getFechaHoraLibres2() {
    return fechaHoraLibres2;
  }

  public void setFechaHoraLibres2(LocalDateTime fechaHoraLibres2) {
    this.fechaHoraLibres2 = fechaHoraLibres2;
  }

  public LocalDateTime  getFechaHoraLibres3() {
    return fechaHoraLibres3;
  }

  public void setFechaHoraLibres3(LocalDateTime fechaHoraLibres3) {
    this.fechaHoraLibres3 = fechaHoraLibres3;
  }

  public Long getNumOrden() {
    return numOrden;
  }

  public void setNumOrden(Long numOrden) {
    this.numOrden = numOrden;
  }

  public LocalDateTime getFechaHoraClasificacion() {
    return fechaHoraClasificacion;
  }

  public void setFechaHoraClasificacion(LocalDateTime fechaHoraClasificacion) {
    this.fechaHoraClasificacion = fechaHoraClasificacion;
  }

  public LocalDateTime  getFechaHoraCarrera() {
    return fechaHoraCarrera;
  }

  public void setFechaHoraCarrera(LocalDateTime fechaHoraCarrera) {
    this.fechaHoraCarrera = fechaHoraCarrera;
  }

  public Collection<ValoracionDTO> getValoraciones() {
    return valoraciones;
  }

  public void setValoraciones(Collection<ValoracionDTO> valoraciones) {
    this.valoraciones = valoraciones;
  }

  public LocalDateTime getFechaHoraSprint() {
    return fechaHoraSprint;
  }

  public void setFechaHoraSprint(LocalDateTime fechaHoraSprint) {
    this.fechaHoraSprint = fechaHoraSprint;
  }

  public Long getAno() {
    return ano;
  }

  public void setAno(Long ano) {
    this.ano = ano;
  }

}

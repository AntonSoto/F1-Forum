package es.udc.asi.notebook_rest.model.service.dto;

import es.udc.asi.notebook_rest.model.domain.Note;
import es.udc.asi.notebook_rest.model.domain.Valoracion;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

public class ValoracionDTO {

  private Long id;
  private Long puntuacion;
  private LocalDate fechaValoracion;
  private String comentario;


  public ValoracionDTO() {
    super();
  }

  public ValoracionDTO(Valoracion valoracion) {
    this.id = valoracion.getId();
    this.puntuacion = valoracion.getPuntuacion();
    this.fechaValoracion = valoracion.getFechaValoracion();
    this.comentario = valoracion.getComentario();

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
}

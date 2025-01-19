package es.udc.asi.notebook_rest.model.service.dto;

import es.udc.asi.notebook_rest.model.domain.CampeonatoPiloto;
import es.udc.asi.notebook_rest.model.domain.Constructor;
import es.udc.asi.notebook_rest.model.domain.Piloto;
import es.udc.asi.notebook_rest.model.domain.PilotoConstructor;

public class PilotoDTO {

  private String id;

  private String nombreCompleto;
  private String nacionalidad;
  private Long victorias;
  private Float puntos;
  private Long ano;
  private String constructorId;
  private String constructorNombre;
  private String constructorNacionalidad;
  private Boolean tieneImagen = false;

  public PilotoDTO() {
  }

  public PilotoDTO(Piloto piloto, CampeonatoPiloto campeonatoPiloto, Constructor constructor) {
    this.id = piloto.getId();
    this.nombreCompleto = piloto.getNombreCompleto();
    this.nacionalidad = piloto.getNacionalidad();
    this.victorias = campeonatoPiloto.getVictorias();
    this.puntos = campeonatoPiloto.getPuntos();
    this.constructorId = constructor.getId();
    this.constructorNombre = constructor.getNombre();
    if (piloto.getNombreImagen() != null) {
      this.tieneImagen = true;
    }

  }

  public PilotoDTO(Piloto piloto) {
    this.id = piloto.getId();
    this.nombreCompleto = piloto.getNombreCompleto();
    this.nacionalidad = piloto.getNacionalidad();
    if (piloto.getNombreImagen() != null) {
      this.tieneImagen = true;
    }

  }

  public PilotoDTO(
    String id, String nombreCompleto, String nacionalidad, Long victorias,
    Float puntos, Long ano, String constructorId, String constructorNombre, String constructorNacionalidad) {
    this.id = id;
    this.nombreCompleto = nombreCompleto;
    this.nacionalidad = nacionalidad;
    this.victorias = victorias;
    this.puntos = puntos;
    this.ano = ano;
    this.constructorId = constructorId;
    this.constructorNombre = constructorNombre;
    this.constructorNacionalidad = constructorNacionalidad;
  }


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNombreCompleto() {
    return nombreCompleto;
  }

  public void setNombreCompleto(String nombreCompleto) {
    this.nombreCompleto = nombreCompleto;
  }

  public String getNacionalidad() {
    return nacionalidad;
  }

  public void setNacionalidad(String nacionalidad) {
    this.nacionalidad = nacionalidad;
  }

  public String getConstructorId() {
    return constructorId;
  }

  public void setConstructorId(String constructorId) {
    this.constructorId = constructorId;
  }

  public String getConstructorNombre() {
    return constructorNombre;
  }

  public void setConstructorNombre(String constructorNombre) {
    this.constructorNombre = constructorNombre;
  }

  public String getConstructorNacionalidad() {
    return constructorNacionalidad;
  }

  public void setConstructorNacionalidad(String constructorNacionalidad) {
    this.constructorNacionalidad = constructorNacionalidad;
  }

  public Long getVictorias() {
    return victorias;
  }

  public void setVictorias(Long victorias) {
    this.victorias = victorias;
  }

  public Float getPuntos() {
    return puntos;
  }

  public void setPuntos(Float puntos) {
    this.puntos = puntos;
  }

  public Long getAno() {
    return ano;
  }

  public void setAno(Long ano) {
    this.ano = ano;
  }

  public Boolean getTieneImagen() {
    return tieneImagen;
  }

}

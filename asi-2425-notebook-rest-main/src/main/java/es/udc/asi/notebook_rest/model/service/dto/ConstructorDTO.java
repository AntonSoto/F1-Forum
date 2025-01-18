package es.udc.asi.notebook_rest.model.service.dto;

import es.udc.asi.notebook_rest.model.domain.CampeonatoConstructor;
import es.udc.asi.notebook_rest.model.domain.Circuito;
import es.udc.asi.notebook_rest.model.domain.Constructor;

public class ConstructorDTO {

  private String id;

  private String nombre;
  private String nacionalidad;
  private Long ano;
  private Long victorias;
  private Long puntos;

  public ConstructorDTO() {}

  public ConstructorDTO(Constructor constructor, CampeonatoConstructor campeonatoConstructor) {

    this.id = constructor.getId();
    this.nombre = constructor.getNombre();
    this.nacionalidad = constructor.getNacionalidad();
    this.victorias = campeonatoConstructor.getVictorias();
    this.puntos = campeonatoConstructor.getPuntos();
    this.ano = campeonatoConstructor.getCampeonato().getAno();

  }

  public ConstructorDTO(Constructor constructor) {

    this.id = constructor.getId();
    this.nombre = constructor.getNombre();
    this.nacionalidad = constructor.getNacionalidad();

  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Long getVictorias() {
    return victorias;
  }

  public void setVictorias(Long victorias) {
    this.victorias = victorias;
  }

  public String getNacionalidad() {
    return nacionalidad;
  }

  public void setNacionalidad(String nacionalidad) {
    this.nacionalidad = nacionalidad;
  }

  public Long getPuntos() {
    return puntos;
  }

  public void setPuntos(Long puntos) {
    this.puntos = puntos;
  }

  public Long getAno() {
    return ano;
  }

  public void setAno(Long ano) {
    this.ano = ano;
  }
}

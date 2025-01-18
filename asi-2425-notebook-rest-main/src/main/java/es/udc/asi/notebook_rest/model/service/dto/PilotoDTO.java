package es.udc.asi.notebook_rest.model.service.dto;

import es.udc.asi.notebook_rest.model.domain.CampeonatoPiloto;
import es.udc.asi.notebook_rest.model.domain.Piloto;
import es.udc.asi.notebook_rest.model.domain.PilotoConstructor;

public class PilotoDTO {

  private String id;

  private String nombreCompleto;
  private String nacionalidad;
  private CampeonatoPiloto campeonatoPiloto;
  private PilotoConstructor pilotoConstructor;
  private String constructorId;
  private String constructorNombre;
  private String constructorNacionalidad;


  public PilotoDTO(Piloto piloto, CampeonatoPiloto campeonatoPiloto, PilotoConstructor pilotoConstructor) {
    this.id = piloto.getId();
    this.nombreCompleto = piloto.getNombreCompleto();
    this.nacionalidad = piloto.getNacionalidad();
    this.campeonatoPiloto = campeonatoPiloto;
    this.pilotoConstructor = pilotoConstructor;
  }

  public PilotoDTO(Piloto piloto) {
    this.id = piloto.getId();
    this.nombreCompleto = piloto.getNombreCompleto();
    this.nacionalidad = piloto.getNacionalidad();
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

  public CampeonatoPiloto getCampeonatoPiloto() {
    return campeonatoPiloto;
  }

  public void setCampeonatoPiloto(CampeonatoPiloto campeonatoPiloto) {
    this.campeonatoPiloto = campeonatoPiloto;
  }

  public PilotoConstructor getPilotoConstructor() {
    return pilotoConstructor;
  }

  public void setPilotoConstructor(PilotoConstructor pilotoConstructor) {
    this.pilotoConstructor = pilotoConstructor;
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
}

package es.udc.asi.notebook_rest.model.service.dto;

import es.udc.asi.notebook_rest.model.domain.Circuito;
import es.udc.asi.notebook_rest.model.domain.GranPremio;
import es.udc.asi.notebook_rest.model.domain.Note;
import jakarta.persistence.Column;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CircuitoDTO {

  private String id;
  private String nombreCircuito;
  private String Latitud;
  private String Longitud;
  private String Localidad;
  private String Pais;

  public Collection<GranPremioDTO> getGrandesPremios() {
    return grandesPremios;
  }

  public void setGrandesPremios(Collection<GranPremioDTO> grandesPremios) {
    this.grandesPremios = grandesPremios;
  }

  private Collection<GranPremioDTO> grandesPremios = new ArrayList<>();

  public CircuitoDTO() {
    super();
  }

  public CircuitoDTO(Circuito circuito) {
    this.id = circuito.getId();
    this.nombreCircuito = circuito.getNombreCircuito();
    this.Latitud = circuito.getLatitud();
    this.Longitud = circuito.getLongitud();
    circuito.getGrandesPremios().forEach(gp -> {
      this.grandesPremios.add(new GranPremioDTO(gp));
    });
    this.Localidad = circuito.getLocalidad();
    this.Pais = circuito.getPais();
  }

  public String getNombreCircuito() {
    return nombreCircuito;
  }

  public void setNombreCircuito(String nombreCircuito) {
    this.nombreCircuito = nombreCircuito;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLatitud() {
    return Latitud;
  }

  public void setLatitud(String latitud) {
    Latitud = latitud;
  }

  public String getLongitud() {
    return Longitud;
  }

  public void setLongitud(String longitud) {
    Longitud = longitud;
  }

  public String getLocalidad() {
    return Localidad;
  }

  public void setLocalidad(String localidad) {
    Localidad = localidad;
  }

  public String getPais() {
    return Pais;
  }

  public void setPais(String pais) {
    Pais = pais;
  }
}

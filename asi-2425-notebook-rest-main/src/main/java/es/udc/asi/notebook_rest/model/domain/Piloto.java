package es.udc.asi.notebook_rest.model.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Piloto {

  @Id
  private String id;

  @Column
  private String nombreCompleto;
  private String nacionalidad;

  @OneToMany(mappedBy = "piloto")
  private List<CampeonatoPiloto> campeonatoPilotos = new ArrayList<>();

  @OneToMany(mappedBy = "piloto")
  private List<PilotoConstructor> pilotoConstructor = new ArrayList<>();

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Piloto piloto = (Piloto) o;
    return Objects.equals(id, piloto.id) && Objects.equals(nombreCompleto, piloto.nombreCompleto) && Objects.equals(nacionalidad, piloto.nacionalidad) && Objects.equals(campeonatoPilotos, piloto.campeonatoPilotos) && Objects.equals(pilotoConstructor, piloto.pilotoConstructor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nombreCompleto, nacionalidad, campeonatoPilotos, pilotoConstructor);
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

  public List<CampeonatoPiloto> getCampeonatoPilotos() {
    return campeonatoPilotos;
  }

  public void setCampeonatoPilotos(List<CampeonatoPiloto> campeonatoPilotos) {
    this.campeonatoPilotos = campeonatoPilotos;
  }

  public List<PilotoConstructor> getPilotoConstructor() {
    return pilotoConstructor;
  }

  public void setPilotoConstructor(List<PilotoConstructor> pilotoConstructor) {
    this.pilotoConstructor = pilotoConstructor;
  }
}

package es.udc.asi.notebook_rest.model.domain;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Constructor {

  @Id
  private String id;

  @Column
  private String nombre;
  private String nacionalidad;

  @OneToMany(mappedBy = "constructor", fetch = FetchType.EAGER)
  private List<CampeonatoConstructor> campeonatoPConstructores = new ArrayList<>();

  @OneToMany(mappedBy = "constructor", fetch = FetchType.EAGER)
  private List<PilotoConstructor> constructorPiloto = new ArrayList<>();

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Constructor that = (Constructor) o;
    return Objects.equals(id, that.id) && Objects.equals(nombre, that.nombre) && Objects.equals(nacionalidad, that.nacionalidad) && Objects.equals(campeonatoPConstructores, that.campeonatoPConstructores) && Objects.equals(constructorPiloto, that.constructorPiloto);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nombre, nacionalidad, campeonatoPConstructores, constructorPiloto);
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

  public String getNacionalidad() {
    return nacionalidad;
  }

  public void setNacionalidad(String nacionalidad) {
    this.nacionalidad = nacionalidad;
  }

  public List<CampeonatoConstructor> getCampeonatoPConstructores() {
    return campeonatoPConstructores;
  }

  public void setCampeonatoPConstructores(List<CampeonatoConstructor> campeonatoPConstructores) {
    this.campeonatoPConstructores = campeonatoPConstructores;
  }

  public List<PilotoConstructor> getConstructorPiloto() {
    return constructorPiloto;
  }

  public void setConstructorPiloto(List<PilotoConstructor> constructorPiloto) {
    this.constructorPiloto = constructorPiloto;
  }
}

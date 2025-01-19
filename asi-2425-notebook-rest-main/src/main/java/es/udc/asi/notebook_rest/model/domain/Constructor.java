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

  private String nombreImagen;

  @OneToMany(mappedBy = "constructor", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<CampeonatoConstructor> campeonatoConstructores = new ArrayList<>();

  @OneToMany(mappedBy = "constructor", fetch = FetchType.EAGER)
  private List<PilotoConstructor> constructorPiloto = new ArrayList<>();

  public Constructor() {}

  public Constructor(String id, String nombre, String nacionalidad) {

    this.id = id;
    this.nombre = nombre;
    this.nacionalidad = nacionalidad;

  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Constructor that = (Constructor) o;
    return Objects.equals(id, that.id) && Objects.equals(nombre, that.nombre) && Objects.equals(nacionalidad, that.nacionalidad) && Objects.equals(campeonatoConstructores, that.campeonatoConstructores) && Objects.equals(constructorPiloto, that.constructorPiloto);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nombre, nacionalidad, campeonatoConstructores, constructorPiloto);
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

  public List<CampeonatoConstructor> getCampeonatoConstructores() {
    return campeonatoConstructores;
  }

  public void setCampeonatoConstructores(List<CampeonatoConstructor> campeonatoPConstructores) {
    this.campeonatoConstructores = campeonatoPConstructores;
  }

  public List<PilotoConstructor> getConstructorPiloto() {
    return constructorPiloto;
  }

  public void setConstructorPiloto(List<PilotoConstructor> constructorPiloto) {
    this.constructorPiloto = constructorPiloto;
  }

  public String getNombreImagen() {
    return nombreImagen;
  }

  public void setNombreImagen(String nombreImagen) {
    this.nombreImagen = nombreImagen;
  }

}

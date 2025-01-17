package es.udc.asi.notebook_rest.model.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Circuito {
  @Id
  private String id;

  @Column
  private String nombreCircuito;

  @Column
  private String Latitud;

  @Column
  private String Longitud;

  @Column
  private String Localidad;

  @Column
  private String Pais;

  @OneToMany(mappedBy = "circuito", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
  private List<GranPremio> grandesPremios = new ArrayList<>();

  public Circuito() {
    super();
  }

  public Circuito(String id, String nombreCircuito, String Latitud, String Longitud, String Localidad, String Pais) {
    super();
    this.id = id;
    this.nombreCircuito = nombreCircuito;
    this.Latitud = Latitud;
    this.Longitud = Longitud;
    this.Localidad = Localidad;
    this.Pais = Pais;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Circuito other = (Circuito) obj;
    return Objects.equals(id, other.id);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNombreCircuito() {
    return nombreCircuito;
  }

  public void setNombreCircuito(String nombreCircuito) {
    this.nombreCircuito = nombreCircuito;
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

  public List<GranPremio> getGrandesPremios() {
    return grandesPremios;
  }

  public void setGrandesPremios(List<GranPremio> grandesPremios) {
    this.grandesPremios = grandesPremios;
  }

}

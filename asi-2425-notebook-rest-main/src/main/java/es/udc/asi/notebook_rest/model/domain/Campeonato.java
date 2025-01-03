package es.udc.asi.notebook_rest.model.domain;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class Campeonato {

  @Id
  private Long ano;

  @OneToMany(mappedBy = "campeonato")
  private List<CampeonatoConstructor> campeonatoConstructors = new ArrayList<>();

  @OneToMany(mappedBy = "campeonato")
  private List<CampeonatoPiloto> campeonatoPilotos = new ArrayList<>();

  @OneToMany(mappedBy = "campeonato")
  private List<GranPremio> granPremios = new ArrayList<>();

  public Campeonato() {
    super();
  }

  public Campeonato(Long ano) {
    super();
    this.ano = ano;
  }

  @Override
  public int hashCode() {
    return Objects.hash(ano);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Campeonato other = (Campeonato) obj;
    return Objects.equals(ano, other.ano);
  }

  public Long getAno() {
    return ano;
  }

  public void setAno(Long ano) {
    this.ano = ano;
  }

  public List<CampeonatoConstructor> getCampeonatoConstructors() {
    return campeonatoConstructors;
  }

  public void setCampeonatoConstructors(List<CampeonatoConstructor> campeonatoConstructors) {
    this.campeonatoConstructors = campeonatoConstructors;
  }

  public List<CampeonatoPiloto> getCampeonatoPilotos() {
    return campeonatoPilotos;
  }

  public void setCampeonatoPilotos(List<CampeonatoPiloto> campeonatoPilotos) {
    this.campeonatoPilotos = campeonatoPilotos;
  }

  public List<GranPremio> getGranPremios() {
    return granPremios;
  }

  public void setGranPremios(List<GranPremio> granPremios) {
    this.granPremios = granPremios;
  }
}

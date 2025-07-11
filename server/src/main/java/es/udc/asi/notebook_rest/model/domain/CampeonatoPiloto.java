package es.udc.asi.notebook_rest.model.domain;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class CampeonatoPiloto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private Float puntos;
  private Long victorias;

  @ManyToOne
  private Campeonato campeonato;

  @ManyToOne
  private Piloto piloto;

  public CampeonatoPiloto() {}

  public CampeonatoPiloto(Float puntos, Long victorias, Campeonato campeonato, Piloto piloto) {

    this.puntos = puntos;
    this.victorias = victorias;
    this.campeonato = campeonato;
    this.piloto = piloto;

  }

  public CampeonatoPiloto(Float puntos, Long victorias) {

    this.puntos = puntos;
    this.victorias = victorias;

  }


  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    CampeonatoPiloto that = (CampeonatoPiloto) o;
    return Objects.equals(id, that.id) && Objects.equals(puntos, that.puntos) && Objects.equals(victorias, that.victorias) && Objects.equals(campeonato, that.campeonato) && Objects.equals(piloto, that.piloto);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, puntos, victorias, campeonato, piloto);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Float getPuntos() {
    return puntos;
  }

  public void setPuntos(Float puntos) {
    this.puntos = puntos;
  }

  public Long getVictorias() {
    return victorias;
  }

  public void setVictorias(Long victorias) {
    this.victorias = victorias;
  }

  public Campeonato getCampeonato() {
    return campeonato;
  }

  public void setCampeonato(Campeonato campeonato) {
    this.campeonato = campeonato;
  }

  public Piloto getPiloto() {
    return piloto;
  }

  public void setPiloto(Piloto piloto) {
    this.piloto = piloto;
  }
}

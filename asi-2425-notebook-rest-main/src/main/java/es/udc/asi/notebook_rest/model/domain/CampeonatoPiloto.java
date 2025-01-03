package es.udc.asi.notebook_rest.model.domain;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class CampeonatoPiloto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private Long puntos;
  private Long victorias;

  @ManyToOne
  private Campeonato campeonato;

  @ManyToOne
  private Piloto piloto;


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

  public Long getPuntos() {
    return puntos;
  }

  public void setPuntos(Long puntos) {
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

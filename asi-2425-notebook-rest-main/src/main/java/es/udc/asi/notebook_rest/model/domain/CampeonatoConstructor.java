package es.udc.asi.notebook_rest.model.domain;


import jakarta.persistence.*;

@Entity
public class CampeonatoConstructor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private Long puntos;
  private Long victorias;

  @ManyToOne
  private Campeonato campeonato;

  @ManyToOne
  private Constructor constructor;

  public CampeonatoConstructor() {}

  public CampeonatoConstructor(Campeonato campeonato, Constructor constructor) {

    this.campeonato = campeonato;
    this.constructor = constructor;

  }

  public CampeonatoConstructor(Long puntos, Long victorias) {

    this.puntos = puntos;
    this.victorias = victorias;

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getVictorias() {
    return victorias;
  }

  public void setVictorias(Long victorias) {
    this.victorias = victorias;
  }

  public Long getPuntos() {
    return puntos;
  }

  public void setPuntos(Long puntos) {
    this.puntos = puntos;
  }

  public Campeonato getCampeonato() {
    return campeonato;
  }

  public void setCampeonato(Campeonato campeonato) {
    this.campeonato = campeonato;
  }

  public Constructor getConstructor() {
    return constructor;
  }

  public void setConstructor(Constructor constructor) {
    this.constructor = constructor;
  }
}

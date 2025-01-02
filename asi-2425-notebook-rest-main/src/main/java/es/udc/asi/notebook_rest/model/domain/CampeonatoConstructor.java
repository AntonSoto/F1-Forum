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

}

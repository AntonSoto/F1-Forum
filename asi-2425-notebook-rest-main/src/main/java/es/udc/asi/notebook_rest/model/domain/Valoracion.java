package es.udc.asi.notebook_rest.model.domain;


import jakarta.persistence.*;

@Entity
public class Valoracion {

  @EmbeddedId
  private ValoracionId id;

  @MapsId("ValoracionId")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long ValoracionId;

  @MapsId("granPremioId")
  @ManyToOne
  private Campeonato campeonato;


}

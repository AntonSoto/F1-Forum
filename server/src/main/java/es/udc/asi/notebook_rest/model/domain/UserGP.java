package es.udc.asi.notebook_rest.model.domain;

import jakarta.persistence.*;

@Entity
public class UserGP {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private User user;

  @ManyToOne
  private GranPremio granPremio;

  public UserGP() {}

  public UserGP(User user, GranPremio granPremio) {
    this.user = user;
    this.granPremio = granPremio;
  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public GranPremio getGranPremio() {
    return granPremio;
  }

  public void setGranPremio(GranPremio granPremio) {
    this.granPremio = granPremio;
  }
}

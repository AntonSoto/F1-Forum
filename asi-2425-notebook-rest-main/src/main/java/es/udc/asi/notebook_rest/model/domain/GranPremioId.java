package es.udc.asi.notebook_rest.model.domain;


import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GranPremioId implements Serializable {

  private Long ano;
  private Long GPid;

  public Long getAno() {
    return ano;
  }

  public void setAno(Long ano) {
    this.ano = ano;
  }

  public Long getId() {
    return GPid;
  }

  public void setId(Long id) {
    this.GPid = id;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    GranPremioId that = (GranPremioId) o;
    return Objects.equals(ano, that.ano) && Objects.equals(GPid, that.GPid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ano, GPid);
  }
}

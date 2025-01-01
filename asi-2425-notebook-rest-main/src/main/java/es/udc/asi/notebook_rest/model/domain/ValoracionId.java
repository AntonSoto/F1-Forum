package es.udc.asi.notebook_rest.model.domain;


import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ValoracionId implements Serializable {

  private GranPremioId granPremioId;
  private Long ValoracionId;


  public GranPremioId getGranPremioId() {
    return granPremioId;
  }

  public void setGranPremioId(GranPremioId granPremioId) {
    this.granPremioId = granPremioId;
  }

  public Long getValoracionId() {
    return ValoracionId;
  }

  public void setValoracionId(Long valoracionId) {
    ValoracionId = valoracionId;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    ValoracionId that = (ValoracionId) o;
    return Objects.equals(granPremioId, that.granPremioId) && Objects.equals(ValoracionId, that.ValoracionId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(granPremioId, ValoracionId);
  }
}

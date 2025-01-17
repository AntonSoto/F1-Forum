package es.udc.asi.notebook_rest.model.service.dto;

import es.udc.asi.notebook_rest.model.domain.Campeonato;
import es.udc.asi.notebook_rest.model.domain.Category;

public class CampeonatoDTO {

  private Long ano;

  public CampeonatoDTO() {
    super();
  }

  public CampeonatoDTO(Campeonato c) {
    this.ano = c.getAno();
  }

  public Long getAno() {
    return ano;
  }

  public void setAno(Long ano) {
    this.ano = ano;
  }
}

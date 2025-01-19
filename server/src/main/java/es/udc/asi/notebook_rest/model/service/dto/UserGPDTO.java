package es.udc.asi.notebook_rest.model.service.dto;

import es.udc.asi.notebook_rest.model.domain.UserGP;

public class UserGPDTO {

  private Long id;

  private Long userId;

  private Long gpId;

  public UserGPDTO() {}
  public UserGPDTO(Long id, Long userId, Long gpId) {
    this.id = id;
    this.userId = userId;
    this.gpId = gpId;
  }

  public UserGPDTO(UserGP userGP) {
    this.id = userGP.getId();
    this.userId = userGP.getUser().getId();
    this.gpId = userGP.getGranPremio().getId();
  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getGpId() {
    return gpId;
  }

  public void setGpId(Long gpId) {
    this.gpId = gpId;
  }
}

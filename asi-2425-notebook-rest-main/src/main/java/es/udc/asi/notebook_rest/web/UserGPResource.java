package es.udc.asi.notebook_rest.web;


import es.udc.asi.notebook_rest.model.exception.NotFoundException;
import es.udc.asi.notebook_rest.model.exception.OperationNotAllowed;
import es.udc.asi.notebook_rest.model.service.NoteService;
import es.udc.asi.notebook_rest.model.service.UserGPService;
import es.udc.asi.notebook_rest.model.service.dto.NoteDTO;
import es.udc.asi.notebook_rest.model.service.dto.UserGPDTO;
import es.udc.asi.notebook_rest.model.service.dto.ValoracionDTO;
import es.udc.asi.notebook_rest.web.exceptions.RequestBodyNotValidException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/visualizaciones")
public class UserGPResource {

  @Autowired
  private UserGPService userGPService;

  @GetMapping("/{GPid}")
  public Collection<UserGPDTO> findOneByUserAndGP(@PathVariable Long GPid) throws NotFoundException {
    return userGPService.findOneByUserAndGP(GPid);
  }

  @PostMapping
  public UserGPDTO create(@RequestBody @Valid UserGPDTO userGP, Errors errors) throws RequestBodyNotValidException {
    if (errors.hasErrors()) {
      throw new RequestBodyNotValidException(errors);
    }

    return userGPService.create(userGP);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) throws NotFoundException, OperationNotAllowed {
    userGPService.deleteById(id);
  }

}

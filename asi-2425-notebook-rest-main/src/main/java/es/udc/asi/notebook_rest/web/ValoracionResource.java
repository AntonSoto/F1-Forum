package es.udc.asi.notebook_rest.web;


import es.udc.asi.notebook_rest.model.domain.Note;
import es.udc.asi.notebook_rest.model.exception.NotFoundException;
import es.udc.asi.notebook_rest.model.exception.OperationNotAllowed;
import es.udc.asi.notebook_rest.model.service.NoteService;
import es.udc.asi.notebook_rest.model.service.ValoracionService;
import es.udc.asi.notebook_rest.model.service.dto.NoteDTO;
import es.udc.asi.notebook_rest.model.service.dto.ValoracionDTO;
import es.udc.asi.notebook_rest.web.exceptions.IdAndBodyNotMatchingOnUpdateException;
import es.udc.asi.notebook_rest.web.exceptions.RequestBodyNotValidException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/valoraciones")
public class ValoracionResource {

  @Autowired
  private ValoracionService valoracionService;

  @PostMapping
  public ValoracionDTO create(@RequestBody @Valid ValoracionDTO valoracion, Errors errors) throws RequestBodyNotValidException {
    if (errors.hasErrors()) {
      throw new RequestBodyNotValidException(errors);
    }

    return valoracionService.create(valoracion);
  }

  @PutMapping("/{id}")
  public ValoracionDTO update(@PathVariable Long id, @RequestBody @Valid ValoracionDTO valoracion, Errors errors)
    throws RequestBodyNotValidException, IdAndBodyNotMatchingOnUpdateException, NotFoundException,
    OperationNotAllowed {
    if (errors.hasErrors()) {
      throw new RequestBodyNotValidException(errors);
    }

    if (id != valoracion.getId()) {
      throw new IdAndBodyNotMatchingOnUpdateException(Note.class);
    }

    return valoracionService.update(valoracion);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) throws NotFoundException, OperationNotAllowed {
    valoracionService.deleteById(id);
  }

  @GetMapping("granPremio/{id}")
  public Collection<ValoracionDTO> findAllByGranPremio(@PathVariable Long id) {
    return valoracionService.findAllByGranPremio(id);
  }

  @GetMapping("/usuario")
  public Collection<ValoracionDTO> findAllByUser() {
    return valoracionService.findAllByUser();
  }

}

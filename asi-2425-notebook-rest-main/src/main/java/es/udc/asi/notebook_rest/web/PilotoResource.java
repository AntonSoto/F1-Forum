package es.udc.asi.notebook_rest.web;


import es.udc.asi.notebook_rest.model.exception.NotFoundException;
import es.udc.asi.notebook_rest.model.service.NoteService;
import es.udc.asi.notebook_rest.model.service.PilotoService;
import es.udc.asi.notebook_rest.model.service.dto.NoteDTO;
import es.udc.asi.notebook_rest.model.service.dto.PilotoDTO;
import es.udc.asi.notebook_rest.model.service.dto.ValoracionDTO;
import es.udc.asi.notebook_rest.web.exceptions.RequestBodyNotValidException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/pilotos")
public class PilotoResource {

  @Autowired
  private PilotoService pilotoService;

  @PostMapping
  public PilotoDTO create(@RequestBody @Valid PilotoDTO piloto, Errors errors) throws RequestBodyNotValidException {
    if (errors.hasErrors()) {
      throw new RequestBodyNotValidException(errors);
    }

    return pilotoService.create(piloto);
  }

  @GetMapping("/{ano}")
  public Collection<PilotoDTO> findOne(@PathVariable Long ano) throws NotFoundException {
    return pilotoService.findByCampeonatoAno(ano);
  }

}

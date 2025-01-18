package es.udc.asi.notebook_rest.web;


import es.udc.asi.notebook_rest.model.exception.NotFoundException;
import es.udc.asi.notebook_rest.model.service.ConstructorService;
import es.udc.asi.notebook_rest.model.service.dto.ConstructorDTO;
import es.udc.asi.notebook_rest.model.service.dto.NoteDTO;
import es.udc.asi.notebook_rest.model.service.dto.ValoracionDTO;
import es.udc.asi.notebook_rest.web.exceptions.RequestBodyNotValidException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/constructores")
public class ConstructorResource {

  @Autowired
  private ConstructorService constructorService;

  @GetMapping("/{ano}")
  public Collection<ConstructorDTO> findByCampeonatoAno(@PathVariable Long ano) throws NotFoundException {
    return constructorService.findByCampeonatoAno(ano);
  }

  @PostMapping
  public ConstructorDTO create(@RequestBody @Valid ConstructorDTO constructor, Errors errors) throws RequestBodyNotValidException {
    if (errors.hasErrors()) {
      throw new RequestBodyNotValidException(errors);
    }

    return constructorService.create(constructor);
  }

}

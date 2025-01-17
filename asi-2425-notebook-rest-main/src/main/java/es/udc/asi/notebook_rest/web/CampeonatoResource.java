package es.udc.asi.notebook_rest.web;


import es.udc.asi.notebook_rest.model.exception.NotFoundException;
import es.udc.asi.notebook_rest.model.service.CampeonatoService;
import es.udc.asi.notebook_rest.model.service.CategoryService;
import es.udc.asi.notebook_rest.model.service.dto.CampeonatoDTO;
import es.udc.asi.notebook_rest.model.service.dto.CategoryDTO;
import es.udc.asi.notebook_rest.web.exceptions.RequestBodyNotValidException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/campeonato")
public class CampeonatoResource {

  @Autowired
  private CampeonatoService campeonatoService;

  @GetMapping("/{id}")
  public CampeonatoDTO findOne(@PathVariable Long ano) throws NotFoundException {
    return campeonatoService.findById(ano);
  }

  @PostMapping
  public CampeonatoDTO create(@RequestBody @Valid CampeonatoDTO campeonato, Errors errors)
    throws RequestBodyNotValidException {
    if (errors.hasErrors()) {
      throw new RequestBodyNotValidException(errors);
    }

    return campeonatoService.create(campeonato);
  }
}

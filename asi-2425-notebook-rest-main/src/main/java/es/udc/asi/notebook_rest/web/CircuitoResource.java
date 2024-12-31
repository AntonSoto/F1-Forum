package es.udc.asi.notebook_rest.web;

import es.udc.asi.notebook_rest.model.exception.NotFoundException;
import es.udc.asi.notebook_rest.model.service.CircuitoService;
import es.udc.asi.notebook_rest.model.service.NoteService;
import es.udc.asi.notebook_rest.model.service.dto.CircuitoDTO;
import es.udc.asi.notebook_rest.model.service.dto.NoteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/circuitos")
public class CircuitoResource {

  @Autowired
  private CircuitoService circuitoService;

  @GetMapping
  public Collection<CircuitoDTO> findAll(@RequestParam(required = false) String category) {
    return circuitoService.findAll();
  }

  @GetMapping("/{id}")
  public CircuitoDTO findOne(@PathVariable String id) throws NotFoundException {
    return circuitoService.findById(id);
  }

}

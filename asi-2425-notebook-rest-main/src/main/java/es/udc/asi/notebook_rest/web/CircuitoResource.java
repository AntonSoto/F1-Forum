package es.udc.asi.notebook_rest.web;

import es.udc.asi.notebook_rest.model.exception.ModelException;
import es.udc.asi.notebook_rest.model.exception.NotFoundException;
import es.udc.asi.notebook_rest.model.service.CircuitoService;
import es.udc.asi.notebook_rest.model.service.NoteService;
import es.udc.asi.notebook_rest.model.service.dto.CircuitoDTO;
import es.udc.asi.notebook_rest.model.service.dto.ImageDTO;
import es.udc.asi.notebook_rest.model.service.dto.NoteDTO;
import es.udc.asi.notebook_rest.web.exceptions.RequestBodyNotValidException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

  @PostMapping
  public CircuitoDTO create(@RequestBody @Valid CircuitoDTO circuito, Errors errors) throws RequestBodyNotValidException {
    if (errors.hasErrors()) {
      throw new RequestBodyNotValidException(errors);
    }

    return circuitoService.create(circuito);
  }

  @GetMapping("/{id}/imagen")
  public void recuperarImagenDeCircuito(@PathVariable String id, HttpServletResponse response) throws ModelException {
    ImageDTO imagen = circuitoService.recuperarImagenDeCircuito(id);

    try {
      response.setHeader("Content-disposition", "filename=" + imagen.getFilename());
      response.setContentType(imagen.getMimeType());
      IOUtils.copy(imagen.getInputStream(), response.getOutputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @PostMapping("/{id}/imagen")
  public void guardarImagenDeCircuito(@PathVariable String id, @RequestParam MultipartFile file) throws ModelException {
    circuitoService.guardarImagenDeCircuito(id, file);
  }


}

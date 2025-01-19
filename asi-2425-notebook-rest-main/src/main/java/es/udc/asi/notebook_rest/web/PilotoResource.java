package es.udc.asi.notebook_rest.web;


import es.udc.asi.notebook_rest.model.exception.ModelException;
import es.udc.asi.notebook_rest.model.exception.NotFoundException;
import es.udc.asi.notebook_rest.model.service.NoteService;
import es.udc.asi.notebook_rest.model.service.PilotoService;
import es.udc.asi.notebook_rest.model.service.dto.ImageDTO;
import es.udc.asi.notebook_rest.model.service.dto.NoteDTO;
import es.udc.asi.notebook_rest.model.service.dto.PilotoDTO;
import es.udc.asi.notebook_rest.model.service.dto.ValoracionDTO;
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

  @GetMapping("/{id}/imagen")
  public void recuperarImagenDePiloto(@PathVariable String id, HttpServletResponse response) throws ModelException {
    ImageDTO imagen = pilotoService.recuperarImagenDePiloto(id);

    try {
      response.setHeader("Content-disposition", "filename=" + imagen.getFilename());
      response.setContentType(imagen.getMimeType());
      IOUtils.copy(imagen.getInputStream(), response.getOutputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @PostMapping("/{id}/imagen")
  public void guardarImagenDePiloto(@PathVariable String id, @RequestParam MultipartFile file) throws ModelException {
    pilotoService.guardarImagenDePiloto(id, file);
  }


  @DeleteMapping("/{id}/imagen")
  public void eliminarImagenDePiloto(@PathVariable String id, @RequestParam MultipartFile file) throws ModelException {
    pilotoService.eliminarImagenDePiloto(id);
  }
}

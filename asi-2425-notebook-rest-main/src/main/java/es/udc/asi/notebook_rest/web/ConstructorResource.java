package es.udc.asi.notebook_rest.web;


import es.udc.asi.notebook_rest.model.exception.ModelException;
import es.udc.asi.notebook_rest.model.exception.NotFoundException;
import es.udc.asi.notebook_rest.model.service.ConstructorService;
import es.udc.asi.notebook_rest.model.service.dto.ConstructorDTO;
import es.udc.asi.notebook_rest.model.service.dto.ImageDTO;
import es.udc.asi.notebook_rest.model.service.dto.NoteDTO;
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

  @GetMapping("/{id}/imagen")
  public void recuperarImagenDeConstructor(@PathVariable String id, HttpServletResponse response) throws ModelException {
    ImageDTO imagen = constructorService.recuperarImagenDeConstructor(id);

    try {
      response.setHeader("Content-disposition", "filename=" + imagen.getFilename());
      response.setContentType(imagen.getMimeType());
      IOUtils.copy(imagen.getInputStream(), response.getOutputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @PostMapping("/{id}/imagen")
  public void guardarImagenDeConstructor(@PathVariable String id, @RequestParam MultipartFile file) throws ModelException {
    constructorService.guardarImagenDeConstructor(id, file);
  }

  @DeleteMapping("/{id}/imagen")
  public void eliminarImagenDeConstructor(@PathVariable String id, @RequestParam MultipartFile file) throws ModelException {
    constructorService.eliminarImagenDeConstructor(id);
  }


}

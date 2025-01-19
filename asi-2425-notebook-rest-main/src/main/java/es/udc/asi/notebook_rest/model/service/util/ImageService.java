package es.udc.asi.notebook_rest.model.service.util;

import org.springframework.web.multipart.MultipartFile;

import es.udc.asi.notebook_rest.model.exception.ModelException;
import es.udc.asi.notebook_rest.model.service.dto.ImageDTO;

public interface ImageService {

  String saveImage(MultipartFile file, String id, String folderName) throws ModelException;

  ImageDTO getImage(String id, String nombreImagen) throws ModelException;

  void deleteImage(String id, String folderName) throws ModelException;

}


package es.udc.asi.notebook_rest.model.service.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import es.udc.asi.notebook_rest.config.Properties;
import es.udc.asi.notebook_rest.model.exception.ModelException;
import es.udc.asi.notebook_rest.model.service.dto.ImageDTO;

@Service
public class ImageServiceFileSystem implements ImageService {

  @Autowired
  private Properties properties;

  private Path rootLocation;

  @Override
  public String saveImage(MultipartFile file, String id, String folderName) throws ModelException {
    String filename = StringUtils.cleanPath(file.getOriginalFilename());
    Path folderPath = getRootLocation().resolve(folderName);

    try {
      // Crear la carpeta si no existe
      if (!Files.exists(folderPath)) {
        Files.createDirectories(folderPath);
      }

      // Ruta completa del archivo
      Path filePath = folderPath.resolve(id + getExtension(filename));

      // Guardar el archivo
      try (InputStream is = file.getInputStream()) {
        Files.copy(is, filePath, StandardCopyOption.REPLACE_EXISTING);
      }

      return folderName + "/" + filePath.getFileName().toString();
    } catch (IOException e) {
      e.printStackTrace();
      throw new ModelException("Se ha producido un error al procesar la imagen: " + e.getMessage());
    }
  }

  private String getExtension(String filename) {
    return filename.substring(filename.lastIndexOf("."));
  }

  private Path getRootLocation() {
    if (rootLocation == null) {
      this.rootLocation = Paths.get(properties.getRutaImagenes());
    }
    return this.rootLocation;
  }

  @Override
  public ImageDTO getImage(String id, String nombreImagen) throws ModelException {
    try {
      InputStream is = new FileInputStream(properties.getRutaImagenes() + id + getExtension(nombreImagen));

      byte[] buffer = new byte[1024];
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      int len;
      while ((len = is.read(buffer)) > -1) {
        os.write(buffer, 0, len);
      }
      InputStream imageIs = new ByteArrayInputStream(os.toByteArray());
      os.flush();
      is.close();

      return new ImageDTO(imageIs, nombreImagen, getImageMediaType(nombreImagen));
    } catch (IOException e) {
      e.printStackTrace();
      throw new ModelException("se ha producido algún error al recuperar la imagen");
    }
  }

  private String getImageMediaType(String nombreImagen) {
    String extension = getExtension(nombreImagen);
    switch (extension.toLowerCase()) {
      case ".png":
        return MediaType.IMAGE_PNG_VALUE;
      case ".gif":
        return MediaType.IMAGE_GIF_VALUE;
      case ".jpg":
      case ".jpeg":
      default:
        return MediaType.IMAGE_JPEG_VALUE;
    }
  }

  @Override
  public void deleteImage(String id, String folderName) throws ModelException {
    try {
      Path folderPath = getRootLocation().resolve(folderName);

      try (DirectoryStream<Path> stream = Files.newDirectoryStream(folderPath, id + "*")) {
        for (Path entry : stream) {
          Files.deleteIfExists(entry);
          break;
        }
      }

      if (Files.exists(folderPath) && Files.isDirectory(folderPath)) {
        if (Files.list(folderPath).count() == 0) {
          Files.delete(folderPath);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw new ModelException("Se ha producido un error al intentar eliminar la imagen: " + e.getMessage());
    }
  }

}

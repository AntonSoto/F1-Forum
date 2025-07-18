package es.udc.asi.notebook_rest.web.exceptions;

import java.util.stream.Collectors;

import org.springframework.validation.Errors;

public class RequestBodyNotValidException extends ResourceException {
  public RequestBodyNotValidException(Errors errors) {
    super(errors.getFieldErrors().stream()
        .map(fe -> String.format("%s.%s %s", fe.getObjectName(), fe.getField(), fe.getDefaultMessage()))
        .collect(Collectors.joining("; ")));
  }
}

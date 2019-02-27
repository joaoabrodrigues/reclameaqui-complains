package br.com.reclameaqui.complaints.exception.handler;

import br.com.reclameaqui.complaints.exception.ErrorDTO;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(Exception ex, HttpServletRequest request) {
        return createResponse(request, Collections.singletonList(ex.getMessage()));
    }

    @ExceptionHandler(RepositoryConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintException(RepositoryConstraintViolationException ex, HttpServletRequest request) {
        return createResponse(request, ex.getErrors().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList()));
    }

    private ResponseEntity<Object> createResponse(HttpServletRequest request, List<String> errors) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorDTO.builder()
                .timestamp(System.currentTimeMillis())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .path(request.getRequestURI())
                .errors(errors)
                .build());
    }
}
package com.music_manager_api.web;

import com.music_manager_api.exception.ArtistAlreadyExistException;
import com.music_manager_api.exception.ArtistNotFoundException;
import com.music_manager_api.exception.GenreNotFoundException;
import com.music_manager_api.exception.SongNotFoundException;
import com.music_manager_api.web.dto.ExceptionResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionTranslator {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, List<String>>> handleNotValidData(
      MethodArgumentNotValidException exception) {
    Map<String, List<String>> errors = exception.getFieldErrors().stream()
        .filter(ex -> ex.getDefaultMessage() != null)
        .collect(Collectors.groupingBy(FieldError::getField,
            Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));
    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler({ArtistAlreadyExistException.class})
  public ResponseEntity<ExceptionResponse> handleBadRequest(RuntimeException exception){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(exceptionResponse(exception.getMessage()));
  }

  @ExceptionHandler({ArtistNotFoundException.class,
      GenreNotFoundException.class, SongNotFoundException.class})
  public ResponseEntity<ExceptionResponse> handleNotFound(RuntimeException exception){
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(exceptionResponse(exception.getMessage()));
  }

  private ExceptionResponse exceptionResponse(String message) {
    return new ExceptionResponse(message,
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss")));
  }
}

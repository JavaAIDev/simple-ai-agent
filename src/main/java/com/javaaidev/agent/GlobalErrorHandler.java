package com.javaaidev.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(GlobalErrorHandler.class);

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleAll(Exception ex) {
    LOGGER.error("Internal error", ex);
    return ResponseEntity.status(500).body(ex.getMessage());
  }
}
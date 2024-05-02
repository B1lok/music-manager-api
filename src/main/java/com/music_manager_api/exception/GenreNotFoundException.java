package com.music_manager_api.exception;

public class GenreNotFoundException extends RuntimeException{
  public GenreNotFoundException(String message) {
    super(message);
  }
}

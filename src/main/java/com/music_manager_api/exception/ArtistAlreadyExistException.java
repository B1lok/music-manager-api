package com.music_manager_api.exception;

public class ArtistAlreadyExistException extends RuntimeException {
  public ArtistAlreadyExistException(String message) {
    super(message);
  }
}

package com.music_manager_api.exception;

public class ArtistNotFoundException extends RuntimeException{
  public ArtistNotFoundException(String message) {
    super(message);
  }
}

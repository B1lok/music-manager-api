package com.music_manager_api.exception;

public class SongNotFoundException extends RuntimeException{
  public SongNotFoundException(String message) {
    super(message);
  }
}

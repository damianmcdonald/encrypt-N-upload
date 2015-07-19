package com.github.damianmcdonald.encryptnupload.errors;

public class EncryptNUploadException extends Exception {

  private static final long serialVersionUID = 8644189686309519275L;

  public EncryptNUploadException(String message) {
    super(message);
  }

  public EncryptNUploadException(String message, Throwable throwable) {
    super(message, throwable);
  }

}

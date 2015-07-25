package com.github.damianmcdonald.encryptnupload.service;

import com.github.damianmcdonald.encryptnupload.domain.RegisteredUser;
import com.github.damianmcdonald.encryptnupload.errors.EncryptNUploadException;

public interface RegistrationService {

  public String register(String userName, String password) throws EncryptNUploadException;

  public void unregister();

  public RegisteredUser getRegistration(String hash) throws EncryptNUploadException;

}

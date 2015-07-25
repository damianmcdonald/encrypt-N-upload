package com.github.damianmcdonald.encryptnupload.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.damianmcdonald.encryptnupload.domain.RegisteredUser;
import com.github.damianmcdonald.encryptnupload.errors.EncryptNUploadErrorCode;
import com.github.damianmcdonald.encryptnupload.errors.EncryptNUploadException;
import com.github.damianmcdonald.encryptnupload.service.CryptographyService;
import com.github.damianmcdonald.encryptnupload.service.RegistrationService;
import com.github.damianmcdonald.encryptnupload.util.ValidationUtil;

@Service
public class RegistrationServiceImpl implements RegistrationService {

  private final Logger log = LoggerFactory.getLogger(this.getClass());
  private static final Map<String, RegisteredUser> REGISTRATION_MAP =
      new HashMap<String, RegisteredUser>();
  private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
  private final Lock readLock = lock.readLock();
  private final Lock writeLock = lock.writeLock();

  @Value("${registration.validity}")
  private long sessionValidity;

  @Autowired
  private CryptographyService crytographyService;

  @Override
  public String register(String userName, String password) throws EncryptNUploadException {
    String hash = "";
    try {
      hash = crytographyService.generateHash(userName);
    } catch (NoSuchAlgorithmException ex) {
      ex.printStackTrace();
      log.error("Unable to generate hash for user: " + userName);
    }
    // ensure that a valid hash has been produced
    ValidationUtil.validateMD5Format(hash, "hash");
    writeLock.lock();
    try {
      if (REGISTRATION_MAP.containsKey(hash)) {
        RegisteredUser registeredUser = REGISTRATION_MAP.get(hash);
        registeredUser.setLastAccessTime(System.currentTimeMillis());
        REGISTRATION_MAP.put(hash, registeredUser);
        return hash;
      }
      RegisteredUser registeredUser =
          new RegisteredUser(userName, password, System.currentTimeMillis());
      REGISTRATION_MAP.put(hash, registeredUser);
      return hash;
    } finally {
      writeLock.unlock();
    }
  }

  @Override
  public void unregister() {
    writeLock.lock();
    try {
      for (Iterator<Map.Entry<String, RegisteredUser>> it = REGISTRATION_MAP.entrySet().iterator(); it.hasNext();) {
        Map.Entry<String, RegisteredUser> entry = it.next();
        if (System.currentTimeMillis() - entry.getValue().getLastAccessTime() > sessionValidity) {
          it.remove();
        }
      }
    } finally {
      writeLock.unlock();
    }
  }

  @Override
  public RegisteredUser getRegistration(String hash) throws EncryptNUploadException {
    readLock.lock();
    try {
      RegisteredUser registeredUser = REGISTRATION_MAP.get(hash);
      if (registeredUser != null) {
        return registeredUser;
      }
      log.error("Unable to find user for hash: " + hash);
      throw new EncryptNUploadException(EncryptNUploadErrorCode.UNRECOGNIZED_USER.getValue());
    } finally {
      readLock.unlock();
    }
  }

}

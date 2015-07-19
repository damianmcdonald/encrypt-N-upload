package com.github.damianmcdonald.encryptnupload.service.impl;

import com.github.damianmcdonald.encryptnupload.domain.RegisteredUser;
import com.github.damianmcdonald.encryptnupload.errors.EncryptNUploadErrorCode;
import com.github.damianmcdonald.encryptnupload.errors.EncryptNUploadException;
import com.github.damianmcdonald.encryptnupload.service.CryptographyService;
import com.github.damianmcdonald.encryptnupload.service.RegistrationService;
import com.github.damianmcdonald.encryptnupload.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final Map<String, RegisteredUser> REGISTRATION_MAP = new HashMap<String, RegisteredUser>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    @Value("${registration.validity}")
    private long sessionValidity;

    @Autowired
    private CryptographyService crytographyService;

    @Override
    public void register(String userName, String password) throws EncryptNUploadException {
        String hash = "";
        try {
            hash = crytographyService.generateHash(userName);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            log.error("Unable to generate hash for user: " + userName);
        }
        // ensure that a valid hash has been produced
        ValidationUtil.validateMD5Format(hash, "hash");

        log.debug("Hash -> User == " + hash + " : " + userName);
        writeLock.lock();
        try {
            if (REGISTRATION_MAP.containsKey(hash)) {
                log.debug("User: " + userName + " is already registered.");
                RegisteredUser registeredUser = REGISTRATION_MAP.get(hash);
                registeredUser.setLastAccessTime(System.currentTimeMillis());
                REGISTRATION_MAP.put(hash, registeredUser);
                log.debug("User: " + userName
                        + " registered with new access time: "
                        + new Date(registeredUser.getLastAccessTime()));
                return;
            }
            log.debug("User: " + userName + " is not currently registered.");
            RegisteredUser registeredUser = new RegisteredUser(userName,
                    password, System.currentTimeMillis());
            REGISTRATION_MAP.put(hash, registeredUser);
            log.debug("User: " + userName
                    + " has been registered with access time: "
                    + new Date(registeredUser.getLastAccessTime()));
        } finally {
            writeLock.unlock();
        }

    }

    @Override
    public void unregister() {
        log.debug("Initiating session invalidity checks");
        writeLock.lock();
        try {
            for (Iterator<Map.Entry<String, RegisteredUser>> it = REGISTRATION_MAP
                    .entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, RegisteredUser> entry = it.next();
                if (System.currentTimeMillis()
                        - entry.getValue().getLastAccessTime() > sessionValidity) {
                    log.debug("Invalid entry found for userName: "
                            + entry.getValue().getUserName());
                    it.remove();
                    log.debug("userName: " + entry.getValue().getUserName()
                            + "'s session has been invalidated");
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
                log.debug("Retrieving user: " + registeredUser.getUserName() + ", "
                        + registeredUser.getLastAccessTime() + " for hash: " + hash);
                return registeredUser;
            }
            log.error("Unable to find user for hash: " + hash);
            throw new EncryptNUploadException(EncryptNUploadErrorCode.UNRECOGNIZED_USER.getValue());
        } finally {
            readLock.unlock();
        }
    }

}

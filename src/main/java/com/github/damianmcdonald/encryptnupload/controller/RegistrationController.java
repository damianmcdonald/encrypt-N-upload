package com.github.damianmcdonald.encryptnupload.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.damianmcdonald.encryptnupload.domain.RegistrationResponse;
import com.github.damianmcdonald.encryptnupload.errors.EncryptNUploadException;
import com.github.damianmcdonald.encryptnupload.service.CryptographyService;
import com.github.damianmcdonald.encryptnupload.service.RegistrationService;
import com.github.damianmcdonald.encryptnupload.util.ValidationUtil;

@RestController
@RequestMapping(value = "/register")
public class RegistrationController {

  @Autowired
  private RegistrationService registrationService;

  @Autowired
  private CryptographyService cryptographyService;

  @RequestMapping(value = "/entity", method = RequestMethod.POST)
  public RegistrationResponse registerUser(
      @RequestParam("parama") String encryptedUserName,
      @RequestParam("paramz") String[] encryptionPartsUserName,
      @RequestParam("paramb") String encryptedPassword,
      @RequestParam("paramy") String[] encryptionPartsPassword,
      @RequestParam("paramc") String saltUserName, 
      @RequestParam("paramx") String ivUserName,
      @RequestParam("paramd") String saltPassword, 
      @RequestParam("paramw") String ivPassword)
      throws EncryptNUploadException, InvalidKeyException, NoSuchAlgorithmException,
      NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException,
      UnsupportedEncodingException, InvalidAlgorithmParameterException {

    // some sanity checks
    ValidationUtil.validateNotNullOrEmpty(encryptedUserName, "encryptedUserName");
    ValidationUtil.validateNotNullOrEmpty(saltUserName, "saltUserName");
    ValidationUtil.validateNotNullOrEmpty(ivUserName, "ivUserName");
    ValidationUtil.validateNotNullOrEmpty(encryptedPassword, "encryptedPassword");
    ValidationUtil.validateNotNullOrEmpty(saltPassword, "saltPassword");
    ValidationUtil.validateNotNullOrEmpty(ivPassword, "ivPassword");
    ValidationUtil.validateArrayLength(encryptionPartsUserName, 10, "encryptionPartsUserName");
    ValidationUtil.validateArrayLength(encryptionPartsPassword, 10, "encryptionPartsPassword");

    final String userName =
        cryptographyService.decrypt(encryptedUserName, encryptionPartsUserName, saltUserName,
            ivUserName);

    final String password =
        cryptographyService.decrypt(encryptedPassword, encryptionPartsPassword, saltPassword,
            ivPassword);

    final String hash = registrationService.register(userName, password);

    return new RegistrationResponse(userName, password, hash);
  }

  @ExceptionHandler(Exception.class)
  public void handleAllException(HttpServletResponse response, Exception ex) throws IOException {
    ex.printStackTrace();
    response.setHeader("Error-Message", ex.getMessage());
    response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
  }

}

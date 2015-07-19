package com.github.damianmcdonald.encryptnupload.controller;

import com.github.damianmcdonald.encryptnupload.errors.EncryptNUploadErrorCode;
import com.github.damianmcdonald.encryptnupload.errors.EncryptNUploadException;
import com.github.damianmcdonald.encryptnupload.util.ValidationUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

@Controller
@RequestMapping(value = "/upload")
public class FileUploadController { 
  @Value("${fileupload.directory}")
  private String uploadDir;

  @Value("${fileupload.secret}")
  private String secret;

  @Value("${fileupload.whitelist}")
  private String[] whiteList;

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @RequestMapping(value = "/file", method = RequestMethod.POST)
  public @ResponseBody String handleFileUpload(@RequestParam("filename") String fileName,
      @RequestParam("hash") String hash, @RequestParam("secret") String sharedKey, 
      @RequestParam("file") MultipartFile file, HttpServletRequest request) throws EncryptNUploadException {

    // validaton checks
    validate(sharedKey, request.getRemoteAddr());

    // some sanity checks
 	ValidationUtil.validateMD5Format(hash, "hash");
 	
    if (!file.isEmpty()) {
      try {
        byte[] bytes = file.getBytes();
        BufferedOutputStream stream =
            new BufferedOutputStream(new FileOutputStream(new File(uploadDir + File.separator
                + fileName)));
        stream.write(bytes);
        stream.close();
        log.debug("You successfully uploaded " + fileName + "!");

        log.debug("Calling FileUploadService to post file upload to external service");
        final String filePath = uploadDir + File.separator + fileName;
        return "Upload sucessfull";
      } catch (Exception e) {
        log.error("FAILURE >>>>> File upload failed.");
        e.printStackTrace();
        throw new EncryptNUploadException(EncryptNUploadErrorCode.UPLOAD_FAILED.getValue());
      }
    } else {
      log.error("FAILURE >>>>> Upload file is empty");
      throw new EncryptNUploadException(EncryptNUploadErrorCode.EMPTY_FILE.getValue());
    }
  }

  @RequestMapping(value = "/bytes", method = RequestMethod.POST)
  public @ResponseBody String handleByteArrayUpload(@RequestParam("filename") String fileName,
      @RequestParam("hash") String hash, @RequestParam("secret") String sharedKey, 
      @RequestParam("bytes") byte[] bytes, HttpServletRequest request) throws EncryptNUploadException {

    // validaton checks
    validate(sharedKey, request.getRemoteAddr());

    // some sanity checks
 	ValidationUtil.validateMD5Format(hash, "hash");
 	ValidationUtil.validateArrayNotEmpty(bytes, "bytes");

    try {
      BufferedOutputStream stream =
          new BufferedOutputStream(new FileOutputStream(new File(uploadDir + File.separator
              + fileName)));
      stream.write(bytes);
      stream.close();
      log.debug("You successfully uploaded " + fileName + "!");

      log.debug("Calling FileUploadService to post file upload to external service");
      final String filePath = uploadDir + File.separator + fileName;
      return "Upload successfull";
    } catch (Exception e) {
      log.error("FAILURE >>>>> File upload failed.");
      e.printStackTrace();
      throw new EncryptNUploadException(EncryptNUploadErrorCode.UPLOAD_FAILED.getValue());
    }
  }

  @ExceptionHandler(Exception.class)
  public void handleAllException(HttpServletResponse response, Exception ex) throws IOException {
	  ex.printStackTrace();
	  response.setHeader("Error-Message", ex.getMessage());
    	response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
  }
  
  @InitBinder
  public void initBinder(ServletRequestDataBinder binder) {
   // Convert multipart object to byte[]
   	binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
  }

  private boolean validate(String sharedKey, String ipAddress) throws EncryptNUploadException {
    // validation checks
    if (!sharedKey.equals(secret)) {
      log.error("FAILURE >>>>> Supplied secret value is not valid: " + secret);
      throw new EncryptNUploadException(EncryptNUploadErrorCode.INVALID_SECRET.getValue());
    }

    final List<String> authorizedIps = Arrays.asList(whiteList);
    if (!authorizedIps.contains(ipAddress)) {
      log.error("FAILURE >>>>> IP address: " + ipAddress + " is not in whitelist");
      throw new EncryptNUploadException(EncryptNUploadErrorCode.UNATHORIZED_CALLER.getValue());
    }
    return true;
  }

}

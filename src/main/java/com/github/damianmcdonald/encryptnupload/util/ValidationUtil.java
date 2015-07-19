package com.github.damianmcdonald.encryptnupload.util;

import com.github.damianmcdonald.encryptnupload.errors.EncryptNUploadErrorCode;
import com.github.damianmcdonald.encryptnupload.errors.EncryptNUploadException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidationUtil {
	
	private final static Logger log = LoggerFactory.getLogger(ValidationUtil.class);


	public static void validateNotNullOrEmpty(String toValidate, String ref) throws EncryptNUploadException {
		if (StringUtils.isEmpty(toValidate)) {
			log.error(ref + " can't be empty or null: " + toValidate);
			throw new EncryptNUploadException(EncryptNUploadErrorCode.INVALID_PARAMETER.getValue());
		}
	}
	
	public static void validateArrayLength(Object[] toValidate, int expected, String ref) throws EncryptNUploadException {
		if (toValidate.length != expected) {
			log.error(ref + " should contains " + expected + "elements. Actual contains " + toValidate.length + " elements");
			throw new EncryptNUploadException(EncryptNUploadErrorCode.INVALID_PARAMETER.getValue());
		}
	}
	
	public static void validateArrayNotEmpty(byte[] toValidate, String ref) throws EncryptNUploadException {
		if (toValidate.length == 0) {
			log.error(ref + " can not be empty");
			throw new EncryptNUploadException(EncryptNUploadErrorCode.INVALID_PARAMETER.getValue());
		}
	}
	
	public static void validateMD5Format(String toValidate, String ref) throws EncryptNUploadException {
		if (!toValidate.matches("^[a-f0-9]{32}$")) {
			log.error(ref + " is not a valid MD5: " + toValidate);
			throw new EncryptNUploadException(EncryptNUploadErrorCode.INVALID_MD5_HASH.getValue());
		}
	}

}

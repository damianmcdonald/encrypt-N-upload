package com.github.damianmcdonald.encryptnupload.errors;

public enum EncryptNUploadErrorCode {

	INVALID_JSON("Failure with error code: 10001"), EMPTY_FILE(
			"Failure with error code: 10002"), UPLOAD_FAILED(
			"Failure with error code: 10003"),UNATHORIZED_CALLER(
			"Failure with error code: 10004"), INVALID_SECRET(
			"Failure with error code: 10005"), UNRECOGNIZED_USER(
			"Failure with error code: 10006"), INVALID_PARAMETER(
			"Failure with error code: 10007"), INVALID_MD5_HASH(
					"Failure with error code: 10008");
	
	private String value;

	EncryptNUploadErrorCode(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static EncryptNUploadErrorCode valueToEnum(String value) {
		for (EncryptNUploadErrorCode enumValue : EncryptNUploadErrorCode.values()) {
			if (enumValue.getValue().equals(value)) {
				return enumValue;
			}
		}
		return null;
	}

}

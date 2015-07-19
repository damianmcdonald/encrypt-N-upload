package com.github.damianmcdonald.encryptnupload.service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public interface CryptographyService {

	public String generateHash(final String textToHash)
			throws NoSuchAlgorithmException;

	public String decrypt(String textToDecrypt, String[] cipherParts, String salt, String iv)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException,
			InvalidAlgorithmParameterException;

}

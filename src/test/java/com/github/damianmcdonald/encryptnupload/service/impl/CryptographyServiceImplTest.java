package com.github.damianmcdonald.encryptnupload.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.github.damianmcdonald.encryptnupload.EncryptNUploadApplication;
import com.github.damianmcdonald.encryptnupload.EncryptNUploadApplicationTestData;
import com.github.damianmcdonald.encryptnupload.service.CryptographyService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EncryptNUploadApplication.class)
public class CryptographyServiceImplTest extends EncryptNUploadApplicationTestData {
	
	@Autowired
	private CryptographyService cryptographyService;
	
	@Test
	public void generateHashTest() throws NoSuchAlgorithmException {
		final String hash = cryptographyService.generateHash(USERNAME);
		assertNotNull("Hash can not be null", hash);
		assertTrue("Hash: " + hash + " is not a valid MD5 format", hash.matches("^[a-f0-9]{32}$"));
		assertTrue("Hash: " + hash + " does not equal: " + MD5_CHECKSUM, MD5_CHECKSUM.equals(hash));
	}
	
	@Test
	public void decryptTest() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException {
		final String decrytpedText = cryptographyService.decrypt(ENCRYPTED_USERNAME, PASSPHRASE_USERNAME, SALT_USERNAME, IV_USERNAME);
		assertNotNull(decrytpedText);
		assertTrue("decryptedText does not equal: " + USERNAME, USERNAME.equals(decrytpedText));
	}

}

package com.github.damianmcdonald.encryptnupload.service.impl;

import com.github.damianmcdonald.encryptnupload.EncryptNUploadApplication;
import com.github.damianmcdonald.encryptnupload.EncryptNUploadApplicationTestData;
import com.github.damianmcdonald.encryptnupload.domain.RegisteredUser;
import com.github.damianmcdonald.encryptnupload.errors.EncryptNUploadException;
import com.github.damianmcdonald.encryptnupload.service.RegistrationService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EncryptNUploadApplication.class)
public class RegistrationServiceImplTest extends EncryptNUploadApplicationTestData {
	
	@Autowired
	private RegistrationService registrationService;

	@Test
	public void getRegistrationTest() throws EncryptNUploadException {
		registrationService.register(USERNAME, PASSWORD);
		RegisteredUser registeredUser = registrationService.getRegistration(MD5_CHECKSUM);
		assertNotNull(registeredUser);
		assertTrue(registeredUser.getUserName() + " does not equal: " + USERNAME, registeredUser.getUserName().equals(USERNAME));
		assertTrue(registeredUser.getPassword() + " does not equal: " + PASSWORD, registeredUser.getPassword().equals(PASSWORD));
	}

}

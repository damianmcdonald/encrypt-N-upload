package com.github.damianmcdonald.encryptnupload.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.github.damianmcdonald.encryptnupload.EncryptNUploadApplication;

import com.github.damianmcdonald.encryptnupload.EncryptNUploadApplicationTestData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EncryptNUploadApplication.class)
@WebAppConfiguration
public class RegistrationControllerTest extends EncryptNUploadApplicationTestData {
	
	@Autowired
    private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void registerUserTest() throws Exception {
		this.mockMvc.perform(post("/register/entity")
		.param("parama", ENCRYPTED_USERNAME)
	    .param("paramb", ENCRYPTED_PASSWORD)
	    .param("paramc", SALT_USERNAME)
	    .param("paramd", SALT_PASSWORD)
	    .param("paramw", IV_PASSWORD)
	    .param("paramx", IV_USERNAME)
	    .param("paramy", arrayToString(PASSPHRASE_PASSWORD))
	    .param("paramz", arrayToString(PASSPHRASE_USERNAME))
	    .accept(MediaType.TEXT_HTML))
		.andExpect(status().isOk())
		.andExpect(content().string(is("Registration successfull")));
	}

}

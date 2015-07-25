package com.github.damianmcdonald.encryptnupload.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.damianmcdonald.encryptnupload.EncryptNUploadApplication;
import com.github.damianmcdonald.encryptnupload.EncryptNUploadApplicationTestData;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EncryptNUploadApplication.class)
@WebAppConfiguration
public class RegistrationControllerTest extends EncryptNUploadApplicationTestData {

  @Autowired
  private WebApplicationContext webApplicationContext;

  private HttpMessageConverter mappingJackson2HttpMessageConverter;

  private MockMvc mockMvc;

  @Autowired
  void setConverters(HttpMessageConverter<?>[] converters) {
    List<HttpMessageConverter<?>> converterList = Arrays.asList(converters);
    for (HttpMessageConverter<?> converter : converterList) {
      if (converter instanceof MappingJackson2HttpMessageConverter) {
        this.mappingJackson2HttpMessageConverter = converter;
      }
    }

    Assert.assertNotNull("the JSON message converter must not be null",
        this.mappingJackson2HttpMessageConverter);
  }

  @Before
  public void setUp() throws Exception {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void registerUserTest() throws Exception {
    mockMvc.perform(
            post("/register/entity")
            .param("parama", ENCRYPTED_USERNAME)
            .param("paramb", ENCRYPTED_PASSWORD)
            .param("paramc", SALT_USERNAME)
            .param("paramd", SALT_PASSWORD)
            .param("paramw", IV_PASSWORD)
            .param("paramx", IV_USERNAME)
            .param("paramy", arrayToString(PASSPHRASE_PASSWORD))
            .param("paramz", arrayToString(PASSPHRASE_USERNAME))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(CONTENT_TYPE))
            .andExpect(jsonPath("$.userName", is(USERNAME)))
            .andExpect(jsonPath("$.password", is(PASSWORD)))
            .andExpect(jsonPath("$.hash", is(MD5_CHECKSUM))
    );
  }

}

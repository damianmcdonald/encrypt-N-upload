package com.github.damianmcdonald.encryptnupload.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.damianmcdonald.encryptnupload.EncryptNUploadApplication;
import com.github.damianmcdonald.encryptnupload.EncryptNUploadApplicationTestData;
import com.github.damianmcdonald.encryptnupload.service.RegistrationService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EncryptNUploadApplication.class)
@WebAppConfiguration
public class FileUploadControllerTest extends EncryptNUploadApplicationTestData {

  @Value("${fileupload.directory}")
  protected String uploadDir;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private RegistrationService registrationService;

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
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void handleFileUploadTest() throws Exception {
    registrationService.register(USERNAME, PASSWORD);

    final File file = new File(FILEPATH);
    final MockMultipartFile firstFile =
        new MockMultipartFile("file", file.getName(), "text/plain", FILE_BYTES);

    mockMvc.perform(
            MockMvcRequestBuilders.fileUpload("/upload/file")
            .file(firstFile)
            .param("filename", file.getName())
            .param("hash", MD5_CHECKSUM)
            .param("secret", SHARED_SECRET)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(CONTENT_TYPE))
            .andExpect(jsonPath("$.uploadedFile", is(uploadDir + "/" + FILENAME))
    );
  }

  @Test
  public void handleFileUploadBytesTest() throws Exception {
    registrationService.register(USERNAME, PASSWORD);

    final File file = new File(FILEPATH);
    final MockMultipartFile firstFile = new MockMultipartFile("bytes", file.getName(), "text/plain", FILE_BYTES);

    mockMvc.perform(
            MockMvcRequestBuilders.fileUpload("/upload/bytes")
            .file(firstFile)
            .param("filename", file.getName())
            .param("hash", MD5_CHECKSUM)
            .param("secret", SHARED_SECRET)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(CONTENT_TYPE))
            .andExpect(jsonPath("$.uploadedFile", is(uploadDir + "/" + FILENAME))
    );
  }

}

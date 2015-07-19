package com.github.damianmcdonald.encryptnupload.controller;

import com.github.damianmcdonald.encryptnupload.EncryptNUploadApplication;
import com.github.damianmcdonald.encryptnupload.EncryptNUploadApplicationTestData;
import com.github.damianmcdonald.encryptnupload.service.RegistrationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EncryptNUploadApplication.class)
@WebAppConfiguration
public class FileUploadControllerTest extends EncryptNUploadApplicationTestData {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private RegistrationService registrationService;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void handleFileUploadTest() throws Exception {
        registrationService.register(USERNAME, PASSWORD);

        final File file = new File(FILENAME);
        final MockMultipartFile firstFile = new MockMultipartFile("bytes", file.getName(), "text/plain", FILE_BYTES);

        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload/bytes")
                .file(firstFile)
                .param("filename", file.getName())
                .param("hash", MD5_CHECKSUM)
                .param("secret", SHARED_SECRET)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().string(is(UPLOAD_SUCCESS_VAL)));
    }


}

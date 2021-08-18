package controller.multipart.commons;

import common.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static controller.multipart.commons.CommonsMultipartController.DATA_PART_NAME;
import static controller.multipart.commons.CommonsMultipartController.JSON_DATA_PART_NAME;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {CommonsMultipartController.class, CommonsMultipartControllerTest.Config.class})
class CommonsMultipartControllerTest extends BaseTest {

    @Test
    void requestParam() throws Exception {
        var filename1 = "file1.json";
        var fileContent1 = "file content 1";
        var multipartFile1 =
                new MockMultipartFile(DATA_PART_NAME, filename1, APPLICATION_JSON_VALUE, fileContent1.getBytes());

        var filename2 = "file2.json";
        var fileContent2 = "file content 2";
        var multipartFile2 =
                new MockMultipartFile(DATA_PART_NAME, filename2, APPLICATION_JSON_VALUE, fileContent2.getBytes());

        var filename3 = "file3.json";
        var fileContent3 = "{\"name\": \"John\"}";
        var multipartFile3 =
                new MockMultipartFile(JSON_DATA_PART_NAME, filename3, APPLICATION_JSON_VALUE, fileContent3.getBytes());

        var multipartBuilder = MockMvcRequestBuilders
                .fileUpload(CommonsMultipartController.ENDPOINT)
                .file(multipartFile1)
                .file(multipartFile2)
                .file(multipartFile3);

        var paramValue = "4";

        var expContent = fileContent1 + fileContent2 + "John" + paramValue;

        mvc.perform(multipartBuilder.param(CommonsMultipartController.PARAM_NAME, paramValue))
                .andExpect(status().isOk())
                .andExpect(content().string(expContent));
    }

    @EnableWebMvc
    @Configuration
    static class Config {
        @Bean
        CommonsMultipartResolver commonsMultipartResolver() {
            return new CommonsMultipartResolver();
        }
    }
}
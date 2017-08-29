package ru.yaal.merch.bookshelf.mvc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.yaal.merch.bookshelf.config.WebTestConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {WebTestConfig.class})
public class BookshelfControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void addBook() throws Exception {
        addBookRequest(resourceToString("book.json"));
        this.mockMvc.perform(
                get("/book/100")
                        .accept(MediaType.parseMediaType("application/json")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].name").value("To Kill a Mockingbird"))
                .andExpect(jsonPath("$[0].abstractPart").value(containsString("a childhood")));
    }


    @Test
    public void findBookByName() throws Exception {
        addBookRequest("{ \"id\": 301, \"name\": \"The Taming of the Shrew\", \"abstractPart\": \"Renowned as Shakespeare's most boisterous comedy\" }");
        addBookRequest("{ \"id\": 302, \"name\": \"The Taming\", \"abstractPart\": \"The Tempest, long considered one of Shakespeare's most lyrical plays\" }");
        this.mockMvc.perform(
                get("/book/find")
                        .accept(MediaType.parseMediaType("application/json"))
                        .param("query", "Taming"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].name").value("The Taming of the Shrew"))
                .andExpect(jsonPath("$[1].name").value("The Taming"));
    }

    private void addBookRequest(String json) throws Exception {
        this.mockMvc.perform(
                post("/book")
                        .accept(MediaType.parseMediaType("application/json"))
                        .contentType("application/json")
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private String resourceToString(String resource) throws IOException {
        Path path = new File((getClass().getResource(resource).getFile())).toPath();
        return new String(Files.readAllBytes(path));
    }
}

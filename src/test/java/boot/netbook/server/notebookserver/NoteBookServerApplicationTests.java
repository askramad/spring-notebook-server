package boot.netbook.server.notebookserver;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fasterxml.jackson.databind.ObjectMapper;

import note.book.server.controller.dto.InterpreterRequestDto;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class NoteBookServerApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void sendingEmptyRequest() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/execute")).andExpect(status().isBadRequest()).andReturn();
        assertThat(result.getResolvedException(), is(instanceOf(HttpMessageNotReadableException.class)));
        assertThat(result.getResolvedException().getMessage(), startsWith("Request body is missing"));
    }

    @Test
    public void sendingWrongRequest() throws Exception {
        InterpreterRequestDto body = new InterpreterRequestDto();
        body.setCode("console.log(1 + 1);");
        MvcResult result = this.mockMvc.perform(post("/execute").content(mapper.writeValueAsString(body)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        assertThat(result.getResolvedException(), is(instanceOf(MethodArgumentNotValidException.class)));
        assertThat(result.getResolvedException().getMessage(), startsWith("Validation failed"));
    }

    @Test
    public void getExpectedResult() throws Exception {
        InterpreterRequestDto body = new InterpreterRequestDto();
        body.setCode("%js\nconsole.log('Hello world');");
        this.mockMvc.perform(post("/execute").content(mapper.writeValueAsString(body)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true))).andExpect(jsonPath("$.result", is("Hello world\n")));
    }

}

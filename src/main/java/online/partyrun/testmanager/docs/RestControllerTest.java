package online.partyrun.testmanager.docs;

import static online.partyrun.testmanager.docs.ApiDocumentUtils.getDocumentRequest;
import static online.partyrun.testmanager.docs.ApiDocumentUtils.getDocumentResponse;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest
@AutoConfigureRestDocs
@WithMockUser(username = "defaultUser")
public abstract class RestControllerTest {

    @Autowired protected MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    protected String toRequestBody(Object value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(value);
    }

    protected void setPrintDocs(ResultActions actions, String title) {
        try {
            actions.andDo(print())
                    .andDo(document(title, getDocumentRequest(), getDocumentResponse()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

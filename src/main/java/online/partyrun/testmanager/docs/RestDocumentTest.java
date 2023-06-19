package online.partyrun.testmanager.docs;

import static online.partyrun.testmanager.docs.ApiDocumentUtils.getDocumentRequest;
import static online.partyrun.testmanager.docs.ApiDocumentUtils.getDocumentResponse;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.catalina.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(
        excludeFilters = {
            @ComponentScan.Filter(
                    type = FilterType.ASSIGNABLE_TYPE,
                    classes = AuthenticationFilter.class),
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        })
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs
public abstract class RestDocumentTest {

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

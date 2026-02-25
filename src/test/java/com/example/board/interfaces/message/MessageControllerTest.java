package com.example.board.interfaces.message;

import com.example.board.domain.message.Message;
import com.example.board.domain.message.MessageCreateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageController.class)
class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MessageCreateService messageCreateService;

    @Test
    @DisplayName("정상 요청이면 201을 반환한다")
    void createMessageSuccess() throws Exception {
        Message message = Message.create("테스트 메시지");
        given(messageCreateService.create(anyString())).willReturn(message);

        String body = objectMapper.writeValueAsString(new MessageCreateRequest("테스트 메시지"));

        mockMvc.perform(post("/api/v1/message/write")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("잘못된 요청이면 400을 반환한다")
    void createMessageValidationFail() throws Exception {
        String body = objectMapper.writeValueAsString(new MessageCreateRequest(" "));

        mockMvc.perform(post("/api/v1/message/write")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }
}

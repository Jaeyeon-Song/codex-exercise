package com.example.board.interfaces.message;

import com.example.board.domain.message.Message;
import com.example.board.domain.message.MessageCreateService;
import com.example.board.domain.message.MessageNotFoundException;
import com.example.board.domain.message.MessageReadService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageController.class)
class MessageReadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageCreateService messageCreateService;

    @MockBean
    private MessageReadService messageReadService;

    @Test
    @DisplayName("GET read 요청이 성공하면 200과 메시지 응답을 반환한다")
    void readSuccess() throws Exception {
        Message message = mock(Message.class);
        LocalDateTime now = LocalDateTime.of(2024, 1, 1, 12, 0, 0);

        given(message.getId()).willReturn(1L);
        given(message.getContent()).willReturn("조회 성공 메시지");
        given(message.getCreatedAt()).willReturn(now);
        given(message.getUpdatedAt()).willReturn(now);
        given(messageReadService.read(1L)).willReturn(message);

        mockMvc.perform(get("/api/v1/message/read/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.content").value("조회 성공 메시지"))
                .andExpect(jsonPath("$.createdAt").value("2024-01-01T12:00:00"))
                .andExpect(jsonPath("$.updatedAt").value("2024-01-01T12:00:00"));
    }

    @Test
    @DisplayName("존재하지 않는 id로 조회하면 404와 에러 포맷을 반환한다")
    void readNotFound() throws Exception {
        given(messageReadService.read(404L)).willThrow(new MessageNotFoundException(404L));

        mockMvc.perform(get("/api/v1/message/read/{id}", 404L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error.code").value("MESSAGE_NOT_FOUND"))
                .andExpect(jsonPath("$.error.message").value("message not found"));
    }
}

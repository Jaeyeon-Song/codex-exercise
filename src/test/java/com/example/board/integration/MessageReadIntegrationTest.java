package com.example.board.integration;

import com.example.board.domain.message.Message;
import com.example.board.infrastructure.message.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MessageReadIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MessageRepository messageRepository;

    @BeforeEach
    void setUp() {
        messageRepository.deleteAll();
    }

    @Test
    @DisplayName("사전 저장된 메시지를 단건 조회하면 200을 반환한다")
    void readSavedMessage() throws Exception {
        Message saved = messageRepository.save(Message.create("통합 조회 메시지"));

        mockMvc.perform(get("/api/v1/message/read/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId()))
                .andExpect(jsonPath("$.content").value("통합 조회 메시지"));
    }

    @Test
    @DisplayName("없는 메시지를 조회하면 404를 반환한다")
    void readMessageNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/message/read/{id}", 99999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error.code").value("MESSAGE_NOT_FOUND"))
                .andExpect(jsonPath("$.error.message").value("message not found"));
    }
}

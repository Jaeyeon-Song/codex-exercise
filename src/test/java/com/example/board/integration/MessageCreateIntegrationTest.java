package com.example.board.integration;

import com.example.board.infrastructure.message.MessageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MessageCreateIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MessageRepository messageRepository;

    @Test
    @DisplayName("POST 요청 후 DB에 저장된다")
    void createMessageAndPersist() throws Exception {
        String request = """
                {
                  "content": "통합 테스트 메시지"
                }
                """;

        mockMvc.perform(post("/api/v1/message/write")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated());

        assertThat(messageRepository.count()).isEqualTo(1);
        assertThat(messageRepository.findAll().getFirst().getContent()).isEqualTo("통합 테스트 메시지");
    }
}

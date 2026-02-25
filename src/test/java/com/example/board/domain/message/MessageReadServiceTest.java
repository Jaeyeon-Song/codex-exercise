package com.example.board.domain.message;

import com.example.board.infrastructure.message.MessageRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MessageReadServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessageReadService messageReadService;

    @Test
    @DisplayName("존재하는 메시지 id로 조회하면 메시지를 반환한다")
    void readSuccess() {
        Message message = Message.create("조회 메시지");
        given(messageRepository.findById(1L)).willReturn(Optional.of(message));

        Message result = messageReadService.read(1L);

        assertThat(result).isSameAs(message);
    }

    @Test
    @DisplayName("존재하지 않는 메시지 id로 조회하면 MessageNotFoundException이 발생한다")
    void readNotFound() {
        given(messageRepository.findById(999L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> messageReadService.read(999L))
                .isInstanceOf(MessageNotFoundException.class)
                .hasMessage("message not found");
    }
}

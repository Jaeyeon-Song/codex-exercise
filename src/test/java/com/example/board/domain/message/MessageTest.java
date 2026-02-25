package com.example.board.domain.message;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MessageTest {

    @Test
    void content_정상_생성() {
        Message message = Message.create("안녕하세요");

        assertThat(message.getContent()).isEqualTo("안녕하세요");
    }

    @Test
    void content_50자_초과_시_예외() {
        String overLimit = "a".repeat(51);

        assertThatThrownBy(() -> Message.create(overLimit))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("content length must be less than or equal to 50");
    }
}

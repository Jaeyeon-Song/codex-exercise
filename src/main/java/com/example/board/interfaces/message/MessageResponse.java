package com.example.board.interfaces.message;

import com.example.board.domain.message.Message;
import java.time.LocalDateTime;

public class MessageResponse {

    public record Read(
            Long id,
            String content,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        public static Read fromDomain(Message message) {
            return new Read(
                    message.getId(),
                    message.getContent(),
                    message.getCreatedAt(),
                    message.getUpdatedAt()
            );
        }
    }
}

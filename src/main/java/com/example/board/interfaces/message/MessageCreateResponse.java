package com.example.board.interfaces.message;

import java.time.LocalDateTime;

public record MessageCreateResponse(
        Long id,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

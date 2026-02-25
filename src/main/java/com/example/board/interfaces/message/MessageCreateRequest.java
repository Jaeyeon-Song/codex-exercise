package com.example.board.interfaces.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MessageCreateRequest(
        @NotBlank(message = "content must not be blank")
        @Size(max = 50, message = "content length must be less than or equal to 50")
        String content
) {
}

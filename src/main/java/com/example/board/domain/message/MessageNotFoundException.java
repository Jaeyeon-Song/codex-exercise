package com.example.board.domain.message;

public class MessageNotFoundException extends RuntimeException {

    public MessageNotFoundException(Long id) {
        super("message not found");
    }
}

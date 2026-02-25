package com.example.board.domain.message;

import com.example.board.infrastructure.message.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageCreateService {

    private final MessageRepository messageRepository;

    public MessageCreateService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Transactional
    public Message create(String content) {
        Message message = Message.create(content);
        return messageRepository.save(message);
    }
}

package com.example.board.domain.message;

import com.example.board.infrastructure.message.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageReadService {

    private final MessageRepository messageRepository;

    public MessageReadService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Transactional(readOnly = true)
    public Message read(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new MessageNotFoundException(id));
    }
}

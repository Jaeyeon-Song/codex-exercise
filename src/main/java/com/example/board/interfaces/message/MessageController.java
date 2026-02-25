package com.example.board.interfaces.message;

import com.example.board.domain.message.Message;
import com.example.board.domain.message.MessageCreateService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController {

    private final MessageCreateService messageCreateService;

    public MessageController(MessageCreateService messageCreateService) {
        this.messageCreateService = messageCreateService;
    }

    @PostMapping("/write")
    public ResponseEntity<MessageCreateResponse> write(@Valid @RequestBody MessageCreateRequest request) {
        Message saved = messageCreateService.create(request.content());

        MessageCreateResponse response = new MessageCreateResponse(
                saved.getId(),
                saved.getContent(),
                saved.getCreatedAt(),
                saved.getUpdatedAt()
        );

        return ResponseEntity
                .created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(saved.getId())
                        .toUri())
                .body(response);
    }
}

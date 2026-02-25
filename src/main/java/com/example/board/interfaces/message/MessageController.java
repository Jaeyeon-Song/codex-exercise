package com.example.board.interfaces.message;

import com.example.board.domain.message.Message;
import com.example.board.domain.message.MessageCreateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Tag(name = "Message")
@RestController
@RequestMapping("/api/v1/message")
public class MessageController {

    private final MessageCreateService messageCreateService;

    public MessageController(MessageCreateService messageCreateService) {
        this.messageCreateService = messageCreateService;
    }

    @Operation(
            summary = "메시지 등록",
            description = "최대 50자 메시지를 등록한다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessageCreateRequest.class),
                            examples = @ExampleObject(
                                    name = "메시지 등록 요청",
                                    value = "{\n  \"content\": \"안녕하세요\"\n}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "메시지 등록 성공"),
                    @ApiResponse(responseCode = "400", description = "입력값 검증 실패")
            }
    )
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

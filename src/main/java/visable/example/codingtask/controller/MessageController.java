package visable.example.codingtask.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import visable.example.codingtask.dao.Message;
import visable.example.codingtask.services.MessageService;

import javax.validation.Valid;
import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1")
@OpenAPIDefinition(info = @Info(title = "Send/Receive API for Messaging"))
@Tag(name = "Register Module")
@Slf4j
public class MessageController {

    private MessageService messageService;

    @PostMapping("/sendMessage/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Send message.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Message sent successfully."),
            @ApiResponse(responseCode = "409", description = "Sender/Recipient do not exist.")
    })
    public ResponseEntity<Object> sendMessage(@Parameter(description = "Message Sender, recipient and content in JSON format .",
            required = true, content = @Content(schema = @Schema(implementation = Message.class))) @Valid @RequestBody Message message){
        Map<Object, Object> map = messageService.publishMessageToQue(message);
        return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
    }

    @GetMapping("/viewRecievedMessage/{recipientId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Retrieve received message/s.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Message retrieved successfully."),
            @ApiResponse(responseCode = "409", description = "Recipient do not exist.")
    })
    public ResponseEntity<List<Message>> viewRecievedMessage(@Parameter(description = "Retrieve received message/s by recipientId .",
            required = true) @PathVariable Long recipientId){
        return new ResponseEntity<>(messageService.listRecievedMessage(recipientId), HttpStatus.ACCEPTED);
    }
    @GetMapping("/viewSentMessage/{senderId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Retrieve sent message/s.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Message retrieved successfully."),
            @ApiResponse(responseCode = "409", description = "Sender do not exist.")
    })
    public ResponseEntity<List<Message>> viewSentMessage(@Parameter(description = "Retrieve sent message/s by senderId .",
            required = true) @PathVariable Long senderId){
        return new ResponseEntity<>(messageService.listSentMessage(senderId), HttpStatus.ACCEPTED);
    }
    @GetMapping("/viewRecievedMessageFrom/{recipientId}/{senderId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Retrieve message/s.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Message retrieved successfully."),
            @ApiResponse(responseCode = "409", description = "Sender/Recipient do not exist.")
    })
    public ResponseEntity<List<Message>> viewRecievedMessageFrom(@Parameter(description = "Retrieve message/s to recipientId from senderId .",
            required = true) @PathVariable Long recipientId, Long senderId){
        return new ResponseEntity<>(messageService.listRecievedMessageFromTo(senderId, recipientId), HttpStatus.ACCEPTED);
    }
}

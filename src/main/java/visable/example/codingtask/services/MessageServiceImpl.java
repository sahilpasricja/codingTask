package visable.example.codingtask.services;

import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import visable.example.codingtask.dao.Message;
import visable.example.codingtask.messageque.config.MessageConfig;
import visable.example.codingtask.repository.MessageRepo;
import visable.example.codingtask.repository.UserRepo;

import java.util.*;

@AllArgsConstructor
@Service
public class MessageServiceImpl implements  MessageService{

    private MessageRepo messageRepo;
    private UserRepo userRepo;
    private RabbitTemplate template;

    private UserServiceImpl userService;



    //todo -- If found return true else throw exception all in .orElse
    public ResponseEntity<String> senderExists(Message message){
       userRepo.findById(message.getSenderId())
               .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender doesn't exist."));
       return new ResponseEntity<>("Message sent successfully", HttpStatus.CREATED);
    }

    //todo -- If found return true else throw exception all in .orElse
    public Optional<Boolean> recipientExists(Message message){
        userRepo.findById(message.getRecipientId())
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender doesn't exist."));
        return Optional.of(true);
    }

    public Map<Object, Object> publishMessageToQue(Message message) {
        if(message.getSenderId() == message.getRecipientId())
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Can not send message to self.");
        else if (!userService.userIdExists(message.getSenderId())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Sender doesn't exist.");
        }
        else if (!userService.userIdExists(message.getRecipientId())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Recipient doesn't exist.");
        }
        template.convertAndSend(MessageConfig.EXCHANGE, MessageConfig.ROUTING_KEY, message);

        Map<Object, Object> map = new LinkedHashMap<>();;
        map.put("Status", " New message Sent to " +  message.getRecipientId());

        return map;
    }

    public Optional<Boolean> messageQueToDb(Message message) {
        messageRepo.save(message);
        return Optional.of(true);
    }

    @Override
    public List<Message> listRecievedMessage(Long recipientId) {
        if (!userService.userIdExists(recipientId)){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"No user exists by given Id.");
        }
        return messageRepo.findByRecipientId(recipientId);
    }

    @Override
    public List<Message> listSentMessage(Long senderId) {
        if (!userService.userIdExists(senderId)){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"No user exists by given Id.");
        }
        return messageRepo.findBySenderId(senderId);
    }

    @Override
    public List<Message> listRecievedMessageFromTo(Long senderId, Long recipientId) {
        if(senderId == recipientId)
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Can to retrieve messages from self.");
        if (!userService.userIdExists(senderId)){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Sender doesn't exist.");
        }
        if (!userService.userIdExists(recipientId)){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Recipient doesn't exist.");
        }
        return messageRepo.findBySenderIdAndRecipientId(senderId, recipientId);
    }


}

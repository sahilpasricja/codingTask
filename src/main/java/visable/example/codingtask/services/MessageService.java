package visable.example.codingtask.services;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import visable.example.codingtask.dao.Message;
import visable.example.codingtask.messageque.config.MessageConfig;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface MessageService {


    Optional<Boolean> recipientExists(Message message);

    Map<Object, Object> publishMessageToQue(Message message);

    Optional<Boolean> messageQueToDb(Message message);

    List<Message> listRecievedMessage(Long recipientId);

    List<Message> listSentMessage(Long senderId);

    List<Message> listRecievedMessageFromTo(Long senderId,Long recipientId);

    public ResponseEntity<String> senderExists(Message message);
}

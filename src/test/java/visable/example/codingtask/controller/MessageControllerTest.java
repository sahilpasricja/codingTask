package visable.example.codingtask.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import visable.example.codingtask.dao.Message;
import visable.example.codingtask.dao.MessageUser;
import visable.example.codingtask.repository.MessageRepo;
import visable.example.codingtask.repository.UserRepo;
import visable.example.codingtask.services.MessageService;
import visable.example.codingtask.services.UserService;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MessageControllerTest {

@Autowired
MockMvc mockMvc;

@MockBean
MessageRepo messageRepo;

@MockBean
UserRepo userRepo;


MessageUser messageUser;

@Autowired
MessageService messageService;

@Autowired
UserService userService;

Message message;

final String SENDER_NICKNAME = "Test_Sender";

final String RECIPIENT_NICKNAME = "Test_RecipientId";

Long SENDERID = null;
Long RECIPIENTID = null;

String CONTENT = null;

@BeforeAll
public void setup() {
MessageUser messageUserSender = userRepo.save(MessageUser.builder()
        .nickname(SENDER_NICKNAME)
        .build()
);
SENDERID = messageUserSender.getId();
MessageUser messageUserRecipient = userRepo.save(MessageUser.builder()
        .nickname(RECIPIENT_NICKNAME)
        .build()
);
RECIPIENTID = messageUserRecipient.getId();
CONTENT = "Test message from user: " + SENDERID + " to:" + RECIPIENTID;
}




@BeforeEach
void setUp() {
message = Message.builder()
    .senderId(SENDERID)
    .recipientId(RECIPIENTID)
    .content(CONTENT)
    .build();
}

@Test
void sendMessage() throws Exception {
ObjectMapper mapper = new ObjectMapper();
mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
String requestJson = ow.writeValueAsString(message);
mockMvc.perform(post("/api/v1/sendMessage")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
    .andExpect(status().isAccepted());
}


        @Test
        void viewRecievedMessage() throws Exception {
                mockMvc.perform(post("/auth/v1/viewRecievedMessage/" + RECIPIENTID)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andExpect(content().string(containsString(CONTENT)));
        }

        @Test
        void viewSentMessage() throws Exception {
                mockMvc.perform(post("/auth/v1/viewSentMessage/" + SENDERID)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andExpect(content().string(containsString(CONTENT)));
        }

        @Test
        void viewRecievedMessageFrom() throws Exception {
                mockMvc.perform(post("/auth/v1/viewRecievedMessageFrom/" + RECIPIENTID + "/" + SENDERID)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andExpect(content().string(containsString(CONTENT)));
        }



}
package visable.example.codingtask.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import visable.example.codingtask.dao.MessageUser;
import visable.example.codingtask.dao.Message;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class MessageRepoTest {

    @Mock
    UserRepo userRepo;

    @Mock
    MessageRepo messageRepo;


    MessageUser messageSender = MessageUser.builder().nickname("TEST_SENDER").id(12345L).build();
    MessageUser messageReceiver = MessageUser.builder().nickname("TEST_RECEIVER").id(12345L).build();

    Message message = Message.builder().senderId(messageSender.getId()).recipientId(messageReceiver.getId()).content("TEST_MESSAGE").build();


    @BeforeEach
    void initUseCase() {
        userRepo.save(messageSender);
        userRepo.save(messageReceiver);
        messageRepo.save(message);
    }

    @AfterEach
    public void destroyAll(){
        userRepo.delete(messageSender);
        userRepo.delete(messageReceiver);
        messageRepo.delete(message);
    }



    @Test
    void findBySenderId() {
        List<Message> sentMessages =  messageRepo.findBySenderId(messageSender.getId());
        for (Message message:sentMessages){
            assertThat(message.getSenderId().equals(messageSender.getId()));
        }

    }

}
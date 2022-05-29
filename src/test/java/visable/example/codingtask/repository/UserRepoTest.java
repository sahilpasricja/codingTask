package visable.example.codingtask.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import visable.example.codingtask.dao.MessageUser;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class UserRepoTest {

    @Autowired
    private UserRepo userRepo;

    MessageUser messageUser = MessageUser.builder().nickname("TEST").id(12345L).build();


    @BeforeEach
    void initUseCase() {
        userRepo.save(messageUser);
    }

    @AfterEach
    public void destroyAll(){
        userRepo.delete(messageUser);
    }

    @Test
    void findByNickname() {
        Optional<MessageUser> savedUser  = userRepo.findByNickname(messageUser.getNickname());
        assertThat(messageUser.getNickname()).isEqualTo(savedUser.get().getNickname());
    }

}
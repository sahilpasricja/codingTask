package visable.example.codingtask.repository;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import visable.example.codingtask.dao.MessageUser;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<MessageUser, Long> {

    Optional<MessageUser> findByNickname(String nickname);


}

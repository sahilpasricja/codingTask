package visable.example.codingtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import visable.example.codingtask.dao.Message;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message,Long> {

    List<Message> findBySenderId(Long senderId);

    List<Message> findByRecipientId(Long recipientId);

    List<Message> findBySenderIdAndRecipientId(Long senderId,Long recipientId);

}

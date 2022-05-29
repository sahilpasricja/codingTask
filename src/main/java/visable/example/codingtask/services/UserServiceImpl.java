package visable.example.codingtask.services;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import visable.example.codingtask.dao.MessageUser;
import visable.example.codingtask.repository.UserRepo;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserServiceImpl implements  UserService{

    private UserRepo userRepo;
    @Override
    public MessageUser registerUser(MessageUser messageUser) {
        if (userRepo.findByNickname(messageUser.getNickname()).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exist.");
        }
        return userRepo.save(messageUser);

    }

    public boolean userIdExists(Long userId){
        return userRepo.existsById(userId);
    }


}

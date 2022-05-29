package visable.example.codingtask.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import visable.example.codingtask.dao.MessageUser;
import visable.example.codingtask.services.UserService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@OpenAPIDefinition(info = @Info(title = "Register API for new User"))
@Tag(name = "Register Module")
@AllArgsConstructor
public class RegisterController {

    private UserService userService;

    //ToDo: chage return to message only but in json format
    @PostMapping("/register")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New user registered successfully."),
            @ApiResponse(responseCode = "409", description = "User already exist.")
    })

    public ResponseEntity<MessageUser> registerNewUser(@Valid  @RequestBody MessageUser messageUser){
        return new ResponseEntity<>(userService.registerUser(messageUser), HttpStatus.CREATED);
    }
}

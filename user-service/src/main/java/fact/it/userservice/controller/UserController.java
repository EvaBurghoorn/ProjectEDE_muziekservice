package fact.it.userservice.controller;

import fact.it.userservice.dto.UserRequest;
import fact.it.userservice.model.User;
import fact.it.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createUser
            (@RequestBody UserRequest userRequest){
        userService.createUser(userRequest);
    }

//    @GetMapping("/id/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<User>getUserById(@PathVariable("id")String userid){
//
//    }


}

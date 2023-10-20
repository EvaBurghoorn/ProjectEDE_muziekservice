package fact.it.userservice.controller;

import fact.it.userservice.dto.UserRequest;
import fact.it.userservice.model.User;
import fact.it.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@PathVariable int userId,@RequestBody UserRequest userRequest)
    {
        userService.editUser(userId, userRequest);
    }
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable int userId)
    {
        userService.deleteUser(userId);
    }

}

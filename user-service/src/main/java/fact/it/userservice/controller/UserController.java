package fact.it.userservice.controller;

import fact.it.userservice.dto.UserRequest;
import fact.it.userservice.dto.UserResponse;
import fact.it.userservice.model.User;
import fact.it.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

//    Get all users
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getAllUsers(){
        return  userService.getAllUsers();
    }


    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserById(@PathVariable("username") String username) {
        User user = userService.getUserByUsername(username);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createUser(@RequestBody UserRequest userRequest)
    {
        userService.createUser(userRequest);
    }

    @PutMapping("/username/{username}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@PathVariable("username") String username,@RequestBody UserRequest userRequest)
    {
        userService.editUserBy(username, userRequest);
    }
    @DeleteMapping("username/{username}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("username") String username)
    {
        userService.deleteByUsername(username);
    }

}

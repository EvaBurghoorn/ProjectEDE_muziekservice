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

// Get a user with username
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserById(@PathVariable("username") String username) {
        User user = userService.getUserByUsername(username);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    Create a new user
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createUser(@RequestBody UserRequest userRequest)
    {
        userService.createUser(userRequest);
    }

//    Edit a user
    @PutMapping("/username/{username}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@PathVariable("username") String username,@RequestBody UserRequest userRequest)
    {
        userService.editUserBy(username, userRequest);
    }

    // Delete a user
    @DeleteMapping("username/{username}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("username") String username)
    {
        userService.deleteByUsername(username);
    }

}

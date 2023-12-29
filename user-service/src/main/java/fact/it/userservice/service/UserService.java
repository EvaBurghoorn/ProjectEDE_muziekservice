package fact.it.userservice.service;

import fact.it.userservice.dto.UserRequest;
import fact.it.userservice.dto.UserResponse;
import fact.it.userservice.model.User;
import fact.it.userservice.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @PostConstruct
    public void loadData(){
        if(userRepository.count() <= 0){
            User user = new User();
            user.setFirstName("Lexi");
            user.setLastName("Blevins");
            user.setUsername("LexiBlevins");
            user.setEmailAddress("LexiBlevins@gmail.com");
            user.setCountry("Belgium");
            user.setCity("Geel");
            user.setPostalCode("2440");
            user.setPhoneNumber("04 12 34 56 78");
            user.setBirthday("02/09/1999");

            User userTwo = new User();
            userTwo.setFirstName("Lillie");
            userTwo.setLastName("Rose");
            userTwo.setUsername("Lillie123");
            userTwo.setEmailAddress("Lillie.rose@gmail.com");
            userTwo.setCountry("Belgium");
            userTwo.setCity("Antwerpen");
            userTwo.setPostalCode("2000");
            userTwo.setPhoneNumber("04 54 78 98 78");
            userTwo.setBirthday("10/07/2001");

            userRepository.save(user);
            userRepository.save(userTwo);
        }
    }


    // Get user by username
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Create a new user
    public void createUser(UserRequest userRequest){
        User user = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .username(userRequest.getUsername())
                .birthday(userRequest.getBirthday())
                .emailAddress(userRequest.getEmailAddress())
                .phoneNumber(userRequest.getPhoneNumber())
                .city(userRequest.getCity())
                .postalCode(userRequest.getPostalCode())
                .country(userRequest.getCountry())
                .build();

        User findUsername = userRepository.findByUsername(userRequest.getUsername());

        if (findUsername == null){
            userRepository.save(user);
        }
        else{
            System.out.println("Username in use");
        }
    }

    // Edit a user by the username
    public void editUserBy (String username,UserRequest userRequest){
        User userForEdit = userRepository.findByUsername(username);
        if(userForEdit != null){
            userForEdit.setFirstName(userRequest.getFirstName());
            userForEdit.setLastName(userRequest.getLastName());
            userForEdit.setBirthday(userRequest.getBirthday());
            userForEdit.setEmailAddress(userRequest.getEmailAddress());
            userForEdit.setPhoneNumber(userRequest.getPhoneNumber());
            userForEdit.setCity(userRequest.getCity());
            userForEdit.setPostalCode(userRequest.getPostalCode());
            userForEdit.setCountry(userRequest.getCountry());
            userRepository.save(userForEdit);
        }
    }

    // Delete a user by username
    public void deleteByUsername(String username){
        User deletingUser = userRepository.findByUsername(username);
        if(deletingUser != null){
            userRepository.deleteByUsername(username);
        }
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .build();
    }
}
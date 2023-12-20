package fact.it.userservice.service;

import fact.it.userservice.dto.UserRequest;
import fact.it.userservice.dto.UserResponse;
import fact.it.userservice.model.User;
import fact.it.userservice.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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


    //    Get all the users
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToUserResponse).toList();
    }

    public Optional<User> getUserById(String userId) {
        return userRepository.findById(userId);
    }



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

        User username = userRepository.findByUsername(userRequest.getUsername());

        if (username == null){
            userRepository.save(user);
        }
        else{
            System.out.println("Username in use");
        }
    }

    public void editUserBy (String userId,UserRequest userRequest){
        Optional<User> userForEdit = userRepository.findById(userId);
        if(userForEdit.isPresent()){
            User user = userForEdit.get();
            user.setFirstName(userRequest.getFirstName());
            user.setLastName(userRequest.getLastName());
            user.setBirthday(userRequest.getBirthday());
            user.setEmailAddress(userRequest.getEmailAddress());
            user.setPhoneNumber(userRequest.getPhoneNumber());
            user.setCity(userRequest.getCity());
            user.setPostalCode(userRequest.getPostalCode());
            user.setCountry(userRequest.getCountry());
            userRepository.save(user);

        }
    }
    public void deleteUser(String userId){
        Optional<User> deletingUser = userRepository.findById(userId);
        if(deletingUser.isPresent()){
            userRepository.deleteById(userId);
        }

    }


    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}

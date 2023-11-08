package fact.it.userservice.service;

import fact.it.userservice.dto.UserRequest;
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

        }
    }

    public User getUserById(int userId) {
        return userRepository.findByUserId(userId);
    }

    public void createUser(UserRequest userRequest){
        User user = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .birthday(userRequest.getBirthday())
                .emailAddress(userRequest.getEmailAddress())
                .phoneNumber(userRequest.getPhoneNumber())
                .city(userRequest.getCity())
                .postalCode(userRequest.getPostalCode())
                .country(userRequest.getCountry())
                .build();

        userRepository.save(user);
    }

    public void editUser(int userId,UserRequest userRequest){
        User editUser = userRepository.findByUserId(userId);
        if(editUser != null){
            editUser.setFirstName(userRequest.getFirstName());
            editUser.setLastName(userRequest.getLastName());
            editUser.setBirthday(userRequest.getBirthday());
            editUser.setEmailAddress(userRequest.getEmailAddress());
            editUser.setPhoneNumber(userRequest.getPhoneNumber());
            editUser.setCity(userRequest.getCity());
            editUser.setPostalCode(userRequest.getPostalCode());
            editUser.setCountry(userRequest.getCountry());
            userRepository.save(editUser);

        }
    }
    public void deleteUser(int userId){
        User deleteUser = userRepository.findByUserId(userId);
        if(deleteUser != null){
            userRepository.delete(deleteUser);
        }

    }
}

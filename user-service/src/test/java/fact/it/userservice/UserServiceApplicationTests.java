package fact.it.userservice;

import fact.it.userservice.dto.UserRequest;
import fact.it.userservice.dto.UserResponse;
import fact.it.userservice.model.User;
import fact.it.userservice.repository.UserRepository;
import fact.it.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceApplicationTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testGetUserById_Found(){
        // Arrange
        String id = "5";

        User user = new User();
        user.setId(id);
        user.setFirstName("Lexi");
        user.setLastName("Blevins");
        user.setUsername("LexiBlevins");
        user.setEmailAddress("LexiBlevins@gmail.com");
        user.setCountry("Belgium");
        user.setCity("Geel");
        user.setPostalCode("2440");
        user.setPhoneNumber("04 12 34 56 78");
        user.setBirthday("02/09/1999");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // Act
        Optional<User> responseUserOptional = userService.getUserById(id);

        // Assert
        assertTrue(responseUserOptional.isPresent());
        User responseUser = responseUserOptional.get();
        assertEquals(String.valueOf(id), responseUser.getId());
        assertEquals("Lexi", responseUser.getFirstName());
        assertEquals("Blevins", responseUser.getLastName());
        assertEquals("LexiBlevins", responseUser.getUsername());
        assertEquals("LexiBlevins@gmail.com", responseUser.getEmailAddress());
        assertEquals("Belgium", responseUser.getCountry());
        assertEquals("Geel", responseUser.getCity());
        assertEquals("2440", responseUser.getPostalCode());
        assertEquals("04 12 34 56 78", responseUser.getPhoneNumber());
        assertEquals("02/09/1999", responseUser.getBirthday());
    }

    @Test
    public void testGetUserById_NotFound(){
        // Arrange
        User user = new User();
        user.setId("15");
        user.setFirstName("Lexi");
        user.setLastName("Blevins");
        user.setUsername("LexiBlevins");
        user.setEmailAddress("LexiBlevins@gmail.com");
        user.setCountry("Belgium");
        user.setCity("Geel");
        user.setPostalCode("2440");
        user.setPhoneNumber("04 12 34 56 78");
        user.setBirthday("02/09/1999");

        when(userRepository.findById("10")).thenReturn(Optional.empty());

        // Act
        Optional<User> responseUserOptional = userService.getUserById("10");

        // Assert
        assertFalse(responseUserOptional.isPresent());
    }

    @Test
    public void testCreateUser() {
        // Arrange
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName("Lillie");
        userRequest.setLastName("Rose");
        userRequest.setUsername("Lillie123");
        userRequest.setEmailAddress("Lillie.rose@gmail.com");
        userRequest.setCountry("Belgium");
        userRequest.setCity("Antwerpen");
        userRequest.setPostalCode("2000");
        userRequest.setPhoneNumber("04 54 78 98 78");
        userRequest.setBirthday("10/07/2001");

        // Act
        userService.createUser(userRequest);

        // Assert
        verify(userRepository, times(1)).save(any(User.class));

    }

    @Test
    public void testEditUser(){
        // Arrange
        User user = new User();
        user.setId("2");
        user.setFirstName("Lillie");
        user.setLastName("Rose");
        user.setUsername("Lillie123");
        user.setEmailAddress("Lillie.rose@gmail.com");
        user.setCountry("Belgium");
        user.setCity("Antwerpen");
        user.setPostalCode("2000");
        user.setPhoneNumber("04 54 78 98 78");
        user.setBirthday("10/07/2001");

        when(userRepository.findById("2")).thenReturn(Optional.of(user));

        // Act
        UserRequest userRequest = new UserRequest();
        userRequest.setEmailAddress("lillierose@hotmail.com");
        userRequest.setPhoneNumber("04 89 65 12 45");

        userService.editUserBy(user.getId(), userRequest);

        // Assert
        verify(userRepository, times(1)).save(Mockito.eq(user));
        assertEquals("lillierose@hotmail.com", user.getEmailAddress());
        assertEquals("04 89 65 12 45", user.getPhoneNumber());
    }

    @Test
    public void testDeleteUser()
    {
        // Arrange
        User user = new User();
        user.setId("4");
        user.setFirstName("Lillie");
        user.setLastName("Rose");
        user.setUsername("Lillie123");
        user.setEmailAddress("Lillie.rose@gmail.com");
        user.setCountry("Belgium");
        user.setCity("Antwerpen");
        user.setPostalCode("2000");
        user.setPhoneNumber("04 54 78 98 78");
        user.setBirthday("10/07/2001");

        when(userRepository.findById("4")).thenReturn(Optional.of(user));

        // Act
        userService.deleteUser(user.getId());

        // Assert
        verify(userRepository, times(1)).deleteById(user.getId());
    }

}

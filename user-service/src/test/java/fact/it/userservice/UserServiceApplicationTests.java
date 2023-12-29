package fact.it.userservice;

import fact.it.userservice.dto.UserRequest;
import fact.it.userservice.model.User;
import fact.it.userservice.repository.UserRepository;
import fact.it.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.mongodb.assertions.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceApplicationTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testGetUserByUserName_Found(){
        // Arrange
        User user = new User();
        user.setId("5");
        user.setFirstName("Lexi");
        user.setLastName("Blevins");
        user.setUsername("LexiBlevins");
        user.setEmailAddress("LexiBlevins@gmail.com");
        user.setCountry("Belgium");
        user.setCity("Geel");
        user.setPostalCode("2440");
        user.setPhoneNumber("04 12 34 56 78");
        user.setBirthday("02/09/1999");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        // Act
        User responseUserOptional = userService.getUserByUsername(user.getUsername());

        // Assert
        verify(userRepository, times(1)).findByUsername(user.getUsername());
        assertEquals("5", responseUserOptional.getId());
        assertEquals("Lexi", responseUserOptional.getFirstName());
        assertEquals("Blevins", responseUserOptional.getLastName());
        assertEquals("LexiBlevins", responseUserOptional.getUsername());
        assertEquals("LexiBlevins@gmail.com", responseUserOptional.getEmailAddress());
        assertEquals("Belgium", responseUserOptional.getCountry());
        assertEquals("Geel", responseUserOptional.getCity());
        assertEquals("2440", responseUserOptional.getPostalCode());
        assertEquals("04 12 34 56 78", responseUserOptional.getPhoneNumber());
        assertEquals("02/09/1999", responseUserOptional.getBirthday());
    }
    @Test
    public void testGetUserByUserName_NotFound(){
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

        when(userRepository.findByUsername("nonExistentUsername")).thenReturn(null);

        // Act
        User responseUser = userService.getUserByUsername("nonExistentUsername");

        // Assert
        assertNull(responseUser);
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

        when(userRepository.findByUsername("Lillie123")).thenReturn((user));

        // Act
        UserRequest userRequest = new UserRequest();
        userRequest.setEmailAddress("lillierose@hotmail.com");
        userRequest.setPhoneNumber("04 89 65 12 45");

        userService.editUserBy(user.getUsername(), userRequest);

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

        when(userRepository.findByUsername("Lillie123")).thenReturn(user);

        // Act
        userService.deleteByUsername(user.getUsername());

        // Assert
        verify(userRepository, times(1)).deleteByUsername(user.getUsername());
    }


}
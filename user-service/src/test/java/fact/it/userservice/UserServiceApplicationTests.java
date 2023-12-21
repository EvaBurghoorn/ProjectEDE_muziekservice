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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceApplicationTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testGetUser(){
        // Arrange
        String id = "5";
        User user = new User();
        user.setId(id);
        user.setFirstName("Kyara");
        user.setLastName("Van Genechten");
        user.setUsername("KyaraVanGenechten2");
        user.setEmailAddress("kyara.vangenechten@outlok.com");
        user.setCountry("Belgium");
        user.setCity("Geel");
        user.setPhoneNumber("04 12 34 56 78");
        user.setBirthday("01/01/2003");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // Act
        Optional<User> responseUserOptional = userService.getUserById(id);

        // Assert
        assertTrue(responseUserOptional.isPresent()); // Controleer of de optionele gebruiker aanwezig is

        User responseUser = responseUserOptional.get(); // Haal de gebruiker op uit de optionele waarde
        assertEquals(String.valueOf(id), responseUser.getId());
        assertEquals("Kyara", responseUser.getFirstName());
        assertEquals("Van Genechten", responseUser.getLastName());
        assertEquals("KyaraVanGenechten2", responseUser.getUsername());
        assertEquals("kyara.vangenechten@outlok.com", responseUser.getEmailAddress());
        assertEquals("Belgium", responseUser.getCountry());
        assertEquals("Geel", responseUser.getCity());
        assertEquals("04 12 34 56 78", responseUser.getPhoneNumber());
        assertEquals("01/01/2003", responseUser.getBirthday());
    }

    @Test
    public void testCreateUser() {
        // Arrange
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName("Kyara");
        userRequest.setLastName("Van Genechten");
        userRequest.setUsername("KyaraVanGenechten2");
        userRequest.setEmailAddress("kyara.vangenechten@outlok.com");
        userRequest.setCountry("Belgium");
        userRequest.setCity("Geel");
        userRequest.setPhoneNumber("04 12 34 56 78");
        userRequest.setBirthday("01/01/2003");

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
        user.setFirstName("Kyara");
        user.setLastName("Van Genechten");
        user.setUsername("KyaraVanGenechten2");
        user.setEmailAddress("kyara.vangenechten@outlok.com");
        user.setCountry("Belgium");
        user.setCity("Geel");
        user.setPhoneNumber("04 12 34 56 78");
        user.setBirthday("01/01/2003");

        when(userRepository.findById("2")).thenReturn(Optional.of(user));

        // Act
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName("Eva");

        userService.editUserBy(user.getId(), userRequest);

        // Assert
        verify(userRepository, times(1)).save(Mockito.eq(user));
    }

    @Test
    public void testDeleteUser()
    {
        // Arrange
        User user = new User();
        user.setId("4");
        user.setFirstName("Kyara");
        user.setLastName("Van Genechten");
        user.setUsername("KyaraVanGenechten2");
        user.setEmailAddress("kyara.vangenechten@outlok.com");
        user.setCountry("Belgium");
        user.setCity("Geel");
        user.setPhoneNumber("04 12 34 56 78");
        user.setBirthday("01/01/2003");
        when(userRepository.findById("4")).thenReturn(Optional.of(user));

        // Act
        userService.deleteUser(user.getId());

        // Assert
        verify(userRepository, times(1)).deleteById(user.getId());
    }

}

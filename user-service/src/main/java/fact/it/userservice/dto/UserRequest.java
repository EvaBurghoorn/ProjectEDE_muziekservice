package fact.it.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String emailAddress;
    private String country;
    private String city;
    private String postalCode;
    private String phoneNumber;
    private String birthday;
}

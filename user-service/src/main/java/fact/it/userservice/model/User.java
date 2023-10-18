package fact.it.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(value = "user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data

public class User {
    private String id;
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

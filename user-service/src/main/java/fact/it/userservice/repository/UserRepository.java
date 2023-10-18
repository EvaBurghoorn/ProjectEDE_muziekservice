package fact.it.userservice.repository;

import fact.it.userservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    List<User> findByUsername(List<String>username);
    User findByUserId(int id);

}

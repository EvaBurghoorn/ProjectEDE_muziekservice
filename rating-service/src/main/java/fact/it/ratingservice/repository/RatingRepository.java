package fact.it.ratingservice.repository;

import fact.it.ratingservice.model.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends MongoRepository<Rating, String> {

    List<Rating> findAllByUsername(@Param("username") String username);

}

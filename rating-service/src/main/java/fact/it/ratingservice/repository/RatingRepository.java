package fact.it.ratingservice.repository;

import fact.it.ratingservice.model.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RatingRepository extends MongoRepository<Rating, String> {

}

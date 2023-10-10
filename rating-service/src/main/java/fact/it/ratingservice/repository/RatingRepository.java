package fact.it.ratingservice.repository;

import fact.it.ratingservice.model.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RatingRepository extends MongoRepository<Rating, String> {
}

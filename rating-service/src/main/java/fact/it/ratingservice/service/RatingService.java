package fact.it.ratingservice.service;

import fact.it.ratingservice.dto.RatingRequest;
import fact.it.ratingservice.dto.RatingResponse;
import fact.it.ratingservice.model.Rating;
import fact.it.ratingservice.repository.RatingRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;

    @PostConstruct
    public void loadData(){
        if(ratingRepository.count() <= 0){
            Rating rating = new Rating();
            rating.setDisliked(false);
            rating.setLiked(true);

            Rating rating_two = new Rating();
            rating_two.setDisliked(true);
            rating_two.setLiked(false);
        }
    }
//    public void createRating(RatingRequest ratingRequest){
//        Rating rating = Rating.builder()
//                .isLiked(ratingRequest.isLiked())
//                .isDisliked(ratingRequest.isDisliked())
//                .build();
//
//        ratingRepository.save(rating);
//    }


    public List<RatingResponse> getAllRatings(){
        List<Rating> ratings = ratingRepository.findAll();
        return ratings.stream().map(this::mapToRatingResponse).toList();
    }


    private RatingResponse mapToRatingResponse(Rating rating)
    {
        return RatingResponse.builder()
                .id(rating.getId())
                .isLiked(rating.isLiked())
                .isDisliked(rating.isDisliked())
                .build();
    }
}

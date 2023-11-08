package fact.it.ratingservice.service;

import fact.it.ratingservice.dto.MusicPodcastResponse;
import fact.it.ratingservice.dto.RatingRequest;
import fact.it.ratingservice.dto.RatingResponse;
import fact.it.ratingservice.model.Rating;
import fact.it.ratingservice.repository.RatingRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final WebClient webClient;

    public void rateMusicPodcast(RatingRequest ratingRequest){
        Rating rating = new Rating();
        rating.setUniqueIdentifier(UUID.randomUUID().toString());

//        String uniqueIdentifierCodes = ratingResponse.getUniqueIdentifier().toString();
        String uniqueIdentifierCode = ratingRequest.getUniqueIdentifier();

        MusicPodcastResponse[] musicPodcastResponseArray = webClient.get()
                .uri("http:localhost:8080/musicpodcast",
                        uriBuilder -> uriBuilder.queryParam("uniqueIdentifier", uniqueIdentifierCode).build())
                .retrieve()
                .bodyToMono(MusicPodcastResponse[].class)
                .block();
//        if(uniqueIdentifierCode != null){
//
//        }

    }

    @PostConstruct
    public void loadData(){
        if(ratingRepository.count() <= 0){
            Rating rating = new Rating();
            rating.setDisliked(false);
            rating.setLiked(true);
            rating.setUniqueIdentifier("Title1Artist1");

            Rating rating_two = new Rating();
            rating_two.setDisliked(true);
            rating_two.setLiked(false);
            rating_two.setUniqueIdentifier("TitlePodcastArtist2");

        }
    }
    public void createRating(RatingRequest ratingRequest){
        Rating rating = Rating.builder()
                .isLiked(ratingRequest.isLiked())
                .isDisliked(ratingRequest.isDisliked())
                .build();

        ratingRepository.save(rating);
    }


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

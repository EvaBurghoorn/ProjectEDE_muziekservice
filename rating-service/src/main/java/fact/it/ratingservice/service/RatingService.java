package fact.it.ratingservice.service;

import fact.it.ratingservice.dto.MusicPodcastResponse;
import fact.it.ratingservice.dto.RatingRequest;
import fact.it.ratingservice.dto.RatingResponse;
import fact.it.ratingservice.dto.UserResponse;
import fact.it.ratingservice.model.Rating;
import fact.it.ratingservice.repository.RatingRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final WebClient webClient;


    public void rateMusicPodcast(RatingRequest ratingRequest){
        Rating rating = new Rating();
//        rating.setUniqueIdentifier(UUID.randomUUID().toString());

//        String uniqueIdentifierCodes = ratingResponse.getUniqueIdentifier().toString();
        String uniqueIdentifierCode = ratingRequest.getUniqueIdentifier();
        String username = ratingRequest.getUsername();

        MusicPodcastResponse[] musicPodcastResponseArray = webClient.get()
                .uri("http:localhost:8080/musicpodcast",
                        uriBuilder -> uriBuilder.queryParam("uniqueIdentifier", uniqueIdentifierCode).build())
                .retrieve()
                .bodyToMono(MusicPodcastResponse[].class)
                .block();

        UserResponse[] userResponseArray = webClient.get()
                .uri("http:localhost:8081/peruser",
                        uriBuilder -> uriBuilder.queryParam("username", username).build())
                .retrieve()
                .bodyToMono(UserResponse[].class)
                .block();

        if(uniqueIdentifierCode != null){
            MusicPodcastResponse musicPodcastResponse = Arrays.stream(musicPodcastResponseArray )
                    .filter(mp -> mp.getUniqueIdentifier().equals(uniqueIdentifierCode))
                    .findFirst()
                    .orElse(null);
            if(username != null) {

                UserResponse userResponse = Arrays.stream(userResponseArray)
                        .filter(u -> u.getUsername().equals(username))
                        .findFirst()
                        .orElse(null);

                if (musicPodcastResponse != null && rating.isLiked()) {
                    rating.setLiked(true);
                    rating.setDisliked(!rating.isDisliked());
                    rating.setUsername(userResponse.getUsername());

                }
                if (musicPodcastResponse != null && rating.isDisliked()) {
                    rating.setDisliked(true);
                    rating.setLiked(!rating.isLiked());
                    rating.setUsername(userResponse.getUsername());
                }
            }
        }
    }

    public void deleteRatingMusicPodcast(int ratingId){
        Rating deleteRating = ratingRepository.findById(ratingId);
        if(deleteRating != null){
            ratingRepository.delete(deleteRating);
        }
    }

    public void editRatingMusicPodcast(int ratingId, RatingRequest ratingRequest){
        Rating editRating = ratingRepository.findById(ratingId);
        if(editRating != null && ratingRequest.isLiked()) {
            editRating.setLiked(true);
            editRating.setDisliked(!editRating.isDisliked());
        }
        if(editRating != null && ratingRequest.isDisliked()) {
            editRating.setDisliked(true);
            editRating.setLiked(!editRating.isLiked());
        }
        ratingRepository.save(editRating);
    }



    public List<MusicPodcastResponse> getMusicPodcast(){
        MusicPodcastResponse[] musicPodcastResponseArray = webClient.get()
                .uri("http:localhost:8080/LikedMusicpodcast",
                        uriBuilder -> uriBuilder.build())
                .retrieve()
                .bodyToMono(MusicPodcastResponse[].class)
                .block();
        return Arrays.stream(musicPodcastResponseArray).toList();
    }


        @PostConstruct
    public void loadData(){
        if(ratingRepository.count() <= 0){
            Rating rating = new Rating();
            rating.setDisliked(false);
            rating.setLiked(true);
            rating.setUniqueIdentifier("Title1Artist1");
            rating.setUsername("LexiBlevins");

            Rating rating_two = new Rating();
            rating_two.setDisliked(true);
            rating_two.setLiked(false);
            rating_two.setUniqueIdentifier("TitlePodcastArtist2");
            rating.setUsername("Lillie123");

        }
    }
    public void createRating(RatingRequest ratingRequest){
        Rating rating = Rating.builder()
                .isLiked(ratingRequest.isLiked())
                .isDisliked(ratingRequest.isDisliked())
                .uniqueIdentifier(ratingRequest.getUniqueIdentifier())
                .username(ratingRequest.getUsername())
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
                .uniqueIdentifier(rating.getUniqueIdentifier())
                .username(rating.getUsername())
                .build();
    }
}

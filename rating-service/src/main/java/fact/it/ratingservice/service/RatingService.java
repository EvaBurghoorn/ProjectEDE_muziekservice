package fact.it.ratingservice.service;

import fact.it.ratingservice.dto.MusicPodcastResponse;
import fact.it.ratingservice.dto.RatingRequest;
import fact.it.ratingservice.dto.RatingResponse;
import fact.it.ratingservice.dto.UserResponse;
import fact.it.ratingservice.model.Rating;
import fact.it.ratingservice.repository.RatingRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final WebClient webClient;

    @Value("${musicpodcastservice.baseurl}")
    private String musicpodcastServiceBaseUrl;

    @Value("${userservice.baseurl}")
    private String userServiceBaseUrl;

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

            ratingRepository.save(rating);
            ratingRepository.save(rating_two);
        }
    }

//    Create a new rating for a music podcast
    public void rateMusicPodcast(RatingRequest ratingRequest){

        Rating rating = new Rating();

        String uniqueIdentifierCode = ratingRequest.getUniqueIdentifier();
        String username = ratingRequest.getUsername();

        MusicPodcastResponse[] musicPodcastResponseArray = webClient.get()
                .uri("http://" + musicpodcastServiceBaseUrl + "/musicpodcast",
                        uriBuilder -> uriBuilder.queryParam("uniqueIdentifier", uniqueIdentifierCode).build())
                .retrieve()
                .bodyToMono(MusicPodcastResponse[].class)
                .block();

        UserResponse[] userResponseArray = webClient.get()
                .uri("http://" + userServiceBaseUrl + "/user",
                        uriBuilder -> uriBuilder.queryParam("username", username).build())
                .retrieve()
                .bodyToMono(UserResponse[].class)
                .block();

        if (uniqueIdentifierCode != null && username != null) {
            MusicPodcastResponse musicPodcastResponse = Arrays.stream(musicPodcastResponseArray )
                    .filter(mp -> mp.getUniqueIdentifier().equals(uniqueIdentifierCode))
                    .findFirst()
                    .orElse(null);


                UserResponse userResponse = Arrays.stream(userResponseArray)
                        .filter(u -> u.getUsername().equals(username))
                        .findFirst()
                        .orElse(null);


            if (musicPodcastResponse != null && userResponse != null) {
                rating.setUniqueIdentifier(uniqueIdentifierCode);
                rating.setUsername(userResponse.getUsername());
                if (ratingRequest.isLiked()) {
                    rating.setLiked(true);
                    rating.setDisliked(false);
                } else if (ratingRequest.isDisliked()) {
                    rating.setDisliked(true);
                    rating.setLiked(false);
                }
                ratingRepository.save(rating);

            }
        }
    }

//    Edit a rating
    public void editRatingMusicPodcast(String ratingId, RatingRequest ratingRequest){
        Optional<Rating> editRating = ratingRepository.findById(ratingId);
        if(editRating.isPresent() && ratingRequest.isLiked()) {
            Rating rating = editRating.get();
            rating.setLiked(true);
            rating.setDisliked(!rating.isDisliked());
            ratingRepository.save(rating);

        }
        if(editRating.isPresent() && ratingRequest.isDisliked()) {
            Rating rating = editRating.get();
            rating.setDisliked(true);
            rating.setLiked(!rating.isLiked());
            ratingRepository.save(rating);

        }
    }

//    Delete a rating
    public void deleteRatingMusicPodcast(String ratingId){
        Optional<Rating> deleteRating = ratingRepository.findById(ratingId);
        if(deleteRating.isPresent()){
            ratingRepository.deleteById(ratingId);
        }
    }

    // Get all liked music podcasts
    public List<MusicPodcastResponse> getAllLikedMusicPodcast(){
        MusicPodcastResponse[] musicPodcastResponseArray = webClient.get()
                .uri("http://" + musicpodcastServiceBaseUrl + "/musicpodcast",
                        uriBuilder -> uriBuilder.build())
                .retrieve()
                .bodyToMono(MusicPodcastResponse[].class)
                .block();
        return Arrays.stream(musicPodcastResponseArray).toList();
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

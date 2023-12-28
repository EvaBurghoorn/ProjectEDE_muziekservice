package fact.it.ratingservice;

import fact.it.ratingservice.dto.MusicPodcastResponse;
import fact.it.ratingservice.dto.RatingRequest;
import fact.it.ratingservice.dto.RatingResponse;
import fact.it.ratingservice.dto.UserResponse;
import fact.it.ratingservice.model.Rating;
import fact.it.ratingservice.repository.RatingRepository;
import fact.it.ratingservice.service.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class RatingServiceApplicationTests {

    @InjectMocks
    private RatingService ratingService;

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(ratingService, "musicpodcastServiceBaseUrl", "http://localhost:8080");
        ReflectionTestUtils.setField(ratingService, "userServiceBaseUrl", "http://localhost:8081");
    }

    @Test
    public void testCreateRating() {
        // Arrange
        String uniqueIdentifier = "test";
        String username = "test";
        Boolean isDisliked= false;
        Boolean isLiked = true;


        MusicPodcastResponse musicPodcastResponse = new MusicPodcastResponse();
        musicPodcastResponse.setUniqueIdentifier(uniqueIdentifier);

        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(username);

        RatingRequest ratingRequest = new RatingRequest();
        ratingRequest.setUniqueIdentifier(uniqueIdentifier);
        ratingRequest.setUsername(username);
        ratingRequest.setDisliked(isDisliked);
        ratingRequest.setLiked(isLiked);

        Rating savedRating = new Rating();
        savedRating.setUniqueIdentifier(uniqueIdentifier);
        savedRating.setUsername(username);
        savedRating.setDisliked(isDisliked);
        savedRating.setLiked(isLiked);

        when(requestHeadersUriSpec.uri("http://localhost:8080/musicpodcast")).thenReturn(requestHeadersSpec);
        when(requestHeadersUriSpec.uri("http://localhost:8081/user")).thenReturn(requestHeadersSpec);

        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(MusicPodcastResponse[].class)).thenReturn(Mono.just(new MusicPodcastResponse[]{musicPodcastResponse}));
        when(responseSpec.bodyToMono(UserResponse[].class)).thenReturn(Mono.just(new UserResponse[]{userResponse}));
        when(ratingRepository.save(any(Rating.class))).thenReturn(savedRating);

        // Act
        ratingService.rateMusicPodcast(ratingRequest);

        // Assert
        verify(ratingRepository, times(1)).save(any(Rating.class));
    }

        @Test
        void testGetAllRatingsPerUser() {
            // Arrange
            String username = "testUser";
            List<Rating> ratings = new ArrayList<>();
            ratings.add(new Rating());

            when(ratingRepository.findAllByUsername(username)).thenReturn(ratings);

            // Act
            List<RatingResponse> result = ratingService.getAllRatingsPerUser(username);

            // Assert
            assertEquals(ratings.size(), result.size());
        }
    @Test
    public void testEditRating() {
        // Arrange
        Rating rating = new Rating();
        rating.setId("2");
        rating.setLiked(true);
        rating.setDisliked(false);
        rating.setUniqueIdentifier("12354B");
        rating.setUsername("Lexi123");

        when(ratingRepository.findById("2")).thenReturn(Optional.of(rating));

        // Act
        RatingRequest ratingRequest = new RatingRequest();
        ratingRequest.setLiked(false);
        ratingRequest.setDisliked(true);

        ratingService.editRatingMusicPodcast(rating.getId(), ratingRequest);

        // Assert
        verify(ratingRepository, times(1)).findById("2");
        verify(ratingRepository, times(1)).save(any(Rating.class));
        assertFalse(rating.isLiked());
        assertTrue(rating.isDisliked());
    }
    @Test
    public void testDeleteRating() {
        // Arrange
        Rating rating = new Rating();
        rating.setId("4");
        rating.setLiked(true);
        rating.setDisliked(false);
        rating.setUniqueIdentifier("12354B");
        rating.setUsername("Lexi123");

        when(ratingRepository.findById("4")).thenReturn(Optional.of(rating));

        // Act
        ratingService.deleteRatingMusicPodcast("4");

        // Assert
        verify(ratingRepository, times(1)).findById(rating.getId());
        verify(ratingRepository, times(1)).deleteById(rating.getId());
    }

}

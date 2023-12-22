package fact.it.ratingservice;

import fact.it.ratingservice.dto.RatingRequest;
import fact.it.ratingservice.model.Rating;
import fact.it.ratingservice.repository.RatingRepository;
import fact.it.ratingservice.service.RatingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    @Test
    public void testGetAllLikedRatings() {
        // Arrange
        // Act
        // Assert
    }
    @Test
    public void testCreateRating() {
        // Arrange
        RatingRequest ratingRequest = new RatingRequest();
        ratingRequest.setDisliked(false);
        ratingRequest.setLiked(true);
        ratingRequest.setUniqueIdentifier("Title1Artist1");
        ratingRequest.setUsername("LexiBlevins");

        // Act
        ratingService.createRating(ratingRequest);

        // Assert
        verify(ratingRepository, times(1)).save(any(Rating.class));
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

package fact.it.ratingservice.controller;

import fact.it.ratingservice.dto.MusicPodcastResponse;
import fact.it.ratingservice.dto.RatingRequest;
import fact.it.ratingservice.dto.RatingResponse;
import fact.it.ratingservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rating")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    // Create a rating for a music podcast per user
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createRating(@RequestBody RatingRequest ratingRequest){
        ratingService.createRating(ratingRequest);
    }
    // Delete a rating for a music podcast per user

    @DeleteMapping("id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRating(@PathVariable int ratingId)
    {
        ratingService.deleteRatingMusicPodcast(ratingId);
    }

    // Edit a rating for a music podcast per user

    @PutMapping("id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateRating(@PathVariable int ratingId, @RequestBody RatingRequest ratingRequest){
        ratingService.editRatingMusicPodcast(ratingId, ratingRequest);
    }



}

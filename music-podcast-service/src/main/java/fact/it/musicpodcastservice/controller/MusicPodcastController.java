package fact.it.musicpodcastservice.controller;

import fact.it.musicpodcastservice.dto.MusicPodcastRequest;
import fact.it.musicpodcastservice.dto.MusicPodcastResponse;
import fact.it.musicpodcastservice.dto.RatingResponse;
import fact.it.musicpodcastservice.model.MusicPodcast;
import fact.it.musicpodcastservice.service.MusicPodcastService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/musicpodcast")
@RequiredArgsConstructor
public class MusicPodcastController {
    private final MusicPodcastService musicPodcastService;


//    Get all songs and podcasts
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<MusicPodcastResponse> getAllSongsPodcasts(){
        return  musicPodcastService.getAllMusicPodcast();
    }

//    Get all songs
    @GetMapping("/songs")
    @ResponseStatus(HttpStatus.OK)
    public List<MusicPodcastResponse> getAllSongs(){
        return musicPodcastService.getAllSongs();
    }

    //    Get all podcasts
    @GetMapping("/podcasts")
    @ResponseStatus(HttpStatus.OK)
    public List<MusicPodcastResponse> getAllPodcasts(){
        return musicPodcastService.getAllPodcast();
    }


//    Get a musicpodcast with an uniqueIdentifier
    @GetMapping("/id/{uniqueIdentifier}")
    public ResponseEntity<MusicPodcast> getMusicPodcastById(@PathVariable("uniqueIdentifier") String songUniqueIdentifier) {
        Optional<MusicPodcast> musicPodcast = musicPodcastService.getMusicPodcastByUniqueIdentifier(songUniqueIdentifier);
        if (musicPodcast.isPresent()) {
            return new ResponseEntity<>(musicPodcast.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


//    @GetMapping("/ratings/{username}")
//    public ResponseEntity<List<MusicPodcastResponse>> getAllMusicPodcastsWithRatingLikedPerUser(@PathVariable String username) {
//        RatingResponse ratingResponse = new RatingResponse();
//        ratingResponse.setUsername(username);
//
//        List<MusicPodcastResponse> musicPodcastResponses = musicPodcastService.getLikedMusicPodcastsByUsername(ratingResponse);
//        return new ResponseEntity<>(musicPodcastResponses, HttpStatus.OK);
//    }
    @GetMapping("/ratings/{username}")
    public ResponseEntity<List<MusicPodcast>> getAllMusicPodcastsWithRatingLikedPerUser(@PathVariable String username) {
        List<MusicPodcast> musicPodcasts = musicPodcastService.getLikedMusicPodcastsByUsername(username);
        return new ResponseEntity<>(musicPodcasts, HttpStatus.OK);
    }

    @GetMapping("/rating/{username}/{uniqueIdentifier}")
    public ResponseEntity<MusicPodcast> getMusicPodcastPerUserByUsername(@PathVariable String username, @PathVariable String uniqueIdentifier) {
        MusicPodcast musicPodcast = musicPodcastService.getMusicPodcastPerUser(username, uniqueIdentifier);
        return new ResponseEntity<>(musicPodcast, HttpStatus.OK);
    }


}

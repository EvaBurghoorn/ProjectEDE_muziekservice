package fact.it.musicpodcastservice.controller;

import fact.it.musicpodcastservice.dto.MusicPodcastRequest;
import fact.it.musicpodcastservice.dto.MusicPodcastResponse;
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

//    Get all songs or podcasts
    @GetMapping("/songs")
    @ResponseStatus(HttpStatus.OK)
    public List<MusicPodcastResponse> getAllSongs(){
        return musicPodcastService.getAllSongs();
    }

    @GetMapping("/podcasts")
    @ResponseStatus(HttpStatus.OK)
    public List<MusicPodcastResponse> getAllPodcasts(){
        return musicPodcastService.getAllPodcast();
    }


//    Get a song with an id
    @GetMapping("/song/{id}")
    public ResponseEntity<MusicPodcast> getSongById(@PathVariable("id") String songId) {
        Optional<MusicPodcast> musicPodcast = musicPodcastService.getSongById(songId);
        if (musicPodcast.isPresent()) {
            return new ResponseEntity<>(musicPodcast.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//     Get a podcast with an id
    @GetMapping("/podcast/{id}")
    public ResponseEntity<MusicPodcast> getPodcastById(@PathVariable("id") String podcastId) {
        Optional<MusicPodcast> musicPodcast = musicPodcastService.getPodcastById(podcastId);
        if (musicPodcast.isPresent()) {
            return new ResponseEntity<>(musicPodcast.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }





}

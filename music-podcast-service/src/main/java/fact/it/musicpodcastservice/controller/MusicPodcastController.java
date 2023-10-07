package fact.it.musicpodcastservice.controller;

import fact.it.musicpodcastservice.dto.MusicPodcastRequest;
import fact.it.musicpodcastservice.dto.MusicPodcastResponse;
import fact.it.musicpodcastservice.service.MusicPodcastService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/musicpodcast")
@RequiredArgsConstructor
public class MusicPodcastController {
    private final MusicPodcastService musicPodcastService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createMusicPodcast (@RequestBody MusicPodcastRequest musicPodcastRequest){
        musicPodcastService.createMusicPodcast(musicPodcastRequest);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<MusicPodcastResponse> getAllSongsPodcasts(){
        return  musicPodcastService.getAllMusicPodcast();
    }

}

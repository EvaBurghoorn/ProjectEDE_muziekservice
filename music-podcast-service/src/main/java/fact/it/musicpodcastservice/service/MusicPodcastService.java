package fact.it.musicpodcastservice.service;

import fact.it.musicpodcastservice.dto.MusicPodcastRequest;
import fact.it.musicpodcastservice.dto.MusicPodcastResponse;
import fact.it.musicpodcastservice.model.MusicPodcast;
import fact.it.musicpodcastservice.repository.MusicPodcastRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MusicPodcastService {
    private final MusicPodcastRepository musicPodcastRepository;

    public void createMusicPodcast(MusicPodcastRequest musicPodcastRequest){
        MusicPodcast musicPodcast = MusicPodcast.builder()
                .title(musicPodcastRequest.getTitle())
                .artist(musicPodcastRequest.getArtist())
                .genre(musicPodcastRequest.getGenre())
                .durationSeconds(musicPodcastRequest.getDurationSeconds())
                .isPodcast(musicPodcastRequest.isPodcast())
                .build();

        musicPodcastRepository.save(musicPodcast);
    }

    public List<MusicPodcastResponse> getAllMusicPodcast(){
        List<MusicPodcast> musicPodcasts = musicPodcastRepository.findAll();
        return musicPodcasts.stream().map(this::mapToMusicPodcastResponse).toList();
    }

    private MusicPodcastResponse mapToMusicPodcastResponse(MusicPodcast musicPodcast) {
        return MusicPodcastResponse.builder()
                .id(musicPodcast.getId())
                .title(musicPodcast.getTitle())
                .artist(musicPodcast.getArtist())
                .genre(musicPodcast.getGenre())
                .durationSeconds(musicPodcast.getDurationSeconds())
                .isPodcast(musicPodcast.isPodcast())
                .build();
    }
}

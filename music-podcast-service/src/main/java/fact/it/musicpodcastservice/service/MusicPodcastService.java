package fact.it.musicpodcastservice.service;

import fact.it.musicpodcastservice.dto.MusicPodcastRequest;
import fact.it.musicpodcastservice.dto.MusicPodcastResponse;
import fact.it.musicpodcastservice.dto.RatingResponse;
import fact.it.musicpodcastservice.model.MusicPodcast;
import fact.it.musicpodcastservice.repository.MusicPodcastRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MusicPodcastService {
    private final MusicPodcastRepository musicPodcastRepository;
    private final WebClient webClient;


//            rating.setUniqueIdentifier(UUID.randomUUID().toString());


    @PostConstruct
    public void loadData(){
        if(musicPodcastRepository.count() <= 0){
            MusicPodcast musicPodcast = new MusicPodcast();
            musicPodcast.setTitle("Title1");
            musicPodcast.setArtist("Artist1");
            musicPodcast.setDurationSeconds(200);
            musicPodcast.setGenre("Pop");
            musicPodcast.setPodcast(false);
            musicPodcast.setUniqueIdentifier("Title1Artist1");

            MusicPodcast musicPodcast_two = new MusicPodcast();
            musicPodcast_two.setTitle("TitlePodcast");
            musicPodcast_two.setArtist("Artist2");
            musicPodcast_two.setDurationSeconds(5000);
            musicPodcast_two.setGenre("Drama");
            musicPodcast_two.setPodcast(true);
            musicPodcast_two.setUniqueIdentifier("TitlePodcastArtist2");

        }
    }



    // Get all musicPodcasts with a rating per user
    public void getAllMusicPodcastsWithRatingPerUser(MusicPodcastRequest musicPodcastRequest){
        String uniqueIdentifierCode = musicPodcastRequest.getUniqueIdentifier();


        RatingResponse[] ratingResponseArray = webClient.get()
                .uri("http:localhost:8082/rating",
                        uriBuilder -> uriBuilder.queryParam("uniqueIdentifier", uniqueIdentifierCode).build())
                .retrieve()
                .bodyToMono(RatingResponse[].class)
                .block();

//        if (uniqueIdentifierCode != null){
//            public List<MusicPodcastResponse> getAllMusicPodcast(){
//                List<MusicPodcast> musicPodcasts = musicPodcastRepository.findAll();
//                return musicPodcasts.stream().map(this::mapToMusicPodcastResponse).toList();
//            }
//        }


    }




//    Get all the songs and podcasts
    public List<MusicPodcastResponse> getAllMusicPodcast(){
        List<MusicPodcast> musicPodcasts = musicPodcastRepository.findAll();
        return musicPodcasts.stream().map(this::mapToMusicPodcastResponse).toList();
    }

//    Get all songs
    public List<MusicPodcastResponse> getAllSongs() {
        List<MusicPodcast> musicPodcasts = musicPodcastRepository.findAll();
        return musicPodcasts.stream()
                .filter(musicPodcast -> !musicPodcast.isPodcast())
                .map(this::mapToMusicPodcastResponse).toList();
    }

//    Get all the podcasts
    public List<MusicPodcastResponse> getAllPodcast() {
        List<MusicPodcast> musicPodcasts = musicPodcastRepository.findAll();
        return musicPodcasts.stream()
                .filter(MusicPodcast:: isPodcast)
                .map(this::mapToMusicPodcastResponse)
                .toList();
    }

// Get a song
    public Optional<MusicPodcast> getSongById(String songId) {
        return musicPodcastRepository.findById(songId);
    }

//    Get a podcast
    public Optional<MusicPodcast> getPodcastById(String podcastId) {
        return musicPodcastRepository.findById(podcastId);
    }


    private MusicPodcastResponse mapToMusicPodcastResponse(MusicPodcast musicPodcast) {
        return MusicPodcastResponse.builder()
                .id(musicPodcast.getId())
                .title(musicPodcast.getTitle())
                .artist(musicPodcast.getArtist())
                .genre(musicPodcast.getGenre())
                .durationSeconds(musicPodcast.getDurationSeconds())
                .isPodcast(musicPodcast.isPodcast())
                .uniqueIdentifier(musicPodcast.getUniqueIdentifier())
                .build();
    }


}

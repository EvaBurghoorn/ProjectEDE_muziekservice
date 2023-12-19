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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
//            musicPodcast.setUsername("LexiBlevins");

            MusicPodcast musicPodcast_two = new MusicPodcast();
            musicPodcast_two.setTitle("TitlePodcast");
            musicPodcast_two.setArtist("Artist2");
            musicPodcast_two.setDurationSeconds(5000);
            musicPodcast_two.setGenre("Drama");
            musicPodcast_two.setPodcast(true);
            musicPodcast_two.setUniqueIdentifier("TitlePodcastArtist2");
//            musicPodcast_two.setUsername("Lillie123");
            musicPodcastRepository.save(musicPodcast);
            musicPodcastRepository.save(musicPodcast_two);
        }
    }



    // Get all liked musicPodcasts per user
    public List<MusicPodcastResponse> getAllMusicPodcastsWithRatingLikedPerUser(MusicPodcastRequest musicPodcastRequest, RatingResponse ratingResponse) {
        String uniqueIdentifierCode = musicPodcastRequest.getUniqueIdentifier();
        String username = ratingResponse.getUsername();


        RatingResponse[] ratingResponsePerUserArray = webClient.get()
                .uri("http:localhost:8082/rating",
                        uriBuilder -> uriBuilder.queryParam("username", username).build())
                .retrieve()
                .bodyToMono(RatingResponse[].class)
                .block();


        List<MusicPodcastResponse> musicPodcastResponses = new ArrayList<>();
        if (username != null && uniqueIdentifierCode != null) {
            RatingResponse userMP = Arrays.stream(ratingResponsePerUserArray)
                    .filter(r -> r.isLiked())
                    .findAny()
                    .orElse(null);

            if (userMP != null) {
                Optional<MusicPodcast> musicPodcastOptional = musicPodcastRepository.findById(userMP.getId());
                if (musicPodcastOptional.isPresent()) {
                    MusicPodcast musicPodcast = musicPodcastOptional.get();
                    MusicPodcastResponse musicPodcastResponse = mapToMusicPodcastResponse(musicPodcast);
                    musicPodcastResponses.add(musicPodcastResponse);
                }
            }
        }
        return musicPodcastResponses;
    }




    // Get a musicPodcast per user
    public MusicPodcast getMusicPodcastPerUser(MusicPodcastRequest musicPodcastRequest, RatingResponse ratingResponse) {
        String uniqueIdentifierCode = musicPodcastRequest.getUniqueIdentifier();
        String username = ratingResponse.getUsername();


        RatingResponse[] ratingResponsePerUserArray = webClient.get()
                .uri("http:localhost:8082/rating",
                        uriBuilder -> uriBuilder.queryParam("username", username, "uniqueIdentifierCode", uniqueIdentifierCode).build())
                .retrieve()
                .bodyToMono(RatingResponse[].class)
                .block();



        if (username != null && uniqueIdentifierCode != null) {
            RatingResponse userMP = Arrays.stream(ratingResponsePerUserArray)
                    .findFirst()
                    .orElse(null);


            if (userMP != null) {
              MusicPodcast musicPodcast = musicPodcastRepository.findByUniqueIdentifier(userMP.getUniqueIdentifier());
                return musicPodcast;

            }
        }
        return null;
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
//                .username(musicPodcast.getUsername())
                .build();
    }


}

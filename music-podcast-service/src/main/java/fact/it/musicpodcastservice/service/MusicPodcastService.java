package fact.it.musicpodcastservice.service;

import fact.it.musicpodcastservice.dto.MusicPodcastRequest;
import fact.it.musicpodcastservice.dto.MusicPodcastResponse;
import fact.it.musicpodcastservice.dto.RatingResponse;
import fact.it.musicpodcastservice.model.MusicPodcast;
import fact.it.musicpodcastservice.repository.MusicPodcastRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MusicPodcastService {
    private final MusicPodcastRepository musicPodcastRepository;
    private final WebClient webClient;

    @Value("${ratingservice.baseurl}")
    private String ratingServiceBaseUrl;

//    @Value("${musicpodcastservice.baseurl}")
//    private String musicpodcastServiceBaseUrl;

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
            musicPodcastRepository.save(musicPodcast);
            musicPodcastRepository.save(musicPodcast_two);
        }
    }


    // Get all musicPodcasts with a liked rating per user
//    public List<MusicPodcastResponse> getAllMusicPodcastsWithRatingLikedPerUser(RatingResponse ratingResponse) {
//        String username = ratingResponse.getUsername();
//
//        if (username != null) {
//            RatingResponse[] ratingResponsePerUserArray = webClient.get()
//                    .uri("http://" + ratingServiceBaseUrl + "/rating",
//                            uriBuilder -> uriBuilder.queryParam("username", username).build())
//                    .retrieve()
//                    .bodyToMono(RatingResponse[].class)
//                    .block();
//
//            List<MusicPodcastResponse> musicPodcastResponses = new ArrayList<>();
//            if (ratingResponsePerUserArray != null) {
//                Arrays.stream(ratingResponsePerUserArray)
//                        .filter(RatingResponse::isLiked)
//                        .map(RatingResponse::getUniqueIdentifier)
//                        .map(this::getMusicPodcastByUniqueIdentifier)
//                        .filter(Optional::isPresent)
//                        .map(Optional::get)
//                        .map(this::mapToMusicPodcastResponse)
//                        .forEach(musicPodcastResponses::add);
//            }
//            return musicPodcastResponses;
//        }
//        return Collections.emptyList();
//    }
    // Create a custom exception class
    public class CustomException extends RuntimeException {
        private final String errorMessage;

        public CustomException(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

    // Modify your method to throw the custom exception
    public List<MusicPodcastResponse> getAllMusicPodcastsWithRatingLikedPerUser(RatingResponse ratingResponse) throws CustomException {
        String username = ratingResponse.getUsername();

        if (username == null) {
            throw new CustomException("Username cannot be null");
        }

        try {
            RatingResponse[] ratingResponsePerUserArray = webClient.get()
                    .uri("http://" + ratingServiceBaseUrl + "/rating",
                            uriBuilder -> uriBuilder.queryParam("username", username).build())
                    .retrieve()
                    .bodyToMono(RatingResponse[].class)
                    .block();

            if (ratingResponsePerUserArray == null) {
                throw new CustomException("No rating responses received");
            }

            List<MusicPodcastResponse> musicPodcastResponses = new ArrayList<>();
            Arrays.stream(ratingResponsePerUserArray)
                    .filter(RatingResponse::isLiked)
                    .map(RatingResponse::getUniqueIdentifier)
                    .map(this::getMusicPodcastByUniqueIdentifier)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(this::mapToMusicPodcastResponse)
                    .forEach(musicPodcastResponses::add);

            return musicPodcastResponses;
        } catch (CustomException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage());
        }
    }



    // Get a musicPodcast per user
    public MusicPodcast getMusicPodcastPerUser(String username, String uniqueIdentifier) {

        RatingResponse[] ratingResponsePerUserArray = webClient.get()
                .uri("http://" + ratingServiceBaseUrl + "/rating",
                        uriBuilder -> uriBuilder.queryParam("username", username, "uniqueIdentifierCode", uniqueIdentifier).build())
                .retrieve()
                .bodyToMono(RatingResponse[].class)
                .block();

        if (username != null && uniqueIdentifier != null) {
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

// Get a music podcast by unique identifier
    public Optional<MusicPodcast> getMusicPodcastByUniqueIdentifier(String songUniqueIdentifier) {
        return Optional.ofNullable(musicPodcastRepository.findByUniqueIdentifier(songUniqueIdentifier));
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

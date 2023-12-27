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


//    // Get all  musicPodcasts with a liked rating per user
//    public List<MusicPodcastResponse> getAllMusicPodcastsWithRatingLikedPerUser(MusicPodcastRequest musicPodcastRequest, RatingResponse ratingResponse) {
//        String uniqueIdentifierCode = musicPodcastRequest.getUniqueIdentifier();
//        String username = ratingResponse.getUsername();
//
//
//        RatingResponse[] ratingResponsePerUserArray = webClient.get()
//                .uri("http://" + ratingServiceBaseUrl + "/rating",
//                        uriBuilder -> uriBuilder.queryParam("username", username).build())
//                .retrieve()
//                .bodyToMono(RatingResponse[].class)
//                .block();
//
//
//        List<MusicPodcastResponse> musicPodcastResponses = new ArrayList<>();
//        if (username != null) {
//            Arrays.stream(ratingResponsePerUserArray)
//                    .filter(r -> r.isLiked())
//                    .forEach(r -> {
//                        MusicPodcastResponse[] musicPodcastResponseArray = webClient.get()
//                                .uri("http://" + musicpodcastServiceBaseUrl + "/musicpodcast",
//                                        uriBuilder -> uriBuilder.queryParam("uniqueIdentifier", r.getUniqueIdentifier()).build())
//                                .retrieve()
//                                .bodyToMono(MusicPodcastResponse[].class)
//                                .block();
//
//                        if (musicPodcastResponseArray != null) {
//                            musicPodcastResponses.addAll(Arrays.asList(musicPodcastResponseArray));
//                        }
//                    });
//        }
//        return musicPodcastResponses;
//    }

////     Get all musicPodcasts with liked rating per user
//    public List<MusicPodcastResponse> getAllMusicPodcastsWithRatingLikedPerUser(RatingResponse ratingResponse) {
//
//        String username = ratingResponse.getUsername();
//
//        if (username != null) {
//
//            RatingResponse[] ratingResponsePerUserArray = webClient.get()
//                    .uri("http://" + ratingServiceBaseUrl + "/rating/username/" + username
//                            )
//                    .retrieve()
//                    .bodyToMono(RatingResponse[].class)
//                    .block();
//
//            List<MusicPodcastResponse> musicPodcastResponses = Arrays.stream(ratingResponsePerUserArray)
//                    .filter(rating -> rating != null && rating.isLiked()) // Filter for liked ratings
//                    .map(RatingResponse::getUniqueIdentifier)
//                    .map(this::getMusicPodcastByUniqueIdentifier)
//                    .filter(Optional::isPresent)
//                    .map(Optional::get)
//                    .map(this::mapToMusicPodcastResponse)
//                    .collect(Collectors.toList());
//
//            return musicPodcastResponses;
//        }
//        return Collections.emptyList();
//    }
public List<MusicPodcastResponse> getAllMusicPodcastsWithRatingLikedPerUser(RatingResponse ratingResponse) {

    System.err.println("Starting method getAllMusicPodcastsWithRatingLikedPerUser");

    String username = null;

    try {
        username = ratingResponse.getUsername();
        System.err.println("Username retrieved: " + username);
    } catch (Exception e) {
        System.err.println("Exception during getting username: " + e.getMessage());
    }

    if (username != null) {

        RatingResponse[] ratingResponsePerUserArray = null;

        try {
            ratingResponsePerUserArray = webClient.get()
                    .uri("http://" + ratingServiceBaseUrl + "/rating/username/" + username
                    )
                    .retrieve()
                    .bodyToMono(RatingResponse[].class)
                    .doOnError(e -> System.err.println("An error occurred: " + e.getMessage()))
                    .block();
            System.err.println("Retrieved rating responses: " + Arrays.toString(ratingResponsePerUserArray));
        } catch (Exception e) {
            System.err.println("Exception during getting rating responses: " + e.getMessage());
        }

        if (ratingResponsePerUserArray != null) {

            System.err.println("Processing rating responses...");

            List<MusicPodcastResponse> musicPodcastResponses = Arrays.stream(ratingResponsePerUserArray)
                    .filter(rating -> {
                        try {
                            boolean result = rating != null && rating.isLiked(); // Filter for liked ratings
                            System.err.println("Filter result: " + result);
                            return result;
                        } catch (Exception e) {
                            System.err.println("Exception during filter: " + e.getMessage());
                            return false;
                        }
                    })
                    .map(RatingResponse::getUniqueIdentifier)
                    .map(uniqueIdentifier -> {
                        try {
                            Optional<MusicPodcast> result = getMusicPodcastByUniqueIdentifier(uniqueIdentifier);
                            System.err.println("Mapping result: " + result);
                            return result;
                        } catch (Exception e) {
                            System.err.println("Exception during map: " + e.getMessage());
                            return Optional.empty();
                        }
                    })
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(musicPodcast -> {
                        try {
                            MusicPodcastResponse result = mapToMusicPodcastResponse((MusicPodcast) musicPodcast);
                            System.err.println("Mapping result: " + result);
                            return result;
                        } catch (Exception e) {
                            System.err.println("Exception during map: " + e.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            System.err.println("Finished processing rating responses");

            return musicPodcastResponses;
        }
    }

    System.err.println("No rating responses found or username was null");

    return Collections.emptyList();
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

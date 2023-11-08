package fact.it.musicpodcastservice.repository;

import fact.it.musicpodcastservice.model.MusicPodcast;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MusicPodcastRepository extends MongoRepository<MusicPodcast, String> {
    List<MusicPodcast>findByTitle(List<String> title);
    List<MusicPodcast>findByArtist(List<String> artist);
    List<MusicPodcast>findByGenre(List<String> genre);
    List<MusicPodcast>findByIsPodcast(List<String> isPodcast);


}

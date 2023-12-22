package fact.it.musicpodcastservice.repository;

import fact.it.musicpodcastservice.dto.MusicPodcastResponse;
import fact.it.musicpodcastservice.model.MusicPodcast;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MusicPodcastRepository extends MongoRepository<MusicPodcast, String> {
    MusicPodcast findByUniqueIdentifier(String uniqueIdentifier);

}

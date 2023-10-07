package fact.it.musicpodcastservice.repository;

import fact.it.musicpodcastservice.model.MusicPodcast;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MusicPodcastRepository extends MongoRepository<MusicPodcast, String> {
}

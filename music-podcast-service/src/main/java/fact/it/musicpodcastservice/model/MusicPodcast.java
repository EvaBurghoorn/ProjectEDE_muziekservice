package fact.it.musicpodcastservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "musicpodcast")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MusicPodcast {
    private String id;
    private String title;
    private String artist;
    private int durationSeconds;
    private String genre;
    private boolean isPodcast;


}

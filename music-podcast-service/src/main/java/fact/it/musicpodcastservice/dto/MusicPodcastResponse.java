package fact.it.musicpodcastservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MusicPodcastResponse {
    private String id;
    private String title;
    private String artist;
    private int durationSeconds;
    private String genre;
    private boolean isPodcast;
}

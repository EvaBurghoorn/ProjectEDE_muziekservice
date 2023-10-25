package fact.it.playlistservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaylistResponse {
    private Long id;
    private String title;
    private int userid;
    private int musicpodcastid;
    private boolean isPrivate;
    private String description;

}

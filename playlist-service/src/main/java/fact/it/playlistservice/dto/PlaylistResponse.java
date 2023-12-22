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
    private String username;
    private String uniqueIdentifier;
    private boolean isPrivate;
    private String description;

}

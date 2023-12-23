package fact.it.musicpodcastservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatingResponse {
    private String id;
    private boolean isLiked;
    private boolean isDisliked;
    private String uniqueIdentifier;
    private String username;
}

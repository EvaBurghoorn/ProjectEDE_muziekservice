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
    //    liked
    private boolean isLiked;
    //    dislike
    private boolean isDisliked;
    private String uniqueIdentifier;
    // unique username (UUID)
    private String username;
}

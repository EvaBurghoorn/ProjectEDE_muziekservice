package fact.it.ratingservice.dto;

import fact.it.ratingservice.model.Rating;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatingRequest {
    private boolean isLiked;
    private boolean isDisliked;
    private String uniqueIdentifier;
    private String username;

}

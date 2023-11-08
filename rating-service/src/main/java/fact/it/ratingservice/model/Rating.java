package fact.it.ratingservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Rating {
    private String id;
//    liked
    private boolean isLiked;
//    dislike
    private boolean isDisliked;
    private String uniqueIdentifier;




}

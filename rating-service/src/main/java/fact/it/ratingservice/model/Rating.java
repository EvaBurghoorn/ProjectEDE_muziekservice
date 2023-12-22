package fact.it.ratingservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Rating {
    private String id;
    private boolean isLiked;
    private boolean isDisliked;
    private String uniqueIdentifier;
    private String username;





}

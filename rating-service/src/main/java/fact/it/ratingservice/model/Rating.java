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
//    e.g Een score van 1-5
    private int score;
//    Eventueel een beschrijving bij de Rating zetten
    private String description;
}

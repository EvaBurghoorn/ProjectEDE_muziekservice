package fact.it.playlistservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "playlist")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private int userid;
    private int musicpodcastid;
    private boolean isPrivate;
    private String description;




}

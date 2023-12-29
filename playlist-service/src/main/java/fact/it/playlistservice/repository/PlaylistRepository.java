package fact.it.playlistservice.repository;

import fact.it.playlistservice.model.Playlist;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {


}

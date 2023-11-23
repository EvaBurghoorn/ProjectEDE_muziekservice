package fact.it.playlistservice.repository;

import fact.it.playlistservice.model.Playlist;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
//    List<Playlist> findByTitle(List<String> title);
//    Playlist findById(int id);

    //Find for the userID the private
//    List<Playlist> findByUserid(List<String> privatePlaylistIds);

}

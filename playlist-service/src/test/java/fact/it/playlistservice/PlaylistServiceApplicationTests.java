package fact.it.playlistservice;

import fact.it.playlistservice.dto.PlaylistResponse;
import fact.it.playlistservice.model.Playlist;
import fact.it.playlistservice.repository.PlaylistRepository;
import fact.it.playlistservice.service.PlaylistService;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaylistServiceApplicationTests {

    @InjectMocks
    private PlaylistService playlistService;

    @Mock
    private PlaylistRepository playlistRepository;
    @Test
    public void testGetAllPlaylists() {
        // Arrange
        List<Playlist> playlists = new ArrayList<>();
        Playlist playlist1 = new Playlist();
        playlist1.setId(1L);
        playlist1.setTitle("My Playlist 1");
        playlist1.setUserid(1);
        playlist1.setMusicpodcastid(1);
        playlist1.setPrivate(true);
        playlist1.setDescription("This is playlist1");

        Playlist playlist2 = new Playlist();
        playlist2.setId(2L);
        playlist2.setTitle("My Playlist 2");
        playlist2.setUserid(2);
        playlist1.setMusicpodcastid(2);
        playlist1.setPrivate(false);
        playlist1.setDescription("This is the second playlist");

        playlists.add(playlist1);
        playlists.add(playlist2);
        when(playlistRepository.findAll()).thenReturn(playlists);

        // Act
        List<PlaylistResponse> result = playlistService.getAllPlaylists();

        // Assert
        assertEquals(2, result.size());

        verify(playlistRepository, times(1)).findAll();
    }

    @Test
    public void testGetPlayListById(){
        // Arrange
        
        // Act

        //Assert
    }
}

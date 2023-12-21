package fact.it.playlistservice;

import fact.it.playlistservice.dto.PlaylistRequest;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaylistServiceApplicationTests {

    @InjectMocks
    private PlaylistService playlistService;

    @Mock
    private PlaylistRepository playlistRepository;

    private List<Playlist> createSamplePlaylists() {
        List<Playlist> playlists = new ArrayList<>();

        Playlist playlist1 = new Playlist();
        playlist1.setId(1L);
        playlist1.setTitle("My Playlist 1");
        playlist1.setUsername("1");
        playlist1.setUniqueIdentifier("1");
        playlist1.setPrivate(true);
        playlist1.setDescription("This is playlist1");

        Playlist playlist2 = new Playlist();
        playlist2.setId(2L);
        playlist2.setTitle("My Playlist 2");
        playlist2.setUsername("2");
        playlist2.setUniqueIdentifier("2");
        playlist2.setPrivate(false);
        playlist2.setDescription("This is the second playlist");

        playlists.add(playlist1);
        playlists.add(playlist2);

        return playlists;
    }

    @Test
    public void testGetAllPlaylists() {
        // Arrange
        List<Playlist> playlists = new ArrayList<>();
        Playlist playlist1 = new Playlist();
        playlist1.setId(1L);
        playlist1.setTitle("My Playlist 1");
        playlist1.setUsername("1");
        playlist1.setUniqueIdentifier("1");
        playlist1.setPrivate(true);
        playlist1.setDescription("This is playlist1");

        Playlist playlist2 = new Playlist();
        playlist2.setId(2L);
        playlist2.setTitle("My Playlist 2");
        playlist2.setUsername("2");
        playlist1.setUniqueIdentifier("2");
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
    public void testGetPlayListByIdExists(){
        // Arrange
        List<Playlist> playlists = createSamplePlaylists();
//       variable for Id of the playlist
        Long playlistId = 1L;

        when(playlistRepository.findById(playlistId)).thenReturn(playlists.stream().filter(p -> p.getId().equals(playlistId)).findFirst());
        // Act
        Optional<PlaylistResponse> responseOptional = playlistService.getPlaylistById(playlistId);

        //Assert
        assertTrue(responseOptional.isPresent());

    }
    @Test
    public void testGetPlaylistByIdWhenNotExists() {
        // Arrange
        List<Playlist> playlists = createSamplePlaylists();

        Long nonExistentPlaylistId = 3L;

        when(playlistRepository.findById(nonExistentPlaylistId)).thenReturn(Optional.empty());

        // Act
        Optional<PlaylistResponse> responseOptional = playlistService.getPlaylistById(nonExistentPlaylistId);

        // Assert
        assertTrue(responseOptional.isEmpty());
    }

    @Test
    public void testCreatePlaylist(){
        // Arrange
        PlaylistRequest playlistRequest = new PlaylistRequest();
        playlistRequest.setTitle("My Playlist");
        playlistRequest.setUsername("20");
        playlistRequest.setUniqueIdentifier("25");
        playlistRequest.setPrivate(false);
        playlistRequest.setDescription("This is a test playlist");

        // Act
        playlistService.createPlaylist(playlistRequest);

        // Assert
        verify(playlistRepository, times(1)).save(any(Playlist.class));

    }

    @Test
    public void testEditPlaylist(){
        // Arrange
        Playlist playlist = new Playlist();
        playlist.setId(5L);
        playlist.setTitle("My Playlist");
        playlist.setUsername("20");
        playlist.setUniqueIdentifier("25");
        playlist.setPrivate(false);
        playlist.setDescription("This is a test playlist");

        PlaylistRequest playlistRequest = new PlaylistRequest();
        playlistRequest.setTitle("Podcasts");
        playlist.setUniqueIdentifier("20");
        playlist.setPrivate(true);
        playlist.setDescription("Podcasts about life");

        when(playlistRepository.findById(5L)).thenReturn(Optional.of(playlist));

        // Act
        playlistService.editPlaylist(playlist.getId(), playlistRequest);

        // Assert
        verify(playlistRepository, times(1)).findById(5L);
        verify(playlistRepository, times(1)).save(any(Playlist.class));
    }

//    @Test
//    public void testDeletePlaylist(){
//        // Arrange
//        List<Playlist> playlists = new ArrayList<>();
//        Playlist playlist1 = new Playlist();
//        playlist1.setId(1L);
//        playlist1.setTitle("My Playlist 1");
//        playlist1.setUserid("1");
//        playlist1.setMusicpodcastid("1");
//        playlist1.setPrivate(true);
//        playlist1.setDescription("This is playlist1");
//
//        Playlist playlist2 = new Playlist();
//        playlist2.setId(2L);
//        playlist2.setTitle("My Playlist 2");
//        playlist2.setUserid("2");
//        playlist1.setMusicpodcastid("2");
//        playlist1.setPrivate(false);
//        playlist1.setDescription("This is the second playlist");
//
//        playlists.add(playlist1);
//        playlists.add(playlist2);
//
//        // id verwijderen
//        Long id = 2L;
//        Optional<Playlist> optionalPlaylist = playlists.stream()
//                .filter(playlist -> playlist.getId().equals(id))
//                .findFirst();
//
//        // Act
//        if (optionalPlaylist.isPresent()) {
//            Playlist playlist = optionalPlaylist.get();
//            playlists.remove(playlist);
//        }
//        // Assert
//        assertFalse(playlists.contains(optionalPlaylist.orElse(null)));
//        verify(playlistRepository, times(1)).findById(id);
//
//    }

    @Test
    public void testDeletePlaylist1(){
        // Arrange
        Long playlistId = 1L;
        Playlist playlist = new Playlist();
        playlist.setId(playlistId);
        playlist.setTitle("My Playlist");
        playlist.setUsername("20");
        playlist.setUniqueIdentifier("25");
        playlist.setPrivate(false);
        playlist.setDescription("This is a test playlist");

        when(playlistRepository.findById(playlistId)).thenReturn(Optional.of(playlist));

        // Act
        playlistService.deletePlaylist(playlistId);

        // Assert
        verify(playlistRepository, times(1)).findById(playlistId);
        verify(playlistRepository, times(1)).delete(playlist);
    }
}

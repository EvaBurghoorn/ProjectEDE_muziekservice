package fact.it.playlistservice.service;

import fact.it.playlistservice.dto.PlaylistRequest;
import fact.it.playlistservice.dto.PlaylistResponse;
import fact.it.playlistservice.model.Playlist;
import fact.it.playlistservice.repository.PlaylistRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaylistService {
    private final PlaylistRepository playlistRepository;


    @PostConstruct
    public void loadData(){
        if(playlistRepository.count() <= 0){
            Playlist playlist = new Playlist();
            playlist.setTitle("Title1");
            playlist.setUsername("eburghoorn ");
            playlist.setUsername("Title1Artist1 ");
            playlist.setPrivate(true);
            playlist.setDescription("Dit is een test description");

            playlistRepository.save(playlist);

        }
    }

    public List<PlaylistResponse> getAllPlaylists(){
        List<Playlist> playlists = playlistRepository.findAll();
        return playlists.stream().map(this::mapToPlaylistResponse).toList();
    }

    public Optional<PlaylistResponse> getPlaylistById(Long playlistId) {
        return playlistRepository.findById(playlistId).map(this::mapToPlaylistResponse);
    }


    public void createPlaylist(PlaylistRequest playlistRequest){
        Playlist playlist = Playlist.builder()
                .title(playlistRequest.getTitle())
                .username(playlistRequest.getUsername())
                .uniqueIdentifier(playlistRequest.getUniqueIdentifier())
                .isPrivate(playlistRequest.isPrivate())
                .description(playlistRequest.getDescription())
                .build();

        playlistRepository.save(playlist);
    }
    public void editPlaylist(Long playlistId, PlaylistRequest playlistRequest){
        Optional<Playlist> editPlaylist = playlistRepository.findById(playlistId);
        if(editPlaylist.isPresent()){
            Playlist playlist = editPlaylist.get();
            playlist = Playlist.builder()
                    .id(playlist.getId())
                    .title(playlistRequest.getTitle())
                    .username(playlistRequest.getUsername())
                    .uniqueIdentifier(playlistRequest.getUniqueIdentifier())
                    .isPrivate(playlistRequest.isPrivate())
                    .description(playlistRequest.getDescription())
                    .build();

            playlistRepository.save(playlist);
        }
    }

    public void deletePlaylist(Long playlistId){
        Optional<Playlist> deletePlaylist = playlistRepository.findById(playlistId);
        deletePlaylist.ifPresent(playlistRepository::delete);

    }



    private PlaylistResponse mapToPlaylistResponse(Playlist playlist) {
        return PlaylistResponse.builder()
                .id(playlist.getId())
                .title(playlist.getTitle())
                .username(playlist.getUsername())
                .uniqueIdentifier(playlist.getUniqueIdentifier())
                .isPrivate(playlist.isPrivate())
                .description(playlist.getDescription())
                .build();
    }

}
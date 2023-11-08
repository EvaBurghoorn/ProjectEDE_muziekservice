package fact.it.playlistservice.controller;

import fact.it.playlistservice.dto.PlaylistRequest;
import fact.it.playlistservice.dto.PlaylistResponse;
import fact.it.playlistservice.model.Playlist;
import fact.it.playlistservice.service.PlaylistService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/playlist")
@RequiredArgsConstructor
public class PlaylistController {
    private final PlaylistService playlistService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<PlaylistResponse>getAllPlaylists(){
        return playlistService.getAllPlaylists();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PlaylistResponse> getPlaylistById (@PathVariable("id") Long playlistId) {
        Optional<PlaylistResponse> playlist = playlistService.getPlaylistById(playlistId);
        if (playlist.isPresent()) {
            return new ResponseEntity<>(playlist.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createPlaylist(@RequestBody PlaylistRequest playlistRequest){
        playlistService.createPlaylist(playlistRequest);
    }

    @PutMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void editPlaylist(@PathVariable Long id,@RequestBody PlaylistRequest playlistRequest)
    {
        playlistService.editPlaylist(id, playlistRequest);
    }
    @DeleteMapping("id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePlaylist(@PathVariable Long id)
    {
        playlistService.deletePlaylist(id);
    }



}


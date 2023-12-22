package fact.it.musicpodcastservice;

import fact.it.musicpodcastservice.repository.MusicPodcastRepository;
import fact.it.musicpodcastservice.service.MusicPodcastService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MusicPodcastServiceApplicationTests {

	@InjectMocks
	private MusicPodcastService musicPodcastService;

	@Mock
	private MusicPodcastRepository musicPodcastRepository;
	@Test
	public void testGetAllMusicPodcast(){
		// Arrange
		// Act
		// Assert
	}

	@Test
	public void testGetAllSongs(){
		// Arrange
		// Act
		// Assert
	}

	@Test
	public void testGetAllPodcast(){
		// Arrange
		// Act
		// Assert
	}

	@Test
	public void testGetSongById(){
		// Arrange
		// Act
		// Assert
	}

	@Test
	public void testGetPodcastById(){
		// Arrange
		// Act
		// Assert
	}
}

package fact.it.musicpodcastservice;

import fact.it.musicpodcastservice.dto.MusicPodcastRequest;
import fact.it.musicpodcastservice.dto.MusicPodcastResponse;
import fact.it.musicpodcastservice.dto.RatingResponse;
import fact.it.musicpodcastservice.model.MusicPodcast;
import fact.it.musicpodcastservice.repository.MusicPodcastRepository;
import fact.it.musicpodcastservice.service.MusicPodcastService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class MusicPodcastServiceApplicationTests {

	@InjectMocks
	private MusicPodcastService musicPodcastService;

	@Mock
	private MusicPodcastRepository musicPodcastRepository;

	@Mock
	private WebClient webClient;

	@Mock
	private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

	@Mock
	private WebClient.RequestHeadersSpec requestHeadersSpec;

	@Mock
	private WebClient.ResponseSpec responseSpec;

	@Test
	public void testGetAllMusicPodcast(){
		// Arrange
		List<MusicPodcast> musicPodcasts = new ArrayList<>();
		MusicPodcast musicPodcast = new MusicPodcast();
		musicPodcast.setTitle("Title1");
		musicPodcast.setArtist("Artist1");
		musicPodcast.setDurationSeconds(200);
		musicPodcast.setGenre("Pop");
		musicPodcast.setPodcast(false);
		musicPodcast.setUniqueIdentifier("Title1Artist1");

		MusicPodcast musicPodcast_two = new MusicPodcast();
		musicPodcast_two.setTitle("TitlePodcast");
		musicPodcast_two.setArtist("Artist2");
		musicPodcast_two.setDurationSeconds(5000);
		musicPodcast_two.setGenre("Drama");
		musicPodcast_two.setPodcast(true);
		musicPodcast_two.setUniqueIdentifier("TitlePodcastArtist2");

		musicPodcasts.add(musicPodcast);
		musicPodcasts.add(musicPodcast_two);

		when(musicPodcastRepository.findAll()).thenReturn(musicPodcasts);

		// Act
		List<MusicPodcastResponse> result = musicPodcastService.getAllMusicPodcast();

		// Assert
		assertEquals(2, result.size());
		verify(musicPodcastRepository, times(1)).findAll();
	}

	@Test
	public void testGetAllSongs(){
		// Arrange
		List<MusicPodcast> musicPodcasts = new ArrayList<>();
		MusicPodcast musicPodcast = new MusicPodcast();
		musicPodcast.setTitle("Title1");
		musicPodcast.setArtist("Artist1");
		musicPodcast.setDurationSeconds(200);
		musicPodcast.setGenre("Pop");
		musicPodcast.setPodcast(false);
		musicPodcast.setUniqueIdentifier("Title1Artist1");

		MusicPodcast musicPodcast_two = new MusicPodcast();
		musicPodcast_two.setTitle("TitlePodcast");
		musicPodcast_two.setArtist("Artist2");
		musicPodcast_two.setDurationSeconds(5000);
		musicPodcast_two.setGenre("Drama");
		musicPodcast_two.setPodcast(true);
		musicPodcast_two.setUniqueIdentifier("TitlePodcastArtist2");

		musicPodcasts.add(musicPodcast);
		musicPodcasts.add(musicPodcast_two);

		when(musicPodcastRepository.findAll()).thenReturn(musicPodcasts);

		// Act
		List<MusicPodcastResponse> result = musicPodcastService.getAllSongs();

		// Assert
		assertEquals(1, result.size());
		verify(musicPodcastRepository, times(1)).findAll();
	}

	@Test
	public void testGetAllPodcast(){
		// Arrange
		List<MusicPodcast> musicPodcasts = new ArrayList<>();
		MusicPodcast musicPodcast = new MusicPodcast();
		musicPodcast.setTitle("Title1");
		musicPodcast.setArtist("Artist1");
		musicPodcast.setDurationSeconds(200);
		musicPodcast.setGenre("Pop");
		musicPodcast.setPodcast(false);
		musicPodcast.setUniqueIdentifier("Title1Artist1");

		MusicPodcast musicPodcast_two = new MusicPodcast();
		musicPodcast_two.setTitle("TitlePodcast");
		musicPodcast_two.setArtist("Artist2");
		musicPodcast_two.setDurationSeconds(5000);
		musicPodcast_two.setGenre("Drama");
		musicPodcast_two.setPodcast(true);
		musicPodcast_two.setUniqueIdentifier("TitlePodcastArtist2");

		musicPodcasts.add(musicPodcast);
		musicPodcasts.add(musicPodcast_two);

		when(musicPodcastRepository.findAll()).thenReturn(musicPodcasts);

		// Act
		List<MusicPodcastResponse> result = musicPodcastService.getAllPodcast();

		// Assert
		assertEquals(1, result.size());
		verify(musicPodcastRepository, times(1)).findAll();
	}

	@Test
	public void testGetMusicPodcastByUniqueIdentifier(){
		// Arrange
		List<MusicPodcast> musicPodcasts = new ArrayList<>();
		MusicPodcast musicPodcast = new MusicPodcast();
		musicPodcast.setId("1");
		musicPodcast.setTitle("Title1");
		musicPodcast.setArtist("Artist1");
		musicPodcast.setDurationSeconds(200);
		musicPodcast.setGenre("Pop");
		musicPodcast.setPodcast(false);
		musicPodcast.setUniqueIdentifier("Title1Artist1");

		MusicPodcast musicPodcast_two = new MusicPodcast();
		musicPodcast.setId("2");
		musicPodcast_two.setTitle("TitlePodcast");
		musicPodcast_two.setArtist("Artist2");
		musicPodcast_two.setDurationSeconds(5000);
		musicPodcast_two.setGenre("Drama");
		musicPodcast_two.setPodcast(true);
		musicPodcast_two.setUniqueIdentifier("TitlePodcastArtist2");

		musicPodcasts.add(musicPodcast);
		musicPodcasts.add(musicPodcast_two);

		String musicPodcastUniqueIdentifier = "Title1Artist1";
		when(musicPodcastRepository.findByUniqueIdentifier(musicPodcastUniqueIdentifier)).thenReturn(musicPodcast);


		// Act
		Optional<MusicPodcast> responseOptional = musicPodcastService.getMusicPodcastByUniqueIdentifier(musicPodcastUniqueIdentifier);

		// Assert
		assertTrue(responseOptional.isPresent());
		assertEquals("Title1", responseOptional.get().getTitle());
	}
	@Test
	public void testGetMusicPodcastByUniqueIdentifier_NotFound(){
		// Arrange
		List<MusicPodcast> musicPodcasts = new ArrayList<>();
		MusicPodcast musicPodcast = new MusicPodcast();
		musicPodcast.setId("1");
		musicPodcast.setTitle("Title1");
		musicPodcast.setArtist("Artist1");
		musicPodcast.setDurationSeconds(200);
		musicPodcast.setGenre("Pop");
		musicPodcast.setPodcast(false);
		musicPodcast.setUniqueIdentifier("Title1Artist1");

		MusicPodcast musicPodcast_two = new MusicPodcast();
		musicPodcast.setId("2");
		musicPodcast_two.setTitle("TitlePodcast");
		musicPodcast_two.setArtist("Artist2");
		musicPodcast_two.setDurationSeconds(5000);
		musicPodcast_two.setGenre("Drama");
		musicPodcast_two.setPodcast(true);
		musicPodcast_two.setUniqueIdentifier("TitlePodcastArtist2");

		musicPodcasts.add(musicPodcast);
		musicPodcasts.add(musicPodcast_two);

		String nonExistentUniqueIdentifier = "NonExistent";
		when(musicPodcastRepository.findByUniqueIdentifier(nonExistentUniqueIdentifier)).thenReturn(null);

		// Act
		Optional<MusicPodcast> responseOptional = musicPodcastService.getMusicPodcastByUniqueIdentifier(nonExistentUniqueIdentifier);

		// Assert
		assertFalse(responseOptional.isPresent());
	}

	@Test
	public void testGetAllMusicPodcastsWithRatingLikedPerUser() {
		// Arrange
		RatingResponse[] mockedRatingResponseArray = {
				new RatingResponse("id1", true, false, "uniqueId1", "user1"),
				new RatingResponse("id2", false, false, "uniqueId2", "user1"),
		};

		when(webClient.get()).thenReturn(requestHeadersUriSpec);
		when(requestHeadersUriSpec.uri(anyString(), anyString())).thenReturn(requestHeadersSpec);
		when(requestHeadersSpec.exchange()).thenReturn(Mono.just(responseSpec));
		when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

		when(responseSpec.bodyToMono(RatingResponse[].class)).thenReturn(Mono.just(mockedRatingResponseArray));


		// Act

		RatingResponse ratingResponse = new RatingResponse();
		ratingResponse.setUsername("user1");

		List<MusicPodcastResponse> result = musicPodcastService.getAllMusicPodcastsWithRatingLikedPerUser(ratingResponse);

		// Assert
		assertEquals(2, result.size());
	}

}

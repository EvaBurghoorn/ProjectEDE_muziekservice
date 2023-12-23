# Project Entreprise Development Experience: MuziekService (Eva Burghoorn & Kyara Van Genechten)
## Onderwerp van het project en de microservices
Het onderwerp van dit project is een muziekservice en deze is opgedeeld in 4 microservices. We hebben voor een muziekservice als onderwerp gekozen omdat wij dit allebei interessant vinden. Een muziekservice bestaat natuurlijk ook uit verschillende microservices, hieronder kan je onze 4 microservices opgesomd terugvinden: 

-	User-service 
-	MusicPodcast-service
-	Playlist-service
-	Rating-service
  
User-service heeft alles te maken met informatie over de gebruikers van de muziekservice. Je kunt een nieuwe gebruiker aanmaken, maar ook aanpassen, verwijderen en een specifieke gebruiker opvragen. 

MusicPodcast-service bevat alle liedjes en alle podcasts die op onze muziekservice te vinden zijn. Op deze microservice kan je alleen maar dingen opvragen zoals de liedjes, podcasts,...

De Playlist-service kan een gebruiker een nieuwe playlist aanmaken met verschillende liedjes en/of podcasts. 

Rating-service houdt alle beoordelingen bij voor de liedjes en/of podcasts die een gebruiker toekent. Natuurlijk kunnen deze aangemaakt worden, maar ook opgevraagd, aangepast of verwijderd worden.

Dit is kort uitgelegd wat ons muziekservice met de 4 microservices inhoudt.

## API Gateway
Wij hebben ook een api gateway geimplementeerd als onderdeel van onze muziekservice. Het dient als een soort toegangspunt voor externe gebruikers, hier rond hoort onder andere ook de authentication, authorization, routing, security,...

Voor de authenticatie hebben wij GCP 0Auth2 geimplementeerd. Alle onze routes zijn beveiligd buiten het aanmaken van een gebruiker. Het aanmaken van een gebruiker gaat dus zonder authenticatie verlopen. 

## Draw.io
Hieronder op de foto kan je ons draw.io schema terugvinden, hierop kan je dus zien hoe onze muziekservice er uitziet. 
Je kan hierop onze 4 microservices terugvinden, met de api gateway. Wij hebben ervoor gekozen om bij de playlist-service met een SQL database te werken en bij de andere 3 met een MongoDB database. Al onze endpoints zijn beveiligd buiten het aanmaken van een gebruiker met GCP OAuth2 zoals hierboven vermeld. Er staat bij elke service ook de poorten vermeld.

![Draw io schema](Images/muziekservice.drawio.svg)


## Projectdoelen en Vereisten
Wij hebben ons vooral gefocust op de algemene eisen en de documentatie. Dus hieronder kan je zien wat wij hebben geimplementeerd. 

### Basics:
- Minstens 3 GET, 1 POST, 1 PUT en 1 DELETE endpoints op een API Gateway, gebaseerd op je eigen services.
- Minstens 1 keer MongoDB als databank en minstens 1 keer SQL.
- Logisch gebruik van path parameters, query parameters en body.

### Documentatie:
- Beschrijving van het gekozen thema, je microservices en andere componenten zoals gateways in lijst en schema, en je uitbreidingen + link naar de zaken die gehost zijn op GitHub README.md.
- Aantoonbare werking van alle endpoints door screenshots van Postman requests op GitHub README.md.

### Deployment:
- Docker container voor de componenten, welke automatisch door GitHub Actions opgebouwd wordt.
- Deployment van de container(s) op Okteto Cloud via Docker Compose.

### Security:
- Auth op Gateway via GCP OAuth2, met secured/unsecured endpoints.

### Testing:
- Unit testing all the Service classes.
  
## Hosted Links
Playlist-service: https://playlist-service-evaburghoorn.cloud.okteto.net/

User-service: https://user-service-evaburghoorn.cloud.okteto.net/

MusicPodcast-service: https://musicpodcast-service-evaburghoorn.cloud.okteto.net/

Rating-service: https://rating-service-evaburghoorn.cloud.okteto.net
  

## Endpoints in postman
Hieronder kan je screenshots vinden van de endpoints die wij gemaakt hebben.

### Playlist-service: 
1. Get /playlist/all -> Geeft alle de playlist terug die er zijn 
![GetPlaylist](Images/EndpointGetAllMusicPodcasts.png)

2. Get /playlist/id/{id} -> Geeft een playlist terug met die specifieke id
![GetPlaylist](Images/EndpointGetPlaylistById.png)

3. Post /playlist -> Maakt een nieuwe playlist aan 
![CreatePlaylist](Images/EndpointCreatePlaylist.png)

4. Edit /playlist/id/{id} -> Edit a specific playlist
   PutPlaylist](Images/EndpointEditPlaylistById.png)

5. Delete /playlist/id/{id} -> Verwijdert een playlist
![DeletePlaylist](Images/EndpointDeletePlaylistById.png)

### User-service: 
1. Get /user/username/{username} -> Geeft een gebruiker terug met die specifieke username
![GetUserByUsername](Images/EndpointGetUserByUsername.png)

2. Post /user -> Maakt een nieuwe gebruiker aan 
![CreateUser](Images/EndpointPostUser.png)

3. Edit /user/username/{username} -> Pas een gebruiker zijn informatie aan
![PutPlaylist](Images/EndpointPutUser.png)

4. Delete /user/username/{username} -> Verwijdert een gebruiker
![DeletePlaylist](Images/EndpointDeleteUserByUsername.png)

### Musicpodcast-service: 
1. Get /musicpodcast/all -> Geeft alle liedjes en podcasts terug
![GetMusicpodcasAll](Images/EndpointGetAllMusicPodcasts.png)

2. Get /musicpodcast/songs -> Geeft alle liedjes terug
![GetMusicpodcastSongs](Images/EndpointGetAllSongs.png)

3. Get /musicpodcast/podcasts -> Geeft alle podcasts terug
![GetMusicpodcastPodcast](Images/EndpointGetAllPodcasts.png)

4. Get /musicpodcast/id/{uniqueIdentifier} -> Geeft een music podcast terug met die specifieke unieke identifier
![GetMusicpodcastByUniqueIdentifier](Images/EndpointGetAllPodcasts.png)

5. Get /musicpodcast/ratings/{username} -> 
6. Get /musicpodcast/ratings/{username}/{uniqueIdentifier} -> 

### Rating-service: 
1. Get /rating/all/liked -> Geeft alle ratings waarvan de liked rating op true staat

2. Post /rating -> Maakt een nieuwe rating aan
![CreateRating](Images/EndpointCreateRating.png)

4. Put /rating/id/{id} -> Update een specifieke rating
![CreateRating](Images/EndpointUpdateRating.png)

5. Delete /rating/id/{id} -> Verwijdert een rating
![CreateRating](Images/EndpointDeleteRating.png)

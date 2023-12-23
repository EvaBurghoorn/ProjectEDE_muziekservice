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

# STEAMER
A directory of gamers, their geography, which games they play and the level(INVINCIBLE, PRO, N00B) they are at in that game.

# Solution
- SpringBoot for API development
- H2 : in-memory database for RDBMS
  - Game : a game in platform
  - Level : it shows a player's achievement in a specific game
  - Player : a player of platform
  - Region : different countries, region, or continent depending on how you divide players. 
  - PlayerGameLevelBridge : it shows different players' levels in different games. A player can play multiple games and have different levels in each game.
- Memcached : Where players registering their real-time status and which game they are in. The in-memory key-value storage can prevent database from frequent and intense access. Player's status and gaming rooms can be built in Memcahced. 
- Swagger2:  http://localhost:8080/swagger-ui.html
- Spring Data JPA: data accessing
- Servlet Filter : for Injection prevention. I didn't use native sql query or any concatenate query, tut it is necessary to filter out any suspicious requests at the first place. 

# API:
- link gamer to a game.  
  post request: /gaming/linkgame/{gameid}  
  parameter: player  
  example post request: http://localhost:8080/gaming/linkgame/3?player=201

- search for players of a specific game who are online and ready for matching  
  get request: {game}/onlineplayers/{region}  
  parameter: level  
  example get request: http://localhost:8080/gaming/3/onlineplayers/2?level=1

- Search for players of a game based on players' level.  
  get request: {game}/players  
  parameter: level  
  example request: http://localhost:8080/gaming/2/players?level=3

# How to run:
## Repository
https://github.com/qswang1988/bsassignment

<code>
git clone https://github.com/qswang1988/bsassignment.git
</code>

## run application

* Please make sure you have <strong>Memcached</strong> running on port 11211 in local or remote(need to modify ip in application.yml) environment, otherwise the api for searching online-players wouldn't work. 

You can use docker to run it in a container.

<code>docker pull memcached</code>

<code>docker run --name my-memcache -p 11211:11211 -d memcached</code>

and then you can run the STEAMER from intellij, or run it from command line: you can go to folder steamer to run 
<code>java -jar target/steamer-0.0.1-SNAPSHOT.jar</code>

## run unit test

- test cases in: 
steamer/src/test/java/com/qswang/steamer/SteamerApplicationTests.java

you can run test from intellij



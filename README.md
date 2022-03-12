- H2 : database
- Memcached : where player registering their status
- Swagger2:  http://localhost:8080/swagger-ui.html 
- SpringBoot: 

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



## run application
Please make sure you have <strong>Memcached</strong> running on port 11211 in local/remote environment, otherwise the api for searching online-players wouldn't work. 

You can use docker to it in a container.

<code>docker pull memcached</code>

<code>docker run --name my-memcache -p 11211:11211 -d memcached</code>

and then you can run the STEAMER from intellij, or from java command: you can go to folder steamer to run 
<code>java -jar target/steamer-0.0.1-SNAPSHOT.jar</code>

## run unit test

- test cases in: 
steamer/src/test/java/com/qswang/steamer/SteamerApplicationTests.java



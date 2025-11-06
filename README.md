## 🔗 Project Link

[🔵 Open DSList Frontend](https://projectdslist.netlify.app/)



# DSList -- Game Catalog and List Management API

REST API built with **Spring Boot**, **Spring Data JPA**, and
**PostgreSQL** for managing and ordering game lists.\
The project follows clean architecture principles, separating
responsibilities into **Controllers**, **Services**, **Repositories**,
**Entities**, and **DTOs**.

------------------------------------------------------------------------

# 📌 1. Architecture Overview

The application is structured using:

-   **Entities** → Map database tables\
-   **Repositories** → Database access operations\
-   **Services** → Business logic\
-   **Controllers** → HTTP request/response layer\
-   **DTOs** → Data sent to the client\
-   **Config** → CORS and global settings\
-   **Profiles** → Configurations for dev, test, and production

------------------------------------------------------------------------

# Entities (Models)

## Game

Represents a game stored in the system.

Fields: - `id`, `title`, `year`, `genre`, `platforms`, `score`, `cover`

Mapping:

``` java
@Entity
@Table(name = "game")
```

------------------------------------------------------------------------

## GameList

Represents a user-created list of games.

Fields: - `id`, `name`

------------------------------------------------------------------------

## Belonging

Intermediate table that links **Game** and **GameList**, allowing manual
ordering.

Fields: - `BelongingPK` (composed of game_id and list_id)\
- `position` (index of the game in the list)

### Why not use `List<Game>` inside `GameList`?

-   Prevents heavy automatic loading (`EAGER`)
-   Allows explicit ordering using `position`
-   Provides better performance and control
-   Avoids complex bidirectional relationship issues

------------------------------------------------------------------------

## BelongingPK

Composite primary key:

``` java
@Embeddable
public class BelongingPK {
    @ManyToOne @JoinColumn(name="game_id")
    private Game game;

    @ManyToOne @JoinColumn(name="list_id")
    private GameList gameList;
}
```

Combines `game_id + list_id` into a unique key.

------------------------------------------------------------------------

# DTOs

### GameMinDTO

Used for lightweight game listing: - id, title, year, cover,
shortDescription

### GameDTO

Used for full game details.

### GameListDTO

Contains only: - id, name

### ReplacementDTO

Used for reordering games:

``` json
{
  "sourceIndex": 2,
  "destinationIndex": 0
}
```

------------------------------------------------------------------------

# Repositories

## GameRepository

Handles operations related to `Game`.\
Includes a native query that retrieves games from a list in their
defined order:

``` sql
SELECT game.id, game.title, game.game_year AS year,
       game.cover, game.short_description AS shortDescription,
       belonging.position
FROM game
INNER JOIN belonging ON game.id = belonging.game_id
WHERE belonging.list_id = :list_id
ORDER BY belonging.position
```

------------------------------------------------------------------------

## GameListRepository

Contains a query used to update game positions:

``` sql
UPDATE belonging 
SET position = :newPosition 
WHERE list_id = :listId AND game_id = :gameId
```

Essential for reordering logic.

------------------------------------------------------------------------

# Services

## GameService

Handles game-related logic: - `create()` → create a new game\
- `findAll()` → list all games\
- `findById()` → full game details\
- `findByList()` → games belonging to a list

------------------------------------------------------------------------

## GameListService

Contains the main ordering logic:

### `move(listId, sourceIndex, destinationIndex)`

Steps: 1. Fetches list games\
2. Removes the game from its old position\
3. Inserts it in the new position\
4. Updates all positions in the database

Supports: - Manual ordering\
- Drag-and-drop behavior\
- Dynamic ranking updates

------------------------------------------------------------------------

# Controllers

## GameController

Routes: - `GET /games`\
- `GET /games/{id}`

------------------------------------------------------------------------

## GameListController

Routes: - `GET /lists` - `GET /lists/{id}` -
`GET /lists/{list_id}/games` - `POST /lists/{listId}/replacement`

------------------------------------------------------------------------

# CORS Configuration

The `WebConfig` file reads:

    cors.origins=http://localhost:5173,http://localhost:3000,...

And enables CORS for these URLs:

``` java
registry.addMapping("/**")
        .allowedMethods("*")
        .allowedOriginPatterns(originsArray)
        .allowCredentials(true);
```

Allows frontend applications like React or Next.js to consume the API
safely.

------------------------------------------------------------------------

# application-\*.properties Files

### application-dev.properties

Used for local development.

Contains: - Local database credentials\
- PostgreSQL connection\
- `ddl-auto=none`\
- Hibernate dialect

### application-test.properties

Used only for tests.

Differences: - `server.port=8081` - Separate database\
- `spring.jpa.show-sql=true` - `ddl-auto=update`

### application-prod.properties

Uses environment variables:

    spring.datasource.url=${DB_URL}
    spring.datasource.username=${DB_USERNAME}
    spring.datasource.password=${DB_PASSWORD}

### application.properties

Global configuration:

    spring.profiles.active=${APP_PROFILE:test}
    spring.jpa.open-in-view=false
    cors.origins=${CORS_ORIGINS:http://localhost:5173,...}

------------------------------------------------------------------------

# Main Annotations Used

  Annotation          Purpose
  ------------------- ------------------------
  `@Entity`           Maps class to table
  `@Table`            Defines table name
  `@Id`               Primary key
  `@GeneratedValue`   Auto-increment
  `@Column`           Custom column settings
  `@Embeddable`       Composite PK class
  `@EmbeddedId`       Uses composite PK
  `@ManyToOne`        Relationship N:1
  `@Service`          Business logic
  `@Repository`       Database layer
  `@Transactional`    Transaction management
  `@RestController`   REST controller
  `@GetMapping`       GET endpoint
  `@PostMapping`      POST endpoint
  `@Value`            Inject properties

------------------------------------------------------------------------

# Running the Project

``` bash
export APP_PROFILE=dev
./mvnw spring-boot:run
```

------------------------------------------------------------------------

# Technologies Used

-   Java 21+
-   Spring Boot
-   Spring Web
-   Spring Data JPA
-   PostgreSQL
-   Maven

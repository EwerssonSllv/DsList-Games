package com.ewersson.dslist.repository;

import com.ewersson.dslist.entities.Game;
import com.ewersson.dslist.projection.GameMinProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    @Query(nativeQuery = true, value = """
    SELECT 
        game.id,
        game.title,
        game.game_year AS year,
        game.cover,
        game.short_description AS shortDescription,
        belonging.position
    FROM game
    INNER JOIN belonging ON game.id = belonging.game_id
    WHERE belonging.list_id = :list_id
    ORDER BY belonging.position
""")
    List<GameMinProjection> searchByList(@Param("list_id") Long list_id);

}

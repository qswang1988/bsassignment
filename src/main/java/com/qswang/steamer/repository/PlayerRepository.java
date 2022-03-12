package com.qswang.steamer.repository;

import com.qswang.steamer.model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player,Long> {

    @Query("select p from Player p where p.regionId = :regionid and p.lastLinkGame = :lastlink and p.playerId in (select pglb.playerId from PlayerGameLevelBridge pglb where pglb.levelId  = :level and pglb.gameId = :lastlink)")
    List<Player> findByRegionLevelGame(@Param("regionid") int regionId, @Param("lastlink") int gameId, @Param("level") int levelId);

    @Query("select p from Player p where p.playerId in (select pglb.playerId from PlayerGameLevelBridge pglb where pglb.levelId = :level and pglb.gameId = :game)")
    List<Player> findByLevelGame(@Param("game") int gameId, @Param("level") int levelId);

}

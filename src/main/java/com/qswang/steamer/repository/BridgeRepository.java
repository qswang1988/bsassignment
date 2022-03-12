package com.qswang.steamer.repository;

import com.qswang.steamer.model.PlayerGameLevelBridge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BridgeRepository extends JpaRepository<PlayerGameLevelBridge,Long> {
    List<PlayerGameLevelBridge> findByPlayerIdAndGameId(@Param("player")long playerId, @Param("game")int gameId);
    @Modifying
    @Transactional
    void deletePlayerGameLevelBridgeByPlayerId(long playerId);
}

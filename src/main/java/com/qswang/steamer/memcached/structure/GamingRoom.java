package com.qswang.steamer.memcached.structure;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
public class GamingRoom implements Serializable {
    @JSONField(name = "ROOM_ID")
    private String roomId;
    @JSONField(name = "GAME_ID")
    private int gameId;
    @JSONField(name = "MIN_PLAYERS")
    private int minPlayers;
    @JSONField(name = "MAX_PLAYERS")
    private int maxPlayers;
    @JSONField(name = "NUM_PLAYERS")
    private int numOfPlayers;
    @JSONField(name = "IS_FULL")
    private boolean full;
    // <GameId,Ip_Port>
    @JSONField(name = "CONNECTIONS")
    private Map<Long,String> connections;
    @JSONField(name = "AVERAGE_SCORE")
    private int averageScore; // for matching

    public GamingRoom(int gameId, int minPlayers, int maxPlayers, int numOfPlayers, boolean full) {
        this.roomId = UUID.randomUUID().toString();
        this.gameId = gameId;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.numOfPlayers = numOfPlayers;
        this.full = full;
        this.connections = new HashMap<>();
    }
}

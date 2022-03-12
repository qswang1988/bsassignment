package com.qswang.steamer.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game implements Serializable {
    @JSONField(name="GAME_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gameId;

    @NotNull
    @JSONField(name="GAME_NAME")
    private String gameName;
    @JSONField(name="MIN_PLAYER")
    private int minNumberOfPlayers;
    @JSONField(name="MAX_PLAYER")
    private int maxNumberOfPlayers;

    public Game(String gameName, int minNumberOfPlayers, int maxNumberOfPlayers) {
        this.gameName = gameName;
        this.minNumberOfPlayers = minNumberOfPlayers;
        this.maxNumberOfPlayers = maxNumberOfPlayers;
    }


}

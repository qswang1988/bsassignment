package com.qswang.steamer.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerGameLevelBridge {

    @JSONField(name="BRIDGE_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bridgeId;

    @NotNull
    @JSONField(name="PLAYER")
    private long playerId;

    @NotNull
    @JSONField(name="GAME")
    private int gameId;

    @NotNull
    @JSONField(name="LEVEL")
    private int levelId;

    @NotNull
    @JSONField(name="SCORE")
    private int score;
}

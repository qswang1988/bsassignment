package com.qswang.steamer.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerGameLevelBridge {

    @JSONField(name="BRIDGE_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bridgeId;

    @JSONField(name="PLAYER")
    private long playerId;

    @JSONField(name="GAME")
    private int gameId;

    @JSONField(name="LEVEL")
    private int levelId;

    @JSONField(name="SCORE")
    private int score;
}

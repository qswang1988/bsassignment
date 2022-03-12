package com.qswang.steamer.memcached.structure;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnlinePlayer implements Serializable {
    // key
    @JSONField(name = "PLAYER_ID")
    private long playerId;
    @JSONField(name = "ACCOUNT_NAME")
    private String accountName;
    @JSONField(name = "GAME_ID")
    private int gameId;
    @JSONField(name = "GAME_NAME")
    private String gameName;
    @JSONField(name = "REGION_ID")
    private int regionId;
    @JSONField(name = "REGION_NAME")
    private String regionName;
    @JSONField(name = "LEVEL")
    private int level;
    @JSONField(name = "LEVEL_NAME")
    private String levelName;
    @JSONField(name = "STATUS")
    private String status;
}

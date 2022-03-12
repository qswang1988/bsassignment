package com.qswang.steamer.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player implements Serializable {
    @JSONField(name="PLAYER_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long playerId;

    @JSONField(name="ACCOUNT_NAME")
    @NotNull
    private String accountName;

    @JSONField(name="REGION")
    private int regionId;

    @JSONField(name="LAST_LINK_DATE",format = "yyyy-MM-dd HH:mm:ss")
    private Date lastLinkDate;


    @JSONField(name = "LAST_LINK")
    private int lastLinkGame;

    @Transient
    private String status;

    @Transient
    @JSONField(name = "REGION_NAME")
    private String regionName;

    public Player(String accountName, int regionId) {
        this.accountName = accountName;
        this.regionId = regionId;
    }
}

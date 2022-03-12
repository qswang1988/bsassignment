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
public class Level implements Serializable {

    public Level(String levelName, int scoreBegin, int scoreEnd) {
        this.levelName = levelName;
        this.scoreBegin = scoreBegin;
        this.scoreEnd = scoreEnd;
    }

    @JSONField(name="LEVEL_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int levelId; // 1:NOOB,2:PRO,3:INVINCIBLE

    @JSONField(name="LEVEL_NAME")
    @NotNull
    private String levelName;

    @JSONField(name="SCORE_BEGIN")
    @NotNull
    private int scoreBegin; // number itself included; s<1001,s>=1001&&s<2001, s>=2001

    @JSONField(name="SCORE_END")
    @NotNull
    private int scoreEnd;   // number itself NOT included

}

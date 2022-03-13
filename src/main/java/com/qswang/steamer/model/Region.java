package com.qswang.steamer.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Region implements Serializable {
    @JSONField(name="REGION_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int regionId;

    public Region(String regionName) {
        this.regionName = regionName;
    }

    @JSONField(name="REGION_NAME")
    @NotNull
    private String regionName;

}

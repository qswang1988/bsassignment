package com.qswang.steamer.service;

import com.qswang.steamer.model.Level;
import com.qswang.steamer.model.Region;

import java.util.Optional;

public interface RegionService {
    public int insertRegion(Region region);
    public int updateRegion(Region region);
    public int deleteRegion(int id);
    public Optional<Region> findById(int id);
}

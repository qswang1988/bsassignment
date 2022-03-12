package com.qswang.steamer.service.implmentation;

import com.qswang.steamer.model.Region;
import com.qswang.steamer.repository.RegionRepository;
import com.qswang.steamer.service.RegionService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Getter
@Setter
@Service
public class RegionServiceImpl implements RegionService {

    private RegionRepository regionRepository;

    @Autowired
    public RegionServiceImpl(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @Override
    public int insertRegion(Region region) {
        regionRepository.saveAndFlush(region);
        return HttpStatus.OK.value();
    }

    @Override
    public int updateRegion(Region region) {
        regionRepository.saveAndFlush(region);
        return HttpStatus.OK.value();
    }

    @Override
    public int deleteRegion(int id) {
        Optional<Region> optionalRegion = regionRepository.findById(id);
        if(optionalRegion.isEmpty())
            return HttpStatus.NOT_FOUND.value();
        regionRepository.delete(optionalRegion.get());
        return HttpStatus.OK.value();
    }

    @Override
    public Optional<Region> findById(int id) {
        return regionRepository.findById(id);
    }
}

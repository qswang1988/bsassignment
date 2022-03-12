package com.qswang.steamer.service.config;

import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Component
@ConfigurationProperties(prefix = "steamer.levels")
public class LevelsConfig {
    private Map<String,Integer> levels;
}

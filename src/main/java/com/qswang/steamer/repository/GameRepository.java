package com.qswang.steamer.repository;

import com.qswang.steamer.model.Game;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

@Configuration
public interface GameRepository extends JpaRepository<Game,Integer> {

}

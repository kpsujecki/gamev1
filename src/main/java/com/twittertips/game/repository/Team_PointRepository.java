package com.twittertips.game.repository;

import com.twittertips.game.model.Team;
import com.twittertips.game.model.Team_Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Team_PointRepository extends JpaRepository<Team_Point, Long> {

List<Team_Point> findTeam_PointByTeam_IdOrderBySeasonDesc(long id);


}

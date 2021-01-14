package com.twittertips.game.repository;

import com.twittertips.game.model.Team;
import com.twittertips.game.model.Tip;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TipRepository extends JpaRepository<Tip, Long> {

    List<Tip> findByTeam(Team team, Sort sort);

    @Query("SELECT t FROM Tip t WHERE t.match = ?1 AND t.Data = ?2")
    List<Tip> findByMatchDate(String match, String Data);
}
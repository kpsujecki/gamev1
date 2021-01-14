package com.twittertips.game.service;

import com.twittertips.game.model.Team;
import com.twittertips.game.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor

public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public List<Team> listAll() {
        return teamRepository.findAll();
    }


    public void save(Team team) {
        teamRepository.save(team);
    }

    public Team get(long id) {
        return teamRepository.findById(id).get();
    }

    public void delete(long id) {
        teamRepository.deleteById(id);
    }
}
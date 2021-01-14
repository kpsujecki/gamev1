package com.twittertips.game.service;

import com.twittertips.game.model.Team;
import com.twittertips.game.model.Team_Point;
import com.twittertips.game.model.Tip;
import com.twittertips.game.repository.Team_PointRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class Team_PointService {

    @Autowired
    TeamService teamService;
    @Autowired
    Team_PointRepository team_pointRepository;

    public List<Team_Point> listAll(){
        return team_pointRepository.findAll();
    }
    public void save(Team_Point team_point){
        team_pointRepository.save(team_point);
    }
    public List<Team_Point> getbyID(long id){
        return  team_pointRepository.findTeam_PointByTeam_IdOrderBySeasonDesc(id);
    }


    public void newSeason(){
        int season = 0;

        List<Team_Point> team_points = listAll();
        if (!(team_points != null && team_points.isEmpty())) {
            for (Team_Point team_point : team_points) {
                season = Math.max(season, team_point.getSeason());
            }
        }
        season++;
        List<Team> teams = teamService.listAll();
        for(Team team : teams){
            Team_Point team_point = new Team_Point(season, 0, team);
            save(team_point);
        }
    }

    public void addPoints(){
        ifExcist();
        List<Team> teams = teamService.listAll();
        for(Team team : teams){
            int points = 0;
                List<Tip> tips = team.getTip();
                    for(Tip tip : tips ){
                        points+=tip.getPoint().getNumberofpoints();
                }
            Team_Point team_point = getbyID(team.getId()).get(0);
            team_point.setPoints(points);
            save(team_point);
        }
    }
    public void ifExcist() {
        int season = 0;
        List<Team_Point> team_points = listAll();
        List<Team> teams = teamService.listAll();
        if (!(team_points != null && team_points.isEmpty())) {
            for (Team_Point team_point : team_points) {
                season = Math.max(season, team_point.getSeason());
            }
        }
        for(Team team : teams){
            boolean excist = false;
            for (Team_Point team_point : team_points) {
                if(team_point.getTeam()==team && season==team_point.getSeason()) excist=true;
            }
            if(!excist){
                Team_Point team_point = new Team_Point(season, 0, team);
                save(team_point);
            }
        }


    }

    public Page<Team_Point> listAllS(int pageNum, String sortField, String sortDir) {
        int pageSize = 20;
        PageRequest pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending()
        );

        return team_pointRepository.findAll(pageable);
    }
}

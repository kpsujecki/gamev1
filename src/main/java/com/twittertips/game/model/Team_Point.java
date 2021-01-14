package com.twittertips.game.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@Getter
@Setter
public class Team_Point {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int season;
    private int points;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    public Team_Point(int season, int points, Team team) {
        this.season = season;
        this.points = points;
        this.team = team;
    }

    public Team_Point() {
    }
}

package com.twittertips.game.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Getter
@Setter
public class Team{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;
    private  String name;
    private  int id_league;
    @OneToMany(
            mappedBy = "team",
            cascade = CascadeType.ALL
    )
    private List<Tip> tip;

    @OneToMany(
            mappedBy = "team",
            cascade = CascadeType.ALL
    )
    private List<Team_Point> team_points;

    public Team() {
    }

    public Team(String name, int id_league) {
        this.name = name;
        this.id_league = id_league;
    }

}

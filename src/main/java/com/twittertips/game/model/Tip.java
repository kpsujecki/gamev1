package com.twittertips.game.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import com.twittertips.game.model.Point;

@Data
@Entity
@Getter
@Setter
public class Tip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String match;
    private String player;
    private int homegoal;
    private int awaygoal;
    private int homegoalhalf;
    private int awaygoalhalf;
    private String Data;
    private String Created;

    @OneToOne(mappedBy ="tip")
    private Point point;
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;



    public Tip(String name, String match, String player, int homegoal, int awaygoal, int homegoalhalf, int awaygoalhalf, String data, String created, Team team) {
        this.name = name;
        this.match = match;
        this.player = player;
        this.homegoal = homegoal;
        this.awaygoal = awaygoal;
        this.homegoalhalf = homegoalhalf;
        this.awaygoalhalf = awaygoalhalf;
        this.Data = data;
        this.Created = created;
        this.team = team;
    }

    public Tip() {

    }



}
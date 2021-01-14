package com.twittertips.game.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Getter
@Setter
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    @Column(name = "numberofpoints", nullable = false, columnDefinition = "int default 0")
    private int numberofpoints;


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "tip_id")
    private Tip tip;

    public Point() {
    }

    public Point(String name, int numberofpoints, Tip tip) {
        this.name = name;
        this.numberofpoints = numberofpoints;
        this.tip = tip;
    }
}

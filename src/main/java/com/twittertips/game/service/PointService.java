package com.twittertips.game.service;

import com.twittertips.game.model.Point;
import com.twittertips.game.model.Tip;
import com.twittertips.game.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PointService {

    @Autowired
    PointRepository pointRepository;

    @Autowired
    TipService tipService;

    public void save(Point point) {
        pointRepository.save(point);
    }

    public void givePoints(String match, String Data, String[] whoscored, int homegoal, int awaygoal, int homegoalhalf, int awaygoalhalf){
        List<Tip> tips = tipService.listbymatch(match, Data);

        for(Tip tip : tips){
            int points = 0;
            if(tip.getPoint()== null) {
                //wygrana gospodarzy
                if (tip.getHomegoal() > tip.getAwaygoal() && homegoal > awaygoal) points++;
                //wygrana gosci
                if (tip.getAwaygoal() > tip.getHomegoal() && awaygoal > homegoal) points++;
                //wygrana gospodarzy do przerwy
                if (tip.getHomegoalhalf() > tip.getAwaygoalhalf() && homegoalhalf > awaygoalhalf) points++;
                //wygrana gosci  do przerwy
                if (tip.getAwaygoalhalf() > tip.getHomegoalhalf() && awaygoalhalf > homegoalhalf) points++;
                //remis
                if (tip.getHomegoal() == tip.getAwaygoal() && homegoal == awaygoal) points++;
                //remis do przerwy
                if (tip.getHomegoalhalf() == tip.getAwaygoalhalf() && homegoalhalf == awaygoalhalf) points++;

                //dokładny wynik końcowy
                if (tip.getHomegoal() == homegoal && tip.getAwaygoal() == awaygoal) points++;
                //dokładny wynik do przerwy
                if (tip.getHomegoalhalf() == homegoalhalf && tip.getAwaygoalhalf() == awaygoalhalf) points++;
                //różnica goli
                if (tip.getHomegoal() - tip.getAwaygoal() == homegoal - awaygoal) points++;
                //strzelec bramki
                int z = whoscored.length - 1;
                for (int i = 0; i <= z; i++) {
                    if (tip.getPlayer().contains(whoscored[i])) points++;
                }
                Point point = new Point(tip.getName(), points, tip);
                save(point);
            }
            else{
                // TO DO UPDATE POINTS
            }

        }
    }
}


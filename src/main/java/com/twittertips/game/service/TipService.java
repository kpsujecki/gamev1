package com.twittertips.game.service;


import com.twittertips.game.model.Team;
import com.twittertips.game.model.Tip;
import com.twittertips.game.repository.TeamRepository;
import com.twittertips.game.repository.TipRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.*;
import twitter4j.auth.AccessToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;




@Service
@Slf4j
public class TipService {

@Autowired
private  TipRepository tipRepository;

@Autowired private TeamRepository teamRepository;
@Autowired private TeamService teamService;

    public List<Tip> listAll() {
        return tipRepository.findAll();
    }

    public List<Tip> listbymatch(String match, String Data) {
        return tipRepository.findByMatchDate(match, Data);
    }

    public void save(Tip tip) {
        tipRepository.save(tip);
    }

    public Tip get(long id) {
        return tipRepository.findById(id).get();
    }

    public void delete(long id) {
        tipRepository.deleteById(id);
    }


    public Tip getTip(String match, String Data) throws TwitterException {

            Twitter twitter = new TwitterFactory().getInstance();
            twitter.setOAuthConsumer("Jk0mIjtX58VgnWPbpkvMcFx7S",
                    "4k2QeqtUw5Ro8hBDHAMr1R4eB9YiJ64rKqj92ke7VSSOZbl1TO");
            twitter.setOAuthAccessToken(new AccessToken("2948825637-hNnKSvonr3FCZff5yRLF3WeRwXP1S5JudLO4Mp2",
                    "IwBp2xZHfAM3wA8y0vUOZx6C6tLRX9falUjR0nx2oqNnz"));


        String[] players = new String[] {"tiba", "ramirez",
                "ISHAK", "KAMIŃSKI", "MODER", "KACZARAWA", "SYKORA", "SKÓRAŚ"};

        String hashtag = "#lechtyper";



        String name;
        int homegoal, homegoalhalf, awaygoal, awaygoalhalf;
        String score, player = null;
        Query query = new Query(hashtag+"||"+match);

        QueryResult result;
        do{
            result = twitter.search(query);
            List<Status> tweets = result.getTweets();
            boolean excist = false;

            for (Status tweet : tweets) {

                score = tweet.getUser().getScreenName() + ":" + tweet.getText();
                score = score.toUpperCase();
                if (score.contains("(") || score.contains("-")) {

                    int z = players.length - 1;
                    for (int i = 0; i <= z; i++) {
                        if (score.contains(players[i])) {
                            player = players[i];
                        }
                    }

                    score = score.replaceAll("[^-?0-9]+", " ");

                    homegoal = Character.getNumericValue(score.charAt(1));
                    awaygoal = Character.getNumericValue(score.charAt(3));
                    homegoalhalf = Character.getNumericValue(score.charAt(5));
                    awaygoalhalf = Character.getNumericValue(score.charAt(7));
                    name = tweet.getUser().getScreenName();


                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
                    String strDate = formatter.format(tweet.getCreatedAt());
                    String created = strDate.toString();
                    addUser(name);

                    List<Tip> tips = tipRepository.findAll();
                    
                        for (Tip tipp : tips) {
                            if (tipp.getName().equals(name)  && tipp.getMatch().equals(match) ) {
                                excist = true;
                                if(excist && compared(created, tipp.getCreated())){
                                    log.info("TAK");
                                    tipp.setHomegoal(homegoal);
                                    tipp.setAwaygoal(awaygoal);
                                    tipp.setHomegoalhalf(homegoalhalf);
                                    tipp.setAwaygoalhalf(awaygoalhalf);
                                    tipp.setPlayer(player);
                                    tipp.setCreated(created);
                                    save(tipp);
                                }
                        } else {
                            log.info("Nie");
                        }
                    }

                    if(!excist){
                        List<Team> teams = teamRepository.findAll();
                        for (Team team : teams) {
                            if (team.getName().equals(name)){
                                Tip tip = new Tip(name, match, player, homegoal, awaygoal, homegoalhalf, awaygoalhalf, Data, created, team);
                                save(tip);
                                }
                            }
                        }
                    }
                }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }while ((query = result.nextQuery()) != null);

        return null;
    }

    public void addUser(String name){
        boolean excist = false;
        int league=1;
        int counter_league = 0;
        List<Team> teams = teamRepository.findAll();
        for (Team team : teams) {
            if (team.getName().equals(name)) excist = true;
            league = Math.max(league,team.getId_league());
            }
        for (Team team : teams) {
            if(league == team.getId_league()) counter_league ++;
            if(counter_league==10){
                league++;
                counter_league=0;
            }
        }

        if(!excist){
            Team tea = new Team(name,league);
            teamService.save(tea);
        }
    }

    public boolean compared(String created, String getData) {
            if(getData != null && !getData.isEmpty()) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
                try {
                    Date date1=formatter.parse(created);
                    Date date2=formatter.parse(getData);
                    //System.out.println(date1+" a "+date2);
                    if (date1.after(date2)) {
                        //System.out.println("Date1 is after Date2");
                        return true;
                    }
                    if (date1.before(date2)) {
                        //System.out.println("Date1 is before Date2");
                        return false;
                    }
                    if (date1.equals(date2)) {
                        //System.out.println("Date1 is equal Date2");
                        return false;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            else {
                return false;
            }
            return false;
        }
    }


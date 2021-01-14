package com.twittertips.game.controller;

import com.twittertips.game.service.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import twitter4j.TwitterException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PointController {

    @Autowired
    PointService pointService;


    @RequestMapping("/admin/check_tips")
    public String checkTips() {

        return "check_tips";
    }

    @RequestMapping(value = "/admin/get_points", method = RequestMethod.POST)
        public String givePoints(@RequestParam("match") String match,
                                 @RequestParam("data") String data, @RequestParam("whoscored") String[] whoscored,
                                 @RequestParam("homegoal") int homegoal, @RequestParam("awaygoal") int awaygoal,
                                 @RequestParam("homegoalhalf") int homegoalhalf,
                                 @RequestParam("awaygoalhalf") int awaygoalhalf){

            pointService.givePoints(match, data, whoscored, homegoal, awaygoal, homegoalhalf, awaygoalhalf);

            return "redirect:/";
        }
    }




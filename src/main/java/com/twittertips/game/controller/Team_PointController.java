package com.twittertips.game.controller;

import com.twittertips.game.model.Team;
import com.twittertips.game.model.Team_Point;
import com.twittertips.game.service.Team_PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class Team_PointController {

    @Autowired
    Team_PointService team_pointService;

    @RequestMapping("/admin/new_season")
    public String newS() {
       team_pointService.newSeason();

        return "redirect:/team";
    }

    @RequestMapping("/admin/team_point")
    public String add() {
        team_pointService.addPoints();

        return "redirect:/team";
    }

    @RequestMapping("/results")
    public String viewHomePage(Model model) {
        return showresults(model, 1, "points", "desc");
    }

    @RequestMapping("/results/{pageNum}")
    public String showresults(Model model, @PathVariable(name = "pageNum") int pageNum, @Param("sortfield") String sortField,
                              @Param("sortDir") String sortDir) {
        Page<Team_Point> page = team_pointService.listAllS(pageNum, sortField, sortDir);
        List<Team_Point> teamList = page.getContent();

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc")?"desc" : "asc");
        model.addAttribute("teamList", teamList);
        return "results";
    }
}

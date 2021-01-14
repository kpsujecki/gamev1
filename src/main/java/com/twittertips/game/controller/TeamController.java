package com.twittertips.game.controller;

import com.twittertips.game.model.Team;
import com.twittertips.game.model.Team_Point;
import com.twittertips.game.service.TeamService;
import com.twittertips.game.service.TipService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TeamController {


    @Autowired
    private final TeamService teamService;

    @RequestMapping("/team")
    public String showTeam(Model model) {
        List<Team> teamList = teamService.listAll();
        model.addAttribute("teamList", teamList);
        return "team";
    }

    @RequestMapping("/admin/team")
    public String showTeamAdmin(Model model) {
        List<Team> teamList = teamService.listAll();
        model.addAttribute("teamList", teamList);
        return "teamAdmin";
    }

    @RequestMapping("/admin/add")
    public String addTeam(Model model) {
        Team team = new Team();
        model.addAttribute("team", team);
        return "new_team";
    }

    @RequestMapping(value = "/admin/save", method = RequestMethod.POST)
    public String saveTeam(@ModelAttribute("team") Team team){
        teamService.save(team);
        return "redirect:/team";
    }
    @RequestMapping("/admin/edit/{id}")
    public ModelAndView showEditTeamPage(@PathVariable(name ="id") long id){
        ModelAndView mav = new ModelAndView("edit");
        Team team = teamService.get(id);
        mav.addObject("team", team);

        return mav;
    }
    @RequestMapping("/admin/list_tips/{id}")
    public ModelAndView showTipsTeams(@PathVariable(name ="id") long id){
        ModelAndView mav = new ModelAndView("list_tips");
        Team team = teamService.get(id);
        mav.addObject("team", team);
        return mav;
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") int id) {
        teamService.delete(id);
        return "team";
    }


}
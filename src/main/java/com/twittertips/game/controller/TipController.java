package com.twittertips.game.controller;


import com.twittertips.game.model.Team;
import com.twittertips.game.model.Tip;
import com.twittertips.game.service.TipService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import twitter4j.TwitterException;

import java.util.List;
@Slf4j
@Controller
@RequiredArgsConstructor
public class TipController {

    private final TipService tipService;

    @GetMapping("/tip")
    public String ShowTip(Model model) {
        List<Tip> tipList = tipService.listAll();
        model.addAttribute("tipList", tipList);
        return "tip";
    }
    @GetMapping("/admin/tip")
    public String showTipAdmin(Model model) {
        List<Tip> tipList = tipService.listAll();
        model.addAttribute("tipList", tipList);
        return "tipAdmin";
    }
    @RequestMapping("/admin/search")
    public String searchTip() {

        return "search";
    }

    @RequestMapping(value = "/admin/search_tip", method = RequestMethod.POST)
    public String getTip(@RequestParam("match") String match,
                      @RequestParam("data") String data) throws TwitterException {
        tipService.getTip(match, data);
        return "redirect:/tip";
    }


    @RequestMapping("/admin/tip_edit/{id}")
    public ModelAndView showEditTeamPage(@PathVariable(name ="id") long id){
        ModelAndView mav = new ModelAndView("tip_edit");
        Tip tip = tipService.get(id);
        mav.addObject("tip", tip);
        return mav;
    }
    @RequestMapping("/admin/tip_delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") int id) {
        tipService.delete(id);
        return "tip";
    }
    @RequestMapping(value = "/admin/save_tip", method = RequestMethod.POST)
    public String saveTip(@ModelAttribute("tip") Tip tip){
        tipService.save(tip);
        return "redirect:/tip";
    }
}

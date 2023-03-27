package com.example.rungroup.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

import javax.validation.Valid;

import com.example.rungroup.dto.ClubDto;
import com.example.rungroup.entities.ClubEntity;
import com.example.rungroup.entities.UserEntity;
import com.example.rungroup.security.SecurityUtil;
import com.example.rungroup.services.*;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
// @RequestMapping("/club")
public class ClubController {
    
    private UserService userService;
    private ClubService clubService;

    @GetMapping("/test")
    public String test(){
        return "basic";
    }

    @GetMapping("/clubs")
    public String listClubs(Model model){
        model.addAttribute("user", userService.getCurrentUserEntity());
        List<ClubDto> clubs = clubService.findAll();
        model.addAttribute("clubs",clubs);
        return "clubs-list";
    }

    @GetMapping("/clubs/new")
    public String createClubForm(Model model){
        ClubEntity club = new ClubEntity();
        model.addAttribute("club", club);
        return "clubs-create";
    }

    @PostMapping("/clubs/new")
    public String saveClub(@ModelAttribute("club") @Valid ClubDto clubDto, BindingResult result){
        if (result.hasErrors()){
            return "clubs-create";
        }
        clubService.save(clubDto);
        return "redirect:/clubs";
    }

    @GetMapping("/clubs/{id}/edit")
    public String editClub (@PathVariable("id") Long id, Model model){
        model.addAttribute("user", userService.getCurrentUserEntity());
        ClubDto club = clubService.findById(id);
        model.addAttribute("club", club);
        return "clubs-edit";
    }

    @PostMapping("/clubs/{id}/edit")
    public String updateClub (@PathVariable("id") Long id, @ModelAttribute("club") @Valid ClubDto club, BindingResult result){
            if (result.hasErrors()){
                return "clubs-edit";
            }
        club.setId(id);
        clubService.updateClub(club);
        return "redirect:/clubs";
    }

    @GetMapping("/default")
    public String testPage(){
        return "layout";
    }

    @GetMapping("/clubs/{id}")
    public String getDetailsById (@PathVariable("id") Long id, Model model){
        model.addAttribute("user", userService.getCurrentUserEntity());
        model.addAttribute("club", clubService.findById(id));
        return "clubs-details";
    }

    @GetMapping("/clubs/{id}/delete")
    public String deleteClub (@PathVariable("id") Long id){
        clubService.deleteByid(id);
        return "redirect:/clubs";
    }

    @GetMapping("/clubs/search")
    public String searchClub (@RequestParam(value="query") String query, Model model){
        List<ClubDto> clubDtoList = clubService.searchClubs(query);
        model.addAttribute("clubs", clubDtoList);
        return "clubs-list";
    }
}

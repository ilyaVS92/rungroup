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
    
    private UserService userSrv;
    private ClubService clubSrv;

    @GetMapping("/clubs")
    public String listClubs(Model model){
        model.addAttribute("user", userSrv.getCurrentUserEntity());
        List<ClubDto> clubs = clubSrv.findAll();
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
        clubSrv.save(clubDto);
        return "redirect:/clubs";
    }

    @GetMapping("/clubs/{clubId}/edit")
    public String getClubEditForm (@PathVariable("clubId") Long clubId, Model model){
        if (clubSrv.userHasAuthority(clubId)){
            model.addAttribute("user", userSrv.getCurrentUserEntity());
            model.addAttribute("club", clubSrv.findById(clubId));
            return "clubs-edit";
        }
        return "redirect:?authFail";
    }

    @PostMapping("/clubs/{clubId}/edit")
    public String saveClubEdits (@PathVariable("clubId") Long clubId, @ModelAttribute("club") @Valid ClubDto clubDto, BindingResult result){
        System.out.println("---update club method in controller------------------> "+clubDto.toString());
        System.out.println("=====================@PostMapping updateClub");
            if (result.hasErrors()){
                return "clubs-edit";
            }
        clubSrv.updateClub(clubDto, clubId);
        return "redirect:/clubs";
    }

    @GetMapping("/clubs/{clubId}")
    public String getDetailsById (@PathVariable("clubId") Long clubId, Model model){
        model.addAttribute("user", userSrv.getCurrentUserEntity());
        model.addAttribute("club", clubSrv.findById(clubId));
        return "clubs-details";
    }

    @GetMapping("/clubs/{clubId}/delete")
    public String deleteClub (@PathVariable("clubId") Long clubId){
        if (clubSrv.userHasAuthority(clubId)){
            clubSrv.deleteByid(clubId);
            return "/clubs/";
        }
        return "redirect:?authFailed";
    }

    @GetMapping("/clubs/search")
    public String searchClub (@RequestParam(value="query") String query, Model model){
        model.addAttribute("user", userSrv.getCurrentUserEntity());
        List<ClubDto> clubDtoList = clubSrv.searchClubs(query);
        model.addAttribute("clubs", clubDtoList);
        return "clubs-list";
    }
}

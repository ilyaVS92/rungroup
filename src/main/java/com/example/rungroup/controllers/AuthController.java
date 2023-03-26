package com.example.rungroup.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.rungroup.dto.RegistrationDto;
import com.example.rungroup.entities.UserEntity;
import com.example.rungroup.services.RoleService;
import com.example.rungroup.services.UserService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private UserService userSrv;
    private RoleService roleSrv;

    @GetMapping("/register")
    public String getRegisterationPage(Model model){
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String register (@Valid RegistrationDto user, BindingResult result, Model model){
        if (userSrv.userRecordExists(user.getUserName(), user.getEmail())){
            System.out.println("..........................,,,,,,,,,,,,,,,,DUPLICATE DETECTED");
            // result.reject("email and/or username already in database - no duplicates allowed for these fields");
            result.rejectValue("email", "","no duplicate users allowed");
            // model.addAttribute("user", user);
            // userSrv.save(user);

        }
        if (result.hasErrors()){
            System.out.println("RESULTS HAVE ERRORS - not really sure which");
            return "redirect:register";
        }
        // UserEntity localUser = userSrv.findByEmail(user.getEmail()); 
        // if (localUser != null && localUser.getEmail() != null && localUser.getEmail().isEmpty()){
        //     result.rejectValue("email", "email or username were already found - all usrs can only reg one time");
        // }

        // UserEntity localUserName = userSrv.findByUserName(user.getUserName());
        // if (localUserName != null && localUserName.getEmail() != null && localUserName.getEmail().isEmpty()){
        //     result.rejectValue("user", "email or username were already found - all usrs can only reg one time");
        // }

        // if (result.hasErrors()){
        //     model.addAttribute("username", localUser);
        //     return "/auth/register";
        // }

        userSrv.save(user);

        return "redirect:/clubs?success";
    }

    // =======================================LOGIN============================================
    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

}

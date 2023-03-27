package com.example.rungroup.security;

import java.security.Principal;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.rungroup.entities.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SecurityUtil {
    public static String getSessionUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)){

            UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String currUsername = user.getUsername();
            System.out.println("===================returning "+currUsername+" as value for getSessionUser");

            return currUsername;

            
        }
        System.out.println("=====================returning NULL as value for getSessionUser");
        return "dne";
    }
}

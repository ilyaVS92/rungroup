package com.example.rungroup.controllers;
import com.example.rungroup.services.*;
import com.example.rungroup.dto.EventDto;
import com.example.rungroup.entities.*;
import java.util.List;
import org.hibernate.event.spi.EventEngine;
import org.springframework.stereotype.Controller;

import javax.persistence.Id;
import javax.validation.Valid;

import lombok.*;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@AllArgsConstructor
// @RequestMapping("/events")
public class EventController {
    private EventService eventSrv;
    private ClubService clubSrv;

    @GetMapping("/clubs/{id}/event/new")
    public String createEvent (@PathVariable("id") Long id, Model model){
        System.out.println("=================================================> creat event "+id+" GET MAPPING");

        Event event = new Event();
        // model.addAttribute("club", clubSrv.findById(id));
        model.addAttribute("clubId", id);
        model.addAttribute("event", event);
        return "events-create";
    }
    //create a unified form for edit and create operations >>>
    // @GetMapping("/form")
    // public String getForm (@RequestParam(required=false) Long id, Model model){
    //     model.addAttribute("club", );
    //     return "events-create";
    // })

    @PostMapping("/clubs/{clubId}/event/new")
    public String createNewEvent (@PathVariable("clubId") Long id, @ModelAttribute("event") @Valid EventDto event){
        System.out.println("=================================================> creat event "+id+" POST MAPPING");
        eventSrv.createEvent(event, id);
        return "redirect:/clubs";
    }

    @GetMapping("/events")
    public String eventList (Model model){
        List<EventDto> eventDtoList = eventSrv.findAllEvents();
        model.addAttribute("events", eventDtoList);
        return "events-list";
    }

    @GetMapping("/events/{eventId}")
    public String detailsFromEventList (@PathVariable("eventId") Long eventId){
        Long clubId = eventSrv.findById(eventId).getClub().getId();
        return "redirect:/clubs/{"+clubId+"}/events/{"+eventId+"}";
    }

    @GetMapping("/events/{eventId}/edit")
    public String editFromEventList (@PathVariable("eventId") Long eventId){
        Long clubId = eventSrv.findById(eventId).getClub().getId();
        return "redirect:/clubs/{"+clubId+"}/events/{"+eventId+"}/edit";
    }

    @GetMapping("/clubs/{clubId}/events/{eventId}")
    public String eventDetails (@PathVariable("clubId") Long clubId, @PathVariable("eventId") Long id, Model model){
        System.out.println("-------------------------------------getmapping event details - ID="+id);
        model.addAttribute("clubId", clubId);
        model.addAttribute("event", eventSrv.findById(id));
        return "events-detail";
    }

    @GetMapping("/clubs/{clubId}/events/{eventId}/edit")
    public String editEvent(@PathVariable("clubId") Long clubId, @PathVariable("eventId") Long eventId, Model model, @Valid EventDto eventDto){
        EventDto localEventDto = eventSrv.findById(eventId);
        model.addAttribute("event", localEventDto);
        return "events-edit";
    }

    @PostMapping("/clubs/{clubId}/events/{eventId}/edit")
    public String saveEditedEvent (@PathVariable("clubId") Long clubId, 
    Model model,
    // BindingResult results,
    @Valid @ModelAttribute ("event") EventDto event, @PathVariable("eventId") Long eventId){
        // if (results.hasErrors()){
        //     model.addAttribute("event", event);
        //     model.addAttribute("results", results);
        //     return "events-edit";
        // } 
        eventSrv.updateEvent(event, eventId, clubId);
        return "events-detail";
    }

    @PostMapping("/clubs/{clubId}/events/{eventId}/delete")
    public String deleteEvent(@PathVariable("eventId") Long eventId, @PathVariable("clubId") Long clubId) {
        eventSrv.delete(eventId);
        return "redirect:/clubs/{clubId}";
    }
}

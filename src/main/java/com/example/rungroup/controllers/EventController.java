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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.yaml.snakeyaml.events.Event.ID;

@Controller
@AllArgsConstructor
// @RequestMapping("/events")
public class EventController {
    private EventService eventSrv;
    private ClubService clubSrv;

    @GetMapping("/clubs/{id}/event/new")
    public String createEvent (@PathVariable("id") Long id, Model model){
        Event event = new Event();
        // model.addAttribute("club", clubSrv.findById(id));
        model.addAttribute("clubId", id);
        model.addAttribute("event", event);
        return "event-create";
    }
    //create a unified form for edit and create operations >>>
    // @GetMapping("/form")
    // public String getForm (@RequestParam(required=false) Long id, Model model){
    //     model.addAttribute("club", );
    //     return "events-create";
    // })

    @PostMapping("/clubs/{clubId}/event/new")
    public String createNewEvent (@PathVariable("clubId") Long id, @ModelAttribute("event") @Valid EventDto event){
        System.out.println("=================================================> "+id);
        eventSrv.createEvent(event, id);
        return "redirect:/clubs";
    }

    @GetMapping("/clubs/{clubId}/events")
    public String eventList (Model model){
        List<EventDto> eventDtoList = eventSrv.findAllEvents();
        model.addAttribute("events", eventDtoList);
        return "events-list";
    }

    @GetMapping("/clubs/{clubId}/events/{eventId}")
    public String eventDetails (@PathVariable("eventId") Long id, Model model){
        System.out.println("-------------------------------------getmapping event details");
        model.addAttribute("event", eventSrv.findById(id));
        return "events-detail";
    }

    @GetMapping("/clubs/{clubId}/events/{eventId}/edit")
    public String editEvent (@PathVariable("clubId") Long clubId, Model model, @RequestParam(required=false, name="eventId") Long eventId){
        System.out.println("================================================> "+eventId);
        model.addAttribute("clubId", clubId);
        model.addAttribute("event", eventSrv.findById(eventId));
        return "events-edit";
    }

    @PostMapping("/clubs/{clubId}/events/{eventId}/edit")
    public String saveEdit (@PathVariable("clubId") Long id, Model model, @Valid EventDto event){
        System.out.println("post mapped saveEvent called for event id="+id);
        eventSrv.save(event);
        return "redirect:/clubs";
    }

}

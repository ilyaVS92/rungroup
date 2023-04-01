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
    // private ClubService clubSrv;
    private UserService userSrv;

    @GetMapping("/events")
    public String eventList (Model model){
        List<EventDto> eventDtoList = eventSrv.findAllEvents();
        model.addAttribute("events", eventDtoList);
        model.addAttribute("user", userSrv.getCurrentUserEntity());
        return "events-list";
    }

    @GetMapping("/clubs/{clubId}/events/{eventId}")
    public String displayEventDetails (Model model, @PathVariable("clubId") Long clubId, @PathVariable("eventId") Long id){
        System.out.println("-------------------------------------displayEventDetails - ID="+id);
        model.addAttribute("clubId", clubId);
        model.addAttribute("event", eventSrv.findById(id));
        model.addAttribute("user", userSrv.getCurrentUserEntity());
        return "events-detail";
    }

    @GetMapping("/clubs/{clubId}/events/form")
    public String displayFormNewEvent (@PathVariable("clubId") Long clubId, Model model, @RequestParam(required=false) Long eventId){
        System.out.println("---------------> EventController.displayForm clubId= "+clubId+"; eventId= "+eventId);

        if (eventSrv.userHasAuthority(eventId)){
            model.addAttribute("clubId", clubId);
            model.addAttribute("event", eventSrv.findById(eventId));
            return "events-edit";
        }
        return "redirect:/clubs/{clubId}/events/"+eventId+"?authFail";
    }

    @PostMapping("/clubs/{clubId}/event/new")
    public String saveNewEvent (Model model,
                            BindingResult result,
                            @PathVariable("clubId") Long clubId, 
                             @ModelAttribute("event") @Valid EventDto event){
        System.out.println("=================================================> creat event "+clubId+" POST MAPPING");
        if (result.hasErrors()){
            model.addAttribute("event", event);
            return "events-create";
        }
        eventSrv.save(event, clubId);
        return "redirect:/clubs/"+clubId;
    }
    //======================================================================================================================

    //create a unified form for edit and create operations >>>
    // @GetMapping("/form")
    // public String getForm (@RequestParam(required=false) Long id, Model model){
    //     model.addAttribute("club", );
    //     return "events-create";
    // })

    // @GetMapping("/events/{eventId}")
    // public String detailsFromEventList (@PathVariable("eventId") Long eventId){
    //     // Long clubId = eventSrv.findById(eventId).getClubId();
    //     Long clubId = eventSrv.findById(eventId).getClub().getId();
    //     return "redirect:/clubs/{"+clubId+"}/events/{"+eventId+"}";
    // }

    // @GetMapping("/events/{eventId}/edit")
    // public String editFromEventList (@PathVariable("eventId") Long eventId){
    //     // Long clubId = eventSrv.findById(eventId).getClubId();
    //     Long clubId = eventSrv.findById(eventId).getClub().getId();
    //     return "redirect:/clubs/{"+clubId+"}/events/{"+eventId+"}/edit";
    // }



    @GetMapping("/clubs/{clubId}/events/{eventId}/edit")
    public String displayEventEditForm (Model model, @PathVariable("clubId") Long clubId, @PathVariable("eventId") Long eventId, @Valid EventDto eventDto){
        if (eventSrv.userHasAuthority(eventId)){
            EventDto localEventDto = eventSrv.findById(eventId);
            model.addAttribute("event", localEventDto);
            System.out.println("---------------> EventController.displayEventEditForm -> displaying: "+localEventDto.toString());
            return "events-edit";
        }
        return "redirect:?userFail";
    }

    @PostMapping("/clubs/{clubId}/events/{eventId}/edit")
    public String saveEditedEvent (@Valid @ModelAttribute ("event") EventDto event, 
                                    Model model,
                                    BindingResult result,
                                    @PathVariable("eventId") Long eventId,
                                    @PathVariable("clubId") Long clubId){

        if (result.hasErrors()){
            model.addAttribute("event", event);
            return "events-edit?hasErrors";
        }
        // System.out.println("------> 1. EventController.updateEvent() retriving event: "+event.toString());
        // EventDto localDto = eventSrv.findById(eventId);
        // event.setId(eventId);
        // event.setClub(localDto.getClub());
        System.out.println("------> 2. EventController.updateEvent() retriving event: "+event.toString());
        eventSrv.updateEvent(event, eventId, clubId);
        return "redirect:/clubs/"+clubId;
    }

    @PostMapping("/clubs/{clubId}/events/{eventId}/delete")
    public String deleteEvent(@PathVariable("eventId") Long eventId, @PathVariable("clubId") Long clubId) {
        System.out.println("---------------> EventController.deleteEvent eventID="+eventId+" clubID="+clubId);

        if (eventSrv.userHasAuthority(eventId)){
            System.out.println("---------------> EventController.deleteEvent USER AUTH=true for eventID="+eventId+" clubID="+clubId);

            eventSrv.delete(eventId);
            return "redirect:/clubs/"+clubId;
        }
        return "redirect:?deleteFail";

    }
}

package com.santy.taskorg.controllers;

import com.santy.taskorg.models.Task;
import com.santy.taskorg.services.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CalendarController {

    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    // Shows homepage with form
    @GetMapping("/")
    public String showForm(Model model, @AuthenticationPrincipal OAuth2User principal) {
        // Retrieves username from the Google account to greet
        if (principal != null) {
            String name = principal.getAttribute("name");
            model.addAttribute("userName", name);
        }

        model.addAttribute("task", new Task());
        return "index"; // Looks for the file caller index.html
    }

    // Receive the form data and create the event
    @PostMapping("/create-task")
    public String createTask(
            @ModelAttribute Task t,
            @RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient client,
            Model model) {

        // Retrieves security token
        String accessToken = client.getAccessToken().getTokenValue();

        try {
            calendarService.createEvent(accessToken, t.getTitle(), t.getDueDate());
            model.addAttribute("messageSuccess", "Task '" + t.getTitle() + "' added to your Google Calendar.");
        } catch (Exception e) {
            model.addAttribute("messageError", "Oops, hubo there was a problem: " + e.getMessage());
        }

        // Goes back to the homepage
        model.addAttribute("task", new Task()); // Form cleaning
        return "index";
    }
}

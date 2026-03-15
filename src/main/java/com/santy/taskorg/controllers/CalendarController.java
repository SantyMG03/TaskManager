package com.santy.taskorg.controllers;

import com.santy.taskorg.models.Task;
import com.santy.taskorg.repository.TaskRepository;
import com.santy.taskorg.services.CalendarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CalendarController {

    private final CalendarService calendarService;
    private final TaskRepository taskRepository;

    public CalendarController(CalendarService calendarService, TaskRepository taskRepository) {
        this.calendarService = calendarService;
        this.taskRepository = taskRepository;
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
            @Valid @ModelAttribute Task t,
            BindingResult br,
            @RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient client,
            @AuthenticationPrincipal OAuth2User principal,
            Model model) {

        // If there are error, it goes back to homepage
        if (br.hasErrors()) {
            if (principal != null) {
                model.addAttribute("userName", principal.getAttribute("name"));
            }
            return "index";
        }

        // Retrieves security token
        String accessToken = client.getAccessToken().getTokenValue();

        try {
            // Sent to Google
            calendarService.createEvent(accessToken, t.getTitle(), t.getDueDate());

            // Saved into our database
            taskRepository.save(t);

            model.addAttribute("taskTitle", t.getTitle());
            model.addAttribute("taskDate", t.getDueDate().toString());

            return "success"; // Redirects to the success page
        } catch (Exception e) {
            // Catch error
            model.addAttribute("messageError", "Oops, there was a problem: " + e.getMessage());

            model.addAttribute("task", new Task());

            // Return to homepage
            return "index";
        }
    }

    @GetMapping("/history")
    public String showHistory(Model model, @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            model.addAttribute("userName", principal.getAttribute("name"));
        }
        model.addAttribute("tasks", taskRepository.findAll());

        return "history";
    }
}

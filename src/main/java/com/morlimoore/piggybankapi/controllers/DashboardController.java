package com.morlimoore.piggybankapi.controllers;

import com.morlimoore.piggybankapi.entities.NotificationEmail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @GetMapping("/index")
    public String dashboard() {
        return "dashboard";
    }
}

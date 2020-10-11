package com.morlimoore.piggybankapi.controllers;

import com.morlimoore.piggybankapi.entities.NotificationEmail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/dashboard")
public class DashboardController {

    @PostMapping("/index")
    public String dashboard(@RequestBody NotificationEmail notificationEmail) {
        return "dashboard";
    }
}

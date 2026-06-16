package com.nikhil.teamcollab.controller;

import com.nikhil.teamcollab.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping
    public List<String> getRecentActivities() {
        return activityService.getRecentActivities();
    }
}

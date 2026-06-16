package com.nikhil.teamcollab.controller;

import com.nikhil.teamcollab.dto.NotificationMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    @MessageMapping("/notify")
    @SendTo("/topic/updates")
    public NotificationMessage send(
            NotificationMessage message) {

        return message;
    }
}


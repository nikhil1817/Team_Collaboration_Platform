package com.nikhil.teamcollab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityService {

    private static final String ACTIVITY_KEY = "recent_activities";

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void addActivity(String activity) {
        String message = LocalDateTime.now() + " - " + activity;

        redisTemplate.opsForList().leftPush(ACTIVITY_KEY, message);
        redisTemplate.opsForList().trim(ACTIVITY_KEY, 0, 49);
    }

    public List<String> getRecentActivities() {
        return redisTemplate.opsForList().range(ACTIVITY_KEY, 0, 49);
    }
}

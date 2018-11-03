package com.azhen.tomcat;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

@RestController
public class TimeZoneController {
    @RequestMapping("/timeZone")
    public String auto(){
        return Calendar.getInstance().getTimeZone().getDisplayName();
    }
}

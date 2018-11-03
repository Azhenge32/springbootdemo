package com.azhen.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@RestController
@RequestMapping("/timezone")
public class TimeZoneController {
    @RequestMapping("/show")
    public String show() {
        TimeZone timeZone = Calendar.getInstance().getTimeZone();

        return timeZone.toString();
    }

    @RequestMapping("/toDate")
    public String toDate(String dateStr) throws ParseException {
        System.out.println(dateStr);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = simpleDateFormat.parse(dateStr);
        System.out.println(date);
        System.out.println(date.getTime());
        return "";
    }

    public static void main1(String[] args) {
        System.setProperty("user.timezone","UTC");
        System.out.println(Calendar.getInstance().getTimeZone().toString());
    }

    public static void main(String[] args) throws ParseException {
        System.setProperty("user.timezone","UTC");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = simpleDateFormat.parse("2018/10/12 10:05:00");
        System.out.println(date);
        System.out.println(date.getTime());

        System.out.println(new Date(1539338700000L));
    }
}

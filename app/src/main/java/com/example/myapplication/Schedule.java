package com.example.myapplication;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.net.URL;

public class Schedule {

    private Week[] Weeks;
    private String GroupName, SpecializationName;

    public Schedule(String url) throws IOException {

        Weeks = new Week[22];

        Document Page = Jsoup.parse(new URL(url + "&selectedWeek=" + 1 + "&selectedWeekday=0"), 15000);

        GroupName = Page.select("h2[class=h2-text info-block__title]").text();
        SpecializationName = Page.select("div[class=body-text info-block__description]").text();

        Weeks[0] = new Week(1,
                Page.select("div[class=caption-text schedule__head-date]"),
                Page.select("div[class=schedule__time]"),
                Page.select("div[class=schedule__item]"));

        for (int weekNum = 1; weekNum < Weeks.length; weekNum++) {
            Page = Jsoup.parse(new URL(url + "&selectedWeek=" + (weekNum + 1) + "&selectedWeekday=0"), 15000);

            Weeks[weekNum] = new Week(weekNum + 1,
                    Page.select("div[class=caption-text schedule__head-date]"),
                    Page.select("div[class=schedule__time]"),
                    Page.select("div[class=schedule__item]"));
        }
    }
    public void printSchedule(){
        System.out.println("Название группы: " + GroupName);
        System.out.println("Специальность: " + SpecializationName);
        System.out.println();
        for(int i = 0; i < Weeks.length; i++){
            Weeks[i].printDays();
        }
    }
    public Week getWeeks(int index) {
        return Weeks[index];
    }
    public String getGroupName() {
        return GroupName;
    }
    public String getSpecializationName() {
        return SpecializationName;
    }
}
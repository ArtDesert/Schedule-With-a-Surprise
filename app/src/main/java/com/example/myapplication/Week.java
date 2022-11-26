package com.example.myapplication;

import org.jsoup.select.Elements;

public class Week {

    private Day[] Days;
    private int WeekNum, MaxDaySubjects;

    public Week(int WeekNum, Elements ElemDates, Elements ElemTimes, Elements ElemSubjects) {
        this.WeekNum = WeekNum;
        MaxDaySubjects = ElemTimes.size();
        Days = new Day[6];
        Elements elemSubjectsInfoList = new Elements(), elemTimesList = new Elements();
        for (int currentDayOfTheWeek = 0; currentDayOfTheWeek < 6; ++currentDayOfTheWeek, elemTimesList.clear(), elemSubjectsInfoList.clear()) {
            for (int currentSubject = 0; currentSubject < MaxDaySubjects; ++currentSubject) {
                if (ElemSubjects.get(currentSubject * 6 + currentDayOfTheWeek).hasText()) {
                    elemTimesList.add(ElemTimes.get(currentSubject));
                    elemSubjectsInfoList.add(ElemSubjects.get(currentSubject * 6 + currentDayOfTheWeek));
                }
            }
            Days[currentDayOfTheWeek] = new Day(elemTimesList, elemSubjectsInfoList, currentDayOfTheWeek, ElemDates.get(currentDayOfTheWeek).text());
        }
    }

    public String isEven() {
        if (WeekNum % 2 == 0) return (" (чётная)");
        else return (" (нечётная)");
    }

    public void printDays(){
        System.out.println("Номер недели: " + WeekNum + isEven());
        System.out.println("Максимальное количество пар в день: " + MaxDaySubjects);
        System.out.println();
        for (int i = 0; i < Days.length; i++) {
            Days[i].printSubjects();
        }
    }
    public Day getDays(int index) {
        return Days[index];
    }
    public int getWeekNum() {
        return WeekNum;
    }
    public int getMaxDaySubjects() {
        return MaxDaySubjects;
    }
}
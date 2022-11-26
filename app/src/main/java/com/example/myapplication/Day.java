package com.example.myapplication;

import org.jsoup.select.Elements;

public class Day {

    private Subject[] Subjects;
    private String Date, DayOfTheWeek;
    private int SubjectsCount;

    public Day(Elements ElemTimes, Elements ElemSubjects, int CurrentDayOfTheWeek, String Date) {
        switch (CurrentDayOfTheWeek) {
            case 0:
                DayOfTheWeek = "Понедельник";
                break;
            case 1:
                DayOfTheWeek = "Вторник";
                break;
            case 2:
                DayOfTheWeek = "Среда";
                break;
            case 3:
                DayOfTheWeek = "Четверг";
                break;
            case 4:
                DayOfTheWeek = "Пятница";
                break;
            case 5:
                DayOfTheWeek = "Суббота";
        }
        this.Date = Date;
        this.SubjectsCount = ElemSubjects.size();
        Subjects = new Subject[SubjectsCount];
        for (int subjectNum = 0, subjectTypeNum; subjectNum < SubjectsCount; subjectNum++) {
            for (subjectTypeNum = 0; !ElemSubjects.get(subjectNum).select("div[class=schedule__lesson lesson-border lesson-border-type-" + subjectTypeNum + "]").hasText(); subjectTypeNum++);
            Subjects[subjectNum] = new Subject(ElemTimes.get(subjectNum).text(), subjectTypeNum, ElemSubjects.get(subjectNum));
        }
    }

    public void printSubjects(){
        System.out.println("Дата: " + Date);
        System.out.println("День недели: " + DayOfTheWeek);
        System.out.println("Количество пар в день: " + SubjectsCount);
        System.out.println();
        for(int i = 0; i < Subjects.length; i++){
            Subjects[i].printSubjects();
        }
    }

    public String printSubject(int i){
        return Subjects[i].printSubject();
    }

    public Subject getSubjects(int index) {
        return Subjects[index];
    }
    public String getDate() {
        return Date;
    }
    public String getDayOfTheWeek() {
        return DayOfTheWeek;
    }
    public int getSubjectsCount() {
        return SubjectsCount;
    }
}
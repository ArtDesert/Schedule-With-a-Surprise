package com.example.myapplication;

import org.jsoup.nodes.Element;
import java.util.ArrayList;

public class Subject {
    private String Type, Time;
    private ArrayList<String> Name, Location, Lecturer, GroupType, Comment;
    public Subject(String Time, int TypeNum, Element ElemSubject) {
        switch(TypeNum) {
            case 1:
                Type = "Лекция";
                break;
            case 2:
                Type = "Лабораторная";
                break;
            case 3:
                Type = "Практика";
                break;
            case 4:
                Type = "Другое";
        }
        this.Time = Time;
        this.Name = new ArrayList<>();
        this.Location = new ArrayList<>();
        this.Lecturer = new ArrayList<>();
        this.GroupType = new ArrayList<>();
        this.Comment = new ArrayList<>();
        for (int groupTypeNum = 0; groupTypeNum < ElemSubject.select("div[class=schedule__lesson lesson-border lesson-border-type-" + TypeNum + "]").size(); groupTypeNum++) {
            this.Name.add(ElemSubject.select("div[class=body-text schedule__discipline lesson-color lesson-color-type-" + TypeNum + "]").get(groupTypeNum).text());
            if(groupTypeNum < ElemSubject.select("div[class=caption-text schedule__place]").size()) {
                this.Location.add(ElemSubject.select("div[class=caption-text schedule__place]").get(groupTypeNum).text());
            }
            else {
                this.Location.add("");
            }
            if(groupTypeNum < ElemSubject.select("div[class=schedule__teacher]").size()){
                this.Lecturer.add(ElemSubject.select("div[class=schedule__teacher]").get(groupTypeNum).text());
            }
            else {
                this.Lecturer.add("");
            }
            if(groupTypeNum < ElemSubject.select("div[class=schedule__groups]").size()){
                this.GroupType.add(ElemSubject.select("div[class=schedule__groups]").get(groupTypeNum).text());
            }
            else {
                this.GroupType.add("");
            }
            if(groupTypeNum < ElemSubject.select("div[class=caption-text schedule__comment]").size()){
                this.Comment.add(ElemSubject.select("div[class=caption-text schedule__comment]").get(groupTypeNum).text());
            }
            else {
                this.Comment.add("");
            }
        }
    }
    
    public void printSubjects() {
        System.out.println("Тип занятия: " + Type);
        System.out.println("Время: " + Time);
        for (int i = 0; i < Name.size(); i++) {
            System.out.println("Название предмета: " + Name.get(i));
            System.out.println("Аудитория: " + Location.get(i));
            System.out.println("Преподаватель: " + Lecturer.get(i));
            System.out.println("Подгруппа: " + GroupType.get(i));
            System.out.println("Дополнительная информация о занятии: " + Comment.get(i));
            System.out.println();
        }
    }

    public String printSubject() {
        return "Тип занятия: " + Type;/* + '\n' +
                "Время: " + Time + '\n' +
                "Название предмета: " + Name.get(i) + '\n' +
                "Аудитория: " + Location.get(i) + '\n' +
                "Преподаватель: " + Lecturer.get(i) + '\n' +
                "Подгруппа: " + GroupType.get(i) + '\n' +
                "Дополнительная информация о занятии: " + Comment.get(i) + '\n';*/
    }

    public String getType() {
        return Type;
    }
    public String getTime() {
        return Time;
    }
    public String getName(int index) {
        return Name.get(index);
    }
    public String getLocation(int index) {
        return Location.get(index);
    }
    public String getLecturer(int index) {
        return Lecturer.get(index);
    }
    public String getGroupType(int index) {
        return GroupType.get(index);
    }
    public String getComment(int index) {
        return Comment.get(index);
    }
}
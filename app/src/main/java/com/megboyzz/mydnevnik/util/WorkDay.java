package com.megboyzz.mydnevnik.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class WorkDay {

    private ArrayList<Lesson> lessons = new ArrayList<>();

    public void addLesson(String lessonTitle, String teacherName, int numDay, String lessonStartTime, String lessonEndTime, String cabinet){
        lessons.add(new Lesson(lessonTitle, teacherName, numDay, lessonStartTime, lessonEndTime, cabinet));
    }

    public void addLesson(String lessonTitle, String teacherName, int numDay, String cabinet){
        lessons.add(new Lesson(lessonTitle, teacherName, numDay, cabinet));
    }

    public int getNumberOfLessons(){
        return lessons.size();
    }

    public Lesson getLesson(int numLesson){
        return lessons.get(numLesson);
    }

    public ArrayList<String> getLessonsList(){
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0; i < lessons.size(); i++)
            list.add((i + 1) + ". " + lessons.get(i).getLessonTitle());
        return list;
    }

    public ArrayList<ArrayList<String>> getAddintionalInfoList(){

        ArrayList<ArrayList<String>> list = new ArrayList<>();
        ArrayList<String> listik = new ArrayList<>();

        for(int i = 0; i < lessons.size(); i++){

            listik.add(lessons.get(i).getTeacherName() + "\n");

            Date start = lessons.get(i).getLessonStartTime();
            Date end = lessons.get(i).getLessonEndTime();
            if(start != null && end != null) {
                DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
                listik.add("Время: " + dateFormat.format(start) + " - " + dateFormat.format(end) + "\n");
            }

            listik.add("Кабинет: " + lessons.get(i).getCabinet());

            list.add(listik);
            listik = new ArrayList<>();
        }

        return list;
    }

    @Override
    public String toString() {
        return "WorkDay{" + "\n" +
                "lessons=" + lessons.toString() +
                '}';
    }

}

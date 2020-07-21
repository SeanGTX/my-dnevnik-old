package com.megboyzz.mydnevnik.util;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Lesson {

    private String LessonTitle;
    private String TeacherName;
    private Date LessonStartTime;
    private Date LessonEndTime;
    private String Cabinet;
    private int numDay;
    private boolean noTime = false;

    public Lesson(String lessonTitle, String teacherName, int numDay , String lessonStartTime, String lessonEndTime, String cabinet) {
        LessonTitle = lessonTitle;
        TeacherName = teacherName;
        Cabinet = cabinet;
        this.numDay = numDay;
        String strDay;
        switch (numDay){
            case 1:strDay = "Mon "; break;
            case 2:strDay = "Tue "; break;
            case 3:strDay = "Wed "; break;
            case 4:strDay = "Thu "; break;
            case 5:strDay = "Fri "; break;
            case 6:strDay = "Sat "; break;
            case 7:strDay = "Sun "; break;
            default:strDay = "";
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("EEE HH:mm", Locale.ENGLISH);
            LessonStartTime = formatter.parse(strDay + lessonStartTime);
            LessonEndTime = formatter.parse(strDay + lessonEndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Lesson(String lessonTitle, String teacherName, int numDay, String cabinet){
        LessonTitle = lessonTitle;
        TeacherName = teacherName;
        Cabinet = cabinet;
        noTime = true;
    }

    public String getLessonTitle() {
        return LessonTitle;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public Date getLessonStartTime() {
        if(noTime) return null;
        else return LessonStartTime;
    }

    public Date getLessonEndTime() {
        if(noTime) return null;
        else return LessonEndTime;
    }

    public String getCabinet() {
        return Cabinet;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "LessonTitle='" + LessonTitle + '\'' +
                ", TeacherName='" + TeacherName + '\'' +
                ", DayNumber='" + numDay + '\'' +
                ", LessonStartTime=" + LessonStartTime +
                ", LessonEndTime=" + LessonEndTime +
                ", Cabinet='" + Cabinet + '\'' +
                '}';
    }
}

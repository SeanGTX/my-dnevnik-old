package com.megboyzz.mydnevnik.util;
import java.util.ArrayList;

public class SubjectInfo {

    private String SubjectTitle;
    private int SubjectNumber;
    private ArrayList<Mark> Marks;
    private float AverageMark;

    private int numOfDelays;
    private int lessonsSkip;
    private int lessonsSkip_ill;

    public SubjectInfo(String subjectTitle, int subjectNumber, ArrayList<Mark> marks, float averageMark, int numOfDelays, int lessonsSkip, int lessonsSkip_ill) {
        SubjectTitle = subjectTitle;
        SubjectNumber = subjectNumber;
        Marks = marks;
        AverageMark = averageMark;
        this.numOfDelays = numOfDelays;
        this.lessonsSkip = lessonsSkip;
        this.lessonsSkip_ill = lessonsSkip_ill;
    }

    @Override
    public String toString() {

        String subject = SubjectNumber + ". " + SubjectTitle + " | ";

        for (Mark s : Marks){
            subject = subject + s.getValue() + " ";
        }

        subject = subject + " | Опаздания: " + numOfDelays + " | Пропуски: " + lessonsSkip + " | Пропуски по болезни: " + lessonsSkip_ill + " |";


        return subject;
    }
}

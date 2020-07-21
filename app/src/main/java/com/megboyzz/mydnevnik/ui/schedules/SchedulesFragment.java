package com.megboyzz.mydnevnik.ui.schedules;

import android.app.AlertDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.megboyzz.mydnevnik.R;
import com.megboyzz.mydnevnik.util.WorkDay;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SchedulesFragment extends Fragment {

    private SchedulesViewModel schedulesViewModel;

    private WorkDay day = new WorkDay();
    private ArrayList<WorkDay> week = new ArrayList<>();

    private static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    private void setCurrentLessonInfo(){
        TextView nowLessonTitle = root.findViewById(R.id.now_title);
        TextView TeacherName = root.findViewById(R.id.teacher_name);
        TextView CabinetName = root.findViewById(R.id.cabinet_num);
        TextView Next = root.findViewById(R.id.next);

        Date date = new Date();

        DateFormat dayOfWeekFormat = new SimpleDateFormat("EEE", Locale.ENGLISH);
        SimpleDateFormat timeformat = new SimpleDateFormat("EEE HH:mm", Locale.ENGLISH);
        String strDayOfWeek = dayOfWeekFormat.format(date);
        String genForm = timeformat.format(date);
        Date nowDate = null;
        try {
            nowDate = timeformat.parse(genForm);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int numDayOfWeek;
        switch (strDayOfWeek){
            case "Mon": numDayOfWeek = 0; break;
            case "Tue": numDayOfWeek = 1; break;
            case "Wed": numDayOfWeek = 2; break;
            case "Thu": numDayOfWeek = 3; break;
            case "Fri": numDayOfWeek = 4; break;
            case "Sat": numDayOfWeek = 5; break;
            case "Sun": numDayOfWeek = 6; break;
            default: numDayOfWeek = -1; break;
        }

        WorkDay CheckDay = new WorkDay();

        try {
            CheckDay = week.get(numDayOfWeek);
        }catch (IndexOutOfBoundsException e){
            if(numDayOfWeek != 5 || numDayOfWeek != 6) Log.e("Checking", "Error checking workday!");
            return;
        }

        if(0 <= numDayOfWeek && numDayOfWeek <= 5) {
            for (int i = 0; i < CheckDay.getLessonsList().size(); i++) {
                Date start = CheckDay.getLesson(i).getLessonStartTime();
                Date end = CheckDay.getLesson(i).getLessonEndTime();
                if(start == null || end == null) return;
                if((start.before(nowDate) && end.after(nowDate)) || (start.equals(nowDate) || end.equals(nowDate))){

                    if (i - 1 != CheckDay.getLessonsList().size()){
                        Next.setText("\nСледующий урок:\n" + CheckDay.getLesson(i + 1).getLessonTitle());
                    }else Next.setText("Ура! Это последний урок!");

                    nowLessonTitle.setText(getString(R.string.title_now_lesson) + "\n" + CheckDay.getLesson(i).getLessonTitle());

                    TeacherName.setText("Урок ведет:" + "\n" + CheckDay.getLesson(i).getTeacherName().substring(8));

                    CabinetName.setText("В кабинете" + "\n" + CheckDay.getLesson(i).getCabinet());

                    return;
                }else nowLessonTitle.setText(R.string.title_nolesson);
            }
        }
    }

    private void createMessageBox(ListView day, final ArrayList<String> list, final ArrayList<ArrayList<String>> message){
        Log.i("lol", "msg is " + message.toString());
        day.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                String title = list.get(position);
                builder.setTitle(title.substring(3));
                builder.setMessage(message.get(position).get(0) + (message.get(position).size() == 3 ? message.get(position).get(1) + message.get(position).get(2) : message.get(position).get(1)));
                builder.show();
            }
        });
    }

    private View createLessonsTable(){

        View Table = LayoutInflater.from(getContext()).inflate(R.layout.lessons, null);

        ArrayList<ListView> Days = new ArrayList<>();

        Days.add((ListView)Table.findViewById(R.id.mon_lessonsList));
        Days.add((ListView)Table.findViewById(R.id.tue_lessonsList));
        Days.add((ListView)Table.findViewById(R.id.wed_lessonsList));
        Days.add((ListView)Table.findViewById(R.id.thr_lessonsList));
        Days.add((ListView)Table.findViewById(R.id.fri_lessonsList));

        ArrayAdapter DayAdapter;

        File DataDirectory = getContext().getFilesDir();

        File SchedulesFile = new File(DataDirectory, "PrimarySchedulesFile.html");
        if(!SchedulesFile.exists()) return null;

        Element LessonsTable = null;

        try {
            LessonsTable = Jsoup.parse(SchedulesFile, "utf-8").selectFirst("table#editor");
        }catch (Exception e){
            Log.e("Error!", "TableView not created - " + e.toString());
            return null;
        }

        Elements AllRows = null;

        try {
            AllRows = LessonsTable.select("tr");
        }catch (NullPointerException e){
            Log.e("ERROR!", "file not yet initialized! " + e.toString());
            return null;
        }

        for(int i = 1; i < 7 ;i++) {
            for (int j = 1; j < 13; j++) {
                Element td = AllRows.get(j).select("td").get(i);
                Elements a = td.select("a");
                final Elements p = td.select("p");
                if (p.size() == 0){
                    if(i == 6 && j == 1) break;
                    j = 13;
                    week.add(day);

                    ArrayList<String> list = day.getLessonsList();
                    ArrayList<ArrayList<String>> infoList = day.getAddintionalInfoList();

                    DayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list);
                    Days.get(i-1).setAdapter(DayAdapter);

                    createMessageBox(Days.get(i-1), list, infoList);

                    day = new WorkDay();
                }else{
                    String LessonTitle = a.get(0).attr("title");
                    String TeacherName = p.get(0).attr("title");
                    String time = p.get(1).attr("title");
                    String cabinet = p.get(2).text();

                    String[] times = time.split(" ");
                    String begin;
                    String end;

                    if(times.length > 1) {
                        begin = time.split(" ")[1];
                        end = time.split(" ")[3];
                        day.addLesson(LessonTitle, TeacherName, i, begin, end, cabinet);
                    }else day.addLesson(LessonTitle, TeacherName, i, cabinet);
                }
            }
        }

        Log.i("struture", "Structure is " + week.get(0).getNumberOfLessons());
        Log.i("struture", "Structure is " + week.get(1).getNumberOfLessons());

        return Table;

    }

    private String generateCallsSchedule(){

        File DataDirectory = getContext().getFilesDir();

        File SchedulesFile = new File(DataDirectory, "CallSchedulesFile.html");

        Element elm = null;

        try {
            elm = Jsoup.parse(SchedulesFile, "utf-8").selectFirst("div.panel.blue2.s2");
        }catch (IOException e){
            Log.e("create Table calls", "error creating Table! " + e.toString());
        }

        String Message = elm.select("h3").text() + "\n";

        Element table = elm.select("table.grid.vam").first();

        Elements AllRows = table.select("tr");

        for(int i = 0; i < AllRows.size(); i++){
            String LessonStartTime = AllRows.get(i).select("td.tac.s3").select("strong").get(0).text();
            String LessonEndTime = AllRows.get(i).select("td.tac.s3").select("strong").get(1).text();
            Message = Message + (i + 1) + " Урок: " + LessonStartTime + " - " + LessonEndTime + "\n";
        }

        return Message;

    }

    private View root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_schedules, container, false);

        final View Table = createLessonsTable();

        final AlertDialog.Builder TableBuilder = new AlertDialog.Builder(getContext());
        TableBuilder.setTitle(R.string.title_lessons_schedule);

        if(Table != null)
            TableBuilder.setView(Table);
        else
            TableBuilder.setMessage(getString(R.string.no_onweek_lessons));

        Button LessonsScheduleButton = root.findViewById(R.id.lessons_schedule);

        LessonsScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TableBuilder.setPositiveButton(R.string.ok, null);
                TableBuilder.show();
            }
        });

        Button CallsScheduleButton = root.findViewById(R.id.calls_schedule);

        CallsScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder CallsBuilder = new AlertDialog.Builder(getContext());
                CallsBuilder.setTitle(R.string.title_school_schedule);
                CallsBuilder.setMessage(generateCallsSchedule());
                CallsBuilder.setPositiveButton(R.string.ok, null);
                CallsBuilder.show();
            }
        });

        setCurrentLessonInfo();

        return root;
    }

}
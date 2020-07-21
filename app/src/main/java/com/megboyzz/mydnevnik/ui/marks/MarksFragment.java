package com.megboyzz.mydnevnik.ui.marks;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.sax.Element;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.megboyzz.mydnevnik.R;
import com.megboyzz.mydnevnik.util.SessionDataManager;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MarksFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final SessionDataManager DataManager = new SessionDataManager(getContext());

        View root = inflater.inflate(R.layout.fragment_marks, container, false);

        TextView MarksTitle = root.findViewById(R.id.title);
        MarksTitle.setText(getString(R.string.current_marks) +  DataManager.getSchoolYear() + getString(R.string.current_marks_end));
        TextView ClassNumber = root.findViewById(R.id.class_num);
        ClassNumber.setText(DataManager.getUserClass());

        File DataDirectory = getContext().getFilesDir();
        File MarksFile = new File(DataDirectory, "PrimaryMarksFile.html");

        Elements Switch = new Elements();

        try {
            Switch = Jsoup.parse(MarksFile, "utf-8").selectFirst("ul.switch").select("li");
        } catch (IOException e) {
            e.printStackTrace();
        }

        LinearLayout.LayoutParams Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        Params.weight = 1;

        int activeElement = 0;





        return root;
    }
}
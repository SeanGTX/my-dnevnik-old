package com.megboyzz.mydnevnik.ui.homeworks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.megboyzz.mydnevnik.R;

public class HomeWorksFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        HomeWorksViewModel homeworksViewModel;

        homeworksViewModel = ViewModelProviders.of(this).get(HomeWorksViewModel.class);

        View root = inflater.inflate(R.layout.fragment_homeworks, container, false);


        final TextView textView = root.findViewById(R.id.text_dashboard);
        homeworksViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        Button toastButton = root.findViewById(R.id.ToastButton);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Toast.makeText(HomeWorksFragment.super.getContext(), "В разработке!", Toast.LENGTH_LONG).show();
            }
        };

        toastButton.setOnClickListener(listener);

        return root;
    }


}
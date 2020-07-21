package com.megboyzz.mydnevnik.ui.marks;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.megboyzz.mydnevnik.R;

import org.jsoup.nodes.Element;


public class MarksViewerFragment extends Fragment {

    private static final String TERM_NUM = "TERM_NUM";
    private static final int FIRST_TERM_CODE = 0;
    private static final int SECOND_TERM_CODE = 1;
    private static final int THIRD_TERM_CODE = 2;
    private static final int FORTH_TERM_CODE = 3;
    private static final int RESULT_CODE = 5;
    private static final int PREVIOUS_YEAR_CODE = -1;
    private static final int NEXT_YEAR_CODE = -2;

    private static int InstanceNum = 0;
    private int mParam1;

    public MarksViewerFragment() {
        InstanceNum++;
    }


    public static MarksViewerFragment newInstance(int activeTerm, Element element) {
        MarksViewerFragment fragment = new MarksViewerFragment();
        Bundle args = new Bundle();
        args.putInt(TERM_NUM, activeTerm);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            mParam1 = getArguments().getInt(TERM_NUM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_marks_viewer_list, container, false);

        return root;
    }
}

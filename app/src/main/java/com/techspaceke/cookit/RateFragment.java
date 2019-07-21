package com.techspaceke.cookit;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class RateFragment extends Fragment {
    private TextView mTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_rate, container, false);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null){
            TextView textView = (TextView) view.findViewById(R.id.rateTextView);
            textView.setText("Hello");
            Log.e("====================", "Working");
        }
    }
}

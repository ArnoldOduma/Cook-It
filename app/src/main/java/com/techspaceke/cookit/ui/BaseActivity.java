package com.techspaceke.cookit.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.techspaceke.cookit.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    String title = "Cook It";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_base);
//
//        setSupportActionBar(mToolBar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mToolBar.setTitle("Home");
    }

    @Override
    public void setContentView(int layoutResID) {
        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);
        Toolbar mToolBar = (Toolbar) findViewById(R.id.my_toolbar);
        if (useToolbar()){
            setSupportActionBar(mToolBar);
            setTitle(getCustomTitle());
            setTitle("Cook It");
        }else {
            mToolBar.setVisibility(View.GONE);
        }

    }

    public boolean useToolbar(){
        return true;
    }

    public void setCustomTitle(String title){
       title = title;
    }

    public String getCustomTitle() {
        return title;
    }
}

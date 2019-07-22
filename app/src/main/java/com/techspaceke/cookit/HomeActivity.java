package com.techspaceke.cookit;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.helloTextView) TextView mHelloTextView;
    @BindView(R.id.helloDescTextView) TextView mHelloDescTextView;
    @BindView(R.id.tryButton) Button mTryOutButton;


    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };

    GridView gridView;
    String[] description = new String[]{
            "If you've wanted to learn how to make crispy fried chicken, this is the recipe for you. " +
                    "Always a picnic favorite, this deep fried chicken recipe is delicious either hot or cold. " +
                    "Kids call it my Kentucky Fried Chicken!"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        setContentView(R.layout.trending_item);


         ButterKnife.bind(this);

        //Set Click listeners
        mTryOutButton.setOnClickListener(this);

        gridView = findViewById(R.id.baseGridView);
        TrendingAdapter trendingAdapter = new TrendingAdapter(this, description);
        gridView.setAdapter(trendingAdapter);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        mHelloTextView.setText("Hello " + name + " ,");


        Typeface caviarDreams = Typeface.createFromAsset(getAssets(),"fonts/caviar_dreams.ttf");
        Typeface bodoni = Typeface.createFromAsset(getAssets(),"fonts/bodoni.ttf");
        mHelloDescTextView.setTypeface(caviarDreams);
        mHelloTextView.setTypeface(bodoni);

        //Default font
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/default.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
    @Override
    public void onClick(View view) {
        if(view == mTryOutButton){
            Intent intent = new Intent(HomeActivity.this,CookActivity.class);
            startActivity(intent);
        }
    }
}

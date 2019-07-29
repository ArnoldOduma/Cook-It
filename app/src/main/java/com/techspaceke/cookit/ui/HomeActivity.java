package com.techspaceke.cookit.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.techspaceke.cookit.R;
import com.techspaceke.cookit.adapters.TrendingAdapter;
import com.techspaceke.cookit.models.Recipes;
import com.techspaceke.cookit.services.RecipeService;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = HomeActivity.class.getSimpleName();
    @BindView(R.id.helloTextView) TextView mHelloTextView;
    @BindView(R.id.helloDescTextView) TextView mHelloDescTextView;
    @BindView(R.id.tryButton) Button mTryOutButton;
    private List<Recipes> recipes = new ArrayList<>();
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
        String meal = intent.getStringExtra("meal");
        mHelloTextView.setText("Results for " + meal);

        Log.d(TAG, "When the activity is created");

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

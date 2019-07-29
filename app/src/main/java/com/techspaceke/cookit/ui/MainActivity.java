package com.techspaceke.cookit.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import com.techspaceke.cookit.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName() ;
    @BindView(R.id.search_button) ImageButton mSearchButton;
    @BindView(R.id.nameEditText) EditText mNameEditText;
    @BindView(R.id.beef) ImageButton mBeefImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ButterKnife.bind(this);

        Typeface caviarDreams = Typeface.createFromAsset(getAssets(),"fonts/caviar_dreams.ttf");
        Typeface bebasRegular = Typeface.createFromAsset(getAssets(), "fonts/Bebas-Regular.otf");


        //OnClick listeners
        mSearchButton.setOnClickListener(this);

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

        if (view == mSearchButton) {
            if(TextUtils.isEmpty(mNameEditText.getText())){
                Toast.makeText(this, "Please enter a meal", Toast.LENGTH_SHORT);

            }else {
                String meal = mNameEditText.getText().toString();
                Intent intent = new Intent(MainActivity.this, RecipesActivity.class);
                intent.putExtra("meal", meal);
                startActivity(intent);
            }
        }
    }

    public void beef(View view) {
        Intent intent = new Intent(MainActivity.this, RecipesActivity.class);
        intent.putExtra("meal", "beef");
        startActivity(intent);
    }
    public void chicken(View view) {
        Intent intent = new Intent(MainActivity.this, RecipesActivity.class);
        intent.putExtra("meal", "beef");
        startActivity(intent);
    }
    public void fish(View view) {
        Intent intent = new Intent(MainActivity.this, RecipesActivity.class);
        intent.putExtra("meal", "beef");
        startActivity(intent);
    }
    public void pork(View view) {
        Intent intent = new Intent(MainActivity.this, RecipesActivity.class);
        intent.putExtra("meal", "beef");
        startActivity(intent);
    }
//    public void pudding(View view) {
//        Intent intent = new Intent(MainActivity.this, RecipesActivity.class);
//        intent.putExtra("meal", "beef");
//        startActivity(intent);
//    }
//    public void beef(View view) {
//        Intent intent = new Intent(MainActivity.this, RecipesActivity.class);
//        intent.putExtra("meal", "beef");
//        startActivity(intent);
//    }


}

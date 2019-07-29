package com.techspaceke.cookit.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.techspaceke.cookit.adapters.IngredientsArrayAdapter;
import com.techspaceke.cookit.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class CookActivity extends AppCompatActivity {
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.ingredientsListView ) ListView mIngredientsListView;
    @BindView(R.id.directionsListView) ListView mDirectionsListView;
    @BindView(R.id.text1) TextView mText1;
    @BindView(R.id.ingredient) TextView mIngredient;
    @BindView(R.id.instruction) TextView mInstruction;
    @BindView(R.id.serving) TextView mServing;
    @BindView(R.id.preparation) TextView mPreparation;
    @BindView(R.id.cookScrollView) ScrollView mCookScrollView;

    private String[] ingredients = {
            "4 cups all-purpose flour, divided ",
            "2 tablespoons garlic salt ",
            "1 tablespoon paprika ",
            "3 teaspoons pepper, divided ",
            "2-1/2 teaspoons poultry seasoning ",
            "2 large eggs ",
            "1-1/2 cups water ",
            "1 teaspoon salt ",
            "2 broiler/fryer chickens (3-1/2 to 4 pounds each), cut up ",
            "Oil for deep-fat frying"
    };

    private String[] instructions = {"In a large shallow dish, combine 2-2/3 cups flour, 2 tablespoons garlic salt, 1 tablespoon paprika," +
            " 2-1/2 teaspoons pepper and 2-1/2 teaspoons poultry seasoning. In another shallow dish, beat eggs and 1-1/2 cups water; " +
            "add 1 teaspoon salt and the remaining 1-1/3 cup flour and 1/2 teaspoon pepper. Dip chicken in egg mixture, then place in the flour mixture, " +
            "a few pieces at a time. Turn to coat.",
            " In a deep-fat fryer, heat oil to 375°. Working in batches, fry chicken, several pieces at a time, until golden " +
            "brown and a thermometer inserted into chicken reads 165°, about 7-8 minutes on each side. Drain on paper towels."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook);

        ButterKnife.bind(this);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mCookScrollView.fullScroll(ScrollView.FOCUS_UP);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Rate this Recipe", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                mCookScrollView.fullScroll(ScrollView.FOCUS_UP);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RateFragment()).commit();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Fonts
        Typeface bodoni = Typeface.createFromAsset(getAssets(),"fonts/bodoni.ttf");
        mText1.setTypeface(bodoni);
        mIngredient.setTypeface(bodoni);
        mInstruction.setTypeface(bodoni);
        mServing.setTypeface(bodoni);
        mPreparation.setTypeface(bodoni);

        IngredientsArrayAdapter adapter = new IngredientsArrayAdapter(this, ingredients, R.id.list_itemTextView, R.layout.list_item);
        mIngredientsListView.setAdapter(adapter);

        IngredientsArrayAdapter directionAdapter = new IngredientsArrayAdapter(this, instructions, R.id.directions_TextView,R.layout.direction_item);
        mDirectionsListView.setAdapter(directionAdapter);


//        FragmentManager fm = getSupportFragmentManager();
//        Fragment fragment = fm.findFragmentById(R.id.detail_frag);
//        if (fragment == null) {
//            fragment = new RateFragment();
//            fm.beginTransaction()
//                    .add(R.id.fragment_container, fragment)
//                    .commit();
//        }




//        RateFragment fragment = (RateFragment)
//                getSupportFragmentManager().findFragmentById(R.id.detail_frag);
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
}

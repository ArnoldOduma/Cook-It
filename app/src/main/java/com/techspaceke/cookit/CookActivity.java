package com.techspaceke.cookit;

import android.graphics.Typeface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CookActivity extends AppCompatActivity {
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.ingredientsListView ) ListView mIngredientsListView;
    @BindView(R.id.directionsListView) ListView mDirectionsListView;
    @BindView(R.id.text1) TextView mText1;
    @BindView(R.id.ingredient) TextView mIngredient;
    @BindView(R.id.instruction) TextView mInstruction;
    @BindView(R.id.serving) TextView mServing;
    @BindView(R.id.preparation) TextView mPreparation;

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


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Rate this Recipe", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

        RateFragment fragment = (RateFragment)
                getSupportFragmentManager().findFragmentById(R.id.detail_frag);

    }


}

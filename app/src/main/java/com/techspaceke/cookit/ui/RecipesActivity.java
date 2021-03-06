package com.techspaceke.cookit.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.techspaceke.cookit.Constants;
import com.techspaceke.cookit.R;
import com.techspaceke.cookit.adapters.RecipeListAdapter;
import com.techspaceke.cookit.models.Recipes;
import com.techspaceke.cookit.services.RecipeService;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

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

public class RecipesActivity extends AppCompatActivity {
    private static final String TAG = RecipesActivity.class.getSimpleName();
    private SharedPreferences mSharedPreferences;
    private String mRecentRecipe;

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView ;
    @BindView(R.id.recipeKeyedResult) TextView mRecipeKeyedResult;
    @BindView(R.id.progressBar2) ProgressBar mProgressBar;
    @BindView(R.id.my_toolbar) Toolbar mToolBar;
    private RecipeListAdapter mAdapter;
    public List<Recipes> recipesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        ButterKnife.bind(this);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolBar.getOverflowIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
        Intent intent = getIntent();
        String meal = intent.getStringExtra("meal");
        mToolBar.setTitle("hello");
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mRecentRecipe = mSharedPreferences.getString(Constants.PREFERENCES_RECIPE_KEY, null);

        showProgressDialog();


//        mRecipeKeyedResult.setText("Results for " + meal);
        getRecipes(meal);

        Typeface scope = Typeface.createFromAsset(getAssets(),"fonts/scope.ttf");
        mRecipeKeyedResult.setTypeface(scope);

        //import font
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/poppins.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    private void getRecipes(String meal){
        RecipeService.findRecipes(meal, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                RecipesActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RecipesActivity.this,e.getLocalizedMessage()+"Error !!!" ,Toast.LENGTH_LONG).show();
                        String httpError = e.getMessage();
                        mRecipeKeyedResult.setTextColor(Color.parseColor("red"));
                        mRecipeKeyedResult.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);

                        if (httpError.contains("address")){
                            mRecipeKeyedResult.setText("Unable to resolve host !!!\n Please check your internet connection and try again");
                        }else {
                            mRecipeKeyedResult.setText(e.getLocalizedMessage() + "Error !!!");
                        }
                        mRecipeKeyedResult.setText(e.getLocalizedMessage());
                        Date date = new Date();
                        int time = date.getMinutes();

                        Toast.makeText(RecipesActivity.this,time+"Error !!!" ,Toast.LENGTH_LONG).show();
//                        if (time = )
                        hideProgressDialog();
                    }
                });
                e.printStackTrace();
            }
 
            @Override
            public void onResponse(Call call, Response response) throws IOException {//
                ResponseBody responseBody = response.peekBody(Long.MAX_VALUE);
                String jsonData = responseBody.string();
                Log.e(TAG, jsonData);
                if (response.isSuccessful()){
                    recipesList = RecipeService.processResults(response);
                    RecipesActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter = new RecipeListAdapter(getApplicationContext(), recipesList);
                            mRecyclerView.setAdapter(mAdapter);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RecipesActivity.this);
                            mRecyclerView.setLayoutManager(layoutManager);
                            mRecyclerView.setHasFixedSize(true);
                            if (jsonData.length() < 50){
                                Toast.makeText(RecipesActivity.this,"Error !!!\nRecipe not found please try again" ,Toast.LENGTH_LONG).show();
                                mRecipeKeyedResult.setTextColor(Color.parseColor("red"));
                                mRecipeKeyedResult.setText("Recipe not found please try again!!!");
                                Intent intent = new Intent(RecipesActivity.this,MainActivity.class);
                                startActivity(intent);
                            }
                            hideProgressDialog();
                        }
                    });
                }
            }
        });
    }

    public void showProgressDialog(){
        mProgressBar.setVisibility(View.VISIBLE);
    }
    public void hideProgressDialog(){
        mProgressBar.setVisibility(View.INVISIBLE);
    }
}

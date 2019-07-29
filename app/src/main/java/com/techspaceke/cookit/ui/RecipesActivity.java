package com.techspaceke.cookit.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.techspaceke.cookit.R;
import com.techspaceke.cookit.adapters.RecipeListAdapter;
import com.techspaceke.cookit.models.Recipes;
import com.techspaceke.cookit.services.RecipeService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class RecipesActivity extends AppCompatActivity {
    private static final String TAG = RecipesActivity.class.getSimpleName();
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView ;
    @BindView(R.id.recipeKeyedResult) TextView mRecipeKeyedResult;
    private RecipeListAdapter mAdapter;
    public List<Recipes> recipesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String meal = intent.getStringExtra("meal");
        mRecipeKeyedResult.setText("Results for " + meal);
        getRecipes(meal);
    }

    private void getRecipes(String meal){
        RecipeService.findRecipes(meal, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//
//                ResponseBody responseBody = response.peekBody(Long.MAX_VALUE);
//                String jsonData = responseBody.string();
//                Log.e(TAG, jsonData);
                recipesList = RecipeService.processResults(response);
                RecipesActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new RecipeListAdapter(getApplicationContext(), recipesList);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RecipesActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }
                });
            }
        });
    }
}

package com.techspaceke.cookit.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.techspaceke.cookit.R;
import com.techspaceke.cookit.models.Categories;
import com.techspaceke.cookit.services.CategoriesService;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName() ;
    @BindView(R.id.search_button) ImageButton mSearchButton;
    @BindView(R.id.searchEditText) EditText mSearchEditText;
    @BindView(R.id.beef) ImageButton mBeefImageButton;
    @BindView(R.id.chickenImageButton) ImageButton mChickenImageButton;
    @BindView(R.id.porkImageButton) ImageButton mPorkImageButton;
    @BindView(R.id.fishImageButton) ImageButton mFishImageButton;
    @BindView(R.id.httpProgressBar) ProgressBar mProgressBar;
    @BindView(R.id.my_toolbar) Toolbar mToolBar;

    public List<Categories> categoriesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolBar);
        mToolBar.getOverflowIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
        Typeface caviarDreams = Typeface.createFromAsset(getAssets(),"fonts/caviar_dreams.ttf");
        Typeface bebasRegular = Typeface.createFromAsset(getAssets(), "fonts/Bebas-Regular.otf");
        listCategories();
//        hideProgressDialog();
        showProgressDialog();
        //OnClick listeners
        mSearchButton.setOnClickListener(this);

        ViewPump.init(ViewPump.builder()
        .addInterceptor(new CalligraphyInterceptor(
                new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/poppins.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()))
        .build());
    }

    @Override
    protected void onStart() {
        super.onStart();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawer, menu);

//        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);
//        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
//        SearchView searchView =  null;
//        if (searchItem != null){
//            searchView = (SearchView) searchItem.getActionView();
//        }
//        if (searchView != null){
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
//            Toast.makeText(MainActivity.this, "Hurray Searching", Toast.LENGTH_SHORT).show();
//        }
        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public boolean onSearchRequested() {
//        Bundle appData = new Bundle();
//        appData.putBoolean(SearchManager);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

//        SearchView searchView = (SearchView) findViewById(R.id.app_bar_search);
        switch (item.getItemId()){
            case R.id.action_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.login:
                login();
                return true;

            case R.id.logOut:
                logout();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void showProgressDialog(){
        mProgressBar.setVisibility(View.VISIBLE);
    }
    public void hideProgressDialog(){
        mProgressBar.setVisibility(View.INVISIBLE);
    }


    private void listCategories(){
        showProgressDialog();
        CategoriesService.listCategories(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                hideProgressDialog();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {//
                ResponseBody responseBody = response.peekBody(Long.MAX_VALUE);
                String jsonData = responseBody.string();
                Log.e(TAG, jsonData);

                categoriesList = CategoriesService.processResults(response);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        hideProgressDialog();
//                        mAdapter = new RecipeListAdapter(getApplicationContext(), recipesList);
//                        mRecyclerView.setAdapter(mAdapter);
//                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RecipesActivity.this);
//                        mRecyclerView.setLayoutManager(layoutManager);
//                        mRecyclerView.setHasFixedSize(true);
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View view) {
        ButterKnife.bind(this);
        if (view == mSearchButton) {
            if (TextUtils.isEmpty(mSearchEditText.getText())) {
                Toast.makeText(this, "Please enter a meal", Toast.LENGTH_SHORT).show();

            } else {
                String meal = mSearchEditText.getText().toString();
                Intent intent = new Intent(MainActivity.this, RecipesActivity.class);
                intent.putExtra("meal", meal);
                startActivity(intent);
            }
        }

    }

    public void login(){
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }
    public void logout(){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(this, "Logging out...",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }

    public void signUp(View view) {
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    public void beef(View view) {
        Intent intent = new Intent(MainActivity.this, RecipesActivity.class);
        intent.putExtra("meal", "beef");
        startActivity(intent);
    }
    public void chicken(View view) {
        Intent intent = new Intent(MainActivity.this, RecipesActivity.class);
        intent.putExtra("meal", "chicken");
        startActivity(intent);
    }
    public void fish(View view) {
        Intent intent = new Intent(MainActivity.this, RecipesActivity.class);
        intent.putExtra("meal", "fish");
        startActivity(intent);
    }
    public void pork(View view) {
        Intent intent = new Intent(MainActivity.this, RecipesActivity.class);
        intent.putExtra("meal", "pork");
        startActivity(intent);
    }
}

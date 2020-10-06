package com.techspaceke.cookit.ui;

import android.app.ActivityOptions;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.techspaceke.cookit.Constants;
import com.techspaceke.cookit.R;
import com.techspaceke.cookit.adapters.PopularRecipeListAdapter;
import com.techspaceke.cookit.controlls.ExpandableHeightGridView;
import com.techspaceke.cookit.models.Categories;
import com.techspaceke.cookit.services.CategoriesService;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Type;
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

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    public List<Categories> categoriesList = new ArrayList<>();
    private DatabaseReference mSearchedRecipeRef;
    private ValueEventListener mSearchedRecipeRefListener;
    FirebaseUser mUser;
    private PopularRecipeListAdapter mAdapter;
    Gson gson = new Gson();

    private int screenHeight  = 0;
    private int screenWidth = 0;

    @BindView(R.id.favouritesBtn)
    ImageView mFavouritesImageViewButton;
    @BindView(R.id.httpProgressBar)
    ProgressBar mProgressBar;
//    @BindView(R.id.my_toolbar)
//    Toolbar mToolBar;
    TextView mDesc;
    @BindView(R.id.categoriesGrid)
    GridView mCategoriesGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSearchedRecipeRef = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_SEARCHED_RECIPES);

        mSearchedRecipeRefListener = mSearchedRecipeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    String recipe = recipeSnapshot.getValue().toString();
                    Log.e("Recipe Updated firebase", recipe);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        if (getSupportActionBar() != null){
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
////     getSupportActionBar()
//
//        }
//        mToolBar.setVisibility(View.GONE);
//        setSupportActionBar(mToolBar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mToolBar.getOverflowIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();
        listCategories();
        mCategoriesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Categories categories = categoriesList.get(position);
                Intent intent = new Intent(MainActivity.this, FilteredMealActivity.class);
                intent.putExtra("meal", categoriesList.get(position).getStrCategory());
                startActivity(intent);
            }
        });

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        //OnClick listeners
        mFavouritesImageViewButton.setOnClickListener(this);

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/poppins.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
    }

    Handler handler = new Handler();
    Runnable updateData = new Runnable() {
        @Override
        public void run() {
            listCategories();
        }
    };

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
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search recipes");
        searchView.setElevation(4);
        searchView.setPadding(10, 0, 10, 0);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent = new Intent(MainActivity.this, RecipesActivity.class);
                intent.putExtra("meal", s);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
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

    public void showProgressDialog() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressDialog() {
        mProgressBar.setVisibility(View.GONE);
    }


    private void listCategories() {
        showProgressDialog();
        String data = mSharedPreferences.getString("SavedCategories", "");
        Type type = new TypeToken<List<Categories>>() {
        }.getType();
        if (data != "") {
            categoriesList = gson.fromJson(data, type);
            MainActivity.this.runOnUiThread(()-> {
//                @Override
//                public void run() {
                hideProgressDialog();
                mAdapter = new PopularRecipeListAdapter(getApplicationContext(), categoriesList);
                mCategoriesGridView.setAdapter(mAdapter);
                mCategoriesGridView.animate().alpha(0.0f).setDuration(0).translationY(500).setDuration(0).alpha(1.0f).setDuration(500).translationY(0).setStartDelay(500).setDuration(800);
//                }
            });
        } else {
            CategoriesService.listCategories(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            handler.postDelayed(updateData, 3000);
                            Toast.makeText(MainActivity.this, "Error Getting categories", Toast.LENGTH_SHORT).show();
                        }
                    });

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
                            mAdapter = new PopularRecipeListAdapter(getApplicationContext(), categoriesList);
                            mCategoriesGridView.setAdapter(mAdapter);
                            ViewGroup.LayoutParams params = mCategoriesGridView.getLayoutParams();
                            params.height = screenHeight;
                            mCategoriesGridView.setLayoutParams(params);
                            hideProgressDialog();
                            mCategoriesGridView.animate().alpha(1.0f).setDuration(500).setStartDelay(500).translationY(0).setDuration(800);
//
                        }
                    });
                }
            });
        }
    }

    public void saveRecipeToFirebase(String recipe) {
        mSearchedRecipeRef.push().setValue(recipe);
    }

    private void addToSharedPreferences(String recipe) {
        mEditor.putString(Constants.PREFERENCES_RECIPE_KEY, recipe).apply();
    }

    @Override
    public void onClick(View view) {
        ButterKnife.bind(this);
        if (view == mFavouritesImageViewButton) {
            Intent intent = new Intent(this, SavedRecipeListActivity.class);
            startActivity(intent);
        }

    }

    public void login() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void signUp(View view) {
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchedRecipeRef.removeEventListener(mSearchedRecipeRefListener);
    }
}

package com.techspaceke.cookit;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.loginButton) Button mLoginButton;
    @BindView(R.id.nameEditText) EditText mNameEditText;
    @BindView(R.id.nameTextView)
    TextView mNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        Typeface caviarDreams = Typeface.createFromAsset(getAssets(),"fonts/caviar_dreams.ttf");
        Typeface bebasRegular = Typeface.createFromAsset(getAssets(), "fonts/Bebas-Regular.otf");
        mLoginButton.setTypeface(caviarDreams);
        mNameTextView.setTypeface(caviarDreams);

        //OnClick listeners
        mLoginButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if (view == mLoginButton) {
            String name = mNameEditText.getText().toString();
            Toast.makeText(MainActivity.this, "Hello " + name + "\nWelcome to CookIt", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            intent.putExtra("name", name);
            startActivity(intent);
        }

    }
}

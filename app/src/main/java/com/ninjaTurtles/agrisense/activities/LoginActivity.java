package com.ninjaTurtles.agrisense.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.ninjaTurtles.agrisense.R;
import com.ninjaTurtles.agrisense.utils.AnimationHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private ImageView imgLogo;
    private TextInputLayout tilEmail, tilPassword;
    private MaterialButton btnLogin;
    private TextView tvRegisterLink, tvForgotPassword;
    private EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imgLogo = findViewById(R.id.imgLoginLogo);
        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegisterLink = findViewById(R.id.tvRegisterLink);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        // Pre-fill demo credentials
        etEmail.setText("demo@agrisense");
        etPassword.setText("password123");

        runStaggeredEntryAnimations();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationHelper.animateButtonPress(LoginActivity.this, btnLogin);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
                    }
                }, 150);
            }
        });

        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    private void runStaggeredEntryAnimations() {
        // Logo (300ms)
        Animation logoAnim = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        imgLogo.startAnimation(logoAnim);

        // Email (400ms delay)
        Animation slideUpEmail = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        slideUpEmail.setStartOffset(100);
        tilEmail.startAnimation(slideUpEmail);

        // Password (500ms delay)
        Animation slideUpPass = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        slideUpPass.setStartOffset(200);
        tilPassword.startAnimation(slideUpPass);

        // Button (600ms delay)
        Animation scaleBtn = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleBtn.setStartOffset(300);
        btnLogin.startAnimation(scaleBtn);

        // Register link (700ms delay)
        Animation fadeReg = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadeReg.setStartOffset(400);
        tvRegisterLink.startAnimation(fadeReg);
    }
}

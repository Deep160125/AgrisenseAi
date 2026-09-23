package com.ninjaTurtles.agrisense.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.ninjaTurtles.agrisense.R;
import com.ninjaTurtles.agrisense.utils.AnimationHelper;
import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {

    private ImageView imgLogo, imgTogglePassword;
    private MaterialButton btnLogin;
    private LinearLayout btnMobileOtp, btnFingerprint;
    private TextView tvRegisterLink, tvForgotPassword;
    private EditText etEmail, etPassword;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imgLogo = findViewById(R.id.imgLoginLogo);
        btnLogin = findViewById(R.id.btnLogin);
        btnMobileOtp = findViewById(R.id.btnMobileOtp);
        btnFingerprint = findViewById(R.id.btnFingerprint);
        tvRegisterLink = findViewById(R.id.tvRegisterLink);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        imgTogglePassword = findViewById(R.id.imgTogglePassword);

        // Pre-fill demo credentials matching design
        etEmail.setText("farmer@agrisense.com");

        runStaggeredEntryAnimations();

        // Password visibility toggle
        if (imgTogglePassword != null) {
            imgTogglePassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isPasswordVisible = !isPasswordVisible;
                    if (isPasswordVisible) {
                        etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    } else {
                        etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    }
                    etPassword.setSelection(etPassword.getText().length());
                }
            });
        }

        // Primary Login button
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

        // Mobile OTP button
        if (btnMobileOtp != null) {
            btnMobileOtp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AnimationHelper.animateButtonPress(LoginActivity.this, btnMobileOtp);
                    Toast.makeText(LoginActivity.this, "OTP verification sent to registered phone number", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Fingerprint button
        if (btnFingerprint != null) {
            btnFingerprint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AnimationHelper.animateButtonPress(LoginActivity.this, btnFingerprint);
                    Toast.makeText(LoginActivity.this, "Sensor ready. Touch fingerprint sensor to sign in", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Register link navigation
        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        // Forgot password navigation
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
        if (imgLogo != null) {
            Animation logoAnim = AnimationUtils.loadAnimation(this, R.anim.scale_up);
            imgLogo.startAnimation(logoAnim);
        }

        if (btnLogin != null) {
            Animation scaleBtn = AnimationUtils.loadAnimation(this, R.anim.scale_up);
            scaleBtn.setStartOffset(200);
            btnLogin.startAnimation(scaleBtn);
        }

        if (tvRegisterLink != null) {
            Animation fadeReg = AnimationUtils.loadAnimation(this, R.anim.fade_in);
            fadeReg.setStartOffset(350);
            tvRegisterLink.startAnimation(fadeReg);
        }
    }
}

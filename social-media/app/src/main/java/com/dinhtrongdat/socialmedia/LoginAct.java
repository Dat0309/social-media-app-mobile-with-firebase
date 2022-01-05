package com.dinhtrongdat.socialmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginAct extends AppCompatActivity implements View.OnClickListener {

    /**
     * Defind project variable
     */
    TextView imgLogo;
    TextInputLayout edtUser, edtPass;
    Button btnSignin, btnSignup, btnForgot;
    ProgressBar progressBar;
    LinearLayout linear;
    TextView txtDes, txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        initUI();
    }

    private void initUI() {
        imgLogo = findViewById(R.id.img_logo);
        edtUser = findViewById(R.id.username);
        edtPass = findViewById(R.id.password);
        btnSignin = findViewById(R.id.btn_signin);
        btnSignup = findViewById(R.id.btn_signup);
        btnForgot = findViewById(R.id.btn_forgot_pass);
        progressBar = findViewById(R.id.progres);
        linear = findViewById(R.id.linear_btn);
        txtDes = findViewById(R.id.txt_des);
        txtTitle = findViewById(R.id.txt_title);

        Sprite wave = new Wave();
        progressBar.setIndeterminateDrawable(wave);

        // Set animation to component
        Animation topAnim = AnimationUtils.loadAnimation(this, R.anim.anim_top);
        linear.setAnimation(topAnim);
        txtTitle.setAnimation(topAnim);
        txtDes.setAnimation(topAnim);
        imgLogo.setAnimation(topAnim);

        btnSignin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
        btnForgot.setOnClickListener(this);
    }

    /**
     * Hàm kiểm tra đăng nhập với google
     */
    private void SigninGmail() {
        if (!ValidationUser() | !ValidationPass()) return;
        HideKeyboard(LoginAct.this);
        progressBar.setVisibility(View.VISIBLE);

        String strEmail = Objects.requireNonNull(edtUser.getEditText()).getText().toString().trim();
        String strPass = Objects.requireNonNull(edtPass.getEditText()).getText().toString().trim();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(strEmail, strPass).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {

                Toast.makeText(LoginAct.this, "Login Success", Toast.LENGTH_SHORT).show();
                FirebaseUser user = auth.getCurrentUser();

                Intent intent = new Intent(LoginAct.this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
                progressBar.setVisibility(View.GONE);
            } else {
                Toast.makeText(LoginAct.this, "Fail", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    /**
     * Hàm ẩn bàn phím khi nhấn đăng nhập
     *
     * @param loginAct
     */
    private void HideKeyboard(LoginAct loginAct) {
        InputMethodManager inputMethodManager = (InputMethodManager) loginAct.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = loginAct.getCurrentFocus();
        if (view == null)
            view = new View(loginAct);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Hàm kiểm tra giá trị gmail nhập vào của người dùng
     *
     * @return true or false
     */
    private boolean ValidationUser() {
        String val = edtUser.getEditText().getText().toString();
        String space = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            edtUser.setError("Nhập tài khoản");
            return false;
        } else {
            edtUser.setError(null);
            return true;
        }
    }

    /**
     * Hàm kiểm tra giá trị mật khẩu người dùng nhập vào
     *
     * @return true or false
     */
    private boolean ValidationPass(){
        String val = edtPass.getEditText().getText().toString();
        String space = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            edtPass.setError("Nhập mật khẩu");
            return false;
        } else if (val.length() >= 15) {
            edtPass.setError("Mật khẩu không được quá 15 ký tự");
            return false;
        } else if (!val.matches(space)) {
            edtPass.setError("Mật khẩu không được chứa khoảng trắng");
            return false;
        } else {
            edtPass.setError(null);
            return true;
        }
    }

    /**
     * Hàm chuyển màn hình đăng ký
     */
    private void RegisterUser() {

        Intent intent = new Intent(LoginAct.this, RegisterAct.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_signin:
                SigninGmail();
                break;
            case R.id.btn_signup:
                RegisterUser();
                break;
            case R.id.btn_forgot_pass:
                break;
        }
    }
}
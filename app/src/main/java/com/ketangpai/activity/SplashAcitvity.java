package com.ketangpai.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.ketangpai.model.UserModel;
import com.ketangpai.modelImpl.UserModelImpl;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.utils.VolleyUtils;

/**
 * Created by nan on 2016/3/9.
 */
public class SplashAcitvity extends AppCompatActivity {

    Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        String account = getSharedPreferences("user", 0).getString("account", "");
        String password = getSharedPreferences("user", 0).getString("password", "");


        if (!account.equals("") && !password.equals("")) {
            Log.i(LoginActivity.TAG, "account=" + account + "  password=" + password);
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }

        finish();

    }
}

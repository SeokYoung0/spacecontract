package com.spaceplanning.app.spacecontract;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

public class LoginActivity extends AppCompatActivity {
    LinearLayout ll_naver_login;

    OAuthLogin mOAuthLoginModule;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = getApplicationContext();

        ll_naver_login = findViewById(R.id.ll_naver_login);
        ll_naver_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NaverLoginInstance();

                @SuppressLint("HandlerLeak")
                OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
                    @Override
                    public void run(boolean success) {
                        NaverLoginBoolean(success);
                    }
                };
                mOAuthLoginModule.startOauthLoginActivity(LoginActivity.this, mOAuthLoginHandler);

            }
        });
    }

    // 네이버 아이디로 로그인을 시도하며 이때 성공과 실패로 두고 결과를 보여준다.
    private void NaverLoginBoolean(boolean success) {
        if (success) {
            Toast.makeText(mContext, "네이버 로그인 성공!", Toast.LENGTH_SHORT).show();
            redirectSignupActivity();
        } else {
            Toast.makeText(mContext, "로그인 실패", Toast.LENGTH_SHORT).show();
        }
    }

    // 네이버 아이디로 로그인 API의 연결을 위해 client id, secret, name Instance를 호출해온다.
    private void NaverLoginInstance() {
        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(
                mContext
                , getString(R.string.naver_client_id)
                , getString(R.string.naver_client_secret)
                , getString(R.string.naver_client_name)
                //,OAUTH_CALLBACK_INTENT
                // SDK 4.1.4 버전부터는 OAUTH_CALLBACK_INTENT변수를 사용하지 않습니다.
        );
    }

    // 이동할 다음 Activity인 MainActivity로 보낸다.
    private void redirectSignupActivity() {
        final Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

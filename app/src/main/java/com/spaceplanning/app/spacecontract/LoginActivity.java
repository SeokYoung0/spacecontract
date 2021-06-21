package com.spaceplanning.app.spacecontract;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    LinearLayout ll_naver_login;
    LinearLayout ll_google_login;
    GoogleSignInClient mGoogleSignInClient;
    NaverHandler mNaverHandler;
    private PreferenceManager mPreferenceManager;


    OAuthLogin mOAuthLoginModule;
    Context mContext;
    private int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mNaverHandler = new NaverHandler(mContext,mOAuthLoginModule, LoginActivity.this);
        mContext = getApplicationContext();

        ll_naver_login = findViewById(R.id.ll_naver_login);
        ll_naver_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NaverLoginInstance();
                mNaverHandler = new NaverHandler(mContext,mOAuthLoginModule, LoginActivity.this);
                Toast.makeText(mContext, "네이버 로그인 버튼 클릭", Toast.LENGTH_SHORT).show();
                @SuppressLint("HandlerLeak")
                OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
                    @Override
                    public void run(boolean success) {
//                        NaverLoginBoolean(success);
                        mNaverHandler.run(success);

                    }
                };
                mOAuthLoginModule.startOauthLoginActivity(LoginActivity.this, mOAuthLoginHandler);

            }
        });
        // google login
        ll_google_login = findViewById(R.id.ll_google_login);
        // 앱에 필요한 사용자 데이터를 요청하도록 로그인 옵션을 설정한다.
        // DEFAULT_SIGN_IN parameter는 유저의 ID와 기본적인 프로필 정보를 요청하는데 사용된다.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();
        // 위에서 만든 GoogleSignInOptions을 사용해 GoogleSignInClient 객체를 만듬
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // 기존에 로그인 했던 계정을 확인한다.
        GoogleSignInAccount gsa = GoogleSignIn.getLastSignedInAccount(LoginActivity.this);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        // 로그인 되있는 경우 (토큰으로 로그인 처리)
        if (gsa != null && gsa.getId() != null) {
            Log.d("google", String.valueOf(account.getAccount()));
        }
        ll_google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent singInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(singInIntent, RC_SIGN_IN);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            if(resultCode == RESULT_OK){
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            }
        }
    }



    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            mPreferenceManager.setString(mContext,"email" , account.getEmail());
            mPreferenceManager.setString(mContext,"name" , account.getFamilyName() + account.getGivenName());
            finish();
            // Signed in successfully, show authenticated UI.

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d("google", String.valueOf(e.getStatusCode()));
        }
    }


    // 네이버 아이디로 로그인을 시도하며 이때 성공과 실패로 두고 결과를 보여준다.
    private void NaverLoginBoolean(boolean success) {
        if (success) {
            String accessToken = mOAuthLoginModule.getAccessToken(mContext);
            Toast.makeText(mContext, "네이버 로그인 성공!"+accessToken, Toast.LENGTH_SHORT).show();
            Log.d("Naver", "ㅇㅇ"+ String.valueOf(mOAuthLoginModule.getAccessToken(mContext)));

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
//        final Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
        finish();
    }
}

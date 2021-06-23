package com.spaceplanning.app.spacecontract.BottomNaviFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.nhn.android.naverlogin.OAuthLogin;
import com.spaceplanning.app.spacecontract.LoginActivity;
import com.spaceplanning.app.spacecontract.PreferenceManager;
import com.spaceplanning.app.spacecontract.R;

public class MenuFragment extends Fragment {
    private IOnClickMenuButtonListener mListener;
    public void setOnClickMenuButtonListener(IOnClickMenuButtonListener listener){
        mListener = listener;
    }


    private Context mContext;
    private Button btn_logout;
    private Button btn_accountsetting;
    private Button btn_withdrawal;
    private OAuthLogin mOAuthLoginModule;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        mContext = getContext();

        // 네이버로 로그인시 로그아웃 버튼 동작
        mOAuthLoginModule = OAuthLogin.getInstance();
        btn_logout = (Button)view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOAuthLoginModule.logout(mContext);
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();
                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getContext(), gso);
                googleSignInClient.signOut();
                Toast.makeText(getActivity(),"로그아웃 되었습니다.",Toast.LENGTH_SHORT).show();
                PreferenceManager.clear(getContext());
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        // 계정 정보확인을 위해 계정 설정을 들어가면 가지고 온 정보들을 확인 할 수 있어야한다.
        btn_accountsetting = (Button)view.findViewById(R.id.btn_accountsetting);
        btn_accountsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onMenuButtonSelected("btn_accountsetting");
            }
        });


        //연동된 이메일 탈퇴 기능
        btn_withdrawal = (Button)view.findViewById(R.id.btn_withdrawal);
        btn_withdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (IOnClickMenuButtonListener)context;
        }catch (ClassCastException e){
            throw new ClassCastException(((Activity)context)
            .getLocalClassName()+ "는 IOnClickMenuButtonListener를 구현해야합니다.");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }



}
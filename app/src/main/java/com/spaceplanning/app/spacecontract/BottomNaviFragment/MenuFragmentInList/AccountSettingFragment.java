package com.spaceplanning.app.spacecontract.BottomNaviFragment.MenuFragmentInList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.data.OAuthLoginState;
import com.spaceplanning.app.spacecontract.NaverHandler;
import com.spaceplanning.app.spacecontract.PreferenceManager;
import com.spaceplanning.app.spacecontract.R;

public class AccountSettingFragment extends Fragment {
    private static Context mContext;
    private static OAuthLogin mOAuthLoginInstance = OAuthLogin.getInstance();
    private TextView tv_user_email;
    private TextView tv_user_name;

    //로그인한 사용자의 이메일과 작성된 이름을 보여준다.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account_setting, container, false);

        tv_user_email = (TextView)view.findViewById(R.id.tv_user_email);
        tv_user_name = (TextView)view.findViewById(R.id.tv_user_name);

        tv_user_email.setText(PreferenceManager.getString(getActivity(), "email"));
        tv_user_name.setText(PreferenceManager.getString(getActivity(), "name"));

        return view;
    }


}
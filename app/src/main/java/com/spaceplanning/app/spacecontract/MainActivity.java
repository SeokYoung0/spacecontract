package com.spaceplanning.app.spacecontract;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.spaceplanning.app.spacecontract.BottomNaviFragment.ConsultingFragment;
import com.spaceplanning.app.spacecontract.BottomNaviFragment.HomeFragment;
import com.spaceplanning.app.spacecontract.BottomNaviFragment.IOnClickMenuButtonListener;
import com.spaceplanning.app.spacecontract.BottomNaviFragment.MenuFragment;
import com.spaceplanning.app.spacecontract.BottomNaviFragment.MenuFragmentList.AccountSettingFragment;
import com.spaceplanning.app.spacecontract.BottomNaviFragment.NoticeFragment;

public class MainActivity extends AppCompatActivity implements IOnClickMenuButtonListener {
    //Title Toolbar
    TextView textview_title;
    ImageView iv_logo;

    //Bottom Navigation
    BottomNavigationView mBottomNavigationView;

    //Bottom Fragments
    Fragment fragment_home = new HomeFragment();
    Fragment fragment_consulting = new ConsultingFragment();
    Fragment fragment_menu = new MenuFragment();
    Fragment fragment_notice = new NoticeFragment();

    //Menu Fragments
    Fragment fragment_account_setting = new AccountSettingFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Toolbar Setting
        textview_title = findViewById(R.id.textview_title);
        iv_logo = findViewById(R.id.iv_logo);

        // 초기 프레그먼트 설정
        replaceFragment(fragment_home, "home");

        bottomNavigationClick();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    // BottomNavigation 클릭 시 동작
    private void bottomNavigationClick() {
        // BottomNaviagtion
        mBottomNavigationView = findViewById(R.id.bottomNavigationView);

        // 리스너 등록 하단 Navigation 별 Fragment 버튼 클릭시 Fragment이동
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                       replaceFragment(fragment_home, "home");
                       toolbarSetting("Space 계약", View.VISIBLE);
                        break;
                    case R.id.consulting:
                        replaceFragment(fragment_consulting, "consulting");
                        toolbarSetting("상담", View.GONE);
                        return true;
                    case R.id.menu:
                        replaceFragment(fragment_menu, "menu");
                        toolbarSetting("메뉴", View.GONE);
                        break;
                    case R.id.notice:
                        replaceFragment(fragment_notice, "notice");
                        toolbarSetting("알림", View.GONE);
                        break;
                }
                return true;
            }
        });
    }

    // 상단 toolbar 설정 변경
    private void toolbarSetting(String 상담, int gone) {
        textview_title.setText(상담);
        iv_logo.setVisibility(gone);
    }

    // 뒤로가기 버튼 클릭시 HomeFragment로 돌아옴
    @Override
    public void onBackPressed() {
        if(isBackHome()){
            onBackHome();
        }

        else {
            onBackMenu();
        }
        super.onBackPressed();
    }

    private void onBackMenu() {
        replaceFragment(fragment_menu, "menu", "이해불가");
//        replaceFragment(fragment_menu, "menu");
//        replaceFragment(fragment_menu, "menu");
    }

    private boolean isBackHome() {
        String[] tags = {"home", "menu", "consulting", "notice"} ;
        String temp = null;
        for (Fragment fragment: getSupportFragmentManager().getFragments()){

           if(fragment.isVisible())
           {
               for (int i=0; i<tags.length; i++){
                   if(fragment instanceof HomeFragment)
                       temp = "Home";
                   if(fragment instanceof MenuFragment)
                       temp = "Menu";
                   if(fragment instanceof ConsultingFragment)
                       temp = "Consulting";
                   if(fragment instanceof NoticeFragment)
                       temp = "Notice";
               }
           }
        }

        return temp != null? true: false;
    }

    private void onBackHome() {
        switch (mBottomNavigationView.getSelectedItemId()) {
            case R.id.menu:
                mBottomNavigationView.setSelectedItemId(R.id.home);
                replaceFragment(fragment_home, "home");
                break;
            case R.id.notice:
                mBottomNavigationView.setSelectedItemId(R.id.home);
                replaceFragment(fragment_home, "home");
                break;
            case R.id.consulting:
                mBottomNavigationView.setSelectedItemId(R.id.home);
                replaceFragment(fragment_home, "home");
                break;
            case R.id.home:
                finish();
                break;
            default:
                break;
        }
    }

    // Fragment별 이동
    public void replaceFragment(Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_layout, fragment, tag)
                .addToBackStack(null)
                .commit();
    }
    public void replaceFragment(Fragment fragment, String tag, String daf) {
        Log.d("tag","1");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_layout, fragment, tag)
                .addToBackStack(null)
                .commit();
    }
    @Override
    public void onMenuButtonSelected(String ButtonId) {
        switch (ButtonId){
            case "btn_accountsetting":
                replaceFragment(fragment_account_setting, "accountsetting");
                break;
        }
    }
}
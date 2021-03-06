package com.spaceplanning.app.spacecontract;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.spaceplanning.app.spacecontract.BottomNaviFragment.ConsultingFragment;
import com.spaceplanning.app.spacecontract.BottomNaviFragment.HomeFragment;
import com.spaceplanning.app.spacecontract.BottomNaviFragment.IOnClickMenuButtonListener;
import com.spaceplanning.app.spacecontract.BottomNaviFragment.MenuFragment;
import com.spaceplanning.app.spacecontract.BottomNaviFragment.MenuFragmentInList.AccountSettingFragment;
import com.spaceplanning.app.spacecontract.BottomNaviFragment.NoticeFragment;
import com.spaceplanning.app.spacecontract.HomeFragmentInList.HowToWriteContractFragment;
import com.spaceplanning.app.spacecontract.HomeFragmentInList.UploadFilesFragment;
import com.spaceplanning.app.spacecontract.HomeFragmentInList.WriteContractFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IOnClickMenuButtonListener {
    private static final String TAG = "MainActivity";
    PreferenceManager mPreferenceManager;
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

    //Image Slider
    private ViewPager2 sliderViewPager;
    private LinearLayout layoutIndicator;
    private View viewFadingEdge;
    private int[] images = new int[]{
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3
    };

    // Constant Request Code
    int IMG_FROM_WRITE_CONTRACT_FRAGMENT = 1;
    int HWP_FROM_WRITE_CONTRACT_FRAGMENT = 2;
    int PDF_FROM_WRITE_CONTRACT_FRAGMENT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Toolbar Setting
        textview_title = findViewById(R.id.textview_title);
        iv_logo = findViewById(R.id.iv_logo);

        // ?????? ??????????????? ??????
        replaceFragment(fragment_home, "home");

        //???????????? ?????????
        sliderViewPager = findViewById(R.id.sliderViewPager);
        layoutIndicator = findViewById(R.id.layoutIndicators);

        sliderViewPager.setOffscreenPageLimit(1);
        sliderViewPager.setAdapter(new ImageSliderAdapter(this, images));

        sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });
        setupIndicators(images.length);

        // ????????? ????????? ?????? ??? ???????????? ??????

        bottomNavigationClick();
    }

    // BottomNavigation ?????? ??? ??????
    private void bottomNavigationClick() {
        // BottomNaviagtion
        mBottomNavigationView = findViewById(R.id.bottomNavigationView);

        // ????????? ?????? ?????? Navigation ??? Fragment ?????? ????????? Fragment??????
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        viewAsFragmentChange(fragment_home, "home", "Space ??????", View.VISIBLE);
                        break;
                    case R.id.consulting:
                        viewAsFragmentChange(fragment_consulting, "consulting", "??????", View.GONE);
                        break;
                    case R.id.menu:
                        viewAsFragmentChange(fragment_menu, "menu", "??????", View.GONE);
                        break;
                    case R.id.notice:
                        viewAsFragmentChange(fragment_notice, "notice", "??????", View.GONE);
                        break;
                }
                return true;
            }
        });
    }

    private void viewAsFragmentChange(Fragment fragment, String tag, String toolbarTitle, int visible) {
        replaceFragment(fragment, tag);
        toolbarSetting(toolbarTitle, visible);
        viewPagerItemVisibility(visible);
    }


    // Fragment??? ??????
    public void replaceFragment(Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_layout, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }
    public void addFragment(Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_layout, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }
    // ?????? toolbar ?????? ??????
    private void toolbarSetting(String ??????, int gone) {
        textview_title.setText(??????);
        iv_logo.setVisibility(gone);
    }
    // ??????????????? ????????? viewpager ?????? ?????? ?????? 
    public void viewPagerItemVisibility(int visible) {
        sliderViewPager.setVisibility(visible);
        layoutIndicator.setVisibility(visible);
//        viewFadingEdge.setVisibility(visible);
    }


    // ???????????? ?????? ????????? HomeFragment??? ?????????
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (isBackHome()) {
            onBackHome();
        } else if(isContractFragments()) {
            onBackHomeFromContractPages();
        } else {
            onBackMenu();
        }
    }

    private void onBackHomeFromContractPages() {
        replaceFragment(fragment_home,"home");
    }

    private void onBackMenu() {
        replaceFragment(fragment_menu, "menu");
    }

    private boolean isBackHome() {
        String[] tags = {"home","menu","consulting", "notice"};
        String temp = null;

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            Log.d("MainActivity", String.valueOf(fragment));
            if (fragment.isVisible()) {
                for (int i = 0; i < tags.length; i++) {
                    if (fragment instanceof HomeFragment)
                        temp = tags[i];
                    else if (fragment instanceof MenuFragment)
                        temp = tags[i];
                    else if (fragment instanceof ConsultingFragment)
                        temp = tags[i];
                    else if (fragment instanceof NoticeFragment)
                        temp = tags[i];
                }
                break;
            }
        }

        return temp != null ? true : false;
    }
    private boolean isContractFragments() {
        String[] tags = {"write contract","how to write contract", "upload files"};
        String temp = null;

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            Log.d("MainActivity", String.valueOf(fragment));
            if (fragment.isVisible()) {
                for (int i = 0; i < tags.length; i++) {
                    if (fragment instanceof WriteContractFragment)
                        temp = tags[i];
                    else if (fragment instanceof HowToWriteContractFragment)
                        temp = tags[i];
                    else if(fragment instanceof UploadFilesFragment)
                        temp = tags[i];
                }
                break;
            }
        }
        return temp != null ? true : false;
    }

    private void onBackHome() {
        switch (mBottomNavigationView.getSelectedItemId()) {
            case R.id.menu:
                mBottomNavigationView.setSelectedItemId(R.id.home);
                break;
            case R.id.notice:
                mBottomNavigationView.setSelectedItemId(R.id.home);
                break;
            case R.id.consulting:
                mBottomNavigationView.setSelectedItemId(R.id.home);
                break;
            case R.id.home:
                finish();
                break;
            default:
                break;
        }
    }


    @Override
    public void onMenuButtonSelected(String ButtonId) {
        switch (ButtonId) {
            case "btn_accountsetting":
                viewAsFragmentChange(fragment_account_setting, "accountsetting", "??? ??????", View.GONE);
                break;
        }
    }

    private void setupIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(16, 8, 16, 8);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(this);
            indicators[i].setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.bg_indicator_inactive));
            indicators[i].setLayoutParams(params);
            layoutIndicator.addView(indicators[i]);
        }
        setCurrentIndicator(0);
    }

    private void setCurrentIndicator(int position) {
        int childCount = layoutIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutIndicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        this,
                        R.drawable.bg_indicator_active
                ));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        this,
                        R.drawable.bg_indicator_inactive
                ));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        checkRequestCode(requestCode, resultCode, data);
    }

    private void checkRequestCode(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable Intent data) {
        ArrayList<Integer> RequestCodes = new ArrayList<>();
        RequestCodes.add(PDF_FROM_WRITE_CONTRACT_FRAGMENT);
        RequestCodes.add(HWP_FROM_WRITE_CONTRACT_FRAGMENT);
        RequestCodes.add(IMG_FROM_WRITE_CONTRACT_FRAGMENT);

        if(RequestCodes.contains(requestCode)){
            fragmentOnActivityResult(requestCode, resultCode, data);
        }
    }

    private void fragmentOnActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable @Nullable Intent data) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("upload files");
        fragment.onActivityResult(requestCode, resultCode, data);
    }
}
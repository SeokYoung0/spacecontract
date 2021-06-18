package com.spaceplanning.app.spacecontract;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

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

    //Image Slider
    private ViewPager2 sliderViewPager;
    private LinearLayout layoutIndicator;
    private View viewFadingEdge;
    private int[] images = new int[]{
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3
    };

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

        //슬라이드 이미지
        sliderViewPager = findViewById(R.id.sliderViewPager);
        layoutIndicator = findViewById(R.id.layoutIndicators);
        viewFadingEdge = findViewById(R.id.viewFadingEdge);

        viewFadingEdge.setVisibility(View.GONE);
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

        // 계약서 작성법 버튼 및 계약하기 버튼

        bottomNavigationClick();
    }

    // BottomNavigation 클릭 시 동작
    private void bottomNavigationClick() {
        // BottomNaviagtion
        mBottomNavigationView = findViewById(R.id.bottomNavigationView);

        // 리스너 등록 하단 Navigation 별 Fragment 버튼 클릭시 Fragment이동
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        replaceFragment(fragment_home, "home");
                        toolbarSetting("Space 계약", View.VISIBLE);
                        viewPagerItemVisibility(View.VISIBLE);
                        break;
                    case R.id.consulting:

                        replaceFragment(fragment_consulting, "consulting");
                        toolbarSetting("상담", View.GONE);
                        viewPagerItemVisibility(View.GONE);
                        break;
                    case R.id.menu:
                        replaceFragment(fragment_menu, "menu");
                        toolbarSetting("메뉴", View.GONE);
                        viewPagerItemVisibility(View.GONE);
                        break;
                    case R.id.notice:
                        replaceFragment(fragment_notice, "notice");
                        toolbarSetting("알림", View.GONE);
                        viewPagerItemVisibility(View.GONE);
                        break;
                }
                return true;
            }
        });
    }


    // Fragment별 이동
    public void replaceFragment(Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_layout, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }
    // 상단 toolbar 설정 변경
    private void toolbarSetting(String 상담, int gone) {
        textview_title.setText(상담);
        iv_logo.setVisibility(gone);
    }
    // 프래그먼트 변경시 viewpager 화면 표시 여부 
    private void viewPagerItemVisibility(int visible) {
        sliderViewPager.setVisibility(visible);
        layoutIndicator.setVisibility(visible);
        viewFadingEdge.setVisibility(visible);
    }


    // 뒤로가기 버튼 클릭시 HomeFragment로 돌아옴
    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        if (isBackHome()) {
            onBackHome();
        } else {
            onBackMenu();
        }
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
                replaceFragment(fragment_account_setting, "accountsetting");
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
}
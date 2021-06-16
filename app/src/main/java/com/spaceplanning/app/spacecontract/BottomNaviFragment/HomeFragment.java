package com.spaceplanning.app.spacecontract.BottomNaviFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.spaceplanning.app.spacecontract.HomeFragmentInList.GetFileActivity;
import com.spaceplanning.app.spacecontract.MainActivity;
import com.spaceplanning.app.spacecontract.R;

public class HomeFragment extends Fragment {
    private CardView cv_gocontract;
    private CardView cv_pdffilefind;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        cv_gocontract = (CardView) view.findViewById(R.id.cv_gocontract);
        cv_gocontract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cv_pdffilefind = (CardView)view.findViewById(R.id.cv_pdffilefind);
        cv_pdffilefind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GetFileActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}

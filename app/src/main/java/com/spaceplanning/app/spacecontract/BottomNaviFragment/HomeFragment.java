package com.spaceplanning.app.spacecontract.BottomNaviFragment;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spaceplanning.app.spacecontract.R;

public class HomeFragment extends Fragment {
    private CardView cv_gocontract;
    private CardView cv_pdffilefind;
    private IOnClickHomeItemListener mListener;
    public void setOnClickHomeItemListener(IOnClickHomeItemListener listener){
        mListener = listener;
    }


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
        return view;
    }
}

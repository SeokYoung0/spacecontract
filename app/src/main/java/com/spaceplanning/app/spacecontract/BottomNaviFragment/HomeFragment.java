package com.spaceplanning.app.spacecontract.BottomNaviFragment;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.spaceplanning.app.spacecontract.MainActivity;
import com.spaceplanning.app.spacecontract.R;

import org.jetbrains.annotations.NotNull;

public class HomeFragment extends Fragment {
    private CardView cv_gocontract;
    private CardView cv_how_to_use_contract;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        cv_gocontract = (CardView) view.findViewById(R.id.cv_gocontract);
        cv_how_to_use_contract = (CardView) view.findViewById(R.id.cv_how_to_use_contract);
        cv_gocontract.setOnClickListener(homeItemClickEvent());
        cv_how_to_use_contract.setOnClickListener(homeItemClickEvent());
        return view;
    }

    @NotNull
    private View.OnClickListener homeItemClickEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.cv_gocontract:
                        Toast.makeText(getContext(), "계약서 작성", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.cv_how_to_use_contract:
                        Toast.makeText(getContext(), "계약서 작성법", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }
}

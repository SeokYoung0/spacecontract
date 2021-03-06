package com.spaceplanning.app.spacecontract.BottomNaviFragment;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spaceplanning.app.spacecontract.HomeFragmentInList.HowToWriteContractFragment;
import com.spaceplanning.app.spacecontract.HomeFragmentInList.UploadFilesFragment;
import com.spaceplanning.app.spacecontract.MainActivity;
import com.spaceplanning.app.spacecontract.R;
import com.spaceplanning.app.spacecontract.HomeFragmentInList.WriteContractFragment;

import org.jetbrains.annotations.NotNull;

public class HomeFragment extends Fragment {
    private CardView cv_gocontract;
    private CardView cv_how_to_use_contract;
    private CardView cv_save_file;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        cv_save_file = (CardView) view.findViewById(R.id.cv_save_file);
        cv_how_to_use_contract = (CardView) view.findViewById(R.id.cv_how_to_use_contract);
        cv_gocontract = (CardView)view.findViewById(R.id.cv_gocontract);
        cv_save_file.setOnClickListener(homeItemClickEvent());
        cv_how_to_use_contract.setOnClickListener(homeItemClickEvent());
        cv_gocontract.setOnClickListener(homeItemClickEvent());

        ((MainActivity)getActivity()).viewPagerItemVisibility(View.VISIBLE);

        return view;
    }

    @NotNull
    private View.OnClickListener homeItemClickEvent() {
        Fragment fragment_write_contract = new WriteContractFragment();
        Fragment fragment_how_to_write_contract = new HowToWriteContractFragment();
        Fragment fragment_upload_files = new UploadFilesFragment();
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.cv_save_file:
                        ((MainActivity)getActivity()).replaceFragment(fragment_upload_files, "upload files");
                        break;
                    case R.id.cv_how_to_use_contract:
                        ((MainActivity)getActivity()).replaceFragment(fragment_how_to_write_contract, "how to write contract");
                        break;
                    case R.id.cv_gocontract:
                        ((MainActivity)getActivity()).replaceFragment(fragment_write_contract, "write contract");
                        break;
                }
            }
        };
    }



}

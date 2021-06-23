package com.spaceplanning.app.spacecontract.BottomNaviFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spaceplanning.app.spacecontract.R;

public class ConsultingFragment extends Fragment {

    private CardView cardview_1;
    private CardView cardview_2;
    private CardView cardview_3;
    private CardView cardview_4;
    private CardView cardview_5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consulting, container, false);

        cardview_1 = (CardView)view.findViewById(R.id.cardview_1);
        cardview_2 = (CardView)view.findViewById(R.id.cardview_2);
        cardview_3 = (CardView)view.findViewById(R.id.cardview_3);
        cardview_4 = (CardView)view.findViewById(R.id.cardview_4);
        cardview_5 = (CardView)view.findViewById(R.id.cardview_5);

        CardViewClickEvent(cardview_1, "https://www.klac.or.kr/legalstruct/consultationGuidance.do");
        CardViewClickEvent(cardview_2, "http://korea-lawyer.com/");
        CardViewClickEvent(cardview_3, "https://map.naver.com/");
        CardViewClickEvent(cardview_4, "https://www.bizno.net/");
        CardViewClickEvent(cardview_5, "http://www.kosca.or.kr/KS/KS020101.asp?area=00");
        return view;
    }

    private void CardViewClickEvent(CardView cardview, String s) {
        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(s));
                startActivity(intent);
            }
        });
    }
}
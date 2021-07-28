package com.spaceplanning.app.spacecontract.HomeFragmentInList;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.spaceplanning.app.spacecontract.MainActivity;
import com.spaceplanning.app.spacecontract.R;
import com.spaceplanning.app.spacecontract.network.ContractData;
import com.spaceplanning.app.spacecontract.network.ContractResponse;
import com.spaceplanning.app.spacecontract.network.RealPathFromURI;
import com.spaceplanning.app.spacecontract.network.RetrofitClient;
import com.spaceplanning.app.spacecontract.network.ServiceApi;

import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteContractFragment extends Fragment {

    private RealPathFromURI mRealPathFromURI;
    private List<String> tempData;
    private String TAG = "WritecontractFragment";

    private EditText person1_name;
    private EditText person1_phonenumber;
    private EditText person1_email;
    private EditText person2_name;
    private EditText person2_phonenumber;
    private EditText person2_email;
    private Button upload_contract;
    private WebView contract_viewer;
    private Button btn_formid;

    String formId = "60b88164f07a5cf0004c5e13";

    private ServiceApi service;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRealPathFromURI = new RealPathFromURI();
        tempData = new ArrayList<>();
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        service = RetrofitClient.getClient().create(ServiceApi.class);
        View view = inflater.inflate(R.layout.fragment_write_contract, container, false);
        ((MainActivity)getActivity()).viewPagerItemVisibility(View.GONE);

        person1_name = (EditText)view.findViewById(R.id.person1_name);
        person1_phonenumber = (EditText)view.findViewById(R.id.person1_phonenumber);
        person1_email = (EditText)view.findViewById(R.id.person1_email);
        person2_name = (EditText)view.findViewById(R.id.person2_name);
        person2_phonenumber = (EditText)view.findViewById(R.id.person2_phonenumber);
        person2_email = (EditText)view.findViewById(R.id.person2_email);
        upload_contract = (Button)view.findViewById(R.id.upload_contract);
        contract_viewer = (WebView)view.findViewById(R.id.contract_viewer);
        btn_formid = (Button)view.findViewById(R.id.btn_formid);


        upload_contract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(isCheckValue()){
                   String name1 = String.valueOf(person1_name.getText());
                   String phone1 = String.valueOf(person1_phonenumber.getText());
                   String email1 = String.valueOf(person1_email.getText());

                   String name2 = String.valueOf(person2_name.getText());
                   String phone2= String.valueOf(person2_phonenumber.getText());
                   String email2 = String.valueOf(person2_email.getText());

                   ContractData data = new ContractData(name1 ,phone1,email1,name2,phone2,email2,
                        formId,
                        "20210712");
                   SendFormData(data);
               }else {
                   Log.d(TAG, "No send");
               }
            }
        });

        btn_formid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "click", Toast.LENGTH_SHORT).show();
                String str = null;
                try {
                    str = "formId=" + URLEncoder.encode(formId, "UTF-8");
                    contract_viewer.postUrl("http://172.16.2.14:3000/api/eform/preview", str.getBytes());
                    contract_viewer.getSettings().setUseWideViewPort(true);
                    contract_viewer.getSettings().setLoadWithOverviewMode(true);
                    contract_viewer.getSettings().setBuiltInZoomControls(true);
                    contract_viewer.getSettings().setSupportZoom(true);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    private Boolean isCheckValue() {
        EditText[] texts = {person1_name, person1_phonenumber, person2_name, person2_phonenumber};
        HashMap<EditText, String> textmap = new HashMap<EditText, String>();
        textmap.put(person1_name, "계약인 이름(소비자)");
        textmap.put(person1_phonenumber, "계약인 전화번호(소비자)");
        textmap.put(person2_name, "계약인 이름(업체)");
        textmap.put(person2_phonenumber, "계약인 전화번호(업체)");

        for (EditText text: texts)
        {
            if (text.getText().length() <= 0) {
                String textValue= textmap.get(text);
                Toast.makeText(getContext(), textValue + "는 필수 입력 사항입니다.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private void SendFormData(ContractData data) {
        // 4개+ id값 +파기날짜 = 6개, 6개 +id값 +파기날짜 = 8개

        service.sendContract(data).enqueue(new Callback<ContractResponse>() {
            @Override
            public void onResponse(Call<ContractResponse> call, Response<ContractResponse> response) {
                ContractResponse result = response.body();
                Log.d(TAG, result.getMessage() +" : reuslt");
            }

            @Override
            public void onFailure(Call<ContractResponse> call, Throwable t) {
                Log.d(TAG, t.getMessage()+ ": Error message");
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}

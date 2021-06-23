package com.spaceplanning.app.spacecontract.HomeFragmentInList;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
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
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.loader.content.Loader;

import com.spaceplanning.app.spacecontract.MainActivity;
import com.spaceplanning.app.spacecontract.R;
import com.spaceplanning.app.spacecontract.network.AttachedFile;
import com.spaceplanning.app.spacecontract.network.AttachedFileData;
import com.spaceplanning.app.spacecontract.network.AttachedFileResponse;
import com.spaceplanning.app.spacecontract.network.RealPathFromURI;
import com.spaceplanning.app.spacecontract.network.RetrofitClient;
import com.spaceplanning.app.spacecontract.network.ServiceApi;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteContractFragment extends Fragment {

    private RealPathFromURI mRealPathFromURI;
    private List<String> tempData;

    private EditText person1_name;
    private EditText person1_phonenumber;
    private EditText person1_email;
    private EditText person2_name;
    private EditText person2_phonenumber;
    private EditText person2_email;
    private Button upload_contract;
    private WebView contract_viewer;

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


        upload_contract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckDefault();
            }
        });
        return view;
    }

    private void CheckDefault() {
        EditText[] texts = {person1_name, person1_phonenumber, person2_name, person2_phonenumber};
        HashMap<EditText, String> textmap = new HashMap<EditText, String>();
        textmap.put(person1_name, "계약인 이름(소비자)");
        textmap.put(person1_phonenumber, "계약인 전화번호(소비자)");
        textmap.put(person2_name, "계약인 이름(업체)");
        textmap.put(person2_phonenumber, "계약인 전화번호(업체)");

        for (EditText text: texts)
        {
            Log.d("Tag", "hello :" + text.getText());
            if (text.getText().length() <= 0) {
                String textValue= textmap.get(text);
                Toast.makeText(getContext(), textValue + "는 필수 입력 사항입니다.", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}

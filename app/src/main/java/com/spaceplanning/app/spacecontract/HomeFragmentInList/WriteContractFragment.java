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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

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
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteContractFragment extends Fragment {

    private RealPathFromURI mRealPathFromURI;
    private List<String> tempData;

    private EditText mFileName_1;
    private EditText mFileName_2;
    private EditText mFileName_3;
    private Button mUploadFileBtn_1;
    private Button mUploadFileBtn_2;
    private Button mUploadFileBtn_3;
    private Button mUpload_files;

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

        mFileName_1 = view.findViewById(R.id.upload_file_name_1);
        mFileName_2 = view.findViewById(R.id.upload_file_name_2);
        mFileName_3 = view.findViewById(R.id.upload_file_name_3);
        mUploadFileBtn_1 = view.findViewById(R.id.upload_file_btn_1);
        mUploadFileBtn_2 = view.findViewById(R.id.upload_file_btn_2);
        mUploadFileBtn_3 = view.findViewById(R.id.upload_file_btn_3);

        mUploadFileBtn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_upload = new Intent();
                intent_upload.setType("application/pdf");
                intent_upload.setAction(Intent.ACTION_GET_CONTENT);
                getActivity().startActivityForResult(intent_upload, 1);
            }
        });
        mUploadFileBtn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_upload = new Intent();
                intent_upload.setType("application/pdf");
                intent_upload.setAction(Intent.ACTION_GET_CONTENT);
                getActivity().startActivityForResult(intent_upload, 2);
            }
        });
        mUploadFileBtn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_upload = new Intent();
                intent_upload.setType("application/pdf");
                intent_upload.setAction(Intent.ACTION_GET_CONTENT);
                getActivity().startActivityForResult(intent_upload, 3);
            }
        });

        mUpload_files = view.findViewById(R.id.upload_files);
        mUpload_files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttachedFileData uploadData = new AttachedFileData(tempData);
                upload_files(uploadData);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri returnUri;
        Cursor returnCursor;
        int nameIndex;
       switch (requestCode) {
           case 1:
               returnUri = data.getData();
               returnCursor =
                       getActivity().getContentResolver().query(returnUri, null, null, null, null);
               nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
               returnCursor.moveToFirst();

               mFileName_1.setText(returnCursor.getString(nameIndex));
               String file_1_path = mRealPathFromURI.getRealPathFromURI(getActivity(),returnUri);
               Log.e("WriteContractFragment", file_1_path);
               tempData.add(file_1_path);
               returnCursor.close();
               break;
           case 2:
               returnUri = data.getData();
               returnCursor =
                       getActivity().getContentResolver().query(returnUri, null, null, null, null);
               nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
               returnCursor.moveToFirst();

               mFileName_2.setText(returnCursor.getString(nameIndex));
               String file_2_path = mRealPathFromURI.getRealPathFromURI(getActivity(),returnUri);
               tempData.add(file_2_path);
               returnCursor.close();
               break;
           case 3:
               returnUri = data.getData();
               returnCursor =
                       getActivity().getContentResolver().query(returnUri, null, null, null, null);
               nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
               returnCursor.moveToFirst();

               mFileName_3.setText(returnCursor.getString(nameIndex));
               String file_3_path = mRealPathFromURI.getRealPathFromURI(getActivity(),returnUri);
               tempData.add(file_3_path);
               returnCursor.close();
               break;
       }
    }

    public void upload_files(AttachedFileData data) {
        service = RetrofitClient.getClient().create(ServiceApi.class);
        service.attachedFiles(data).enqueue(new Callback<AttachedFileResponse>(){

            @Override
            public void onResponse(Call<AttachedFileResponse> call, Response<AttachedFileResponse> response) {
                Log.e("WriteContractFragment", response.message());
            }

            @Override
            public void onFailure(Call<AttachedFileResponse> call, Throwable t) {
                Log.e("WriteContractFragment", t.getMessage());
            }
        });
    }

}

package com.spaceplanning.app.spacecontract.HomeFragmentInList;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.spaceplanning.app.spacecontract.MainActivity;
import com.spaceplanning.app.spacecontract.R;
import com.spaceplanning.app.spacecontract.network.AttachedFileData;
import com.spaceplanning.app.spacecontract.network.AttachedFileResponse;
import com.spaceplanning.app.spacecontract.network.RealPathFromURI;
import com.spaceplanning.app.spacecontract.network.RetrofitClient;
import com.spaceplanning.app.spacecontract.network.ServiceApi;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UploadFilesFragment extends Fragment {


    private static final String TAG = "UploadFilesFragment";

    public UploadFilesFragment() {
        // Required empty public constructor
    }

    private RealPathFromURI mRealPathFromURI;
    private HashMap<String, RequestBody> tempData;
    private Uri returnUri;

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
        tempData = new HashMap<String, RequestBody>();


    }

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_upload_files, container, false);
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
                intent_upload.setType("*/*");
                intent_upload.setAction(Intent.ACTION_OPEN_DOCUMENT);
                getActivity().getIntent().putExtra("Position", intent_upload);
                getActivity().startActivityForResult(intent_upload, 1);
            }
        });
        mUploadFileBtn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_upload = new Intent();
                intent_upload.setType("application/pdf");
                intent_upload.setAction(Intent.ACTION_OPEN_DOCUMENT);
                getActivity().startActivityForResult(intent_upload, 2);
            }
        });
        mUploadFileBtn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_upload = new Intent();
                intent_upload.setType("application/pdf");
                intent_upload.setAction(Intent.ACTION_OPEN_DOCUMENT);
                getActivity().startActivityForResult(intent_upload, 3);
            }
        });

        mUpload_files = view.findViewById(R.id.upload_files);
        mUpload_files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                upload_files(tempData);
                postAttachment(new File(returnUri.getPath()));
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Cursor returnCursor;
        int nameIndex;
        String path;
        Uri uri;


        switch (requestCode) {
            case 1:
                String[] proj = {};

                returnUri = data.getData();
                Log.d(TAG, "file Path check1 : " + DocumentsContract.getDocumentId(returnUri));
                Log.d(TAG, "file Path check1 : " + Environment.DIRECTORY_DOWNLOADS);

                returnCursor =
                        getActivity().getContentResolver().query(returnUri, null , null, null, null);

                nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);

                returnCursor.moveToFirst();
                mFileName_1.setText(returnCursor.getString(nameIndex));

//                File file_1_path = new File();
                File file = new File(data.getData().toString());
                RequestBody date_file_1 = RequestBody.create(file , MediaType.parse("application/pdf"));
                tempData.put("file_1.pdf",date_file_1);
                returnCursor.close();
                break;
            case 2:
                returnUri = data.getData();
                Log.d(TAG, "file Path1 : " + data.getData());
                returnCursor =
                        getActivity().getContentResolver().query(returnUri, null, null, null, null);
                nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                returnCursor.moveToFirst();
                mFileName_2.setText(returnCursor.getString(nameIndex));

//                File file_1_path = new File();
                Log.d(TAG, "file Path3 : " + returnUri.getLastPathSegment());
                RequestBody date_file_2 = RequestBody.create(returnUri.getLastPathSegment(), MediaType.parse("application/pdf"));
                tempData.put("file_2.pdf",date_file_2);
                returnCursor.close();
                break;
            case 3:

                break;
        }
    }


    public void postAttachment(File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/application/pdf"), file.getPath());
        MultipartBody.Part multipartBody =MultipartBody.Part.createFormData("files",file.getName(),requestFile);
        service = RetrofitClient.getClient().create(ServiceApi.class);
        service.postAttachment(multipartBody).enqueue(new Callback<AttachedFileResponse>() {
            @Override
            public void onResponse(Call<AttachedFileResponse> call, Response<AttachedFileResponse> response) {
                Log.d(TAG, String.valueOf(response.body().getMessage()));
                Log.e(TAG, response.message());
            }

            @Override
            public void onFailure(Call<AttachedFileResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });

    }
}
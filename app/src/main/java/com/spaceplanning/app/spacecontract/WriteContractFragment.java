package com.spaceplanning.app.spacecontract;

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

import com.spaceplanning.app.spacecontract.network.RealPathFromURI;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class WriteContractFragment extends Fragment {

    private RealPathFromURI mRealPathFromURI;
    private EditText mFileName_1;
    private EditText mFileName_2;
    private EditText mFileName_3;
    private Button mUploadFileBtn_1;
    private Button mUploadFileBtn_2;
    private Button mUploadFileBtn_3;


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRealPathFromURI = new RealPathFromURI();
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
               returnCursor.close();
               break;
           case 2:
               returnUri = data.getData();
               returnCursor =
                       getActivity().getContentResolver().query(returnUri, null, null, null, null);
               nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
               returnCursor.moveToFirst();

               mFileName_2.setText(returnCursor.getString(nameIndex));
               returnCursor.close();
               break;
           case 3:
               returnUri = data.getData();
               returnCursor =
                       getActivity().getContentResolver().query(returnUri, null, null, null, null);
               nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
               returnCursor.moveToFirst();

               mFileName_3.setText(returnCursor.getString(nameIndex));
               returnCursor.close();
               break;
       }
    }

}

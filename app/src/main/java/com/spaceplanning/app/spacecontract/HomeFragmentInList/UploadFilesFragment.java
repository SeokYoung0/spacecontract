package com.spaceplanning.app.spacecontract.HomeFragmentInList;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.documentfile.provider.DocumentFile;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

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
    private RequestBody requestFile;
    private MultipartBody.Part[] multipartBody = new MultipartBody.Part[2];
    private ArrayList<AttachedFileData> tempData;
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
        tempData = new ArrayList<>();


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
                try {
                    postAttachment(tempData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                getFileData(data, mFileName_1);
                break;
            case 2:
                getFileData(data, mFileName_2);
                break;
            case 3:
                break;
        }
    }

    private void getFileData(@org.jetbrains.annotations.Nullable Intent data, EditText editText) {
        Cursor returnCursor;
        int nameIndex;

        returnUri = data.getData();
        returnCursor = getActivity().getContentResolver().query(
                returnUri, null , null, null, null);

        nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        AttachedFileData file = createAttachedFileData(returnCursor, nameIndex);
        tempData.add(file);

        editText.setText(returnCursor.getString(nameIndex));
        returnCursor.close();
        Log.d(TAG, "File Path! : "+ createCopyAndReturnRealPath(getActivity(), returnUri));
    }

    @NotNull
    private AttachedFileData createAttachedFileData(Cursor returnCursor, int nameIndex) {
        String fileName = returnCursor.getString(nameIndex);
        String fileType = getActivity().getContentResolver().getType(returnUri);
        AttachedFileData file = new AttachedFileData(fileName,fileType,returnUri);
        return file;
    }

    private String readTextFromUri(Uri uri) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }

    public void postAttachment(ArrayList<AttachedFileData> files) throws IOException {
        int count = 0;

        for (AttachedFileData data : files) {
            requestFile = RequestBody.create(MediaType.parse(data.getFileType()), new File(createCopyAndReturnRealPath(getActivity(), data.getFileUri())));
            multipartBody[count] = MultipartBody.Part.createFormData("files",data.getFileName(),requestFile);
            count++;
        }
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


    // 절대경로 파악할 때 사용된 메소드
    @Nullable
    public static String createCopyAndReturnRealPath(@NonNull Context context, @NonNull Uri uri) {
        final ContentResolver contentResolver = context.getContentResolver();
        if (contentResolver == null)
            return null;
        // 파일 경로를 만듬
        String filePath = context.getApplicationInfo().dataDir + File.separator
                + System.currentTimeMillis();
        File file = new File(filePath);
        try {
            // 매개변수로 받은 uri 를 통해  이미지에 필요한 데이터를 불러 들인다.
            InputStream inputStream = contentResolver.openInputStream(uri);
            if (inputStream == null)
                return null;
            // 이미지 데이터를 다시 내보내면서 file 객체에  만들었던 경로를 이용한다.
            OutputStream outputStream = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0)
                outputStream.write(buf, 0, len);
            outputStream.close();
            inputStream.close();
        } catch (IOException ignore) {
            return null;
        }
        return file.getAbsolutePath();
    }
}
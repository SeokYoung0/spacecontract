package com.spaceplanning.app.spacecontract.HomeFragmentInList;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.spaceplanning.app.spacecontract.LoginActivity;
import com.spaceplanning.app.spacecontract.R;
import com.spaceplanning.app.spacecontract.network.RetrofitClient;
import com.spaceplanning.app.spacecontract.network.ServiceApi;

import java.io.File;

import javax.xml.transform.Result;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetFileActivity extends AppCompatActivity {
    private Button btn_fileget;
    private TextView tv_fileset;
    // Request code for selecting a PDF document.
    private static final int PICK_PDF_FILE = 2;
    private Uri uri;
    private ServiceApi service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_file);
        service = RetrofitClient.getClient().create(ServiceApi.class);

        btn_fileget = findViewById(R.id.btn_fileget);
        btn_fileget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFile(uri);
                }
        });

        tv_fileset = findViewById(R.id.tv_fileset);

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == PICK_PDF_FILE && resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                tv_fileset.setText(uri.toString());
                File file = new File("content:/com.google.android.apps.docs.storage/document/acc%3D1%3Bdoc%3Dencoded%3DJaPuce2t5148YDHofN0s1Lm%2FUxBU%2Bp2byAszKoA0H2DGq8d5g%2F34oC8%3D.pdf");
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestFile);
                Call<Result> resultCall =service.uploadImage(body);
                resultCall.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        Toast.makeText(GetFileActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Toast.makeText(GetFileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("IID",  t.getMessage());
                    }
                });
                // Perform operations on the document using its URI.
            }
        }
    }
    private void openFile(Uri pickerInitialUri) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);
        Log.d("tag", String.valueOf(intent));
        startActivityForResult(intent, PICK_PDF_FILE);
    }
}
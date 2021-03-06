package com.moringaschool.e_commerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.datatransport.runtime.scheduling.jobscheduling.Uploader;
import com.google.android.gms.tasks.OnFailureListener;
import com.squareup.picasso.BuildConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class SellActivity<StorageReference> extends AppCompatActivity {
    private Button mSave,mCapture;
    private ImageView result;
    private Uri filePath;
    private TextView Pname,Desc,price;
    private Spinner cate;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    String mCurrentPhotoPath;
    private static final String SHARED_PROVIDER_AUTHORITY = BuildConfig.APPLICATION_ID + ".myfileprovider";
    private static final String SHARED_FOLDER = "shared";

    private final int PICK_IMAGE_REQUEST = 71;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        setupUI();

        mCapture=findViewById(R.id.capture);
        mSave=findViewById(R.id.save);
        result=findViewById(R.id.imageView);
        Spinner spinner = (Spinner) findViewById(R.id.p_cate);

        // Initializing a String Array
        String[] category = new String[]{
                "TECH",
                "MOTORS",
                "FASHION",
                "ENTERTAINMENT",
                "LEISURE",
                "OTHERS"
        };
        final List<String> CategoryList = new ArrayList<>(Arrays.asList(category));
        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.support_simple_spinner_dropdown_item,CategoryList);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);


        mCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });



        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateAllFeilds();
                String pname = Pname.getText().toString();
                String desc = Desc.getText().toString();
                String p_price = price.getText().toString();
                String p_cate =cate.getSelectedItem().toString();

                Toast.makeText(SellActivity.this, "Product Uploded Successfully!!!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SellActivity.this,MainActivity.class));
                // uploadImage();
            }
        });
    }


    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            result.setImageBitmap(imageBitmap);
        }
    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    private void uploadImage(Object storageReference){
        if(filePath != null)
        {
            Log.i("filepath_error!",filePath.toString());
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

//            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
//            Log.i("Storage reference!",ref.toString());
//            ref.putFile(filePath)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            progressDialog.dismiss();
//                            Toast.makeText(SellActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            progressDialog.dismiss();
//                            Toast.makeText(SellActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(Uploader.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
//                                    .getTotalByteCount());
//                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
//                        }
//                    });
        }
        Log.i("No File Path!",filePath.toString());
    }


    private void setupUI()
    {
        Pname = findViewById(R.id.p_name);
        Desc = findViewById(R.id.p_desc);
        price = findViewById(R.id.p_price);
        cate = findViewById(R.id.p_cate);
    }

    private boolean ValidateAllFeilds(){
        boolean result = false;
        String pname = Pname.getText().toString();
        String desc = Desc.getText().toString();
        String p_price = price.getText().toString();
        String p_cate =cate.getSelectedItem().toString();


        if(pname.isEmpty()|| desc.isEmpty()|| p_price.isEmpty() || p_cate.isEmpty())
        {
            Toast.makeText(this,"Please enter alll the details", Toast.LENGTH_SHORT).show();
        }
        else
        {
            result =true;
        }

        return result;
    }
}


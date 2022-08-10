package com.example.vinci;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

import utils.MyEncrypter;

public class fragmentOnbording3 extends Fragment {
    Intent myFileIntent;

    private static final String FILE_NAME_ENC ="encrypted_image" ;
    private static final String FILE_NAME_DEC ="image_dec.png" ;

    int SELECT = 1;
    Button choose2;
    Button btn_decrypt;

    InputStream is;
    ImageView imageView;
    File myDir;


    String my_key="E3CP7VdFGE5eJkra";
    String my_spec_key="JXQKVYXKBUb8l8VY";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = (ViewGroup) inflater.inflate(R.layout.fragment_onbording3, container, false);

        choose2 = (Button) view.findViewById(R.id.choose2);
        btn_decrypt = (Button) view.findViewById(R.id.btn_decrypt);
        imageView = view.findViewById(R.id.test2);

        myDir=new File(Environment.getExternalStorageDirectory().toString()+"/saved_images");
        //File outputFileEnc =new File(myDir,FILE_NAME_ENC);

        choose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Working", Toast.LENGTH_SHORT).show();
                // image picker as bitmap
                myFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                myFileIntent.setType("*/*");

                startActivityForResult(Intent.createChooser(myFileIntent, "pick encfile"), 1);
            }
        });




        btn_decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File outputFileDec =new File(myDir,FILE_NAME_DEC);
                //File encFile = (file)inte
                File encFile =new File(myDir,FILE_NAME_ENC);
                //Toast.makeText(getActivity(), "nice", Toast.LENGTH_SHORT).show();
                try {
                    MyEncrypter.decryptToFile(my_key,my_spec_key,new FileInputStream(encFile),new FileOutputStream(outputFileDec));
                    Toast.makeText(getActivity(), "Decrypted", Toast.LENGTH_SHORT).show();
                    imageView.setImageURI(Uri.fromFile(outputFileDec));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK && requestCode == 1) {

            assert data != null;
            Uri uri = data.getData();
            String filePath = data.getData().getPath();
//            try {
//
//                is=new ByteArrayInputStream(data.toByteArray());
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//
            //testimage.setImageURI(imageUri);


        }
    }
}

